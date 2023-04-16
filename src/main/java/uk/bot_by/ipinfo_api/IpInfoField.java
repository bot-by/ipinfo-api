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
package uk.bot_by.ipinfo_api;

import static java.util.Objects.isNull;

public enum IpInfoField {

  Address("ip"), AnyCast("anycast"), Bogon, City, Country, Hostname,
  Location("loc"), Organisation("org"), Postal, Region, Timezone;

  private String fieldName;

  IpInfoField() {
  }

  IpInfoField(String fieldName) {
    this.fieldName = fieldName;
  }

  public String toString() {
    return (isNull(fieldName)) ? name().toLowerCase() : fieldName;
  }

}
