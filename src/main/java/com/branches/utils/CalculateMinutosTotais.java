package com.branches.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;

@RequiredArgsConstructor
@Service
public class CalculateMinutosTotais {
    private final ValidateHoraInicioAndHoraFim validateHoraInicioAndHoraFimAndIntervalo;

    public Integer execute(LocalTime horaInicio, LocalTime horaFim, Integer minutosIntervalo) {
        validateHoraInicioAndHoraFimAndIntervalo.execute(horaInicio, horaFim, minutosIntervalo);

        if (horaFim == null || horaInicio == null) {
            return null;
        }
        Duration durationIntervalo = minutosIntervalo != null ? Duration.ofMinutes(minutosIntervalo) : Duration.ZERO;

        Duration duration = Duration.between(horaInicio, horaFim)
                .minus(durationIntervalo);

        return (int) duration.toMinutes();
    }
}
