package br.com.teste.demokafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.regex.Pattern;

public class LogService {
    public static void main(String[] args) {
        var consumer = new KafkaConsumer<String, String>(properties());

        // Subscreve-se em qualquer tópico que começa com LOJA - usa Expressão Regular
        consumer.subscribe(Pattern.compile("LOJA.*"));

        while (true) {
            var registros = consumer.poll(Duration.ofMillis(100));

            if (!registros.isEmpty()) {
                System.out.println(" Processando " + registros.count() + " registros");

                for (var registro : registros) {
                    System.out.println("LOG : " + registro.topic() + " key "
                            + registro.key()
                            + " :: Valor "
                            + registro.value()
                            + " Particao " + registro.partition()
                            + " offset " + registro.offset());
                }
            }
        }
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, LogService.class.getSimpleName());

        return properties;
    }
}
