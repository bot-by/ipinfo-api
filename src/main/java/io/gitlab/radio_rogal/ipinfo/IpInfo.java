package io.gitlab.radio_rogal.ipinfo;

/*
 * Copyright 2017-2023 Witalij Berdinskich
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

import java.util.StringJoiner;

/**
 * Full IP details bean.
 *
 * See IpInfo's <a href="https://ipinfo.io/developers/full-ip-details">Full IP details</a>.
 *
 * @since 1.0.0
 * @author Witalij Berdinskich
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IpInfo extends IpGeo {

	private final String hostname;
	private final String org;

	@JsonCreator
	public IpInfo(@JsonProperty("bogon") boolean bogon, @JsonProperty("city") String city, @JsonProperty("country") String country,
			@JsonProperty("ip") String ip, @JsonProperty("loc") String loc, @JsonProperty("postal") String postal,
			@JsonProperty("region") String region, @JsonProperty("hostname") String hostname, @JsonProperty("org") String org) {
		super(bogon, city, country, ip, loc, postal, region);
		this.hostname = hostname;
		this.org = org;
	}

	public String getHostname() {
		return hostname;
	}

	public String getOrg() {
		return org;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj instanceof IpInfo) {
			IpInfo info = (IpInfo) obj;

			if (null != getIp()) {
				return getIp().equals(info.getIp());
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return null == getIp() ? 0 : getIp().hashCode() + 4;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", IpInfo.class.getSimpleName() + "[", "]")
				.add("bogon=" + isBogon())
				.add("city='" + getCity() + "'")
				.add("country='" + getCountry() + "'")
				.add("hostname='" + hostname + "'")
				.add("ip='" + getIp() + "'")
				.add("loc='" + getLoc() + "'")
				.add("org='" + org + "'")
				.add("postal='" + getPostal() + "'")
				.add("region='" + getRegion() + "'")
				.toString();
	}

}
