package com.ashish.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name = "Loans",
        description = "Schema to hold cards information"
)
public class LoansDto {

    @Schema(
            description = "Mobile Number of Eazy Bank Loans", example = "3454433243"
    )
    @NotEmpty(message = "Mobile number cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Loan Number of Eazy Bank Loan", example = "10071469799154"
    )
    @NotEmpty(message = "Loan Number cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{14})", message = "Mobile number must be 14 digits")
    private String loanNumber;

    @Schema(
            description = "Loan Type of Eazy Bank Card", example = "Car Loan"
    )
    @NotEmpty(message = "Loan Type cannot be empty")
    private String loanType;

    @Schema(
            description = "Total Loan of Eazy Bank Card", example = "4000"
    )
    @NotNull(message = "Total Loan cannot be null")
    @Positive(message = "Total loan amount should be greater than zero")
    private int totalLoan;

    @Schema(
            description = "Amount Used of Eazy Bank Card", example = "200"
    )
    @NotNull(message = "Amount Used cannot be null")
    @PositiveOrZero(message = "Total loan amount paid should be equal or greater than zero")
    private int amountPaid;

    @Schema(
            description = "Amount available of Eazy Bank Card", example = "3800"
    )
    @NotNull(message = "Amount Available Limit cannot be null")
    @PositiveOrZero(message = "Total outstanding amount should be equal or greater than zero")
    private int outstandingAmount;

}
