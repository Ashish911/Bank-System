package com.ashish.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(
        name = "Cards",
        description = "Schema to hold cards information"
)
public class CardsDto {

    @Schema(
            description = "Mobile Number of Eazy Bank Cards", example = "3454433243"
    )
    @NotEmpty(message = "Mobile number cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "Card Number of Eazy Bank Card", example = "100646930341"
    )
    @NotEmpty(message = "Card Number cannot be empty")
    @Pattern(regexp="(^$|[0-9]{12})",message = "CardNumber must be 12 digits")
    private String cardNumber;

    @Schema(
            description = "Card Type of Eazy Bank Card", example = "Master Card"
    )
    @NotEmpty(message = "Card Type cannot be empty")
    private String cardType;

    @Schema(
            description = "Total Limit of Eazy Bank Card", example = "4000"
    )
    @NotNull(message = "Total Limit cannot be null")
    @Positive(message = "Amount used should be greater than zero")
    private int totalLimit;

    @Schema(
            description = "Amount Used of Eazy Bank Card", example = "200"
    )
    @NotNull(message = "Amount Used cannot be null")
    @Min(value = 0, message = "Amount used should be equal or greater than zero")
    private int amountUsed;

    @Schema(
            description = "Amount available of Eazy Bank Card", example = "3800"
    )
    @NotNull(message = "Amount Available Limit cannot be null")
    @PositiveOrZero( message = "Amount used should be equal or greater than zero")
    private int availableAmount;

}
