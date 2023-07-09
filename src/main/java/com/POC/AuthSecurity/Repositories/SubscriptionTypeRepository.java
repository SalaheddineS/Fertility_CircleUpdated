package com.POC.AuthSecurity.Repositories;

import com.POC.AuthSecurity.Entities.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType, Integer> {
    SubscriptionType findSubscriptionTypeByName(String name);

    void deleteSubscriptionTypeByName(String name);
}
