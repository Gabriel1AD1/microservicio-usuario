package com.keola.microservice.user.services.customer;

import com.keola.microservice.user.dto.CreateCustomerDTO;
import com.keola.microservice.user.dto.ReadCustomerDTO;
import com.keola.microservice.user.dto.UpdateCustomerDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class CustomerServiceI implements CustomerService {

    @Override
    public Mono<ReadCustomerDTO> findById(Long customerId) {
        return null;
    }

    @Override
    public Flux<ReadCustomerDTO> findAll() {
        return null;
    }

    @Override
    public Mono<Void> createCustomer(CreateCustomerDTO createCustomerDTO) {
        return null;
    }

    @Override
    public Mono<Void> updateCustomer(UpdateCustomerDTO updateCustomerDTO) {
        return null;
    }

    @Override
    public Mono<Void> deleteCustomer(Long idCustomer) {
        return null;
    }
}
