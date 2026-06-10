package dev.java10x.CadastroDeNinjas.infra.repository;

import dev.java10x.CadastroDeNinjas.domain.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    Optional<UsuarioModel> findByUsername(String username);
}
