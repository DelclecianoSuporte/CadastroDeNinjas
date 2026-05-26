package dev.java10x.CadastroDeNinjas.Controller;
import dev.java10x.CadastroDeNinjas.Model.NinjaModel;
import dev.java10x.CadastroDeNinjas.Service.NinjaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public NinjaModel criarNinja(@RequestBody NinjaModel ninja){
        return ninjaService.criarNinja(ninja);
    }

    @GetMapping
    public List<NinjaModel> listarNinjas(){
        return ninjaService.listarNinjas();
    }

    @GetMapping({"/{id}"})
    public NinjaModel ListarNinjasPorId(@PathVariable Long id){
        return ninjaService.listarNinjasPorId(id);
    }

    @PutMapping("/{id}")
    public NinjaModel atualizarNinja(@PathVariable Long id, @RequestBody NinjaModel ninja) {
        return ninjaService.atualizarNinja(id, ninja);
    }

    @DeleteMapping("/{id}")
    public String deletarNinja(@PathVariable Long id) {
        return  ninjaService.deletarNinja(id);
    }

}
