package ru.gb.internetshop.core.integrations;

import api.CartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {

    private final WebClient cartServiceWebClient;

    public CartDto getCurrentCart(String username) {
        return cartServiceWebClient.get()
                .uri("/api/v1/cart/0")
                .header("username",username)
                .retrieve()
                .bodyToMono(CartDto.class)
                .block();
    }

    public ResponseEntity clearCart(String username) {
        return cartServiceWebClient.get()
                .uri("/api/v1/cart/0/clear")
                .header("username",username)
                .retrieve()
                .bodyToMono(ResponseEntity.class)
                .block();
    }

}
