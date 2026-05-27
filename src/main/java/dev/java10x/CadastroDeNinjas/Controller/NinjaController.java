package dev.java10x.CadastroDeNinjas.Controller;
import dev.java10x.CadastroDeNinjas.DTO.NinjaDTO;
import dev.java10x.CadastroDeNinjas.Model.NinjaModel;
import dev.java10x.CadastroDeNinjas.Service.NinjaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<NinjaDTO> criarNinja(@RequestBody NinjaDTO ninjaDTO) {
        NinjaDTO novoNinja = ninjaService.criarNinja(ninjaDTO);

        return ResponseEntity.ok(novoNinja);
    }

    @GetMapping
    public List<NinjaDTO> listarNinjas(){
        return ninjaService.listarNinjas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NinjaDTO> listarNinjasPorId(@PathVariable Long id){
        NinjaDTO ninjaDTO = ninjaService.listarNinjasPorId(id);

        if(ninjaDTO != null){
            return ResponseEntity.ok(ninjaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<NinjaDTO> atualizarNinja(@PathVariable Long id, @RequestBody NinjaDTO ninjaDTO){
        NinjaDTO ninjaAtualizado = ninjaService.atualizarNinja(id, ninjaDTO);

        if(ninjaAtualizado != null){
            return ResponseEntity.ok(ninjaAtualizado);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarNinja(@PathVariable Long id) {
        boolean deletado = ninjaService.deletarNinja(id);

        if (deletado) {
            return ResponseEntity.ok("Ninja com ID " + id + " deletado com sucesso!");
        }

        return ResponseEntity.status(404).body("Erro: Ninja com ID " + id + " não foi encontrado no sistema.");
    }
}