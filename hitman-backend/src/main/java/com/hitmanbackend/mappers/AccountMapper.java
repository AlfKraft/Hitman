package com.hitmanbackend.mappers;

import com.hitmanbackend.dto.TestAccountDto;
import com.hitmanbackend.entities.TestAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel="spring")
public interface AccountMapper {

    @Mapping(target = "name", expression = "java(dto.getFirstName() + \" \" + dto.getLastName())")
    TestAccountEntity testAccountEntToDto(TestAccountDto dto);
}
