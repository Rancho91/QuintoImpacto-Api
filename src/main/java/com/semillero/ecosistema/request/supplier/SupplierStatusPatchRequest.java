package com.semillero.ecosistema.request.supplier;

import com.semillero.ecosistema.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SupplierStatusPatchRequest(
        @NotBlank
        @Pattern(regexp = "ACEPTADO|DENEGADO|REQUIERE_CAMBIOS", message ="The status can only be ACCEPTED, DENIED, or REQUIRES_CHANGES.")
        String status,
        @Size(max = 300, message = "The character count should be between 16 and 299")
        String feedback) {
    public Status toStatus(){
        return Status.valueOf(this.status);
    }
}
