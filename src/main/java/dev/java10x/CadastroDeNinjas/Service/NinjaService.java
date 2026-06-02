package dev.java10x.CadastroDeNinjas.Service;

import dev.java10x.CadastroDeNinjas.DTO.NinjaDTO;
import dev.java10x.CadastroDeNinjas.Exception.MissaoNaoEncontradaException;
import dev.java10x.CadastroDeNinjas.Exception.NinjaNaoEncontradoException;
import dev.java10x.CadastroDeNinjas.Mapper.NinjaMapper;
import dev.java10x.CadastroDeNinjas.Model.MissaoModel;
import dev.java10x.CadastroDeNinjas.Model.NinjaModel;
import dev.java10x.CadastroDeNinjas.Repository.MissaoRepository;
import dev.java10x.CadastroDeNinjas.Repository.NinjaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

@Service
public class NinjaService {

    @Autowired
    private NinjaRepository ninjaRepository;

    @Autowired
    private MissaoRepository missaoRepository;

    @Autowired
    private NinjaMapper ninjaMapper;

    public NinjaDTO criarNinja(NinjaDTO ninjaDTO){

        NinjaModel ninja = ninjaMapper.toModel(ninjaDTO);

        // Verifica se o ninja vai ter vinculo com missão
        if(ninjaDTO.getMissaoId() != null){
            MissaoModel missao = missaoRepository.findById(ninjaDTO.getMissaoId())
                    .orElseThrow(() -> new MissaoNaoEncontradaException("Missão com ID " + ninjaDTO.getMissaoId() + " não encontrada"));

            ninja.setMissao(missao);
        }

        NinjaModel ninjaSalvo = ninjaRepository.save(ninja);

        NinjaDTO dto = new NinjaDTO();

        dto.setNome(ninjaSalvo.getNome());
        dto.setEmail(ninjaSalvo.getEmail());
        dto.setIdade(ninjaSalvo.getIdade());

        if(ninjaSalvo.getMissao() != null){
            dto.setMissaoId(ninjaSalvo.getMissao().getId());
        }

        return dto;
    }

    public Page<NinjaDTO> listarNinjas(Pageable pageable){

        Page<NinjaModel> ninjas = ninjaRepository.findAll(pageable);

        return ninjas.map(ninjaMapper::toDTO);
    }

    public NinjaDTO listarNinjasPorId(Long id){

        NinjaModel ninja = ninjaRepository.findById(id)
                .orElseThrow(() ->
                        new NinjaNaoEncontradoException("Ninja com ID " + id + " não encontrado"));

        return ninjaMapper.toDTO(ninja);
    }

    public NinjaDTO atualizarNinja(Long id, NinjaDTO ninjaDTO){

        NinjaModel ninjaExistente = ninjaRepository.findById(id)
                .orElseThrow(() ->
                        new NinjaNaoEncontradoException(
                                "Ninja com ID " + id + " não encontrado"
                        ));

        ninjaExistente.setNome(ninjaDTO.getNome());
        ninjaExistente.setEmail(ninjaDTO.getEmail());
        ninjaExistente.setIdade(ninjaDTO.getIdade());

        if(ninjaDTO.getMissaoId() != null){

            MissaoModel missao = missaoRepository.findById(ninjaDTO.getMissaoId())
                    .orElseThrow(() ->
                    new MissaoNaoEncontradaException("Missão com ID " + ninjaDTO.getMissaoId() + " não encontrada"));

            ninjaExistente.setMissao(missao);
        }
        else {
            ninjaExistente.setMissao(null);
        }

        NinjaModel ninjaSalvo = ninjaRepository.save(ninjaExistente);

        return ninjaMapper.toDTO(ninjaSalvo);
    }

    public void deletarNinja(Long id){
        if(!ninjaRepository.existsById(id)){
            throw new NinjaNaoEncontradoException("Ninja com ID " + id + " não encontrado");
        }
        ninjaRepository.deleteById(id);
    }

    public List<NinjaDTO> buscarPorNome(String nome){

        List<NinjaModel> ninjas = ninjaRepository.findByNome(nome);

        return ninjas.stream().map(ninjaMapper::toDTO).toList();
    }

    public List<NinjaDTO> buscarPorTrecho(String trecho) {

        List<NinjaModel> ninjas = ninjaRepository.findByNomeContainingIgnoreCase(trecho);

        return ninjas.stream().map(ninjaMapper::toDTO).toList();
    }

    public List<NinjaDTO> buscarNinjaPorMissao(Long missaoId){

        List<NinjaModel> ninjas = ninjaRepository.findByMissaoId(missaoId);

        return ninjas.stream()
                .map(ninja -> {

                    NinjaDTO dto = new NinjaDTO();

                    dto.setNome(ninja.getNome());
                    dto.setEmail(ninja.getEmail());
                    dto.setIdade(ninja.getIdade());

                    if(ninja.getMissao() != null){
                        dto.setMissaoId(ninja.getMissao().getId());
                    }

                    return dto;
                })
                .toList();
    }
}
