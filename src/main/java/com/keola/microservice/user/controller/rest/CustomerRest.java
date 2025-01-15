package com.keola.microservice.user.controller.rest;

import com.keola.microservice.user.dto.CreateCustomerDTO;
import com.keola.microservice.user.dto.ReadCustomerDTO;
import com.keola.microservice.user.dto.UpdateCustomerDTO;
import com.keola.microservice.user.services.customer.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static com.keola.microservice.user.controller.rest.common.ApiVersion.api_v1;

@RestController
@RequestMapping(api_v1 + "customer")
@AllArgsConstructor
@Valid
@Tag(name = "Customer API", description = "API for managing customers")
public class CustomerRest {

    private final CustomerService customerService;

    @Operation(summary = "Create a new customer", description = "This endpoint creates a new customer and returns the created customer details.")
    @PostMapping
    public Mono<ResponseEntity<ReadCustomerDTO>> create(
            @RequestBody @Valid CreateCustomerDTO customerDTO) {
        return customerService.createCustomer(customerDTO)
                .map(savedCustomer -> ResponseEntity.created(URI.create(api_v1 + "customer/" + savedCustomer.getId()))
                        .body(savedCustomer));
    }

    @Operation(summary = "Find customer by ID", description = "This endpoint retrieves a customer by their ID.")
    @GetMapping("/{customer-id}")
    public Mono<ResponseEntity<ReadCustomerDTO>> findById(
            @Parameter(description = "ID of the customer to retrieve") @PathVariable("customer-id") Long customerId) {
        return customerService
                .findById(customerId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get all customers", description = "This endpoint retrieves all customers.")
    @GetMapping
    public Flux<ReadCustomerDTO> findAll() {
        return customerService.findAll();
    }

    @Operation(summary = "Update customer details", description = "This endpoint updates the details of an existing customer.")
    @PutMapping("/{customer-id}")
    public Mono<ResponseEntity<Void>> update(
            @RequestBody UpdateCustomerDTO customerDTO,
            @Parameter(description = "ID of the customer to update") @PathVariable("customer-id") Long customerId) {
        return customerService.updateCustomer(customerDTO, customerId)
                .then(Mono.just(ResponseEntity.ok().build()));
    }

    @Operation(summary = "Delete customer", description = "This endpoint deletes a customer by their ID.")
    @DeleteMapping("/{customer-id}")
    public Mono<ResponseEntity<Object>> delete(
            @Parameter(description = "ID of the customer to delete") @PathVariable("customer-id") Long customerId) {
        return customerService
                .findById(customerId)
                .flatMap(existing -> customerService.deleteCustomer(customerId)
                        .then(Mono.just(ResponseEntity.noContent().build())));
    }
}
