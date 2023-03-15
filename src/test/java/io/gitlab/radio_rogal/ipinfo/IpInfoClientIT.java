package io.gitlab.radio_rogal.ipinfo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.Logger.Level;
import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;
import feign.slf4j.Slf4jLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.stream.Collectors;

import static feign.Util.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IpInfoClientIT {

	private static final String LOOPBACK_IP_ADDRESS = "127.0.0.1";
	private static final String GOOGLE_DNS_IP_ADDRESS = "2001:4860:4860::8888";

	private IpInfoClient client;

	@Test
	public void getGeoOfBogon() {
		IpGeo geo = client.lookupGeo(LOOPBACK_IP_ADDRESS);

		assertEquals(LOOPBACK_IP_ADDRESS, geo.getIp(), "Bogon IP address");
		assertTrue(geo.isBogon(), "Loopback");
	}

	@Test
	public void getGeoOfGooglePublicDNS() {
		IpGeo geo = client.lookupGeo(GOOGLE_DNS_IP_ADDRESS);

		assertEquals(GOOGLE_DNS_IP_ADDRESS, geo.getIp(), "Google Public DNS IP address");
		assertFalse(geo.isBogon(), "Reserved IP");
	}

	@Test
	public void getInfoOfGooglePublicDNS() {
		IpInfo info = client.lookup(GOOGLE_DNS_IP_ADDRESS);

		assertEquals(GOOGLE_DNS_IP_ADDRESS, info.getIp(), "Google Public DNS IP address");
		assertFalse(info.isBogon(), "Reserved IP");
	}

	@Test
	public void getInfoOfOwnIp() {
		IpInfo info = client.lookup();

		assertNotNull(info.getIp(), "Own IP");
		assertFalse(info.isBogon(), "Reserved IP");
	}

	@Test
	public void getIp() throws IOException {
		Response response = client.lookupField(LOOPBACK_IP_ADDRESS, IpInfoField.Ip);

		assertNotNull(response, "Own IP");
		assertEquals(200, response.status(), "HTTP 200 OK");
		BufferedReader bodyReader = new BufferedReader(response.body().asReader());
		assertEquals(LOOPBACK_IP_ADDRESS, bodyReader.lines().collect(Collectors.joining("\n")), "Loopback");
	}

	@Test
	public void getCountry() throws IOException {
		Response response = client.lookupField(GOOGLE_DNS_IP_ADDRESS, IpInfoField.Country);

		assertNotNull(response, "Own IP");
		assertEquals(200, response.status(), "HTTP 200 OK");
		BufferedReader bodyReader = new BufferedReader(response.body().asReader(UTF_8));
		assertEquals("US", bodyReader.lines().collect(Collectors.joining("\n")), "US");
	}

	@BeforeEach
	public void setUp() {
		ObjectMapper mapper = new ObjectMapper()
				.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		client = HystrixFeign.builder()
				.logLevel(Level.BASIC)
				.logger(new Slf4jLogger())
				.decoder(new JacksonDecoder(mapper))
				.target(IpInfoClient.class, "http://ipinfo.io/");
	}

}
