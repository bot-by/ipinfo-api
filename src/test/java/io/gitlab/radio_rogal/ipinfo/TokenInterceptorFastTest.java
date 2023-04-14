package io.gitlab.radio_rogal.ipinfo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsMapContaining.hasKey;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import feign.RequestTemplate;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
class TokenInterceptorFastTest {

  private TokenInterceptor interceptor;
  private RequestTemplate requestTemplate;

  @BeforeEach
  public void setUp() {
    interceptor = new TokenInterceptor("qwerty");
    requestTemplate = new RequestTemplate();
  }

  @DisplayName("Add the API token to a request template")
  @Test
  public void happyPath() {
    // when
    interceptor.apply(requestTemplate);

    // then
    assertAll("HTTP header with API token",
        () -> assertThat("HTTP header with API token", requestTemplate.headers(),
            hasKey("Authorization")), () -> assertEquals("Bearer qwerty",
            requestTemplate.headers().get("Authorization").iterator().next(),
            "The API token was added to the request template"));
  }

  @DisplayName("The API token header exists")
  @Test
  public void headerExists() {
    // given
    requestTemplate.header("Authorization", "abc");

    // when
    interceptor.apply(requestTemplate);

    // then
    assertAll("HTTP header with API token",
        () -> assertThat("HTTP header with API token", requestTemplate.headers(),
            hasKey("Authorization")),
        () -> assertEquals("abc", requestTemplate.headers().get("Authorization").iterator().next(),
            "The API token wasn't overwritten"));
  }

  @DisplayName("The API token parameter exists")
  @Test
  public void queryParameterExists() {
    // given
    var queryParameters = new HashMap<String, Collection<String>>();

    queryParameters.put("abc", Collections.singleton("qwerty"));
    queryParameters.put("token", Collections.singleton("xzy"));
    requestTemplate.queries(queryParameters);

    // when
    interceptor.apply(requestTemplate);

    // then
    assertAll("HTTP header with API token",
        () -> assertThat("HTTP header with API token", requestTemplate.headers(),
            not(hasKey("Authorization"))),
        () -> assertThat("HTTP header with API token", requestTemplate.queries(),
            hasKey("token")),
        () -> assertEquals("xzy", requestTemplate.queries().get("token").iterator().next(),
            "The API token wasn't overwritten"));
  }

}
