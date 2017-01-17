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

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IpInfoClientIT {

	private static final String IP_ADDRESS = "2001:4860:4860::8888";

	private IpInfoClient client;

	@Test
	public void getGeoOfGooglePublicDNS() {
		IpGeo geo = client.lookupGeo(IP_ADDRESS);

		assertEquals("Google Public DNS IP address", IP_ADDRESS, geo.getIp());
	}

	@Test
	public void getInfoOfGooglePublicDNS() {
		IpInfo info = client.lookup(IP_ADDRESS);

		assertEquals("Google Public DNS IP address", IP_ADDRESS, info.getIp());
	}

	@Test
	public void getInfoOfOwnIp() {
		IpInfo info = client.lookup();

		assertNotNull("Own IP", info.getIp());
	}

	@Before
	public void setUp() {
		ObjectMapper mapper = new ObjectMapper()
				.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		client = HystrixFeign.builder()
				.decoder(new JacksonDecoder(mapper))
				.target(IpInfoClient.class, IpInfoClient.REGULAR_URL);
	}

}