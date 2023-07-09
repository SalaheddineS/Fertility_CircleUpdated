package com.POC.AuthSecurity.Services.Implementations;

import com.POC.AuthSecurity.Entities.Subscription;
import com.POC.AuthSecurity.Entities.SubscriptionType;
import com.POC.AuthSecurity.Entities.User;
import com.POC.AuthSecurity.Repositories.SubscriptionRepository;
import com.POC.AuthSecurity.Repositories.UserRepository;
import com.POC.AuthSecurity.Services.Interfaces.ISubscriptionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class SubscriptionService implements ISubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionTypeService subscriptionTypeService;
    private final UserRepository userRepository;

    @Override
    public List<Subscription> getAllSubscriptions() {
        try
        {
            return subscriptionRepository.findAll();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error while getting all subscriptions, here are the details : " + e);
        }

    }

    @Override
    public Subscription getSubscriptionById(int subscriptionId) {
        try
        {
            return subscriptionRepository.findById(subscriptionId).get();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error while getting subscription by id, here are the details : " + e);
        }
    }

    @Override
    public Subscription createSubscription(String subscriptionTypeName) {
        try
        {
            SubscriptionType subscriptionType = subscriptionTypeService.getSubscriptionTypeByName(subscriptionTypeName);

            Subscription subscription = Subscription.builder()
                    .subscriptionType(subscriptionType)
                    .startDate(LocalDate.now())
                    .endDate(LocalDate.now().plusMonths(subscriptionType.getDurationMonths()))
                    .build();
            return subscriptionRepository.save(subscription);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error while creating subscription, here are the details : " + e);
        }
    }

    @Override
    public String changeSubscriptionTypeToSubscriptionById(int subscriptionId, String subscriptionTypeName) {
        try {
            Subscription subscription = subscriptionRepository.findById(subscriptionId).get();
            if(subscription.getEndDate().isAfter(LocalDate.now())) throw new RuntimeException("Subscription is still active");
            SubscriptionType subscriptionType = subscriptionTypeService.getSubscriptionTypeByName(subscriptionTypeName);
            if(subscriptionType==null) throw new RuntimeException("Subscription type not found");
            subscription.setSubscriptionType(subscriptionType);
            subscription.setStartDate(LocalDate.now());
            subscription.setEndDate(LocalDate.now().plusMonths(subscriptionType.getDurationMonths()));
            subscriptionRepository.save(subscription);
            return "Subscription type changed successfully";
        }
        catch (Exception e) {
            throw new RuntimeException("Error while changing subscription type, here are the details : " + e);
        }

    }

    @Override
    public String deleteSubscriptionById(int subscriptionId) {
        try
        {
            subscriptionRepository.deleteById(subscriptionId);
            return "Subscription deleted successfully";
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error while deleting subscription, here are the details : " + e);
        }
    }

    @Override
    public String createAndAssignSubscription(String subscriptionTypeName, String email) {
        try {
            Subscription newSubscription=createSubscription(subscriptionTypeName);
            if(newSubscription==null) throw new RuntimeException("Subscription creation failed");
            User user = userRepository.findByEmail(email).get();
            user.setSubscription(newSubscription);
            userRepository.save(user);
            return "Subscription created and assigned successfully";
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error while creating and assigning subscription, here are the details : " + e);
        }
    }
}
