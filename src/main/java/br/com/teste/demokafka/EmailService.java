package br.com.teste.demokafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class EmailService {
    public static void main(String[] args) {
        var emailService = new EmailService();
        try(var service = new KafkaConsumidorService(EmailService.class.getSimpleName(), "LOJA_ENVIA_EMAIL", emailService::parse)){
            service.run();
        }
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
