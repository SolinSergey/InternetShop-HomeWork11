package api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Модель адреса доставки")
public class DeliveryAdressDto {
    @Schema(description = "Город доставуки", required = true, example = "Нижний Новгород")
    private String city;
    @Schema(description = "Наименование улицы в адресе доставки", required = true, example = "ул.Ленина")
    private String street;
    @Schema(description = "Номер дома", required = true, example = "110")
    private String homeNumber;
    @Schema(description = "Номер квартиры", required = true, example = "52")
    private String roomNumber;

    @Override
    public String toString() {
        return "DeliveryAdressDto{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", homeNumber='" + homeNumber + '\'' +
                ", roomNumber='" + roomNumber + '\'' +
                '}';
    }
}
