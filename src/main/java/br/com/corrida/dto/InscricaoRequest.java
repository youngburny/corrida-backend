package br.com.corrida.dto;

import br.com.corrida.model.Percurso;

import java.time.LocalDate;

public class InscricaoRequest {

    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private Percurso percurso;

    public InscricaoRequest() {
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

    public void setEmail(String email) {
        this.email = email;
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
