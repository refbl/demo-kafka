package br.com.teste.demokafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NovoPedido {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String , String>(properties());
        var key = UUID.randomUUID().toString();
        var valor = key + ", 232322, 438787878";
        var record = new ProducerRecord<String, String>("LOJA_NOVO_PEDIDO",key, valor);
        Callback callback = (retorno, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("Sucesso Enviando : " + retorno.topic() + " => partition " + retorno.partition() + " / offset " + retorno.offset() + " / timestamp " + retorno.timestamp());

        };
        producer.send(record, callback).get();

        var email = "Teste Envio Email";
        var emailRecord = new ProducerRecord<String, String>("LOJA_ENVIA_EMAIL",key, email);
        producer.send(emailRecord, callback).get();

    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;

    }
}
