package com.uj.expenseManager.mapper;

import com.uj.expenseManager.dto.UserDTO;
import com.uj.expenseManager.models.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserDTO dto);

    UserDTO toDTO(User user);

    List<User> toEntityList(List<UserDTO> userDtoList);

    List<UserDTO> toDTOList(List<User> users);
}
