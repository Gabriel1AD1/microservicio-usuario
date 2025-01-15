package com.keola.microservice.user.service;

import com.keola.microservice.user.dto.CreateCustomerDTO;
import com.keola.microservice.user.dto.ReadCustomerDTO;
import com.keola.microservice.user.dto.UpdateCustomerDTO;
import com.keola.microservice.user.entity.CustomerEntity;
import com.keola.microservice.user.exception.EntityNotFoundException;
import com.keola.microservice.user.mapper.CustomerEntityMapper;
import com.keola.microservice.user.repo.CustomerEntityRepository;
import com.keola.microservice.user.services.customer.CustomerServiceI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceITest {

    @Mock
    private CustomerEntityRepository customerEntityRepository;

    @Mock
    private CustomerEntityMapper customerEntityMapper;

    @InjectMocks
    private CustomerServiceI customerService;

    private CustomerEntity customerEntity;
    private CreateCustomerDTO createCustomerDTO;
    private UpdateCustomerDTO updateCustomerDTO;

    @BeforeEach
    void setUp() {
        customerEntity = new CustomerEntity(1L, "John Doe", "johndoe@example.com", "123 Main St", "password");
        createCustomerDTO = new CreateCustomerDTO("John Doe", "johndoe@example.com", "123 Main St", "password");
        updateCustomerDTO = new UpdateCustomerDTO("John Doe Updated", "johndoe@example.com", "123 Main St Updated", "newpassword");
    }

    @Test
    void testFindById_success() {
        // Arrange
        when(customerEntityRepository.findById(1L)).thenReturn(Mono.just(customerEntity));
        when(customerEntityMapper.toDTO(customerEntity)).thenReturn(new ReadCustomerDTO(1L, "John Doe", "johndoe@example.com", "123 Main St", "password"));

        // Act
        Mono<ReadCustomerDTO> result = customerService.findById(1L);

        // Assert
        ReadCustomerDTO readCustomerDTO = result.block();
        assertNotNull(readCustomerDTO);
        assertEquals(1L, readCustomerDTO.getId());
        assertEquals("John Doe", readCustomerDTO.getName());
        verify(customerEntityRepository).findById(1L);
    }

    @Test
    void testFindById_entityNotFound() {
        // Arrange
        when(customerEntityRepository.findById(1L)).thenReturn(Mono.empty());

        // Act & Assert
        Mono<ReadCustomerDTO> result = customerService.findById(1L);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, result::block);
        assertEquals("Customer with ID 1 not found", exception.getMessage());
    }

    @Test
    void testCreateCustomer_success() {
        // Arrange
        CustomerEntity newCustomerEntity = new CustomerEntity(null, "John Doe", "johndoe@example.com", "123 Main St", "password");
        when(customerEntityRepository.save(any(CustomerEntity.class))).thenReturn(Mono.just(customerEntity));
        when(customerEntityMapper.toDTO(customerEntity)).thenReturn(new ReadCustomerDTO(1L, "John Doe", "johndoe@example.com", "123 Main St", "password"));

        // Act
        Mono<ReadCustomerDTO> result = customerService.createCustomer(createCustomerDTO);

        // Assert
        ReadCustomerDTO readCustomerDTO = result.block();
        assertNotNull(readCustomerDTO);
        assertEquals("John Doe", readCustomerDTO.getName());
        verify(customerEntityRepository).save(any(CustomerEntity.class));
    }

    @Test
    void testUpdateCustomer_entityNotFound() {
        // Arrange
        when(customerEntityRepository.findById(1L)).thenReturn(Mono.empty());

        // Act & Assert
        Mono<Void> result = customerService.updateCustomer(updateCustomerDTO, 1L);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, result::block);
        assertEquals("Customer not found with id: 1", exception.getMessage());
    }

    @Test
    void testDeleteCustomer_success() {
        // Arrange
        when(customerEntityRepository.deleteById(1L)).thenReturn(Mono.empty());

        // Act
        Mono<Void> result = customerService.deleteCustomer(1L);

        // Assert
        result.block();  // Execute the Mono
        verify(customerEntityRepository).deleteById(1L);
    }
}
