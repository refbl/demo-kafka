package br.com.teste.demokafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NovoPedido {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var producer = new KafkaProducer<String , String>(properties());
        var valor = "12344, 232322, 438787878";
        var record = new ProducerRecord<String, String>("NOVO_PEDIDO",valor, valor);
        producer.send(record,(retorno, ex) -> {
            if (ex != null){
                ex.printStackTrace();
                return;
            }
            System.out.println("Sucesso Enviando : " + retorno.topic() + " => partition " + retorno.partition() + " / offset " + retorno.offset() + " / timestamp " + retorno.timestamp());

        }).get();

    }

    private static Properties properties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        return properties;

    }
}
