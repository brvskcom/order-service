package com.example.orderservice.purchase;

import com.brvsk.commons.event.MailNotificationType;
import com.brvsk.commons.event.OrderEvent;
import com.brvsk.commons.event.OrderNotificationMessage;
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
    private final KafkaTemplate<String, OrderNotificationMessage> kafkaTemplate;

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

        String customerEmail = purchase.getCustomer().getEmail();
        String mailNotificationTypeString = MailNotificationType.ORDER_PLACED.toString();
        OrderNotificationMessage orderNotificationMessage = new OrderNotificationMessage(customerEmail, orderTrackingNumber, mailNotificationTypeString);
        kafkaTemplate.send("notificationTopic", orderNotificationMessage);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
