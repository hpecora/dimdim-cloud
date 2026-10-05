package com.dimdim.app.repository;

import com.dimdim.app.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    boolean existsByCategoria_Id(Long categoriaId);

    List<Transacao> findAllByOrderByDataDescIdDesc();
}