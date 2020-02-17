package br.com.teste.demokafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.HashMap;

public class EmailService {
    public static void main(String[] args) {
        var emailService = new EmailService();
        try(var service = new KafkaConsumidorService<Email>(EmailService.class.getSimpleName()
                                    , "LOJA_ENVIA_EMAIL"
                                    , emailService::parse
                                    , Email.class
                                    ,new HashMap<String, String>())){
            service.run();
        }
    }

    private void parse (ConsumerRecord<String,Email> record) {
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
