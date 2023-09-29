package com.example.orderservice.purchase;

import com.brvsk.commons.event.MailNotificationType;
import com.brvsk.commons.event.OrderEvent;
import com.example.orderservice.customer.Customer;
import com.example.orderservice.customer.CustomerRepository;
import com.example.orderservice.item.OrderItem;
import com.example.orderservice.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final CustomerRepository customerRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

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

        // TODO: 9/29/2023 customer need to pass an email
        String fakeUserEmail = "kacpersjuszb@gmail.com";

        OrderEvent orderEvent = new OrderEvent(fakeUserEmail, orderTrackingNumber, MailNotificationType.ORDER_PLACED);
        kafkaTemplate.send("notificationTopic", orderEvent);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
