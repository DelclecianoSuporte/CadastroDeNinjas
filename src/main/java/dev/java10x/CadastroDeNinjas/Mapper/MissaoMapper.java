package dev.java10x.CadastroDeNinjas.Mapper;

import dev.java10x.CadastroDeNinjas.DTO.MissaoDTO;
import dev.java10x.CadastroDeNinjas.Model.MissaoModel;
import org.springframework.stereotype.Component;

@Component
public class MissaoMapper {
    public MissaoDTO toDTO(MissaoModel missao){

        MissaoDTO dto = new MissaoDTO();

        dto.setNome(missao.getNome());
        dto.setDificuldade(missao.getDificuldade());

        return dto;
    }

    public MissaoModel toModel(MissaoDTO dto){

        MissaoModel missao = new MissaoModel();

        missao.setNome(dto.getNome());
        missao.setDificuldade(dto.getDificuldade());

        return missao;
    }
}
