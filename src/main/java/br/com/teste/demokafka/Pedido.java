package br.com.teste.demokafka;

import java.math.BigDecimal;

public class Pedido {
    private final String userId, pedidoId;
    private final BigDecimal valor;

    public Pedido(String userId, String pedidoId, BigDecimal valor) {
        this.userId = userId;
        this.pedidoId = pedidoId;
        this.valor = valor;
    }
}
