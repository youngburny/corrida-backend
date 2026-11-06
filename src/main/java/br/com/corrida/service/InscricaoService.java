package br.com.corrida.service;

import br.com.corrida.exception.InscricaoNaoEncontradaException;

import br.com.corrida.dto.InscricaoAtualizacaoRequest;
import br.com.corrida.dto.InscricaoRequest;
import br.com.corrida.dto.InscricaoResponse;
import br.com.corrida.dto.ResumoResponse;
import br.com.corrida.model.Categoria;
import br.com.corrida.model.Inscricao;
import br.com.corrida.model.Percurso;
import br.com.corrida.repository.InscricaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Objects;

@Service
public class InscricaoService {

    private static final LocalDate DATA_PROVA = LocalDate.of(2027, 3, 14);

    private static final Pattern EMAIL_VALIDO = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final InscricaoRepository repository;

    public InscricaoService(InscricaoRepository repository) {
        this.repository = repository;
    }

    public InscricaoResponse cadastrar(InscricaoRequest request) {

        String nome = normalizarNome(request.getNome());
        String email = normalizarEmail(request.getEmail());

        validarDados(nome, email, request.getDataNascimento(), request.getPercurso());

        if (repository.buscarPorEmail(email).isPresent()) {
            throw new IllegalStateException("Já existe uma inscrição com este e-mail.");
        }

        Inscricao inscricao = new Inscricao(
                nome,
                email,
                request.getDataNascimento(),
                request.getPercurso()
        );

        repository.salvar(inscricao);

        return converterParaResponse(inscricao);
    }

    private void validarDados(
            String nome,
            String email,
            LocalDate nascimento,
            Percurso percurso
    ) {
        if (nome == null || nome.length() < 3 || nome.length() > 100) {
            throw new IllegalArgumentException("O nome deve ter entre 3 e 100 caracteres.");
        }

        if (email == null || !EMAIL_VALIDO.matcher(email).matches()) {
            throw new IllegalArgumentException("Informe um e-mail válido.");
        }

        if (nascimento == null || !nascimento.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "Informe uma data de nascimento válida, anterior a hoje."
            );
        }

        if (percurso == null) {
            throw new IllegalArgumentException("O percurso é obrigatório.");
        }

        int idade = calcularIdade(nascimento);

        if (idade < 14) {
            throw new IllegalArgumentException("A idade mínima para participar é 14 anos.");
        }

        if (percurso == Percurso.KM_10 && idade < 18) {
            throw new IllegalArgumentException("O percurso de 10 km exige idade mínima de 18 anos.");
        }
    }

    private String normalizarNome(String nome) {
        if (nome == null) {
            return null;
        }

        return nome.trim().replaceAll("\\s+", " ");
    }

    private String normalizarEmail(String email) {
        if (email == null) {
            return null;
        }

        return email.trim().toLowerCase(Locale.ROOT);
    }

    private int calcularIdade(LocalDate nascimento) {
        return Period.between(nascimento, DATA_PROVA).getYears();
    }

    private Categoria determinarCategoria(int idade) {
        if (idade < 14) return Categoria.SEM_CATEGORIA;
        if (idade <= 17) return Categoria.JUVENIL;
        if (idade <= 39) return Categoria.ADULTO;
        if (idade <= 59) return Categoria.MASTER;

        return Categoria.SENIOR;
    }

    private InscricaoResponse converterParaResponse(Inscricao inscricao) {
        int idade = calcularIdade(inscricao.getDataNascimento());

        return new InscricaoResponse(
                inscricao.getNome(),
                inscricao.getEmail(),
                inscricao.getDataNascimento(),
                inscricao.getPercurso(),
                idade,
                determinarCategoria(idade)
        );
    }

    public List<InscricaoResponse> listarTodas() {
        return repository.listarTodas().stream().map(this::converterParaResponse).toList();
    }

    public InscricaoResponse buscarPorEmail(String email) {
        String emailNormalizado = normalizarEmail(email);

        Inscricao inscricao = repository.buscarPorEmail(emailNormalizado).orElseThrow(() -> new InscricaoNaoEncontradaException("Inscrição não encontrada."));

        return converterParaResponse(inscricao);
    }

    public InscricaoResponse atualizar(String email, InscricaoAtualizacaoRequest request) {

        String emailNormalizado = normalizarEmail(email);

        Inscricao inscricao = repository.buscarPorEmail(emailNormalizado).orElseThrow(() -> new InscricaoNaoEncontradaException("Inscrição não encontrada."));

        String novoNome = normalizarNome(request.getNome());

        validarDados(
                novoNome,
                emailNormalizado,
                request.getDataNascimento(),
                request.getPercurso()
        );

        inscricao.setNome(novoNome);
        inscricao.setDataNascimento(request.getDataNascimento());
        inscricao.setPercurso(request.getPercurso());

        return converterParaResponse(inscricao);
    }

    public void cancelar(String email) {

        String emailNormalizado = normalizarEmail(email);

        boolean removida = repository.removerPorEmail(emailNormalizado);

        if (!removida) {
            throw new InscricaoNaoEncontradaException("Inscrição não encontrada.");
        }
    }

    public ResumoResponse gerarResumo() {

        List<Inscricao> inscricoes = repository.listarTodas();

        int total = inscricoes.size();

        int km5 = (int) inscricoes.stream().filter(inscricao -> inscricao.getPercurso() == Percurso.KM_5).count();

        int km10 = (int) inscricoes.stream().filter(inscricao -> inscricao.getPercurso() == Percurso.KM_10).count();

        return new ResumoResponse(total, km5, km10);
    }
}