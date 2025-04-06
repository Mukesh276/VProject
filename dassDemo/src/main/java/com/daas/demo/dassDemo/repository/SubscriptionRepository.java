package com.daas.demo.dassDemo.repository;

import com.daas.demo.dassDemo.entity.Subscription;
import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
}
