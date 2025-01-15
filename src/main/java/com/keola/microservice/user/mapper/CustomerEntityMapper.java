package com.keola.microservice.user.mapper;

import com.keola.microservice.user.dto.ReadCustomerDTO;
import com.keola.microservice.user.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import reactor.core.publisher.Flux;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerEntityMapper {
    // Mapea un solo CustomerEntity a ReadCustomerDTO

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "password", target = "password")
    ReadCustomerDTO toDTO(CustomerEntity entity);
    // Mapea una lista de CustomerEntity a una lista de ReadCustomerDTO
    List<ReadCustomerDTO> toListDTO(List<CustomerEntity> entity);}
