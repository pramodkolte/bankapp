package com.bankapp.auth_service.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank(message = "Firstname is required")
    @Size(min = 3, max = 50, message = "Firstname must be between 3 and 50 characters")
    private String firstname;

    @NotBlank(message = "Lastname is required")
    @Size(min = 3, max = 50, message = "Lastname must be between 3 and 50 characters")
    private String lastname;

    @NotBlank(message = "Username is required")
    @Pattern(
            regexp = "^(?=.{3,20}$)(?![0-9.])[a-z0-9._]+(?<![_.])$",
            message = "Username must be 3-20 characters long and can only contain lowercase letters, numbers, underscores, and dots. " +
                    "Can not start with number or dot. No consecutive special characters or trailing special characters are allowed."
    )
    private String username;

    @NotBlank(message = "Email is required")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
            message = "Invalid email format"
    )
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
    )
    private String password;

    private String roles = "ROLE_CUSTOMER";
}
