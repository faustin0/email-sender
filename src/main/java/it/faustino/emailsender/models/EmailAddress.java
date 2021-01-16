package it.faustino.emailsender.models;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class EmailAddress {
    private static final EmailValidator validator = EmailValidator.getInstance(false);
    private final String address;

    private EmailAddress(String address) {
        this.address = address;
    }

    public static EmailAddress createValidEmailAddress(String address) {
        if (validator.isValid(address)) {
            return new EmailAddress(address.trim());
        } else {
            throw new IllegalArgumentException("Invalid email address " + address);
        }
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailAddress that = (EmailAddress) o;
        return Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address);
    }
}
