package com.POC.AuthSecurity.Services.Interfaces;

import com.POC.AuthSecurity.Entities.SubscriptionType;

import java.util.List;

public interface ISubscriptionTypeService {

    //CREATE
    SubscriptionType addSubscriptionType(SubscriptionType subscriptionType);

    //READ
    List<SubscriptionType> getAllSubscriptions();
    SubscriptionType getSubscriptionTypeById(int id);
    SubscriptionType getSubscriptionTypeByName(String name);

    //UPDATE
    String updateSubscriptionTypeById(int id, SubscriptionType newSubscriptionType);
    String updateSubscriptionTypeByName(String name, SubscriptionType newSubscriptionType);

    //DELETE
    String deleteSubscriptionTypeById(int id);
    String deleteSubscriptionTypeByName(String name);

}