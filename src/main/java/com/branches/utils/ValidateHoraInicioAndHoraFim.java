package com.branches.utils;

import com.branches.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class ValidateHoraInicioAndHoraFim {
    public void execute(LocalTime horaInicio, LocalTime horaFim) {
        if (horaInicio == null && horaFim == null) return;

        if (horaInicio == null || horaFim == null) {
            throw new BadRequestException("Ambos os campos 'horaInicio' e 'horaFim' devem ser preenchidos ou ambos devem ser nulos");
        }
    }
}
