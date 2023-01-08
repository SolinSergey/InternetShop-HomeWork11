package ru.gb.internetshop.core.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="delivery_adress")
@NoArgsConstructor
@Data
public class DeliveryAdress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="adress")
    private String adress;

}
