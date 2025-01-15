package com.keola.microservice.user.exception;

import java.util.Map;

public class ErrorDuplicateDB {
    public static final Map<String, String> UNIQUE_CONSTRAINT_MESSAGES = Map.of(
            "tbl_customer_email_key", "The email address is already in use."
    );
}
