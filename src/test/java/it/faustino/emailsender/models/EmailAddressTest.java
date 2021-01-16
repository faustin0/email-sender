package it.faustino.emailsender.models;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailAddressTest {


    @ParameterizedTest
    @ValueSource(strings = {"pad_right@b.com ", " pad_left@b.com", "no-pad@b.com"})
    void shouldCreateValidEmailAddress(String mail) {
        EmailAddress validEmailAddress = EmailAddress.createValidEmailAddress(mail);

        assertThat(validEmailAddress.getAddress())
                .isEqualTo(mail.trim());
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "mail@mail", ""})
    void shouldFailToCreate_anInvalidEmailAddress(String mail) {
        assertThatThrownBy(() -> EmailAddress.createValidEmailAddress(mail))
                .hasMessageContaining(mail);

    }
}