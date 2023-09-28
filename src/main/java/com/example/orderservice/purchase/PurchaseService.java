package com.example.orderservice.purchase;

import com.example.orderservice.customer.Customer;
import com.example.orderservice.customer.CustomerRepository;
import com.example.orderservice.item.OrderItem;
import com.example.orderservice.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final CustomerRepository customerRepository;

    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase){
        Order order = purchase.getOrder();

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        Customer customer = purchase.getCustomer();
        customer.add(order);


        customerRepository.save(customer);
        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
