package com.example.caso_tdd_gestion_pedidos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class CalculadoraPedido {

    private static final BigDecimal TASA_IGV = new BigDecimal("0.18");

    public BigDecimal calcularSubtotal(List<Producto> productos) {
        for (Producto producto : productos) {
            validarProducto(producto);
        }

        BigDecimal subtotal = productos.stream()
                .map(producto -> producto.precio().multiply(BigDecimal.valueOf(producto.cantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return redondear(subtotal);
    }

    public BigDecimal aplicarDescuento(BigDecimal subtotal, BigDecimal porcentaje) {
        if (porcentaje.compareTo(BigDecimal.ZERO) < 0
                || porcentaje.compareTo(new BigDecimal("100")) > 0) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100");
        }

        BigDecimal descuento = subtotal.multiply(porcentaje).divide(new BigDecimal("100"));
        return redondear(subtotal.subtract(descuento));
    }

    public BigDecimal calcularImpuesto(BigDecimal baseImponible) {
        return redondear(baseImponible.multiply(TASA_IGV));
    }

    public BigDecimal calcularTotal(List<Producto> productos, BigDecimal porcentaje) {
        BigDecimal subtotal = calcularSubtotal(productos);
        BigDecimal baseImponible = aplicarDescuento(subtotal, porcentaje);
        BigDecimal impuesto = calcularImpuesto(baseImponible);
        return redondear(baseImponible.add(impuesto));
    }

    private BigDecimal redondear(BigDecimal monto) {
        return monto.setScale(2, RoundingMode.HALF_UP);
    }

    private void validarProducto(Producto producto) {
        if (producto.precio().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
        if (producto.cantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
    }
}
