# ipinfo-api

Unofficial Java wrapper for [ipinfo.io][] IP geolocation API. 

## Usage

It can look up:

* own IP info,
* any IP info,
* short (geo) IP info.

Just build a client with [Feign][] and [Hystrix][] or [OkHttp][].

```java
IpInfoClient client = HystrixFeign.builder()
		.decoder(new JacksonDecoder())
		.target(IpInfoClient.class, "http://ipinfo.io/");
IpInfo info = client.lookup();

log.info("my IP {}", info.getIp());
```

If you need only one field.

```java
log.info("now I am at {}", client.lookupField(IpInfoField.Country));
```

[![Maven Central](https://img.shields.io/maven-central/v/ua.co.ur6lad/ipinfo-api.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22ua.co.ur6lad%22%20AND%20a%3A%22ipinfo-api%22)

[![Download](https://api.bintray.com/packages/ur6lad/maven/ipinfo-api/images/download.svg?version=1.0.0) ](https://bintray.com/ur6lad/maven/ipinfo-api/1.0.0/link)

## To Do

* Add token for paid plans of [ipinfo.io][].

## License

`ipinfo-api` is licensed under the **Apache License v2.0**

[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

[ipinfo.io]: http://ipinfo.io "Comprehensive IP details website and API"
[Feign]: https://github.com/OpenFeign/feign "Feign makes writing java http clients easier"
[Hystrix]: https://github.com/Netflix/Hystrix "Fault tolerance library"
[OkHttp]: https://github.com/square/okhttp "HTTP+HTTP/2 client for Java" 