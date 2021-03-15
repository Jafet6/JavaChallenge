package br.com.jafethenrique.JavaChallenge.mappers;

import br.com.jafethenrique.JavaChallenge.responses.UserDTO;
import br.com.jafethenrique.JavaChallenge.domain.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

public class UserMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO convertUserModelToDto(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    public User convertUserDTOToEntity(UserDTO userDto) throws ParseException {
        userDto.setId(null);
        User userModel = modelMapper.map(userDto, User.class);
        return userModel;
    }
}
