package dev.java10x.CadastroDeNinjas.Repository;

import dev.java10x.CadastroDeNinjas.Model.NinjaModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NinjaRepository extends JpaRepository<NinjaModel, Long> {
}
