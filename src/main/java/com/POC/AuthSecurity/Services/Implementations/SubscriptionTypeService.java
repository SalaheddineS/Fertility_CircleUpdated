package com.POC.AuthSecurity.Services.Implementations;

import com.POC.AuthSecurity.Entities.SubscriptionType;
import com.POC.AuthSecurity.Repositories.SubscriptionTypeRepository;
import com.POC.AuthSecurity.Services.Interfaces.ISubscriptionTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class SubscriptionTypeService implements ISubscriptionTypeService {

    SubscriptionTypeRepository subscriptionTypeRepository;
    @Override
    public SubscriptionType addSubscriptionType(SubscriptionType subscriptionType) {
        return subscriptionTypeRepository.save(subscriptionType);
    }

    @Override
    public List<SubscriptionType> getAllSubscriptions() {
        return subscriptionTypeRepository.findAll();
    }

    @Override
    public SubscriptionType getSubscriptionTypeById(int id) {
        return subscriptionTypeRepository.findById(id).get();
    }

    @Override
    public SubscriptionType getSubscriptionTypeByName(String name) {
        return subscriptionTypeRepository.findSubscriptionTypeByName(name);
    }

    @Override
    public String updateSubscriptionTypeById(int id, SubscriptionType newSubscriptionType) {
        SubscriptionType subscriptionType = getSubscriptionTypeById(id);

        if (newSubscriptionType.getName()!=null){
            subscriptionType.setName(newSubscriptionType.getName());
        }

        if (newSubscriptionType.getDescription()!=null){
            subscriptionType.setDescription(newSubscriptionType.getDescription());
        }

       if (newSubscriptionType.getPrice()!=null){
            subscriptionType.setPrice(newSubscriptionType.getPrice());
        }
       if (newSubscriptionType.getDurationMonths()!=null){
            subscriptionType.setDurationMonths(newSubscriptionType.getDurationMonths());
        }
        subscriptionTypeRepository.save(subscriptionType);
        return "Subscription type updated successfully";
    }

    @Override
    public String updateSubscriptionTypeByName(String name, SubscriptionType newSubscriptionType) {
        SubscriptionType subscriptionType = getSubscriptionTypeByName(name);

        if (newSubscriptionType.getName()!=null){
            subscriptionType.setName(newSubscriptionType.getName());
        }

        if (newSubscriptionType.getDescription()!=null){
            subscriptionType.setDescription(newSubscriptionType.getDescription());
        }

        if (newSubscriptionType.getPrice()!=null){
            subscriptionType.setPrice(newSubscriptionType.getPrice());
        }
        if (newSubscriptionType.getDurationMonths()!=null){
            subscriptionType.setDurationMonths(newSubscriptionType.getDurationMonths());
        }

        subscriptionTypeRepository.save(subscriptionType);
        return "Subscription type updated successfully";
    }

    @Override
    public String deleteSubscriptionTypeById(int id) {
        subscriptionTypeRepository.deleteById(id);
        return "Subscription type deleted successfully";
    }

    @Override
    public String deleteSubscriptionTypeByName(String name) {
        try {
            subscriptionTypeRepository.deleteSubscriptionTypeByName(name);
            return "Subscription type deleted successfully";
        }
        catch (Exception e){
            return "Subscription type not found";
        }
    }
}
