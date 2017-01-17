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

/**
 * See http://ipinfo.io/developers
 */
@Headers("Accept: application/json")
public interface IpInfoClient {

	public static final String REGULAR_URL = "http://ipinfo.io/";
	public static final String SECURE_URL = "https://ipinfo.io/";

	@RequestLine("GET /json")
	IpInfo lookup();

	@RequestLine("GET /{ip}/json")
	IpInfo lookup(@Param("ip") String address);

	@RequestLine("GET /geo")
	IpGeo lookupGeo();

	@RequestLine("GET /{ip}/geo")
	IpGeo lookupGeo(@Param("ip") String address);

}
