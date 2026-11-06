package br.com.corrida.model;

import java.time.LocalDate;

public class Inscricao {

    private String nome;
    private final String email;
    private LocalDate dataNascimento;
    private Percurso percurso;

    public Inscricao(String nome, String email, LocalDate dataNascimento, Percurso percurso) {

        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.percurso = percurso;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Percurso getPercurso() {
        return percurso;
    }

    public void setPercurso(Percurso percurso) {
        this.percurso = percurso;
    }

}