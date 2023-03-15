# ipinfo-api

Unofficial Java wrapper for [ipinfo.io][ipinfo] IP geolocation API.

## Usage

It can look up:

- own IP info,
- any IP info,
- short (geo) IP info.

Just build a client with [Feign][feign].

```java
IpInfoClient client = Feign.builder()
		.decoder(new JacksonDecoder())
		.target(IpInfoClient.class, "http://ipinfo.io/");
IpInfo info = client.lookup();

log.info("my IP {}", info.getIp());
```

If you need only one field.

```java
log.info("now I am at {}", client.lookupField(IpInfoField.Country));
```

## To Do

* Add token for paid plans of [ipinfo.io][].

## Contributing

Please read [Contributing](contributing.md).

## History

See [Changelog](changelog.md)

## License

Copyright 2017-2023 Witalij Berdinskich

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

[Apache License v2.0](LICENSE)  
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html)

[ipinfo]: http://ipinfo.io "Comprehensive IP details website and API"

[feign]: https://github.com/OpenFeign/feign "Feign makes writing java http clients easier"
