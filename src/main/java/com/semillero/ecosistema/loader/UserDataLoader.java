package com.semillero.ecosistema.loader;

import com.semillero.ecosistema.enums.Role;
import com.semillero.ecosistema.models.UserModel;
import com.semillero.ecosistema.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDataLoader implements CommandLineRunner {

    @Autowired
    IUserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    public void loadUserData(String... args) throws Exception {
        if (userRepository.count() == 0) {
            List<UserModel> users = new ArrayList<>();
            users.add(new UserModel("Dario", "Egea", "darioegea@gmail.com", false, Role.ADMINISTRADOR, "1553665585"));
            users.add(new UserModel("Federico", "Higa", "higamaradonafederico@gmail.com", false, Role.PROVEEDOR, "1553665585"));
            users.add(new UserModel("Leandro", "Mercado", "leandromercadosimi@gmail.com", false, Role.ADMINISTRADOR, "1553665585"));
            users.add(new UserModel("Ramiro", "Sanchez", "ramirosanchezsolano@gmail.com", false, Role.ADMINISTRADOR, "1553665585"));
            users.add(new UserModel("Rancho", "Ranchez", "ranchoranchez@gmail.com", false, Role.PROVEEDOR, "1553665585"));
            users.add(new UserModel("Sofia", "Britos", "sofiibritos01@gmail.com", false, Role.ADMINISTRADOR, "1553665585"));
            userRepository.saveAll(users);
        }
    }
}
