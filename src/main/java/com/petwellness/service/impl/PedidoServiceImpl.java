package com.petwellness.service.impl;

import com.petwellness.dto.PedidoDTO;
import com.petwellness.mapper.PedidoMapper;
import com.petwellness.model.entity.DetallePedido;
import com.petwellness.model.entity.Pedido;
import com.petwellness.model.entity.Producto;
import com.petwellness.model.enums.EstadoPedido;
import com.petwellness.model.enums.PaymentStatus;
import com.petwellness.repository.PedidoRepository;
import com.petwellness.repository.ProductoRepository;
import com.petwellness.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;
    private final PedidoMapper pedidoMapper;

    @Override
    public List<PedidoDTO> obtenerPedidosDeUsuario(Integer usuarioId) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioId(usuarioId);
        return pedidos.stream().map(pedidoMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoMapper.toEntity(pedidoDTO);
        pedido.setFechaPedido(LocalDateTime.now());
        Pedido savedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(savedPedido);
    }

    @Override
    @Transactional
    public PedidoDTO agregarProductoAPedidoDeUsuario(Integer usuarioId, Integer productoId, Integer cantidad) {
        Pedido pedido = pedidoRepository.findTopByUsuarioIdAndEstadoOrderByFechaPedidoDesc(usuarioId, EstadoPedido.PENDIENTE)
                .orElseGet(() -> {
                    Pedido newPedido = new Pedido();
                    newPedido.setUsuarioId(usuarioId);
                    newPedido.setFechaPedido(LocalDateTime.now());
                    newPedido.setEstado(EstadoPedido.PENDIENTE);
                    return pedidoRepository.save(newPedido);
                });

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetallePedido detallePedido = pedido.getDetalles().stream()
                .filter(d -> d.getIdProducto().equals(productoId))
                .findFirst()
                .orElseGet(() -> {
                    DetallePedido newDetalle = new DetallePedido();
                    newDetalle.setPedido(pedido);
                    newDetalle.setIdProducto(productoId);
                    newDetalle.setCantidad(0);
                    newDetalle.setPrecioTotal(BigDecimal.ZERO);
                    pedido.getDetalles().add(newDetalle);
                    return newDetalle;
                });

        detallePedido.setCantidad(detallePedido.getCantidad() + cantidad);
        detallePedido.setPrecioTotal(producto.getCosto().multiply(BigDecimal.valueOf(detallePedido.getCantidad())));

        Pedido updatedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(updatedPedido);
    }

    @Override
    public PedidoDTO obtenerPedidoPorId(Integer pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        return pedidoMapper.toDTO(pedido);
    }

    @Override
    @Transactional
    public PedidoDTO agregarProductoAPedido(Integer pedidoId, Integer productoId, Integer cantidad) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        DetallePedido detallePedido = new DetallePedido();
        detallePedido.setPedido(pedido);
        detallePedido.setIdProducto(productoId);
        detallePedido.setCantidad(cantidad);
        detallePedido.setPrecioTotal(producto.getCosto().multiply(BigDecimal.valueOf(cantidad)));

        pedido.getDetalles().add(detallePedido);
        Pedido updatedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(updatedPedido);
    }

    @Override
    @Transactional
    public PedidoDTO actualizarPedido(Integer pedidoId, PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        pedido.setEstado(pedidoDTO.getEstado());
        pedido.getDetalles().clear();
        pedido.getDetalles().addAll(pedidoDTO.getDetalles().stream()
                .map(detalleDTO -> pedidoMapper.toEntity(detalleDTO, pedido))
                .collect(Collectors.toList()));

        Pedido updatedPedido = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(updatedPedido);
    }

    @Override
    @Transactional
    public void eliminarProductoDePedido(Integer pedidoId, Integer productoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        boolean removed = pedido.getDetalles().removeIf(detalle -> detalle.getIdProducto().equals(productoId));

        if (!removed) {
            throw new RuntimeException("Producto no encontrado en el pedido");
        }

        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void eliminarPedido(Integer pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        pedidoRepository.delete(pedido);
    }

    @Override
    public List<PedidoDTO> obtenerPedidosDeUsuarioPorEstado(Integer usuarioId, EstadoPedido estado) {
        List<Pedido> pedidos = pedidoRepository.findByUsuarioIdAndEstadoOrderByFechaPedidoDesc(usuarioId, estado);
        return pedidos.stream()
                .map(pedidoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PedidoDTO confirmarPedido(Integer pedidoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Calcular el total del pedido sumando el precio total de cada detalle
        /*
        BigDecimal total = pedido.getDetalles().stream()
                .map(DetallePedido::getPrecioTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

         */
        pedido.setEstado(EstadoPedido.PAGADO);

        Pedido pedidoActualizado = pedidoRepository.save(pedido);
        return pedidoMapper.toDTO(pedidoActualizado);
    }

}