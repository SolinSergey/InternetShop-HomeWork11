package ru.gb.internetshop.core.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.gb.internetshop.core.entities.Order;

import java.util.List;


@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByUsername(String username);

}
