# ipinfo-api

Unofficial Java wrapper for [ipinfo.io][] IP geolocation API. 

## Usage

It can look up:

* own IP info,
* any IP info,
* short (geo) IP info.

Just build a client with [Feign][] and [Hystrix][] or [OkHttp][] or _your love provider_.

```java
IpInfoClient client = HystrixFeign.builder()
		.decoder(new JacksonDecoder())
		.target(IpInfoClient.class, IpInfoClient.REGULAR_URL);
IpInfo info = client.lookup();

log.info("my IP {}", info.getIp());
```

## License

`ipinfo-api` is licensed under the **Apache License v2.0**

[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

[ipinfo.io]: http://ipinfo.io "Comprehensive IP details website and API"
[Feign]: https://github.com/OpenFeign/feign "Feign makes writing java http clients easier"
[Hystrix]: https://github.com/Netflix/Hystrix "Fault tolerance library"
[OkHttp]: https://github.com/square/okhttp "HTTP+HTTP/2 client for Java" 