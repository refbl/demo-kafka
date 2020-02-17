package br.com.teste.demokafka;

public class Email {
    private final String titulo, mensagem;


    public Email(String titulo, String mensagem) {
        this.titulo = titulo;
        this.mensagem = mensagem;
    }

    @Override
    public String toString() {
        return "Titulo = " + this.titulo + " ::: Mensagem= " + this.mensagem;
    }
}
