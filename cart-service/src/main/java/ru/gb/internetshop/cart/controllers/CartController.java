package ru.gb.internetshop.cart.controllers;

import api.CartDto;
import api.ProductDto;
import api.StringResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.internetshop.cart.converters.CartConverter;
import ru.gb.internetshop.cart.exceptions.AppError;
import ru.gb.internetshop.cart.service.CartService;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Контроллер корзины", description = "Методы работы с корзиной")
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @Operation(
            summary = "Генерация id коорзины",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = StringResponse.class))
                    )
            }
    )
    @GetMapping("/generate_id")
    public StringResponse generateGuestCartId(){
        StringResponse newUUID=new StringResponse(UUID.randomUUID().toString());
        return newUUID;
    }

    @Operation(
            summary = "Запрос на добавление продукта по id в корзину с guestCardId",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{guestCardId}/add/{productId}")
    public void addProductToCart(@RequestHeader(required = false) String username, @PathVariable String guestCardId,@PathVariable Long productId){
        String currentCardId = selectCartId(username,guestCardId);
        cartService.addToCart(currentCardId,productId);
    }

    @Operation(
            summary = "Запрос на уменьшение количества продукта с id в корзине с guestCardId",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{guestCardId}/sub/{productId}")
    public void subProductFromCurrentCart(@RequestHeader(required = false) String username, @PathVariable String guestCardId,@PathVariable Long productId){
        String currentCardId = selectCartId(username,guestCardId);
        cartService.subToCart(currentCardId,productId);
    }

    @Operation(
            summary = "Запрос на удаление позиции продукта с id целиком",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @DeleteMapping("/{guestCardId}/remove/{id}")
    public void removeItem(@RequestHeader(required = false) String username, @PathVariable String guestCardId,@PathVariable Long id) {
        String currentCardId = selectCartId(username,guestCardId);
        cartService.removeItem(currentCardId,id);
    }

    @Operation(
            summary = "Запрос на получение данных корзины с guestCardId",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = CartDto.class))
                    ),
                    @ApiResponse(
                            description = "Нет данных", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = AppError.class))
                    )
            }
    )
    @GetMapping("/{guestCardId}")
    public CartDto getCurrentCart(@RequestHeader(required = false) String username, @PathVariable String guestCardId){
        String currentCardId = selectCartId(username,guestCardId);
        CartDto cartDto=cartConverter.entityToCartDto(cartService.getCurrentCart(currentCardId));
        return cartDto;
    }

    @Operation(
            summary = "Запрос на очистку корзины с guestCardId",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/{guestCardId}/clear")
    public void clearCart(@RequestHeader(required = false) String username, @PathVariable String guestCardId){
        String currentCardId = selectCartId(username,guestCardId);
        cartService.clearCart(currentCardId);
    }

    private String selectCartId(String username, String guestCartId) {
        if (username != null) {
            return username;
        }
        return guestCartId;
    }

    @Operation(
            summary = "Запрос на объединение данных корзины с guestCardId и корзины с username",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    )
            }
    )
    @GetMapping("/merge/{guestCartId}/{username}")
    public ResponseEntity mergeCart(@PathVariable String username, @PathVariable String guestCartId){
        cartService.mergeCarts(username,guestCartId);
        return new ResponseEntity(HttpStatus.OK);
    }
}
