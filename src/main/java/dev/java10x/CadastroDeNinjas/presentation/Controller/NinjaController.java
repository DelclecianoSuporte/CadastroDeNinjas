package dev.java10x.CadastroDeNinjas.presentation.Controller;
import dev.java10x.CadastroDeNinjas.application.dto.NinjaDTO;
import dev.java10x.CadastroDeNinjas.application.service.NinjaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/ninjas"))
public class NinjaController {

    @GetMapping("/boasvindas")
    public String boasVindas(){
        return "Essa é minha primeira mensage nessa rota";
    }

    @Autowired
    private NinjaService ninjaService;

    @PostMapping
    public ResponseEntity<NinjaDTO> criarNinja(@Valid @RequestBody NinjaDTO ninjaDTO) {
        NinjaDTO novoNinja = ninjaService.criarNinja(ninjaDTO);

        return ResponseEntity.ok(novoNinja);
    }

    @GetMapping
    public Page<NinjaDTO> listarNinjas(Pageable pageable){

        return ninjaService.listarNinjas(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NinjaDTO> listarNinjasPorId(@PathVariable Long id){

        NinjaDTO ninjaDTO = ninjaService.listarNinjasPorId(id);

        return ResponseEntity.ok(ninjaDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NinjaDTO> atualizarNinja(@PathVariable Long id, @Valid @RequestBody NinjaDTO ninjaDTO){
        NinjaDTO ninjaAtualizado = ninjaService.atualizarNinja(id, ninjaDTO);

        if (ninjaAtualizado != null){
            return ResponseEntity.ok(ninjaAtualizado);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarNinja(@PathVariable Long id){

        ninjaService.deletarNinja(id);

        return ResponseEntity.ok("Ninja deletado com sucesso");
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<NinjaDTO>> buscarPorNome(
            @RequestParam String nome){

        return ResponseEntity.ok(ninjaService.buscarPorNome(nome));
    }

    @GetMapping("/buscarContem")
    public ResponseEntity<List<NinjaDTO>> buscarPorTrecho(@RequestParam String trecho){
        return ResponseEntity.ok(ninjaService.buscarPorTrecho(trecho));
    }

    @GetMapping("/missao/{missaoId}")
    public ResponseEntity<List<NinjaDTO>> buscarPorMissao(@PathVariable Long missaoId){
        return ResponseEntity.ok(ninjaService.buscarNinjaPorMissao(missaoId));
    }
}