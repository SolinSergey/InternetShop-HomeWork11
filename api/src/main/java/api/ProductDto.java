package api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Модель продукта")
public class ProductDto {
    @Schema(description = "ID продукта", required = true, example = "10")
    private Long id;
    @Schema(description = "Наименование продукта", required = true, example = "Оленина")
    private String title;
    @Schema(description = "Цена продукта", required = true, example = "2500")
    private int price;
    @Schema(description = "Категория продукта", required = true, example = "Мясо")
    private String category;

    public ProductDto() {
    }

    public ProductDto(Long id, String title, int price, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
