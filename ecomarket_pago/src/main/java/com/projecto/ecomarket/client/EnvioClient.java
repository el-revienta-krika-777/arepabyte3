package com.projecto.ecomarket.client;

import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.projecto.ecomarket.dto.EnvioDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnvioClient {

    private final WebClient EnvioWebClient;

    public Optional<EnvioDTO> buscarEnvio(Long envioId) {
        try {
            EnvioDTO envio = EnvioWebClient.get()
                    .uri("/api/envios/{id}", envioId)
                    .retrieve()
                    .bodyToMono(EnvioDTO.class)
                    .block();
            return Optional.ofNullable(envio);
        } catch (WebClientResponseException.NotFound e) {
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error al conectar con codigoms_catalogo: {}", e.getMessage());
            throw new RuntimeException("El servicio de catálogo no está disponible");
        }
    }

    public EnvioDTO crearEnvio(EnvioDTO envioDto) {
        try {
            return EnvioWebClient.post()
                    .uri("/api/envios")
                    .bodyValue(envioDto) // Envía el objeto en el cuerpo de la petición
                    .retrieve()
                    .bodyToMono(EnvioDTO.class)
                    .block();
        } catch (Exception e) {
            log.error("Error al crear el envío: {}", e.getMessage());
            throw new RuntimeException("No se pudo registrar el envío");
        }
    }
}