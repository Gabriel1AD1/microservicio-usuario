package com.keola.microservice.user.controller.rest;

import com.keola.microservice.user.services.customer.CustomerService;
import com.keola.microservice.user.dto.CreateCustomerDTO;
import com.keola.microservice.user.dto.ReadCustomerDTO;
import com.keola.microservice.user.dto.UpdateCustomerDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static com.keola.microservice.user.xd.infra.web.rest.common.ApiVersion.api_v1;

@RestController
@RequestMapping(api_v1 + "customer")
@AllArgsConstructor
public class CustomerRest {

    private final CustomerService customerService;

    @PostMapping
    public Mono<ResponseEntity<Void>> create(@RequestBody Mono<CreateCustomerDTO> customerDTO) {
        return customerDTO
                .flatMap(customerService::createCustomer)
                .then(Mono.just(ResponseEntity.created(URI.create(api_v1 + "customer/")).build()));
    }

    @GetMapping("/{customer-id}")
    public Mono<ResponseEntity<ReadCustomerDTO>> findById(@PathVariable("customer-id") Long customerId) {
        return customerService
                .findById(customerId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Flux<ReadCustomerDTO> findAll() {
        return customerService.findAll();
    }

    @PutMapping("/{customer-id}")
    public Mono<ResponseEntity<Void>> update(@RequestBody Mono<UpdateCustomerDTO> customerDTO,
                                             @PathVariable("customer-id") Long parameter) {
        return customerDTO
                .flatMap(customerService::updateCustomer)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @DeleteMapping("/{customer-id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable("customer-id") Long customerId) {
        return customerService
                .findById(customerId) // Verifica si el cliente existe.
                .flatMap(existing -> customerService.deleteCustomer(customerId)
                        .then(Mono.just(ResponseEntity.noContent().build()))) // Si existe, lo elimina.
                .defaultIfEmpty(ResponseEntity.notFound().build()); // Si no existe, devuelve 404.
    }

}
