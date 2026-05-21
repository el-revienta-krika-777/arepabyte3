package com.projecto.ecomarket.service;

import org.springframework.stereotype.Service;
import com.projecto.ecomarket.client.EnvioClient;
import com.projecto.ecomarket.dto.PagoResponseDTO;
import com.projecto.ecomarket.dto.EnvioDTO; // Importación necesaria
import com.projecto.ecomarket.model.Pago;
import com.projecto.ecomarket.repository.PagoRepository;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;
    private final EnvioClient envioClient;
    // Aquí inyectarías el servicio de pedidos para actualizar su estado
    // private final PedidoService pedidoService;

    // Se agrega EnvioClient al constructor para corregir el error de compilación
    public PagoService(PagoRepository pagoRepository, EnvioClient envioClient) {
        this.pagoRepository = pagoRepository;
        this.envioClient = envioClient;
    }

    private PagoResponseDTO mapToDTO(Pago pago) {
        return PagoResponseDTO.builder()
                .pagoId(pago.getId())
                .pedidoId(pago.getPedidoId())
                .monto(pago.getMonto())
                .estado(pago.getEstado())
                .metodoPago(pago.getMetodoPago())
                .build();
    }

    public PagoResponseDTO procesarPago(Pago pago) {
        // Aquí se llamará a una API externa (Stripe/PayPal)
        pago.setEstado("COMPLETADO");
        pago.setTransaccionId("TXN-" + System.currentTimeMillis());
        Pago pagoGuardado = pagoRepository.save(pago);

        // Lógica de envío añadida
        EnvioDTO envio = new EnvioDTO();
        envio.setEstadoEnvio("PREPARANDO");
        envio.setDireccionEntrega("Dirección de prueba"); 
        
        // Cambia 'crearEnvio' por el nombre del método real que definiste en tu EnvioClient
        envioClient.crearEnvio(envio); 

        return mapToDTO(pagoGuardado);
    }

    public PagoResponseDTO obtenerPagoPorPedido(Long pedidoId) {
        Pago pago = pagoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado para este pedido"));
        return mapToDTO(pago);
    }
}