package ru.gb.internetshop.core.services;
import api.CartDto;
import api.CartItemDto;
import api.DeliveryAdressDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.gb.internetshop.core.entities.DeliveryAdress;
import ru.gb.internetshop.core.entities.Order;
import ru.gb.internetshop.core.entities.OrderItem;
import ru.gb.internetshop.core.entities.Product;
import ru.gb.internetshop.core.exceptions.ResourceNotFoundException;
import ru.gb.internetshop.core.integrations.CartServiceIntegration;
import ru.gb.internetshop.core.repositories.DeliveryAdressRepository;
import ru.gb.internetshop.core.repositories.OrderItemRepository;
import ru.gb.internetshop.core.repositories.OrderRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CartServiceIntegration cartServiceIntegration;

    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void createOrder(String username, DeliveryAdressDto deliveryAdressDto){
        DeliveryAdress deliveryAdress=new DeliveryAdress();
        deliveryAdress.setAdress(deliveryAdressDto.getCity()+" "+deliveryAdressDto.getStreet()+" "+deliveryAdressDto.getHomeNumber()+" "+deliveryAdressDto.getRoomNumber());
        CartDto cartDto = cartServiceIntegration.getCurrentCart(username);
        if (cartDto.getItems().isEmpty()){
            throw new IllegalStateException("Нельзя оформить заказ для пустой корзины");
        }
        Order order=new Order();
        order.setUsername(username);
        order.setTotalPrice(cartDto.getTotalPrice());
        List<OrderItem> orderItemList = new ArrayList<>();
        for (CartItemDto c: cartDto.getItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setPrice(c.getPrice());
            orderItem.setAmount(c.getAmount());
            Product product=productService.findById(c.getId()).orElseThrow(()->new ResourceNotFoundException("Продукт с id="+c.getId()+" не найден"));
            orderItem.setProduct(product);
            orderItemList.add(orderItem);
        }
        order.setItems(orderItemList);
        order.setAdress(deliveryAdress);
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItemList);
        cartServiceIntegration.clearCart(username);
    }

    public List<Order> findUserOrders (String username){
        return orderRepository.findAllByUsername(username);
    }
}
