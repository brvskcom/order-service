package com.example.orderservice.address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity(name = "Address")
@Table(name = "address")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "product_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "country",
            nullable = false
    )
    private Country country;

    @Column(
            name = "name",
            nullable = false,
            length = 6
    )
    @PostalCode
    private String postalCode;

    @Column(
            name = "name",
            nullable = false
    )
    private String city;

    @Column(
            name = "name",
            nullable = false
    )
    private String voivodeship;

    @Column(
            name = "name",
            nullable = false
    )
    private String street;

    @Column(
            name = "name",
            nullable = false,
            length = 255
    )
    private String additionalInformation;
}
