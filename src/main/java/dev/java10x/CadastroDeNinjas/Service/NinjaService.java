package dev.java10x.CadastroDeNinjas.Service;

import dev.java10x.CadastroDeNinjas.DTO.NinjaDTO;
import dev.java10x.CadastroDeNinjas.Model.NinjaModel;
import dev.java10x.CadastroDeNinjas.Repository.NinjaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NinjaService {

    @Autowired
    private NinjaRepository ninjaRepository;

    public NinjaDTO criarNinja(NinjaDTO ninjaDTO){

        NinjaModel ninja = new NinjaModel();

        ninja.setNome(ninjaDTO.getNome());
        ninja.setEmail(ninjaDTO.getEmail());
        ninja.setIdade(ninjaDTO.getIdade());

        NinjaModel ninjaSalvo = ninjaRepository.save(ninja);

        return new NinjaDTO(
                ninjaSalvo.getNome(),
                ninjaSalvo.getEmail(),
                ninjaSalvo.getIdade()
        );
    }

    public List<NinjaDTO> listarNinjas(){

        List<NinjaModel> ninjas = ninjaRepository.findAll();
        List<NinjaDTO> ninjaDTOs = new ArrayList<>();

        for(NinjaModel ninja : ninjas){

            NinjaDTO dto = new NinjaDTO();

            dto.setNome(ninja.getNome());
            dto.setEmail(ninja.getEmail());
            dto.setIdade(ninja.getIdade());

            ninjaDTOs.add(dto);
        }

        return ninjaDTOs;
    }

    public NinjaDTO listarNinjasPorId(Long id){

        NinjaModel ninja = ninjaRepository.findById(id).orElse(null);

        if (ninja != null){
            NinjaDTO dto = new NinjaDTO();
            dto.setNome(ninja.getNome());
            dto.setEmail(ninja.getEmail());
            dto.setIdade(ninja.getIdade());

            return dto;
        }

        return null;
    }

    public NinjaDTO atualizarNinja(Long id, NinjaDTO ninjaDTO){

        NinjaModel ninjaExistente = ninjaRepository.findById(id).orElse(null);

        if (ninjaExistente != null){
            ninjaExistente.setNome(ninjaDTO.getNome());
            ninjaExistente.setIdade(ninjaDTO.getIdade());
            ninjaExistente.setEmail(ninjaDTO.getEmail());

            NinjaModel ninjaSalvo = ninjaRepository.save(ninjaExistente);

            NinjaDTO dto = new NinjaDTO();

            dto.setNome(ninjaSalvo.getNome());
            dto.setEmail(ninjaSalvo.getEmail());
            dto.setIdade(ninjaSalvo.getIdade());

            return dto;
        }

        return null;
    }

    public boolean deletarNinja(Long id) {

        if (ninjaRepository.existsById(id)){
            ninjaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
