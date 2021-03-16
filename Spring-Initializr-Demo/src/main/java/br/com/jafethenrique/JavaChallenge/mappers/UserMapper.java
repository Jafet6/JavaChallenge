package br.com.jafethenrique.JavaChallenge.mappers;

import br.com.jafethenrique.JavaChallenge.requests.UserLoginRequest;
import br.com.jafethenrique.JavaChallenge.requests.UserRegisterRequest;
import br.com.jafethenrique.JavaChallenge.responses.UserDataResponse;
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

    public UserDataResponse convertUserModelToUserResponse(User user) {
        UserDataResponse userDataResponse = modelMapper.map(user, UserDataResponse.class);
        return userDataResponse;
    }

    public User convertUserRegisterRequestToEntity(UserRegisterRequest userRequest) throws ParseException {
        User userModel = modelMapper.map(userRequest, User.class);
        return userModel;
    }

    public User convertUserLoginRequestToEntity(UserLoginRequest userRequest) throws ParseException {
        User userModel = modelMapper.map(userRequest, User.class);
        return userModel;
    }
}
