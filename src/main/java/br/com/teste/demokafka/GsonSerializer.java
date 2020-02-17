package br.com.teste.demokafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Serializer;

public class GsonSerializer<T> implements Serializer<T> {
    private final Gson gson = new GsonBuilder().create();

    //Implementa Metodo serialize que devolve array de bytes.
    // converte o objeto do generics para json e transforma para bytes para retornar
    @Override
    public byte[] serialize(String s, T object) {
        return this.gson.toJson(object).getBytes();
    }
}
