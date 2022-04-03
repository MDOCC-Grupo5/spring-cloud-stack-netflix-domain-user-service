package com.master.domain_user_service.dto;

import com.master.domain_user_service.dto.validations.MandatoryFieldsValidation;
import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto implements Serializable {

    private static final long serialVersionUID = 552992342L;

    private Long id;

    @NotBlank(groups = {MandatoryFieldsValidation.class}, message = "first name is mandatory")
    private String firstName;

    @NotBlank(groups = {MandatoryFieldsValidation.class}, message = "last name is mandatory")
    private String lastName;

    @NotBlank(groups = {MandatoryFieldsValidation.class}, message = "email is mandatory")
    @Email(groups = {MandatoryFieldsValidation.class}, message = "email format is invalid")
    private String email;

}
