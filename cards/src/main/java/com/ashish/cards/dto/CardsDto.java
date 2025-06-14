package com.ashish.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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
            description = "Card Number of Eazy Bank Card", example = "2000 2423 2423 2423"
    )
    @NotEmpty(message = "Card Number cannot be empty")
    private String cardNumber;

    @Schema(
            description = "Card Type of Eazy Bank Card", example = "Master Card"
    )
    @NotEmpty(message = "Card Type cannot be empty")
    private String cardType;

    @Schema(
            description = "Total Limit of Eazy Bank Card", example = "2000"
    )
    @NotEmpty(message = "Total Limit cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{4})", message = "Total Limit must be 4 digits")
    private Long totalLimit;

    @Schema(
            description = "Amount Used of Eazy Bank Card", example = "200"
    )
    @NotEmpty(message = "Amount Used cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{4})", message = "Total Limit must be 4 digits")
    private Long amountUsed;

    @Schema(
            description = "Amount available of Eazy Bank Card", example = "3800"
    )
    @NotEmpty(message = "Amount Available Limit cannot be empty")
    @Pattern(regexp = "(^$|[0-9]{4})", message = "Total Limit must be 4 digits")
    private Long availableAmount;

}
