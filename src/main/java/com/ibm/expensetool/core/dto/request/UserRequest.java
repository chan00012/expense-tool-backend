package com.ibm.expensetool.core.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibm.expensetool.utils.Rest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest {

    @NotNull(message = "First name cannot be blank")
    private String firstName;

    @NotNull(message = "Last name cannot be blank")
    private String lastName;

    @NotNull(message = "Email cannot be blank")
    @Email(message = "Not a valid email address")
    private String email;

    @NotNull(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be atleast 8 characters")
    private String password;

    @Override
    public String toString() {
        return Rest.toJsonString(this);
    }

}
