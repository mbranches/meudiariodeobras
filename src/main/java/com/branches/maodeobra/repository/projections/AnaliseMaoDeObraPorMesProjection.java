package com.branches.maodeobra.repository.projections;

import java.math.BigDecimal;

public interface AnaliseMaoDeObraPorMesProjection {
    Integer getMes();
    Long getMaoDeObraId();
    String getNomeMaoDeObra();
    String getFuncaoMaoDeObra();
    Integer getTotalMinutos();
    Long getTotalFaltas();
    Long getTotalPresentes();
}
