
package br.com.corrida.service;

import br.com.corrida.dto.InscricaoRequest;
import br.com.corrida.dto.InscricaoResponse;
import br.com.corrida.model.Categoria;
import br.com.corrida.model.Percurso;
import br.com.corrida.repository.InscricaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class InscricaoServiceTest {

    private InscricaoService service;

    @BeforeEach
    void configurar() {
        InscricaoRepository repository = new InscricaoRepository();
        service = new InscricaoService(repository);
    }

    private InscricaoResponse cadastrar(
            LocalDate nascimento, Percurso percurso) {

        InscricaoRequest request = new InscricaoRequest();

        request.setNome("Pessoa Teste");
        request.setEmail("teste@email.com");
        request.setDataNascimento(nascimento);
        request.setPercurso(percurso);

        return service.cadastrar(request);
    }

    @Test
    void deveCadastrarPessoaCom14Anos() {
        InscricaoResponse response = cadastrar(
                LocalDate.of(2013, 3, 14),
                Percurso.KM_5
        );

        assertEquals(14, response.getIdadeNaProva());
        assertEquals(Categoria.JUVENIL, response.getCategoria());
    }

    @Test
    void deveRejeitarPessoaComMenosDe14Anos() {
        assertThrows(IllegalArgumentException.class, () ->
                cadastrar(
                        LocalDate.of(2013, 3, 15),
                        Percurso.KM_5
                )
        );
    }

    @Test
    void deveRejeitarMenorDe18AnosNoPercursoDe10Km() {
        assertThrows(IllegalArgumentException.class, () ->
                cadastrar(
                        LocalDate.of(2009, 3, 15),
                        Percurso.KM_10
                )
        );
    }

    @Test
    void deveClassificarPessoaCom18AnosComoAdulto() {
        InscricaoResponse response = cadastrar(
                LocalDate.of(2009, 3, 14),
                Percurso.KM_10
        );

        assertEquals(18, response.getIdadeNaProva());
        assertEquals(Categoria.ADULTO, response.getCategoria());
    }

    @Test
    void deveClassificarPessoaCom40AnosComoMaster() {
        InscricaoResponse response = cadastrar(
                LocalDate.of(1987, 3, 14),
                Percurso.KM_5
        );

        assertEquals(40, response.getIdadeNaProva());
        assertEquals(Categoria.MASTER, response.getCategoria());
    }

    @Test
    void deveClassificarPessoaCom60AnosComoSenior() {
        InscricaoResponse response = cadastrar(
                LocalDate.of(1967, 3, 14),
                Percurso.KM_5
        );

        assertEquals(60, response.getIdadeNaProva());
        assertEquals(Categoria.SENIOR, response.getCategoria());
    }


    @Test
    void deveClassificarPessoaCom17AnosComoJuvenil() {
        InscricaoResponse response = cadastrar(
                LocalDate.of(2010, 3, 14),
                Percurso.KM_5
        );

        assertEquals(17, response.getIdadeNaProva());
        assertEquals(Categoria.JUVENIL, response.getCategoria());
    }

    @Test
    void deveClassificarPessoaCom39AnosComoAdulto() {
        InscricaoResponse response = cadastrar(
                LocalDate.of(1988, 3, 14),
                Percurso.KM_5
        );

        assertEquals(39, response.getIdadeNaProva());
        assertEquals(Categoria.ADULTO, response.getCategoria());
    }

    @Test
    void deveClassificarPessoaCom59AnosComoMaster() {
        InscricaoResponse response = cadastrar(
                LocalDate.of(1968, 3, 14),
                Percurso.KM_5
        );

        assertEquals(59, response.getIdadeNaProva());
        assertEquals(Categoria.MASTER, response.getCategoria());
    }

    @Test
    void atualizacaoInvalidaNaoDeveAlterarInscricaoOriginal() {
        // Primeiro, cadastramos uma inscrição válida.
        cadastrar(
                LocalDate.of(2000, 5, 20),
                Percurso.KM_5
        );

        InscricaoRequest request = new InscricaoRequest();
        request.setNome("Nome Alterado");
        request.setEmail("teste@email.com");
        request.setDataNascimento(LocalDate.of(2013, 3, 15));
        request.setPercurso(Percurso.KM_5);

        // A atualização deve ser rejeitada porque a pessoa terá 13 anos.
        assertThrows(IllegalArgumentException.class, () ->
                service.atualizar("teste@email.com", request)
        );

        // A inscrição original deve continuar intacta.
        InscricaoResponse original =
                service.buscarPorEmail("teste@email.com");

        assertEquals("Pessoa Teste", original.getNome());
        assertEquals(LocalDate.of(2000, 5, 20), original.getDataNascimento());
        assertEquals(Percurso.KM_5, original.getPercurso());
    }

}
