package br.com.jafethenrique.JavaChallenge.mappers;

import br.com.jafethenrique.JavaChallenge.DTO.UserDTO;
import br.com.jafethenrique.JavaChallenge.user.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;

public class UserMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDTO convertUserModelToDto(UserModel user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    public UserModel convertUserDTOToEntity(UserDTO userDto) throws ParseException {
        userDto.setId(null);
        UserModel userModel = modelMapper.map(userDto, UserModel.class);
        return userModel;
    }
}
