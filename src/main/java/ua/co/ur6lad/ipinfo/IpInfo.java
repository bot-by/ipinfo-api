package ua.co.ur6lad.ipinfo;

/*
 * Copyright 2017 Vitaliy Berdinskikh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IpInfo extends IpGeo {

	private String hostname;
	private String org;

	@JsonCreator
	public IpInfo(@JsonProperty("city") String city, @JsonProperty("country") String country, @JsonProperty("ip") String ip,
			@JsonProperty("loc") String loc, @JsonProperty("postal") String postal, @JsonProperty("region") String region,
			@JsonProperty("hostname") String hostname, @JsonProperty("org") String org) {
		super(city, country, ip, loc, postal, region);
		this.hostname = hostname;
		this.org = org;
	}

	public String getHostname() {
		return hostname;
	}

	public String getOrg() {
		return org;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder(this.getClass().getSimpleName());

		builder.append("{ ip: ").append(getIp());
		builder.append(", hostname: ").append(getHostname());
		builder.append(", country: ").append(getCountry());
		builder.append(", region: ").append(getRegion());
		builder.append(", city: ").append(getCity());
		builder.append(", loc: ").append(getLoc());
		builder.append(", postal: ").append(getPostal());
		builder.append(", org: ").append(getOrg()).append(" }");

		return builder.toString();
	}

}