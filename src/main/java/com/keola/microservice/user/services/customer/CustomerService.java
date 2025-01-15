package com.keola.microservice.user.services.customer;

import com.keola.microservice.user.dto.CreateCustomerDTO;
import com.keola.microservice.user.dto.ReadCustomerDTO;
import com.keola.microservice.user.dto.UpdateCustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Mono<ReadCustomerDTO> findById(Long customerId);

    Flux<ReadCustomerDTO> findAll();

    Mono<ReadCustomerDTO> createCustomer(CreateCustomerDTO createCustomerDTO);

    Mono<Void> updateCustomer(UpdateCustomerDTO updateCustomerDTO,Long customerId);

    Mono<Void> deleteCustomer(Long idCustomer);
}
