package com.keola.microservice.user.services.customer;

import com.keola.microservice.user.dto.CreateCustomerDTO;
import com.keola.microservice.user.dto.ReadCustomerDTO;
import com.keola.microservice.user.dto.UpdateCustomerDTO;
import com.keola.microservice.user.entity.CustomerEntity;
import com.keola.microservice.user.exception.EntityNotFoundException;
import com.keola.microservice.user.mapper.CustomerEntityMapper;
import com.keola.microservice.user.repo.CustomerEntityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Service
public class CustomerServiceI implements CustomerService {
    private final CustomerEntityRepository customerEntityRepository;
    private final CustomerEntityMapper customerEntityMapper;
    @Override
    public Mono<ReadCustomerDTO> findById(Long customerId) {
        return customerEntityRepository.findById(customerId)
                .flatMap(entity -> Mono.just(customerEntityMapper.toDTO(entity))) // Si la entidad existe, la conviertes a DTO
                .switchIfEmpty(Mono.defer(() -> handleEntityNotFound(customerId))); // Si no se encuentra la entidad, lanza la excepción
    }

    private Mono<ReadCustomerDTO> handleEntityNotFound(Long customerId) {
        throw new EntityNotFoundException("Customer with ID " + customerId + " not found");
    }

    @Override
    public Flux<ReadCustomerDTO> findAll() {
        return customerEntityRepository.findAll()
                .collectList() // Convierte el Flux en List
                .map(customerEntityMapper::toListDTO) // Mapea la lista
                .flatMapMany(Flux::fromIterable); // Convierte de vuelta a Flux
    }


    @Override
    @Transactional
    public Mono<ReadCustomerDTO> createCustomer(CreateCustomerDTO createCustomerDTO) {
        // Crear la entidad del cliente y guardarla en la base de datos
        return customerEntityRepository.save(CustomerEntity.builder()
                        .address(createCustomerDTO.getAddress())
                        .email(createCustomerDTO.getEmail())
                        .password(createCustomerDTO.getPassword())
                        .name(createCustomerDTO.getName())
                        .build()) // Guarda el cliente en la base de datos
                .map(customerEntityMapper::toDTO);
    }


    @Override
    @Transactional
    public Mono<Void> updateCustomer(UpdateCustomerDTO updateCustomerDTO, Long customerId) {
        return customerEntityRepository.findById(customerId)
                .flatMap(existingCustomer -> {
                    // Actualiza los campos de la entidad existente con los nuevos valores
                    existingCustomer.setName(updateCustomerDTO.getName());
                    existingCustomer.setEmail(updateCustomerDTO.getEmail());
                    existingCustomer.setAddress(updateCustomerDTO.getAddress());
                    existingCustomer.setPassword(updateCustomerDTO.getPassword());

                    // Guarda los cambios
                    return customerEntityRepository.save(existingCustomer).then();
                })
                .switchIfEmpty(Mono.error(new EntityNotFoundException("Customer not found with id: " + customerId))); // Lanza excepción si no se encuentra
    }




    @Override
    @Transactional
    public Mono<Void> deleteCustomer(Long idCustomer) {
        return customerEntityRepository.deleteById(idCustomer);
    }
}
