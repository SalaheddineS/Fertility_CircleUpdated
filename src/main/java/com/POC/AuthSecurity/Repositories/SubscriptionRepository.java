package com.POC.AuthSecurity.Repositories;

import com.POC.AuthSecurity.Entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {
}
