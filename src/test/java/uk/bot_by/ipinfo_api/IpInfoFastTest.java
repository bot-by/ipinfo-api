package uk.bot_by.ipinfo_api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@Tag("fast")
class IpInfoFastTest {

  @DisplayName("Get an instance")
  @ParameterizedTest
  @NullSource
  @ValueSource(strings = "qwerty")
  void instance(String token) {
    // when and then
    assertNotNull(assertDoesNotThrow(() -> IpInfo.getInstance(token)));
  }

  @DisplayName("Get a token")
  @Test
  void token() {
    // when
    var tokenMap = IpInfo.getToken("qwerty");

    // then
    assertThat("map with a token", tokenMap, hasEntry("token", "qwerty"));
  }

}
