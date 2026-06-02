package dev.java10x.CadastroDeNinjas.Service;

import dev.java10x.CadastroDeNinjas.DTO.MissaoDTO;
import dev.java10x.CadastroDeNinjas.DTO.NinjaDTO;
import dev.java10x.CadastroDeNinjas.Enums.Dificuldade;
import dev.java10x.CadastroDeNinjas.Exception.MissaoNaoEncontradaException;
import dev.java10x.CadastroDeNinjas.Mapper.MissaoMapper;
import dev.java10x.CadastroDeNinjas.Model.MissaoModel;
import dev.java10x.CadastroDeNinjas.Model.NinjaModel;
import dev.java10x.CadastroDeNinjas.Repository.MissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MissaoService {

    @Autowired
    private MissaoRepository missaoRepository;

    @Autowired
    private MissaoMapper missaoMapper;

    public MissaoDTO criarMissao(MissaoDTO missaoDTO){

        MissaoModel missao = missaoMapper.toModel(missaoDTO);

        MissaoModel missaoSalva = missaoRepository.save(missao);

        return missaoMapper.toDTO(missaoSalva);
    }

    public Page<MissaoDTO> listarNissoes(Pageable pageable){

        Page<MissaoModel> missoes = missaoRepository.findAll(pageable);

        return missoes.map(missaoMapper::toDTO);
    }

    public MissaoDTO listarMissaoPorId(Long id){

        MissaoModel missao = missaoRepository.findById(id).orElseThrow(() -> new MissaoNaoEncontradaException("Missão com ID " + id + " não encontrada"));

        return missaoMapper.toDTO(missao);
    }

    public MissaoDTO atualizarMissao(Long id, MissaoDTO missaoDTO){

        MissaoModel missaoExistente = missaoRepository.findById(id).orElseThrow(() ->
                new MissaoNaoEncontradaException("Missão com ID " + id + " não encontrada"));

        missaoExistente.setNome(missaoDTO.getNome());
        missaoExistente.setDificuldade(missaoDTO.getDificuldade());

        MissaoModel missaoSalva = missaoRepository.save(missaoExistente);

        return missaoMapper.toDTO(missaoSalva);
    }

    public void deletarMissao(Long id){

        if(!missaoRepository.existsById(id)){
            throw new MissaoNaoEncontradaException("Missão com ID " + id + " não encontrada");
        }

        missaoRepository.deleteById(id);
    }

    public List<MissaoDTO> buscaMissaoPorDificuldade(Dificuldade dificuldade){

        List<MissaoModel> missoes = missaoRepository.findByDificuldade(dificuldade);

        return missoes.stream().map(missaoMapper::toDTO).toList();
    }

}
