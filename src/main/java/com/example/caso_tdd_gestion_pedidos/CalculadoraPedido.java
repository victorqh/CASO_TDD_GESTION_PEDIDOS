package com.example.caso_tdd_gestion_pedidos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalculadoraPedido {

    public BigDecimal calcularSubtotal(List<Producto> productos) {
        return productos.stream()
                .map(producto -> producto.precio().multiply(BigDecimal.valueOf(producto.cantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal aplicarDescuento(BigDecimal subtotal, BigDecimal porcentaje) {
        BigDecimal descuento = subtotal.multiply(porcentaje).divide(new BigDecimal("100"));
        return subtotal.subtract(descuento).setScale(2, RoundingMode.HALF_UP);
    }
}
