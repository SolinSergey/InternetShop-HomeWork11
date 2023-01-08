package api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Модель заказа")
public class OrderDto {
    @Schema(description = "ID заказа", required = true, example = "52")
    private Long id;
    @Schema(description = "Список продуктов входящих в заказ", required = true)
    private List<OrderItemDto> items;
    @Schema(description = "Суммарная стоимость заказа", required = true, example = "10000")
    private int totalPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderDto() {
    }

    public OrderDto(Long id, List<OrderItemDto> items, int totalPrice) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
    }
}
