package br.com.teste.demokafka;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NovoPedido {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var kafkaProdutorPedido = new KafkaProdutor<Pedido>()){
            try(var kafkaProdutorEmail = new KafkaProdutor<Email>()){
                // Envia 10 vezes as mensagens
                for (var i = 0; i < 10; i++) {
                    var userId = UUID.randomUUID().toString();
                    var pedidoId = UUID.randomUUID().toString();
                    var valor = new BigDecimal(Math.random() * 5000 + 1);
                    var pedido = new Pedido(userId, pedidoId, valor);

                    kafkaProdutorPedido.send("LOJA_NOVO_PEDIDO", userId, pedido);

                    var email = new Email("Email Teste", "Este eh um email teste");
                    kafkaProdutorEmail.send("LOJA_ENVIA_EMAIL", userId, email);
                }
            }
        }
    }
}
