package ru.gb.internetshop.core.converters;

import api.OrderItemDto;
import org.springframework.stereotype.Component;
import ru.gb.internetshop.core.entities.OrderItem;


@Component
public class OrderItemConverter {
    public OrderItemDto entityToDto(OrderItem o) {
        return new OrderItemDto(o.getProduct().getId(), o.getProduct().getTitle(), o.getAmount(), o.getPrice());
    }
}
