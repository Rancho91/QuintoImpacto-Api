package com.semillero.ecosistema.dtos;

import com.semillero.ecosistema.enums.Role;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private Role role;
    private String phoneNumber;

//    public UserDto(String name, String lastName, String email, Role role, String phoneNumber) {
//        this.name = name;
//        this.lastName = lastName;
//        this.email = email;
//        this.role = role;
//        this.phoneNumber = phoneNumber;
//    }
////    public UserModel ToUserModel (){
//        final  UserModel user = new UserModel(this.name,this.lastName,this.email,false,this.role,this.phoneNumber);
//        return user;
//    }
}
