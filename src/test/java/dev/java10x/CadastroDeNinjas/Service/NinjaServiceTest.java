package dev.java10x.CadastroDeNinjas.Service;

import dev.java10x.CadastroDeNinjas.DTO.NinjaDTO;
import dev.java10x.CadastroDeNinjas.Exception.MissaoNaoEncontradaException;
import dev.java10x.CadastroDeNinjas.Mapper.NinjaMapper;
import dev.java10x.CadastroDeNinjas.Model.NinjaModel;
import dev.java10x.CadastroDeNinjas.Repository.MissaoRepository;
import dev.java10x.CadastroDeNinjas.Repository.NinjaRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import dev.java10x.CadastroDeNinjas.Exception.NinjaNaoEncontradoException;
import static org.mockito.Mockito.verify;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class NinjaServiceTest {

    @Mock
    private NinjaRepository ninjaRepository;

    @InjectMocks
    private NinjaService ninjaService;

    @Mock
    private NinjaMapper ninjaMapper;

    @Mock
    private MissaoRepository missaoRepository;

    @Test
    public void deveRetornarNinjaQuandoIdExistir() {

        // Arrange
        NinjaModel ninja = new NinjaModel();
        ninja.setId(1L);
        ninja.setNome("Naruto");

        NinjaDTO ninjaDTO = new NinjaDTO();
        ninjaDTO.setNome("Naruto");

        when(ninjaRepository.findById(1L))
                .thenReturn(Optional.of(ninja));

        when(ninjaMapper.toDTO(ninja))
                .thenReturn(ninjaDTO);

        NinjaDTO resultado = ninjaService.listarNinjasPorId(1L);

        assertNotNull(resultado);
        assertEquals("Naruto", resultado.getNome());
    }

    @Test
    public void deveLancarExcecaoQuandoNinjaNaoExistir() {

        when(ninjaRepository.findById(99L))
                .thenReturn(Optional.empty());

        NinjaNaoEncontradoException exception =
                assertThrows(
                        NinjaNaoEncontradoException.class,
                        () -> ninjaService.listarNinjasPorId(99L)
                );

        assertEquals(
                "Ninja com ID 99 não encontrado",
                exception.getMessage()
        );
    }

    @Test
    public void deveCriarNinjaSemMissao() {

        NinjaDTO ninjaDTO = new NinjaDTO();

        ninjaDTO.setNome("Naruto");
        ninjaDTO.setEmail("naruto@email.com");
        ninjaDTO.setIdade(17);

        NinjaModel ninjaModel = new NinjaModel();

        ninjaModel.setNome("Naruto");
        ninjaModel.setEmail("naruto@email.com");
        ninjaModel.setIdade(17);

        when(ninjaMapper.toModel(ninjaDTO))
                .thenReturn(ninjaModel);

        when(ninjaRepository.save(any(NinjaModel.class)))
                .thenReturn(ninjaModel);

        NinjaDTO resultado = ninjaService.criarNinja(ninjaDTO);

        assertNotNull(resultado);
    }

    @Test
    public void deveLancarExcecaoQuandoMissaoNaoExistir() {

        NinjaDTO ninjaDTO = new NinjaDTO();

        ninjaDTO.setNome("Naruto");
        ninjaDTO.setEmail("naruto@email.com");
        ninjaDTO.setIdade(17);
        ninjaDTO.setMissaoId(99L);

        NinjaModel ninjaModel = new NinjaModel();

        when(ninjaMapper.toModel(ninjaDTO))
                .thenReturn(ninjaModel);

        when(missaoRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                MissaoNaoEncontradaException.class,
                () -> ninjaService.criarNinja(ninjaDTO)
        );
    }

    @Test
    public void deveAtualizarNinjaQuandoIdExistir() {

        // Arrange
        Long id = 1L;

        NinjaModel ninjaExistente = new NinjaModel();
        ninjaExistente.setId(id);
        ninjaExistente.setNome("Naruto");

        NinjaDTO ninjaDTO = new NinjaDTO();
        ninjaDTO.setNome("Naruto Uzumaki");
        ninjaDTO.setEmail("naruto@email.com");
        ninjaDTO.setIdade(18);

        NinjaModel ninjaAtualizado = new NinjaModel();
        ninjaAtualizado.setId(id);
        ninjaAtualizado.setNome("Naruto Uzumaki");
        ninjaAtualizado.setEmail("naruto@email.com");
        ninjaAtualizado.setIdade(18);

        NinjaDTO ninjaRetorno = new NinjaDTO();
        ninjaRetorno.setNome("Naruto Uzumaki");
        ninjaRetorno.setEmail("naruto@email.com");
        ninjaRetorno.setIdade(18);

        when(ninjaRepository.findById(id))
                .thenReturn(Optional.of(ninjaExistente));

        when(ninjaRepository.save(any(NinjaModel.class)))
                .thenReturn(ninjaAtualizado);

        when(ninjaMapper.toDTO(ninjaAtualizado))
                .thenReturn(ninjaRetorno);

        NinjaDTO resultado =
                ninjaService.atualizarNinja(id, ninjaDTO);

        assertNotNull(resultado);
        assertEquals("Naruto Uzumaki", resultado.getNome());
    }

    @Test
    public void deveLancarExcecaoQuandoNinjaNaoExistirAoAtualizar() {

        Long id = 99L;

        NinjaDTO ninjaDTO = new NinjaDTO();
        ninjaDTO.setNome("Naruto");

        when(ninjaRepository.findById(id))
                .thenReturn(Optional.empty());

        NinjaNaoEncontradoException exception =
                assertThrows(
                        NinjaNaoEncontradoException.class,
                        () -> ninjaService.atualizarNinja(id, ninjaDTO)
                );

        assertEquals(
                "Ninja com ID 99 não encontrado",
                exception.getMessage()
        );
    }

    @Test
    public void deveDeletarNinjaExistente() {

        when(ninjaRepository.existsById(1L))
                .thenReturn(true);

        ninjaService.deletarNinja(1L);

        verify(ninjaRepository).deleteById(1L);
    }

    @Test
    public void deveLancarExcecaoQuandoNinjaNaoExistirAoDeletar() {

        when(ninjaRepository.existsById(99L))
                .thenReturn(false);

        NinjaNaoEncontradoException exception =
                assertThrows(
                        NinjaNaoEncontradoException.class,
                        () -> ninjaService.deletarNinja(99L)
                );

        assertEquals(
                "Ninja com ID 99 não encontrado",
                exception.getMessage()
        );
    }

    @Test
    public void deveBuscarNinjaPorNome() {

        NinjaModel ninjaModel = new NinjaModel();
        ninjaModel.setNome("Naruto");

        NinjaDTO ninjaDTO = new NinjaDTO();
        ninjaDTO.setNome("Naruto");

        when(ninjaRepository.findByNome("Naruto"))
                .thenReturn(List.of(ninjaModel));

        when(ninjaMapper.toDTO(ninjaModel))
                .thenReturn(ninjaDTO);

        List<NinjaDTO> resultado =
                ninjaService.buscarPorNome("Naruto");

        assertNotNull(resultado);

        assertEquals(1, resultado.size());

        assertEquals(
                "Naruto",
                resultado.get(0).getNome()
        );
    }

    @Test
    public void deveBuscarNinjaPorTrechoDoNome() {

        NinjaModel ninjaModel = new NinjaModel();
        ninjaModel.setNome("Naruto Uzumaki");

        NinjaDTO ninjaDTO = new NinjaDTO();
        ninjaDTO.setNome("Naruto Uzumaki");

        when(
                ninjaRepository.findByNomeContainingIgnoreCase("Nar")
        ).thenReturn(List.of(ninjaModel));

        when(ninjaMapper.toDTO(ninjaModel))
                .thenReturn(ninjaDTO);

        List<NinjaDTO> resultado =
                ninjaService.buscarPorTrecho("Nar");

        assertNotNull(resultado);

        assertEquals(1, resultado.size());

        assertEquals(
                "Naruto Uzumaki",
                resultado.get(0).getNome()
        );
    }

    @Test
    public void deveRetornarListaVaziaQuandoNomeNaoForEncontrado() {

        when(ninjaRepository.findByNome("Sasuke"))
                .thenReturn(List.of());

        List<NinjaDTO> resultado =
                ninjaService.buscarPorNome("Sasuke");

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void deveRetornarListaVaziaQuandoTrechoNaoForEncontrado() {

        when(ninjaRepository.findByNomeContainingIgnoreCase("SA"))
                .thenReturn(List.of());

        List<NinjaDTO> resultado =
                ninjaService.buscarPorTrecho("SA");

        assertTrue(resultado.isEmpty());
    }


}
