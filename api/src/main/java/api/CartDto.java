package api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Модель корзины")
public class CartDto {
    @Schema(description = "Список содержащий позиции корзины", required = true)
    List<CartItemDto> items=new ArrayList<>();
    @Schema(description = "Суммарная стоимость всех позиций корзины", required = true, example = "25000")
    private int totalPrice;

    public CartDto() {
    }

    public List<CartItemDto> getItems() {
        return items;
    }

    public void setItems(List<CartItemDto> items) {
        this.items = items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int calcTotalPrice(){
        totalPrice=0;
        for (CartItemDto i:items){
            totalPrice+=i.getTotalPrice();
        }
        return totalPrice;
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "items=" + items +
                ", totalPrice=" + totalPrice +
                '}';
    }

}
