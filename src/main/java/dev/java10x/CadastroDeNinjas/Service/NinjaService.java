package dev.java10x.CadastroDeNinjas.Service;

import dev.java10x.CadastroDeNinjas.Model.NinjaModel;
import dev.java10x.CadastroDeNinjas.Repository.NinjaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NinjaService {

    @Autowired
    private NinjaRepository ninjaRepository;

    public NinjaModel criarNinja(NinjaModel ninja){
        return ninjaRepository.save(ninja);
    }

    public List<NinjaModel> listarNinjas(){
        return ninjaRepository.findAll();
    }

    public NinjaModel listarNinjasPorId(long id){
        return ninjaRepository.findById(id).orElse(null);
    }

    public NinjaModel atualizarNinja(Long id, NinjaModel ninjaAtualizado){
        NinjaModel ninjaExistente = ninjaRepository.findById(id).orElse(null);

        if (ninjaExistente != null){
            ninjaExistente.setNome(ninjaAtualizado.getNome());
            ninjaExistente.setIdade(ninjaAtualizado.getIdade());
            ninjaExistente.setEmail(ninjaAtualizado.getEmail());

            return ninjaRepository.save(ninjaExistente);
        }

        return null;
    }


    public String deletarNinja(Long id) {

        if(ninjaRepository.existsById(id)){
            ninjaRepository.deleteById(id);
            return "Ninja deletado com sucesso";
        }

        return "Ninja não encontrado";
    }

    /*
    public void deletarNinja(Long id) {
        ninjaRepository.deleteById(id);
    }*/
}
