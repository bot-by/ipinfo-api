package io.gitlab.radio_rogal.ipinfo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("fast")
class IpInfoFastTest {

  @DisplayName("Get an instance")
  @Test
  void instance() {
    // when and then
    assertNotNull(IpInfo.getInstance("qwerty"));
  }

}
