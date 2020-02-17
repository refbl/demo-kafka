package br.com.teste.demokafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class GsonDeserializer<T> implements Deserializer<T> {
    public static final String TYPE_CONFIG = "br.com.teste.demokafka.type_config";
    private final Gson gson = new GsonBuilder().create();
    private Class<T> type;


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        String typeName = String.valueOf(configs.get(TYPE_CONFIG));
        try {
            this.type = (Class<T>) Class.forName(typeName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Classe não existe para deserialização", e);
        }
    }

    @Override
    public T deserialize(String s, byte[] bytes) {
        return this.gson.fromJson(new String(bytes), this.type);
    }
}
