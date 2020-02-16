# demo-kafka
Demo serviços Kafka (Producer / Consumer)

Referencia: http://kafka.apache.org/quickstart

1. Instalação Kafka
 - Acessar http://kafka.apache.org/downloads

 - Baixar o arquivo tgz com a versão mais recente do SCALA (2.4 com scala 2.13)

 - https://downloads.apache.org/kafka/2.4.0/kafka_2.13-2.4.0.tgz
   
 - Descompactar o arquivo em uma pasta (o caminho não deve ter espaços)

 - O Kafka utiliza o Zookeeper para armazenar algumas informações, ele já vem com o kafka e precisa ser iniciado antes
   de rodar o kafka

 - para rodar Zookeeper 
    linux   -> bin/zookeeper-server-start.sh config/zookeeper.properties
    windows ->  bin\windows\zookeeper-server-start.bat config\zookeeper.properties

 - para rodar Kafka     
    linux   -> bin/kafka-server-start.sh config/server.properties
    windows -> bin\windows\kafka-server-start.bat config\server.properties
   
IMPORTANTE: para rodar no Windows, não poderá ser em um diretório longo, pois apresenta erro
Ex. instalar em C:\kafka_2.12-2.3.1


2. Criação de Topicos
 - Criar Topico 
   linux   -> bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic TESTE
   windows -> bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic TESTE

 - Consultar Topico 
   linux   ->  bin/kafka-topics.sh --list --bootstrap-server localhost:9092
   windows ->  bin\windows\kafka-topics.bat --list --bootstrap-server localhost:9092


3. Subir um Producer
   linux   -> bin/kafka-console-producer.sh --broker-list localhost:9092 --topic TESTE
   windows -> bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic TESTE


4.Subir um Consumer
   linux   -> bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic TESTE --from-beginning
   windows -> bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic TESTE --from-beginning

5. Listar Detalhes do Topico
   windows -> bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic TESTE



