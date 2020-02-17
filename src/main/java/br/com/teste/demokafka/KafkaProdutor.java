package br.com.teste.demokafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

class KafkaProdutor<T> implements Closeable {

    private final KafkaProducer<String, T> producer;

    KafkaProdutor() {
        this.producer = new KafkaProducer<>(properties());
    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());

        return properties;
    }

    void send(String topic, String key, T valor) throws ExecutionException, InterruptedException {
        var record = new ProducerRecord<>(topic ,key, valor);
        Callback callback = (retorno, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("Sucesso Enviando : " + retorno.topic() + " => partition " + retorno.partition() + " / offset " + retorno.offset() + " / timestamp " + retorno.timestamp());

        };

        this.producer.send(record, callback).get();
    }

    @Override
    public void close() {
        this.producer.close();
    }
}
