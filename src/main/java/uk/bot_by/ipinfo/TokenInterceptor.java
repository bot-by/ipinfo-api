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
package uk.bot_by.ipinfo;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenInterceptor implements RequestInterceptor {

  private static final String AUTHORIZATION = "Authorization";
  private static final String BEARER = "Bearer ";

  private final String bearerToken;
  private final Logger logger = LoggerFactory.getLogger(getClass());

  public TokenInterceptor(@NotNull String token) {
    this.bearerToken = BEARER + token;
  }

  @Override
  public void apply(RequestTemplate template) {
    if (template.queries().containsKey(IpInfo.TOKEN) || (template.headers()
        .containsKey(AUTHORIZATION))) {
      logger.debug("token query parameter or authorization header are found");
    } else {
      template.header(AUTHORIZATION, bearerToken);
      logger.debug("set the authorization header");
    }
  }

}

