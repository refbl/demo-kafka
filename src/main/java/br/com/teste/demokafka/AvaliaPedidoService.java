package br.com.teste.demokafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;

public class AvaliaPedidoService {
    public static void main(String[] args) {
        var avaliaPedido = new AvaliaPedidoService();
        try (var service = new KafkaConsumidorService<>(AvaliaPedidoService.class.getSimpleName()
                               ,"LOJA_NOVO_PEDIDO"
                               , avaliaPedido::parse
                               ,Pedido.class
                               , new HashMap<String, String>())){
            service.run();
        }

    }

    private void parse(ConsumerRecord<String, Pedido> record) {

        System.out.println("------------------------------------------");
        System.out.println("Processando NOVO Pedido");
        System.out.println(record.key());
        System.out.println(record.value());
        System.out.println(record.partition());
        System.out.println(record.offset());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Novo Pedido Processado com Sucesso.");
    }
}
