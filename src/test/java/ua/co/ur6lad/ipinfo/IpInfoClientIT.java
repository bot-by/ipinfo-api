package ua.co.ur6lad.ipinfo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.hystrix.HystrixFeign;
import feign.jackson.JacksonDecoder;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

	@Before
	public void setUp() {
		ObjectMapper mapper = new ObjectMapper()
				.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
				.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

		client = HystrixFeign.builder()
				.decoder(new JacksonDecoder(mapper))
				.target(IpInfoClient.class, IpInfoClient.REGULAR_URL);
	}

}