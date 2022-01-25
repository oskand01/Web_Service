package com.example.personsrest.domain.util;

import java.util.regex.Pattern;

public class UUIDValidator {
    private final static Pattern UUID_REGEX_PATTERN = Pattern.compile("^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$");

    public static boolean isValidUUID(String str) {

        return UUID_REGEX_PATTERN.matcher(str).matches();
    }
}
