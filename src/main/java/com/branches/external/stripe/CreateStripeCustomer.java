package com.branches.external.stripe;

import com.branches.exception.InternalServerError;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreateStripeCustomer {
    public String execute(String nome, String email) {
        CustomerCreateParams params = CustomerCreateParams.builder()
                .setName(nome)
                .setEmail(email)
                .build();

        try {
            Customer customer = Customer.create(params);

            return customer.getId();
        } catch (Exception e) {
            throw new InternalServerError("Erro ao criar cliente no Stripe: " + e.getMessage());
        }
    }
}
