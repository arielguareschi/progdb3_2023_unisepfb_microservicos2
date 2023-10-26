package br.unisep.edu.pdb3.orderservice.repository;

import br.unisep.edu.pdb3.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
