package com.example.orderservice.purchase;

import com.example.orderservice.address.Address;
import com.example.orderservice.customer.Customer;
import com.example.orderservice.item.OrderItem;
import com.example.orderservice.order.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;

}
