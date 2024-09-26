package com.petwellness.api;

import com.petwellness.dto.PedidoDTO;
import com.petwellness.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PedidoDTO>> obtenerPedidosDeUsuario(@PathVariable Integer usuarioId) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosDeUsuario(usuarioId));
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        return new ResponseEntity<>(pedidoService.crearPedido(pedidoDTO), HttpStatus.CREATED);
    }

    @PostMapping("/{pedidoId}/productos/{productoId}")
    public ResponseEntity<PedidoDTO> agregarProductoAPedido(
            @PathVariable Integer pedidoId,
            @PathVariable Integer productoId,
            @RequestBody Map<String, Integer> requestBody) {
        Integer cantidad = requestBody.get("cantidad");
        return ResponseEntity.ok(pedidoService.agregarProductoAPedido(pedidoId, productoId, cantidad));
    }
    @PostMapping("/usuario/{usuarioId}/productos/{productoId}")
public ResponseEntity<PedidoDTO> agregarProductoAPedidoDeUsuario(
        @PathVariable Integer usuarioId,
        @PathVariable Integer productoId,
        @RequestBody Map<String, Integer> requestBody) {
    Integer cantidad = requestBody.get("cantidad");
    return ResponseEntity.ok(pedidoService.agregarProductoAPedidoDeUsuario(usuarioId, productoId, cantidad));
}

    @PutMapping("/{pedidoId}")
    public ResponseEntity<PedidoDTO> actualizarPedido(
            @PathVariable Integer pedidoId,
            @RequestBody PedidoDTO pedidoDTO) {
        return ResponseEntity.ok(pedidoService.actualizarPedido(pedidoId, pedidoDTO));
    }

    @DeleteMapping("/{pedidoId}/productos/{productoId}")
    public ResponseEntity<Void> eliminarProductoDePedido(
            @PathVariable Integer pedidoId,
            @PathVariable Integer productoId) {
        pedidoService.eliminarProductoDePedido(pedidoId, productoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer pedidoId) {
        pedidoService.eliminarPedido(pedidoId);
        return ResponseEntity.noContent().build();
    }
}