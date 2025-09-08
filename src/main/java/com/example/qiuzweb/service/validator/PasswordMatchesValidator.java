package com.example.qiuzweb.service.validator;



import com.example.qiuzweb.domain.Dto.UserDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDTO> {

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext context) {
        if (userDTO == null || userDTO.getPassword() == null || userDTO.getConfirmPassword() == null) {
            return false;
        }

        //  // Nếu là admin thì bỏ qua xác nhận mật khẩu
        // if ("ADMIN".equalsIgnoreCase(userDTO.getRole())) {
        //     return true;
        // }

        // // Nếu là user thì cần so sánh password và confirmPassword
        // if (userDTO.getConfirmPassword() == null) {
        //     return false;
        // }

        boolean matched = userDTO.getPassword().equals(userDTO.getConfirmPassword());

        if (!matched) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Xác nhận mật khẩu không khớp")
                   .addPropertyNode("confirmPassword")
                   .addConstraintViolation();
        }

        return matched;
    }
}
