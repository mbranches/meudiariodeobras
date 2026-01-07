package com.branches.assinatura.routine;

import com.branches.assinatura.domain.AssinaturaEntity;
import com.branches.assinatura.domain.enums.AssinaturaStatus;
import com.branches.assinatura.repository.AssinaturaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class ExpireAssinaturasRoutine {
    private final AssinaturaRepository assinaturaRepository;

    /**
     * Executa diariamente à meia-noite para verificar e encerrar assinaturas expiradas
     * Assinaturas MENSAL_AVULSO e outras que atingiram a dataFim são automaticamente encerradas
     */
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void expireAssinaturas() {
        log.info("Iniciando processo de expiração de assinaturas");

        LocalDate hoje = LocalDate.now();

        // Busca assinaturas ativas que já passaram da data de fim
        List<AssinaturaEntity> assinaturasExpiradas = assinaturaRepository
                .findAssinaturasDePlanosMensalAvulsoAtivasComDataFimBefore(hoje);

        if (assinaturasExpiradas.isEmpty()) {
            log.info("Nenhuma assinatura expirada encontrada");
            return;
        }

        log.info("Encontradas {} assinaturas expiradas", assinaturasExpiradas.size());

        List<AssinaturaEntity> toSave = assinaturasExpiradas.stream().peek(assinatura -> {
            assinatura.setStatus(AssinaturaStatus.ENCERRADO);

            log.info("Assinatura ID {} do tenant {} foi encerrada. Data de fim: {}",
                    assinatura.getId(),
                    assinatura.getTenantId(),
                    assinatura.getDataFim());

        }).toList();

        assinaturaRepository.saveAll(toSave);

        log.info("Processo de expiração de assinaturas concluído");
    }
}

