package dev.java10x.CadastroDeNinjas.infra.repository;

import dev.java10x.CadastroDeNinjas.domain.model.NinjaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NinjaRepository extends JpaRepository<NinjaModel, Long> {

    List<NinjaModel> findByNome(String nome);
    List<NinjaModel> findByNomeContainingIgnoreCase(String trecho);
    List<NinjaModel> findByMissaoId(Long missaoId);
}
