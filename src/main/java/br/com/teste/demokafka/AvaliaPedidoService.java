package br.com.teste.demokafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class AvaliaPedidoService {
    public static void main(String[] args) {
        var avaliaPedido = new AvaliaPedidoService();
        try (var service = new KafkaConsumidorService(AvaliaPedidoService.class.getSimpleName(), "LOJA_NOVO_PEDIDO", avaliaPedido::parse)){
            service.run();
        }

    }

    private void parse(ConsumerRecord<String, String> record) {
        System.out.println("Processando NOVO Pedido:   key "
                + record.key()
                + " :: Valor "
                + record.value()
                + " Particao " + record.partition()
                + " offset " + record.offset());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Novo Pedido Processado com Sucesso.");
    }
}
