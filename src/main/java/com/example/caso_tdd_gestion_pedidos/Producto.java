package com.example.caso_tdd_gestion_pedidos;

import java.math.BigDecimal;

public record Producto(String nombre, BigDecimal precio, int cantidad) { }
