package com.ronaldosantos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ronaldosantos.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

}
