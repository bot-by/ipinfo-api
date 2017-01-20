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

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

/**
 * A class representing a client of <a href="http://ipinfo.io/">IpInfo</a> service.
 *
 * See IpInfo's <a href="http://ipinfo.io/developers">Developer Documentation</a>.
 *
 * @since 1.0.0
 * @author Vitaliy Berdinskikh
 */
@Headers("Accept: application/json")
public interface IpInfoClient {

	/**
	 * Get full details about own IP.
	 *
	 * @return IP details
	 */
	@RequestLine("GET /json")
	IpInfo lookup();

	/**
	 * Get full details about the target IP.
	 *
	 * @param address target IP
	 * @return IP details
	 */
	@RequestLine("GET /{ip}/json")
	IpInfo lookup(@Param("ip") String address);

	/**
	 * Get short details about own IP.
	 *
	 * @return IP details
	 */
	@RequestLine("GET /geo")
	IpGeo lookupGeo();

	/**
	 * Get short details about the target IP.
	 *
	 * @param address target IP
	 * @return IP details
	 */
	@RequestLine("GET /{ip}/geo")
	IpGeo lookupGeo(@Param("ip") String address);

	/**
	 * Get a specific field.
	 *
	 * @param field a specific field
	 * @return value of a specific field
	 */
	@RequestLine("GET /{field}")
	Response lookupField(@Param("field") IpInfoField field);

	/**
	 * Get a specific field of the target IP.
	 *
	 * @param address target IP
	 * @param field a specific field
	 * @return value of a specific field
	 */
	@RequestLine("GET /{ip}/{field}")
	Response lookupField(@Param("ip") String address, @Param("field") IpInfoField field);

}
