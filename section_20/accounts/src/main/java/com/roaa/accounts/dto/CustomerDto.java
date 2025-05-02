package com.roaa.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to hold Customer and Account information"
)
public class CustomerDto {

    @Schema(
            description = "Name of the customer", example = "Ahmed Mohammed"
    )
    @NotEmpty(message = "Name can not be a null or empty string")
    @Size(min = 5, max = 30, message = "The Length of name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Email address of the customer", example = "tutor@roaa.com"
    )
    @NotEmpty(message = "Email can not be a null or empty string")
    @Email(message = "Please enter a valid email address")
    private String email;

    @Schema(
            description = "Mobile Number of the customer", example = "9345432123"
    )
    @Pattern(regexp = "^$|[0-9]{10}", message = "Please enter a valid mobile number")
    private String mobileNumber;

    @Schema(
            description = "Account details of the Customer"
    )
    private AccountsDto accountsDto;
}
