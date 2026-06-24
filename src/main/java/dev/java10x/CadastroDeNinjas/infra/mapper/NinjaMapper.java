package dev.java10x.CadastroDeNinjas.infra.mapper;

import dev.java10x.CadastroDeNinjas.application.dto.NinjaDTO;
import dev.java10x.CadastroDeNinjas.domain.model.NinjaModel;
import org.springframework.stereotype.Component;

@Component
public class NinjaMapper {

    public NinjaDTO toDTO(NinjaModel ninja){

        NinjaDTO dto = new NinjaDTO();

        dto.setId(ninja.getId());
        dto.setNome(ninja.getNome());
        dto.setEmail(ninja.getEmail());
        dto.setIdade(ninja.getIdade());

        if(ninja.getMissao() != null){
            dto.setMissaoId(ninja.getMissao().getId());
        }

        return dto;
    }

    public NinjaModel toModel(NinjaDTO dto){

        NinjaModel ninja = new NinjaModel();

        ninja.setNome(dto.getNome());
        ninja.setEmail(dto.getEmail());
        ninja.setIdade(dto.getIdade());

        return ninja;
    }
}
