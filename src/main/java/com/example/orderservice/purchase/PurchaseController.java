package com.example.orderservice.purchase;


import com.brvsk.commons.clients.payment.PaymentRequest;
import com.brvsk.commons.clients.payment.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
public class PurchaseController {

    private final PurchaseService purchaseService;


    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {

        PurchaseResponse purchaseResponse = purchaseService.placeOrder(purchase);

        return purchaseResponse;
    }

    @PostMapping("/pay")
    public PaymentResponse payForOrder(
            @RequestBody PaymentRequest paymentRequest,
            @RequestParam Long orderId){
        return purchaseService.payForOrder(paymentRequest, orderId);
    }


}
