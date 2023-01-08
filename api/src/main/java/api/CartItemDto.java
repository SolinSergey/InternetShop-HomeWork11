package api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель позиции данных корзины")
public class CartItemDto {
    @Schema(description = "ID продукта", required = true, example = "20")
    private Long id;
    @Schema(description = "Наименование продукта", required = true, example = "Оленина")
    private String title;
    @Schema(description = "Цена продукта", required = true, example = "2500")
    private int price;
    @Schema(description = "Количество продукта", required = true, example = "20")
    private int amount;
    @Schema(description = "Суммарная стоимость продуктов данной позиции", required = true, example = "50000")
    private int totalPrice;

    public CartItemDto() {
    }

    public CartItemDto(Long id, String title, int price, int amount, int totalPrice) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.amount = amount;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void calcTotalPrice(){
        totalPrice=price*amount;
    }

    @Override
    public String toString() {
        return "CartItemDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
