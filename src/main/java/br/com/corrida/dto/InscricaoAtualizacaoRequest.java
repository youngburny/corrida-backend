package br.com.corrida.dto;

import br.com.corrida.model.Percurso;
import java.time.LocalDate;

public class InscricaoAtualizacaoRequest {

    private String nome;
    private LocalDate dataNascimento;
    private Percurso percurso;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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