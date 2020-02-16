package br.com.teste.demokafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class EmailService {
    public static void main(String[] args) {
        var emailService = new EmailService();
        var service = new KafkaService("LOJA_ENVIA_EMAIL", emailService::parse);
        service.run();
    }

    private void parse (ConsumerRecord<String,String> record) {
        System.out.println("Enviando Email:   key "
                + record.key()
                + " :: Valor "
                + record.value()
                + " Particao " + record.partition()
                + " offset " + record.offset());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Email Processado com Sucesso.");
    }

}
