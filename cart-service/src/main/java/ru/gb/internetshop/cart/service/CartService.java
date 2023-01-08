package ru.gb.internetshop.cart.service;

import api.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.gb.internetshop.cart.integrations.ProductServiceIntegration;
import ru.gb.internetshop.cart.utils.Cart;
import ru.gb.internetshop.cart.utils.CartItem;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String,Object> redisTemplate;

    public Cart getCurrentCart(String cartId){
        if (!redisTemplate.hasKey(cartId)){
            Cart cart = new Cart();
            redisTemplate.opsForValue().set(cartId,cart);
        }

        return (Cart)redisTemplate.opsForValue().get(cartId);
    }

    public void subToCart(String cartId,Long productId){

        execute(cartId,cart -> {
            for (CartItem i: cart.getItems()){
                if (productId.equals(i.getProductId()) && i.getAmount()>1) {
                    i.decrementAmount();
                    break;
                }else if (productId.equals(i.getProductId())){
                    cart.remove(i);
                    break;
                }
            }
        });
    }

    public void removeItem(String cartId,Long productId){
        execute(cartId,cart -> {
            for (CartItem i: cart.getItems()){
                if (productId.equals(i.getProductId())){
                    cart.remove(i);
                    break;
                }
            }
        });
    }
    public void addToCart(String cartId, Long productId){
        ProductDto p = productServiceIntegration.findById(productId);
        execute(cartId,cart -> cart.add(p));
    }

    public void clearCart(String cartId){
        execute(cartId,cart -> cart.clear());
    }

    public void mergeCarts(String username, String guestCartId){
        Cart userCart=getCurrentCart(username);
        Cart guestCart=getCurrentCart(guestCartId);

        if (guestCart.getItems().size()!=0){
            for (CartItem g:guestCart.getItems()){
                ProductDto productDto=new ProductDto();
                productDto.setId(g.getProductId());
                productDto.setTitle(g.getProductTitle());
                productDto.setPrice(g.getPrice());
                for (int i=0;i<g.getAmount();i++){
                    userCart.add(productDto);
                }
            }
        }

        redisTemplate.opsForValue().set(username,userCart);
        redisTemplate.opsForValue().set(guestCartId,guestCart);
    }

    private void execute (String cartId, Consumer<Cart> action){
        Cart cart=getCurrentCart(cartId);
        action.accept(cart);
        redisTemplate.opsForValue().set(cartId,cart);
    }
}
