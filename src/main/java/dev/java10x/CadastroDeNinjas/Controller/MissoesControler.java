package dev.java10x.CadastroDeNinjas.Controller;

import dev.java10x.CadastroDeNinjas.DTO.MissaoDTO;
import dev.java10x.CadastroDeNinjas.Service.MissaoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/missoes"))
public class MissoesControler {

    @Autowired
    private MissaoService missaoService;

    @PostMapping
    public ResponseEntity<MissaoDTO> criarMissao(@Valid @RequestBody MissaoDTO missaoDTO) {
        MissaoDTO novaMissao = missaoService.criarMissao(missaoDTO);

        return ResponseEntity.ok(novaMissao);
    }

    @GetMapping
    public Page<MissaoDTO> listarMissoes(Pageable pageable){
        return missaoService.listarNissoes(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MissaoDTO> listarMissoesPorId(@PathVariable @Min(1) Long id){
        MissaoDTO missaoDTO = missaoService.listarMissaoPorId(id);

        if(missaoDTO != null){
            return ResponseEntity.ok(missaoDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MissaoDTO> atualizarMissao(@PathVariable Long id, @Valid @RequestBody MissaoDTO missaoDTO){
         MissaoDTO missaoAtualizada = missaoService.atualizarMissao(id, missaoDTO);

        if(missaoAtualizada != null){
            return ResponseEntity.ok(missaoAtualizada);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarMissao(@PathVariable Long id){

        missaoService.deletarMissao(id);

        return ResponseEntity.ok("Missão deletada com sucesso");
    }
}
