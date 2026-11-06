package br.com.corrida.repository;

import br.com.corrida.model.Inscricao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InscricaoRepository {

    private final List<Inscricao> inscricoes = new ArrayList<>();

    public Inscricao salvar(Inscricao inscricao) {
        inscricoes.add(inscricao);
        return inscricao;
    }

    public List<Inscricao> listarTodas() {
        return new ArrayList<>(inscricoes);
    }

    public Optional<Inscricao> buscarPorEmail(String email) {
        return inscricoes.stream().filter(inscricao -> inscricao.getEmail().equals(email)).findFirst();
    }

    public boolean removerPorEmail(String email) {
        return inscricoes.removeIf(inscricao -> inscricao.getEmail().equals(email));
    }

}
