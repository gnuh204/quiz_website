package com.example.qiuzweb.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.qiuzweb.domain.Dto.UserDTO;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDTO> {

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext context) {
        if (userDTO.getPassword() == null || userDTO.getConfirmpassword() == null) {
            return false;
        }
        return userDTO.getPassword().equals(userDTO.getConfirmpassword());
    }
}