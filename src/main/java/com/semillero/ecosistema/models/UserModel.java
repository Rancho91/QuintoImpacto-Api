package com.semillero.ecosistema.models;

import com.semillero.ecosistema.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.semillero.ecosistema.dtos.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "user")
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private boolean deleted = false;
    private Role role;
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SupplierModel> suppliers;

    public UserModel(String name, String lastName, String email, boolean deleted, Role role, String phoneNumber) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.deleted = deleted;
        this.role = role;
        this.phoneNumber = phoneNumber;
    }
//    public UserModel() {
//
//    }
//    public UserDto userModelToUserDto (){
//        final  UserDto user = new UserDto(this.name,this.lastName,this.email,this.role,this.phoneNumber);
//        return user;
//    }
}
