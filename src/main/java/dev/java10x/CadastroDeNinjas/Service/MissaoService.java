package dev.java10x.CadastroDeNinjas.Service;

import dev.java10x.CadastroDeNinjas.DTO.MissaoDTO;
import dev.java10x.CadastroDeNinjas.DTO.NinjaDTO;
import dev.java10x.CadastroDeNinjas.Model.MissaoModel;
import dev.java10x.CadastroDeNinjas.Model.NinjaModel;
import dev.java10x.CadastroDeNinjas.Repository.MissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MissaoService {

    @Autowired
    private MissaoRepository missaoRepository;

    public MissaoDTO criarMissao(MissaoDTO missaoDTO){

        MissaoModel missao = new MissaoModel();

        missao.setNome(missaoDTO.getNome());
        missao.setDificuldade(missaoDTO.getDificuldade());

        MissaoModel missaoSalva = missaoRepository.save(missao);

        return new MissaoDTO(
                missaoSalva.getNome(),
                missaoSalva.getDificuldade()
        );
    }

    public Page<MissaoDTO> listarNissoes(Pageable pageable){

        Page<MissaoModel> missoes = missaoRepository.findAll(pageable);

        return missoes.map(missao -> {

            MissaoDTO dto = new MissaoDTO();

            dto.setNome(missao.getNome());
            dto.setDificuldade(missao.getDificuldade());

            return dto;
        });
    }

    public MissaoDTO listarMissaoPorId(Long id){

        MissaoModel missao = missaoRepository.findById(id).orElse(null);

        if (missao != null){
            MissaoDTO dto = new MissaoDTO();
            dto.setNome(missao.getNome());
            dto.setDificuldade(missao.getDificuldade());

            return dto;
        }

        return null;
    }

    public MissaoDTO atualizarMissao(Long id, MissaoDTO missaoDTO){

        MissaoModel missaoExistente = missaoRepository.findById(id).orElse(null);

        if (missaoExistente != null){
            missaoExistente.setNome(missaoDTO.getNome());
            missaoExistente.setDificuldade(missaoDTO.getDificuldade());

            MissaoModel missaoSalva = missaoRepository.save(missaoExistente);

            MissaoDTO dto = new MissaoDTO();
            dto.setNome(missaoSalva.getNome());
            dto.setDificuldade(missaoSalva.getDificuldade());

            return dto;
        }

        return null;
    }

    public void deletarMissao(Long id){

        if(!missaoRepository.existsById(id)){
            throw new RuntimeException("Missao com ID " + id + " não encontrada");
        }

        missaoRepository.deleteById(id);
    }

}
