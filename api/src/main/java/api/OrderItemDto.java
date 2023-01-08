package api;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Модель пунктов заказа")
public class OrderItemDto {
    @Schema(description = "ID продукта", required = true, example = "10")
    private Long productId;
    @Schema(description = "Наименование продукта", required = true, example = "Оленина")
    private String productTitle;
    @Schema(description = "Количество продукта", required = true, example = "10")
    private int quantity;
    @Schema(description = "Цена продукта", required = true, example = "2500")
    private int price;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public OrderItemDto() {
    }

    public OrderItemDto(Long productId, String productTitle, int quantity, int price) {
        this.productId = productId;
        this.productTitle = productTitle;
        this.quantity = quantity;
        this.price = price;
    }
}
