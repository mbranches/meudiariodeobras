package com.branches.external.stripe;

import com.branches.assinaturadeplano.domain.AssinaturaDePlanoEntity;
import com.branches.assinaturadeplano.domain.AssinaturaHistoricoEntity;
import com.branches.assinaturadeplano.domain.IntencaoDePagamentoEntity;
import com.branches.assinaturadeplano.domain.enums.AssinaturaStatus;
import com.branches.assinaturadeplano.domain.enums.EventoHistoricoAssinatura;
import com.branches.assinaturadeplano.repository.AssinaturaDePlanoRepository;
import com.branches.assinaturadeplano.repository.AssinaturaHistoricoRepository;
import com.branches.assinaturadeplano.repository.IntencaoDePagamentoRepository;
import com.branches.exception.NotFoundException;
import com.branches.plano.domain.PlanoEntity;
import com.branches.plano.repository.PlanoRepository;
import com.branches.plano.service.GetPlanoByStripeIdService;
import com.stripe.model.*;
import com.stripe.model.checkout.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Transactional
@Log4j2
@RequiredArgsConstructor
@Service
public class StripeEventsHandlerService {
    private final GetPlanoByStripeIdService getPlanoByStripeIdService;
    private final AssinaturaHistoricoRepository assinaturaHistoricoRepository;
    private final AssinaturaDePlanoRepository assinaturaDePlanoRepository;
    private static final ZoneId TIMEZONE_SP = ZoneId.of("America/Sao_Paulo");
    private final PlanoRepository planoRepository;
    private final IntencaoDePagamentoRepository intencaoDePagamentoRepository;

    public void handle(Event event) {
        switch (event.getType()) {
            case "invoice.paid" -> handleInvoicePaid(event);

            case "invoice.payment_failed" -> handleInvoicePaymentFailed(event);

            case "checkout.session.completed" -> handleCheckoutSessionCompleted(event);

            case "customer.subscription.updated" -> handleSubscriptionUpdated(event);

            case "customer.subscription.deleted" -> handleSubscriptionDeleted(event);

            default -> log.info("Evento Stripe não implementado: {}", event.getType());
        }
    }

    private void handleCheckoutSessionCompleted(Event event) {
        Session session = (Session) event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> new NotFoundException("Session não encontrada no evento de checkout.session.completed"));
        String sessionId = session.getId();
        log.info("Processando evento de checkout.session.completed para sessionId={}", sessionId);

        Subscription subscription;
        try {
            subscription = Subscription.retrieve(session.getSubscription());
        } catch (Exception e) {
            log.error("Erro ao recuperar subscription do Stripe para sessionId={}. Detalhes do erro: {}", sessionId, e.getMessage());
            throw new NotFoundException("Assinatura do Stripe não encontrada para a sessão: " + sessionId);
        }

        String subscriptionId = subscription.getId();

        IntencaoDePagamentoEntity intencaoDePagamentoEntity = intencaoDePagamentoRepository.findByStripeSessionId(sessionId)
                .orElseThrow(() -> new NotFoundException("Intenção de pagamento não encontrada para a sessão: " + sessionId));

        PlanoEntity plano = planoRepository.findById(intencaoDePagamentoEntity.getPlanoId())
                .orElseThrow(() -> new NotFoundException("Plano não encontrado para o ID: " + intencaoDePagamentoEntity.getPlanoId()));

        intencaoDePagamentoEntity.concluir();

        Long tenantId = intencaoDePagamentoEntity.getTenantId();
        AssinaturaDePlanoEntity assinatura = AssinaturaDePlanoEntity.builder()
                .status(AssinaturaStatus.PENDENTE)
                .stripeSubscriptionId(subscriptionId)
                .plano(plano)
                .dataInicio(now())
                .dataFim(plano.calcularDataFim(now()))
                .intencaoDePagamento(intencaoDePagamentoEntity)
                .tenantId(tenantId)
                .build();


        AssinaturaDePlanoEntity saved = assinaturaDePlanoRepository.save(assinatura);

        registrarEventoAssinatura(saved, EventoHistoricoAssinatura.CRIACAO);

        log.info("Assinatura criada com sucesso para a sessão: {}", sessionId);
    }

    private String getPriceIdOfSubscription(Subscription subscription) {
        return subscription.getItems().getData().stream()
                .map(SubscriptionItem::getPrice)
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Price da assinatura não encontrado. SubscriptionId={}", subscription.getId());
                    return new NotFoundException("Price da assinatura não encontrado para SubscriptionId=" + subscription.getId());
                })
                .getId();
    }

    private void handleInvoicePaid(Event event) {
        Invoice invoice = (Invoice) event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> {
                    log.error("Invoice não encontrada no evento de invoice.paid");
                    return new NotFoundException("Invoice não encontrada no evento pago");
                });

        log.info("Invoice paga: {}", invoice.getId());

        String subscriptionId = invoice.getParent().getSubscriptionDetails().getSubscription();

        AssinaturaDePlanoEntity assinatura = assinaturaDePlanoRepository.findByStripeSubscriptionId(subscriptionId)
                .orElseThrow(() -> {
                    log.error("Assinatura não encontrada para subscriptionId={}", subscriptionId);
                    return new NotFoundException("Assinatura não encontrada para subscriptionId=" + subscriptionId);
                });

        LocalDate dataFimCicloAtual = assinatura.getPlano().calcularDataFim(now());
        assinatura.ativar(dataFimCicloAtual);
        assinaturaDePlanoRepository.save(assinatura);

        log.info("Cobrança marcada como paga para a invoice: {}. Assinatura ativada: {}", invoice.getId(), assinatura.getId());
    }

    private LocalDate now() {
        return Instant.now().atZone(TIMEZONE_SP).toLocalDate();
    }

    private void handleInvoicePaymentFailed(Event event) {
        log.info("Processando evento de invoice.payment_failed do Stripe");
        Invoice invoice = (Invoice) event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> {
                    log.error("Invoice não encontrada no evento de invoice.payment_failed");
                    return new NotFoundException("Invoice não encontrada no evento de falha de pagamento");
                });

        log.info("Pagamento falhou para a invoice: {}", invoice.getId());

        String subscriptionId = invoice.getParent().getSubscriptionDetails().getSubscription();

        AssinaturaDePlanoEntity assinaturaDePlanoEntity = assinaturaDePlanoRepository.findByStripeSubscriptionId(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Assinatura não encontrada para subscriptionId=" + subscriptionId));

        if (assinaturaDePlanoEntity.isPendente()) {
            assinaturaDePlanoEntity.definirNaoFinalizada();

            assinaturaDePlanoRepository.save(assinaturaDePlanoEntity);
        }
    }

    private void registrarEventoAssinatura(AssinaturaDePlanoEntity assinatura, EventoHistoricoAssinatura evento) {
        AssinaturaHistoricoEntity historico = new AssinaturaHistoricoEntity(assinatura.getTenantId());
        historico.registrarEvento(assinatura, evento);
        assinaturaHistoricoRepository.save(historico);
    }

    private void handleSubscriptionUpdated(Event event) {
        log.info("Processando evento de customer.subscription.updated do Stripe");
        Subscription subscription = (Subscription) event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> {
                    log.error("Subscription não encontrada no evento de customer.subscription.updated");
                    return new NotFoundException("Subscription não encontrada no evento atualizado");
                });

        String subscriptionId = subscription.getId();
        log.info("Atualizando assinatura Stripe: {}", subscriptionId);

        AssinaturaDePlanoEntity assinatura = assinaturaDePlanoRepository.findByStripeSubscriptionId(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Assinatura não encontrada para subscriptionId=" + subscriptionId));

        String planoPriceId = getPriceIdOfSubscription(subscription);

        PlanoEntity stripeCurrentPlano = getPlanoByStripeIdService.execute(planoPriceId);
        PlanoEntity assinaturaCurrentPlano = assinatura.getPlano();

        if (!stripeCurrentPlano.getId().equals(assinaturaCurrentPlano.getId())) {
            log.info("Plano da assinatura Stripe mudou. Atualizando plano da assinatura local. Assinatura Stripe: {}, Plano Stripe: {}, Plano Local: {}",
                    subscriptionId, stripeCurrentPlano.getNome(), assinaturaCurrentPlano.getNome());

            assinatura.desmarcarProcessamentoAtualizacaoPlano();

            assinatura.atualizarPlano(stripeCurrentPlano);

            boolean isUpgrade = stripeCurrentPlano.getValor().compareTo(assinaturaCurrentPlano.getValor()) > 0;

            EventoHistoricoAssinatura evento = isUpgrade ? EventoHistoricoAssinatura.UPGRADE : EventoHistoricoAssinatura.DOWNGRADE;

            registrarEventoAssinatura(assinatura, evento);
        }

        String stripeStatus = subscription.getStatus();

        switch (stripeStatus) {
            case "active" -> {
                LocalDate dataFimCicloAtual = assinatura.getPlano().calcularDataFim(now());

                assinatura.ativar(dataFimCicloAtual);
            }

            case "past_due" -> assinatura.definirVencido();

            case "unpaid" -> assinatura.definirSuspensa();

            case "canceled" -> {
                assinatura.cancelar();
                registrarEventoAssinatura(assinatura, EventoHistoricoAssinatura.CANCELAMENTO);
            }

            default -> log.info("Status Stripe não tratado: {}", stripeStatus);
        }

        assinaturaDePlanoRepository.save(assinatura);
    }

    private void handleSubscriptionDeleted(Event event) {
        log.info("Processando evento de customer.subscription.deleted do Stripe");
        Subscription subscription = (Subscription) event.getDataObjectDeserializer()
                .getObject()
                .orElseThrow(() -> {
                    log.error("Subscription não encontrada no evento de customer.subscription.deleted");
                    return new NotFoundException("Subscription não encontrada no evento deletado");
                });

        String subscriptionId = subscription.getId();
        log.info("Recebido evento de encerramento definitivo (deleted) para Assinatura Stripe: {}", subscriptionId);

        AssinaturaDePlanoEntity assinatura = assinaturaDePlanoRepository.findByStripeSubscriptionId(subscriptionId)
                .orElseThrow(() -> new NotFoundException("Assinatura não encontrada para subscriptionId=" + subscriptionId));

        if (!assinatura.isCancelada()) {
            log.info("Encerrando assinatura localmente após término do ciclo no Stripe.");
            assinatura.cancelar();

            assinaturaDePlanoRepository.save(assinatura);

            registrarEventoAssinatura(assinatura, EventoHistoricoAssinatura.CANCELAMENTO);
        }
    }
}
