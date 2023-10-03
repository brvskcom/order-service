package com.example.orderservice.purchase;

import com.brvsk.commons.event.*;
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
    private final KafkaTemplate<String, OrderMailMessage> kafkaMailTemplate;
    private final KafkaTemplate<String, OrderSMSMessage> kafkaSmsTemplate;

    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase){
        Order order = purchase.getOrder();

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        Customer customer = purchase.getCustomer();
        customer.add(order);

        customerRepository.save(customer);

        sendNotifications(customer, orderTrackingNumber);

        return new PurchaseResponse(orderTrackingNumber);
    }

    private void sendNotifications(Customer customer, String orderTrackingNumber){
        if (customer.isMailNotification()){
            sendOrderMailMessage(customer.getEmail(), orderTrackingNumber);
        }

        if (customer.isSmsNotification()){
            sendOrderSMSMessage(customer.getPhoneNumber(), orderTrackingNumber);
        }

    }

    private void sendOrderMailMessage(String customerEmail, String orderTrackingNumber){
        String mailNotificationTypeString = OrderMailType.ORDER_PLACED.toString();
        OrderMailMessage orderMailMessage = new OrderMailMessage(customerEmail, orderTrackingNumber, mailNotificationTypeString);
        kafkaMailTemplate.send("mailNotificationTopic", orderMailMessage);
    }
    private void sendOrderSMSMessage(String customerPhoneNumber, String orderTrackingNumber){
        String orderSMSTypeString = OrderSMSType.ORDER_PLACED.toString();
        OrderSMSMessage orderSMSMessage = new OrderSMSMessage(customerPhoneNumber,orderTrackingNumber, orderSMSTypeString);
        kafkaSmsTemplate.send("smsNotificationTopic", orderSMSMessage);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
