package com.semillero.ecosistema.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.semillero.ecosistema.dtos.UserDto;
import com.semillero.ecosistema.enums.Role;
import com.semillero.ecosistema.exceptions.ResourceNotFoundException;
import com.semillero.ecosistema.models.UserModel;
import com.semillero.ecosistema.repositories.IUserRepository;
import com.semillero.ecosistema.request.UserRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    IUserRepository userRepository;
    public UserService(IUserRepository iUserRepository){
        userRepository = iUserRepository;
    }

    @Autowired
    private ModelMapper modelMapper;
    public UserModel createUser(UserRequest user){
        if (userRepository.existsByEmail(user.email())){
            throw new ResourceNotFoundException("This email is already registered.");
        }
        final UserModel userModel = user.ToUserModel();
        return userRepository.save(userModel);
    }
    
    public List<UserDto> getAll(){
        List<UserModel>  listUserModel = this.userRepository.findAll();
        List<UserDto> listUserDto = new ArrayList<>();
        for (UserModel user: listUserModel){
            listUserDto.add( modelMapper.map(user, UserDto.class) );
        }
        return listUserDto;
    }
    
    public UserModel getById(long id){
        final var user = this.userRepository.findById(id);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("user does not exist");
        }
        return user.get();
    }
    
    public UserModel deleteUser(Long id){
        final var user = this.userRepository.findById(id);
        if (!user.isPresent()) {
            throw new ResourceNotFoundException("This user does not exist.");
        }
        user.get().setDeleted(true);
        userRepository.save(user.get());
        return user.get();
    }

    UserModel findUserByEmailOrCreateIt(GoogleIdToken.Payload payload){
        UserModel userModel = null;
        String email = payload.getEmail();

        if (userRepository.existsByEmail(email)) {
            userModel = userRepository.findByEmail(email);
        } else {
            userModel = new UserModel();
            userModel.setEmail(email);
            userModel.setName((String) payload.get("given_name"));
            userModel.setLastName((String) payload.get("family_name"));
            userModel.setRole(Role.PROVEEDOR);
            userRepository.save(userModel);
        }
        return userModel;
    }

    //buscar los email de los usuarios a los que mandar el mail semanal
    public  List<String> getUsersEmails() {
        return userRepository.findAllEmails();
    }
}

