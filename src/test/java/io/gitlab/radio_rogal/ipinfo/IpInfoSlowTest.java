package io.gitlab.radio_rogal.ipinfo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import feign.Feign;
import feign.Logger.Level;
import feign.http2client.Http2Client;
import feign.json.JsonDecoder;
import feign.slf4j.Slf4jLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@Tag("slow")
class IpInfoSlowTest {

  private IpInfo client;

  @BeforeEach
  void setUp() {
    client = Feign.builder().client(new Http2Client()).logger(new Slf4jLogger())
        .logLevel(Level.FULL).decoder(new JsonDecoder()).target(IpInfo.class, IpInfo.API_LOCATOR);
  }

  @DisplayName("Get full result")
  @Test
  void fullResult() {
    // when
    var response = assertDoesNotThrow(() -> client.lookup("8.8.8.8"));

    // then
    assertEquals(
        "{\"ip\":\"8.8.8.8\",\"hostname\":\"dns.google\",\"anycast\":true,"
            + "\"city\":\"Mountain View\",\"region\":\"California\",\"country\":\"US\","
            + "\"loc\":\"37.4056,-122.0775\",\"org\":\"AS15169 Google LLC\",\"postal\":\"94043\","
            + "\"timezone\":\"America/Los_Angeles\",\"readme\":\"https://ipinfo.io/missingauth\"}",
        response.toString(), false);
  }

  @DisplayName("Get a field")
  @ParameterizedTest
  @CsvSource(value = {"Address|8.8.8.8", "City|Mountain View", "Country|US", "Hostname|dns.google",
      "Loc|37.4056,-122.0775", "Org|AS15169 Google LLC", "Postal|94043", "Region|California",
      "Timezone|America/Los_Angeles"}, delimiter = '|')
  void field(String fieldName, String fieldValue) {
    // given
    var field = IpInfoField.valueOf(fieldName);

    // when
    var response = client.lookupField("8.8.8.8", field);

    // then
    assertAll("A field", () -> assertNotNull(response, "response exists"),
        () -> assertThat("value", response, startsWith(fieldValue)));
  }

}
