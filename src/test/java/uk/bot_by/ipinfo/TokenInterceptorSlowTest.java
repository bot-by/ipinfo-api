package uk.bot_by.ipinfo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import feign.Feign;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.json.JsonDecoder;
import feign.mock.HttpMethod;
import feign.mock.MockClient;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("slow")
class TokenInterceptorSlowTest {

  @DisplayName("Add the authorization header")
  @Test
  void addAuthorizationHeader() throws IOException {
    // given
    var tokenInterceptor = new TokenInterceptor("qwerty");
    var responseBody = Files.readAllBytes(Path.of("src/test/resources/google-dns.json"));
    var mockClient = new MockClient().ok(HttpMethod.GET, "https://ipinfo.io/8.8.8.8", responseBody);
    var client = Feign.builder().client(mockClient).decoder(new JsonDecoder())
        .requestInterceptor(tokenInterceptor).target(IpInfo.class, IpInfo.API_LOCATOR);

    // when
    assertDoesNotThrow(() -> client.lookup("8.8.8.8"));

    // then
    var request = mockClient.verifyOne(HttpMethod.GET, "https://ipinfo.io/8.8.8.8");

    assertAll("authorization header",
        () -> assertThat("header", request.headers(), hasKey("Authorization")),
        () -> assertThat("bearer token", request.headers().get("Authorization"),
            contains("Bearer qwerty")));
  }

  @DisplayName("The authorization header exists")
  @Test
  void authorizationHeader() throws IOException {
    // given
    var tokenInterceptor = new TokenInterceptor("qwerty");
    var responseBody = Files.readAllBytes(Path.of("src/test/resources/google-dns.json"));
    var mockClient = new MockClient().ok(HttpMethod.GET, "https://ipinfo.io/8.8.8.8", responseBody);
    var client = Feign.builder().client(mockClient).decoder(new JsonDecoder())
        .requestInterceptor(tokenInterceptor).target(IpInfoHeaderTest.class, IpInfo.API_LOCATOR);

    // when
    assertDoesNotThrow(() -> client.lookup("8.8.8.8"));

    // then
    var request = mockClient.verifyOne(HttpMethod.GET, "https://ipinfo.io/8.8.8.8");

    assertAll("authorization header",
        () -> assertThat("header", request.headers(), hasKey("Authorization")),
        () -> assertThat("bearer token", request.headers().get("Authorization"), contains("abc")));
  }

  @DisplayName("The token query parameter exists")
  @Test
  void queryParameter() throws IOException {
    // given
    var tokenInterceptor = new TokenInterceptor("qwerty");
    var responseBody = Files.readAllBytes(Path.of("src/test/resources/google-dns.json"));
    var mockClient = new MockClient().ok(HttpMethod.GET, "https://ipinfo.io/8.8.8.8?token=xzy",
        responseBody);
    var client = Feign.builder().client(mockClient).decoder(new JsonDecoder())
        .requestInterceptor(tokenInterceptor).target(IpInfo.class, IpInfo.API_LOCATOR);

    // when
    assertDoesNotThrow(() -> client.lookup("8.8.8.8", IpInfo.getToken("xzy")));

    // then
    var request = mockClient.verifyOne(HttpMethod.GET, "https://ipinfo.io/8.8.8.8?token=xzy");

    assertThat("authorization header", request.headers(), not(hasKey("Authorization")));
  }

  @Headers({"Authorization: abc"})
  static interface IpInfoHeaderTest {

    @RequestLine("GET /{ip}")
    @Headers("Accept: application/json")
    JSONObject lookup(@Nullable @Param("ip") String address);

  }

}