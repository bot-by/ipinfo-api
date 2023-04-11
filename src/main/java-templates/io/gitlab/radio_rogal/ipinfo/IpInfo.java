/*
 * Copyright 2017-2023 Witalij Berdinskich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gitlab.radio_rogal.ipinfo;

import feign.Feign;
import feign.Headers;
import feign.Param;
import feign.QueryMap;
import feign.RequestLine;
import feign.http2client.Http2Client;
import feign.json.JsonDecoder;
import java.util.Map;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

/**
 * A class representing a client of <a href="https://ipinfo.io/">IpInfo</a> service.
 * <p>
 * See the <a href="https://ipinfo.io/developers">Developer Documentation</a>.
 *
 * @author Witalij Berdinskich
 * @since 1.0.0
 */
@Headers({"User-Agent: ${project.groupId}:${project.artifactId} ${project.version}"})
public interface IpInfo {

  String API_LOCATOR = "https://ipinfo.io/";

  static IpInfo getInstance(@NotNull String token) {
    return Feign.builder().client(new Http2Client()).decoder(new JsonDecoder())
        .requestInterceptor(new TokenInterceptor(token)).target(IpInfo.class, API_LOCATOR);
  }

  /**
   * Get full details about the IP address.
   *
   * @param address target IP
   * @return IP details
   */
  @RequestLine("GET /{ip}")
  @Headers("Accept: application/json")
  JSONObject lookup(@Nullable @Param("ip") String address);

  /**
   * Get full details about the IP address with a custom token.
   *
   * @param address    target IP
   * @param parameters map contains at least a token
   * @return IP details
   */
  @RequestLine("GET /{ip}")
  @Headers("Accept: application/json")
  JSONObject lookup(@Nullable @Param("ip") String address,
      @QueryMap Map<String, String> parameters);

  /**
   * Get a specific field.
   *
   * @param field a specific field
   * @return value of a specific field
   */
  @RequestLine("GET /{ip}/{field}")
  @Headers("Accept: */*")
  String lookupField(@Nullable @Param("ip") String address, @Param("field") IpInfoField field);

  /**
   * Get a specific field with a custom token.
   *
   * @param field a specific field
   * @return value of a specific field
   */
  @RequestLine("GET /{ip}/{field}")
  @Headers("Accept: */*")
  String lookupField(@Nullable @Param("ip") String address, @Param("field") IpInfoField field,
      @QueryMap Map<String, String> parameters);

}
