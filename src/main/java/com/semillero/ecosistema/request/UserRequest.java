package com.semillero.ecosistema.request;

import com.semillero.ecosistema.enums.Role;
import com.semillero.ecosistema.models.UserModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank(message = "Name cannot be null")
        String name,
        @NotBlank(message = "Last name cannot be null")
        String lastName,
        @Email
        String email,
        @NotBlank(message = "Phone Number cannot be null")
        @Size(min = 10, max = 10, message = "the number of characters is incorrect")
        String phoneNumber,
        @NotBlank(message = "Role cannot be null")
        String role
) {
        public UserModel ToUserModel(){
                Role userRole = Role.valueOf(role);
                final UserModel user = new UserModel(this.name,this.lastName,this.email,false, userRole,this.phoneNumber);
                return user;
        }
}


