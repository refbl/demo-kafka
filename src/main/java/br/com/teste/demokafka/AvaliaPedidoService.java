package br.com.teste.demokafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class AvaliaPedidoService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());

        // Subscreve-se para ouvir um tópico
        consumer.subscribe(Collections.singletonList("NOVO_PEDIDO"));

        while (true) {
            var registros = consumer.poll(Duration.ofMillis(100));

            if (!registros.isEmpty()) {
                System.out.println(" Processando " + registros.count() + " registros");

                for (var registro : registros) {
                    System.out.println("Processando NOVO Pedido:   key "
                            + registro.key()
                            + " :: Valor "
                            + registro.value()
                            + " Particao " + registro.partition()
                            + " offset " + registro.offset());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Novo Pedido Processado com Sucesso.");
                }
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, AvaliaPedidoService.class.getSimpleName());

        return properties;
    }
}
