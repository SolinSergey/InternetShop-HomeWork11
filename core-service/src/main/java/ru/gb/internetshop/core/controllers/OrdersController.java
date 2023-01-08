package ru.gb.internetshop.core.controllers;

import api.DeliveryAdressDto;
import api.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.gb.internetshop.core.converters.OrderConverter;
import ru.gb.internetshop.core.services.OrderService;
//import ru.gb.internetshop.core.services.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Заказы", description = "Методы работы с заказами")
public class OrdersController {
    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @Operation(
            summary = "Запрос на получение списка заказов",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Заказы не найдены", responseCode = "404"
                    )
            }
    )
    @GetMapping
    public List<OrderDto> getUserOrders(@RequestHeader String username){
        return orderService.findUserOrders(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }

    @Operation(
            summary = "Запрос на создание нового заказа",
            responses = {
                    @ApiResponse(
                            description = "Заказ успешно создан", responseCode = "201"
                    )
            }
    )
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(@RequestHeader String username, @RequestBody DeliveryAdressDto deliveryAdressDto) {
        orderService.createOrder(username,deliveryAdressDto);
    }
}
