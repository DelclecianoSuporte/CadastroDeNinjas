package dev.java10x.CadastroDeNinjas.infra.mapper;

import dev.java10x.CadastroDeNinjas.application.dto.MissaoDTO;
import dev.java10x.CadastroDeNinjas.domain.model.MissaoModel;
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
