package ru.gb.internetshop.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.internetshop.core.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}