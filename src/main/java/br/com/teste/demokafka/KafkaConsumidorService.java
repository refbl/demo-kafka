package br.com.teste.demokafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

class KafkaConsumidorService<T> implements Closeable {

    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction parse;

    KafkaConsumidorService(String groupId, String topic, ConsumerFunction parse, Class<T> type, Map<String, String> propriedadesExtras) {
        this.consumer = new KafkaConsumer<>(properties(type, groupId, propriedadesExtras));
        this.parse = parse;
        // Subscreve-se para ouvir um tópico
        consumer.subscribe(Collections.singletonList(topic));
    }

    // Contrutor para receber o Topico em forma de Regex (para consumir mais de um tópico de acordo com um padrão)
    public KafkaConsumidorService(String groupId, Pattern topicRegex, ConsumerFunction parse, Class<T> type, Map<String, String> propriedadesExtras) {
        this.consumer = new KafkaConsumer<>(properties(type, groupId, propriedadesExtras));
        this.parse = parse;
        // Subscreve-se para ouvir um tópico
        consumer.subscribe(topicRegex);
    }

    void run() {
        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));

            if (!records.isEmpty()) {
                System.out.println(" Processando " + records.count() + " registros");

                for (var record : records) {
                    this.parse.consume(record);
                }
            }
        }
    }


    private Properties properties(Class<T> type, String groupId, Map<String, String> propriedadesExtras) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        //Diminui a Leitura de Registros no poll para garantir o commit caso haja rebalancing
        properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        //Para permitir a divisão de topicos para servicos paralelos
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
        //Configuracao para permitir obter o tipo no GsonDeserializer
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());

        // Sobrescreve as Propriedades definidas acima com o que vier nas propriedades extras
        properties.putAll(propriedadesExtras);

        return properties;
    }

    @Override
    public void close() {
        this.consumer.close();
    }
}
