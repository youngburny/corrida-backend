package br.com.corrida.controller;

import br.com.corrida.dto.InscricaoRequest;
import br.com.corrida.dto.InscricaoResponse;
import br.com.corrida.dto.ResumoResponse;
import br.com.corrida.service.InscricaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscricoes")
public class InscricaoController {

    private final InscricaoService service;

    public InscricaoController(InscricaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InscricaoResponse> cadastrar(@RequestBody InscricaoRequest request) {
        InscricaoResponse response = service.cadastrar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<InscricaoResponse>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{email}")
    public ResponseEntity<InscricaoResponse> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(service.buscarPorEmail(email));
    }

    @PutMapping("/{email}")
    public ResponseEntity<InscricaoResponse> atualizar(
            @PathVariable String email,
            @RequestBody InscricaoRequest request
    ) {
        return ResponseEntity.ok(service.atualizar(email, request));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> cancelar(@PathVariable String email) {

        service.cancelar(email);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/resumo")
    public ResponseEntity<ResumoResponse> gerarResumo() {
        return ResponseEntity.ok(service.gerarResumo());
    }

}