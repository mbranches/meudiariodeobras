package com.branches.external.stripe;

import com.branches.exception.InternalServerError;
import com.branches.plano.dto.request.CreatePlanoRequest;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreateStripePlano {
    public CreateStripePlanoResponse execute(CreatePlanoRequest request) {
        try {
            log.info("Iniciando criação de plano no Stripe para: {}", request.nome());

            ProductCreateParams productParams = ProductCreateParams.builder()
                    .setName(request.nome())
                    .setDescription(request.descricao())
                    .setType(ProductCreateParams.Type.SERVICE)
                    .build();

            Product product;
            try {
                product = Product.create(productParams);
            } catch (Exception e) {
                log.error("Erro ao criar produto no Stripe: {}", e.getMessage());

                throw new RuntimeException();
            }

            PriceCreateParams.Builder priceParamsBuilder = PriceCreateParams.builder()
                    .setUnitAmount(request.valor().multiply(new BigDecimal("100")).longValue())
                    .setCurrency("brl")
                    .setProduct(product.getId());

            // Se não for avulso, configura como recorrente
            if (request.recorrencia().toInterval() != null) {
                priceParamsBuilder.setRecurring(
                        PriceCreateParams.Recurring.builder()
                                .setInterval(request.recorrencia().toInterval())
                                .build()
                );
            }

            PriceCreateParams priceParams = priceParamsBuilder.build();

            Price price;
            try {
                price = Price.create(priceParams);
            } catch (Exception e) {
                log.error("Erro ao criar preço no Stripe: {}", e.getMessage());

                throw new RuntimeException();
            }

            return new CreateStripePlanoResponse(product.getId(), price.getId());
        } catch (Exception e) {
            throw new InternalServerError("Erro inesperado ao criar plano no Stripe");
        }
    }
}
