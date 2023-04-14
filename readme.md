# IPinfo API

Unofficial Java wrapper for [ipinfo.io][ipinfo] IP geolocation API.

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/2d65fe469c2a4e0da28cc8995c91234b)](https://app.codacy.com/gl/bot-by/ipinfo-api/dashboard?utm_source=gl&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Codacy Coverage](https://app.codacy.com/project/badge/Coverage/2d65fe469c2a4e0da28cc8995c91234b)](https://app.codacy.com/gl/bot-by/ipinfo-api/dashboard?utm_source=gl&utm_medium=referral&utm_content=&utm_campaign=Badge_coverage)
[![Java Version](https://img.shields.io/static/v1?label=java&message=11&color=blue&logo=java&logoColor=E23D28)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

Table of Contents
=================

   * [Getting started](#getting-started)
      * [Usage](#usage)
   * [Contributing](#contributing)
   * [History](#history)
   * [License](#license)

Created by [gh-md-toc](https://github.com/ekalinin/github-markdown-toc)

## Getting started

This can look up:

- information about your own IP address or someone else,
- short (one-field) information about IP address.

### Usage

You get a ready client

```java
IpInfo client = IpInfo.getInstance("qwerty-token");
```

or make your own with [Feign][feign] and [Java JSON][java-json]

```java
IpInfo client = Feign.builder()
                     .client(new Http2Client())
                     .requestInterceptor(new TokenInterceptor("qwerty-token"))
                     .decoder(new JsonDecoder())
                     .target(IpInfo.class, IpInfo.API_LOCATOR);
```

The token is optional on free plan, see more on  [IPinfo's pricing][pricing] page.

Now you get details of your IP address;

```java
client.lookup(null);
```

or someone else

```java
client.lookup("8.8.8.8")
```

The IPinfo supports IP6 addresses

```java
client.lookup("2001:4860:4860::8888")
```

The volume of information depends on your plan, see [some samples here][api-responses].

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

[pricing]: https://ipinfo.io/pricing "Free, Basic, Standard and Business plans"

[feign]: https://github.com/OpenFeign/feign "Feign makes writing java http clients easier"

[java-json]: https://github.com/stleary/JSON-java "The JSON-Java package is a reference implementation"

[api-responses]: https://ipinfo.io/pricing#api-responses "Sample API responses"
