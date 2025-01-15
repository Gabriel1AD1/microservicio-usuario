package com.keola.microservice.user.repo;

import com.keola.microservice.user.entity.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CustomerEntityRepository extends ReactiveCrudRepository<CustomerEntity,Long> {

    Mono<CustomerEntity> findByName(String title);
}
