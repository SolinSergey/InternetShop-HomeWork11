package ru.gb.internetshop.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.internetshop.core.entities.DeliveryAdress;
import ru.gb.internetshop.core.entities.OrderItem;

public interface DeliveryAdressRepository extends JpaRepository<DeliveryAdress, Long> {
}