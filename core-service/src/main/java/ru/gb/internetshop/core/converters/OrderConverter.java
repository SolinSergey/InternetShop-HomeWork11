package ru.gb.internetshop.core.converters;

import api.OrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.internetshop.core.entities.Order;


import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final OrderItemConverter orderItemConverter;

    public OrderDto entityToDto(Order o) {
        return new OrderDto(o.getId(), o.getItems().stream().map(orderItemConverter::entityToDto).collect(Collectors.toList()), o.getTotalPrice());
    }
}
