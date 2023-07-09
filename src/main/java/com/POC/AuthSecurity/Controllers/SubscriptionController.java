package com.POC.AuthSecurity.Controllers;

import com.POC.AuthSecurity.Entities.Subscription;
import com.POC.AuthSecurity.Services.Implementations.SubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@ResponseBody
@RequestMapping("Subscription")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @GetMapping ("getAll")
    public List<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping ("getById/{subscriptionId}")
    public Subscription getSubscriptionById(@PathVariable int subscriptionId) {
        return subscriptionService.getSubscriptionById(subscriptionId);
    }

    @PostMapping ("create/{subscriptionTypeName}")
    public Subscription createSubscription(@PathVariable String subscriptionTypeName) {
        return subscriptionService.createSubscription(subscriptionTypeName);
    }

    @PutMapping ("changeType/{subscriptionId}/{subscriptionTypeName}")
    public String changeSubscriptionTypeToSubscriptionById(@PathVariable int subscriptionId, @PathVariable String subscriptionTypeName) {
        return subscriptionService.changeSubscriptionTypeToSubscriptionById(subscriptionId, subscriptionTypeName);
    }

    @DeleteMapping ("delete/{subscriptionId}")
    public String deleteSubscriptionById(@PathVariable int subscriptionId) {
        return subscriptionService.deleteSubscriptionById(subscriptionId);
    }

    @PostMapping ("createAndAssign/{subscriptionTypeName}/{email}")
    public String createAndAssignSubscription(@PathVariable String subscriptionTypeName, @PathVariable String email) {
        return subscriptionService.createAndAssignSubscription(subscriptionTypeName, email);
    }


}
