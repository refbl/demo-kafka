package br.com.teste.demokafka;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NovoPedido {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        try(var kafkaProdutor = new KafkaProdutor()){
            // Envia 10 vezes as mensagens
            for (var i = 0; i < 10; i++){
                var key = UUID.randomUUID().toString();
                var valor = key + ", 232322, 438787878";

                kafkaProdutor.send("LOJA_NOVO_PEDIDO",key, valor);

                var email = "Teste Envio Email";
                kafkaProdutor.send("LOJA_ENVIA_EMAIL",key, email);
            }
        }
    }
}
