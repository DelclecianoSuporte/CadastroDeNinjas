package dev.java10x.CadastroDeNinjas.Controller;
import dev.java10x.CadastroDeNinjas.DTO.NinjaDTO;
import dev.java10x.CadastroDeNinjas.Model.NinjaModel;
import dev.java10x.CadastroDeNinjas.Service.NinjaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
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
    public ResponseEntity<NinjaDTO> criarNinja(@Valid @RequestBody NinjaDTO ninjaDTO) {
        NinjaDTO novoNinja = ninjaService.criarNinja(ninjaDTO);

        return ResponseEntity.ok(novoNinja);
    }

    @GetMapping
    public List<NinjaDTO> listarNinjas(){
        return ninjaService.listarNinjas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NinjaDTO> listarNinjasPorId(@PathVariable @Min(1) Long id){
        NinjaDTO ninjaDTO = ninjaService.listarNinjasPorId(id);

        if(ninjaDTO != null){
            return ResponseEntity.ok(ninjaDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<NinjaDTO> atualizarNinja(@PathVariable Long id, @Valid @RequestBody NinjaDTO ninjaDTO){
        NinjaDTO ninjaAtualizado = ninjaService.atualizarNinja(id, ninjaDTO);

        if(ninjaAtualizado != null){
            return ResponseEntity.ok(ninjaAtualizado);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarNinja(@PathVariable Long id){

        ninjaService.deletarNinja(id);

        return ResponseEntity.ok("Ninja deletado com sucesso");
    }
}