package com.uj.expenseManager.mapper;

import com.uj.expenseManager.dto.UserDTO;
import com.uj.expenseManager.models.User;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-21T02:27:14+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        String username = null;
        String password = null;
        String firstName = null;
        String lastName = null;

        username = dto.getUsername();
        password = dto.getPassword();
        firstName = dto.getFirstName();
        lastName = dto.getLastName();

        User user = new User( firstName, lastName, username, password );

        Set<String> set = dto.getCategories();
        if ( set != null ) {
            user.setCategories( new LinkedHashSet<String>( set ) );
        }

        return user;
    }

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUsername( user.getUsername() );
        userDTO.setPassword( user.getPassword() );
        userDTO.setFirstName( user.getFirstName() );
        userDTO.setLastName( user.getLastName() );
        Set<String> set = user.getCategories();
        if ( set != null ) {
            userDTO.setCategories( new LinkedHashSet<String>( set ) );
        }

        return userDTO;
    }

    @Override
    public List<User> toEntityList(List<UserDTO> userDtoList) {
        if ( userDtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( userDtoList.size() );
        for ( UserDTO userDTO : userDtoList ) {
            list.add( toEntity( userDTO ) );
        }

        return list;
    }

    @Override
    public List<UserDTO> toDTOList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( users.size() );
        for ( User user : users ) {
            list.add( toDTO( user ) );
        }

        return list;
    }
}
