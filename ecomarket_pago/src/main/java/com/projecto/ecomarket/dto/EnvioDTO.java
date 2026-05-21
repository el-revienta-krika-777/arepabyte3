package com.projecto.ecomarket.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EnvioDTO {
    private Long id;
    private String direccionEntrega;
    private String transportista; // Ejem: "DHL", "FedEx", "Correos"
    private String numeroSeguimiento;
    private String estadoEnvio; // "PREPARANDO", "EN_TRANSITO", "ENTREGADO", "CANCELADO"
    private LocalDateTime fechaEnvio;
    private LocalDateTime fechaEntregaEstimada;
}