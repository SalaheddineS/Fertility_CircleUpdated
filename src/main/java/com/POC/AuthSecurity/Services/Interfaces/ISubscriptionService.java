package com.POC.AuthSecurity.Services.Interfaces;

import com.POC.AuthSecurity.Entities.Subscription;

import java.util.List;

public interface ISubscriptionService {
    List<Subscription> getAllSubscriptions();
    Subscription getSubscriptionById(int subscriptionId);
    Subscription createSubscription(String subscriptionTypeName);
    String changeSubscriptionTypeToSubscriptionById(int subscriptionId, String subscriptionTypeName);
    String deleteSubscriptionById(int subscriptionId);
    String createAndAssignSubscription( String subscriptionTypeName, String email);


}
