package com.example.orderservice.customer;

import com.example.orderservice.order.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "Customer")
@Table(name = "customer")
public class Customer {

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
            name = "first_name",
            nullable = false,
            updatable = false
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false,
            updatable = false
    )
    private String lastName;

    @Email
    @Column(
            name = "email",
            nullable = false,
            updatable = false
    )
    private String email;

    @Column(
            name = "phone_number",
            nullable = true,
            updatable = false
    )
    @PhoneNumber
    private String phoneNumber;

    private boolean mailNotification;
    private boolean smsNotification;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Order> orders = new HashSet<>();

    public void add(Order order) {

        if (order != null) {

            if (orders == null) {
                orders = new HashSet<>();
            }

            orders.add(order);
            order.setCustomer(this);
        }
    }
}
