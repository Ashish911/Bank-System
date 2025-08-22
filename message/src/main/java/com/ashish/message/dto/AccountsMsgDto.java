package com.ashish.message.dto;


/**
 * AccountsMsgDto is a Java Record class that represents the Data Transfer Object
 * for the Accounts microservice. It contains the details of an account, such as the account number, name, email, and mobile number.
 *
 * @author Ashish
 * @version 1.0
 * @since 1.0
 */
public record AccountsMsgDto(Long accountNumber, String name, String email, String mobileNumber) {
}
