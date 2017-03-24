# play-quandl-metals
Playframework module for Quandl metals prices. Get current and futures prices for gold and silver from quandl.com.

### How to start using

The first step is to include the module in your dependencies list in *build.sbt*:

```scala
resolvers ++= Seq(
  Resolver.url("Play Quandl metals Repository", 
  	url("https://raw.github.com/alepro/play-quandl-metals/repos/"))(Resolver.ivyStylePatterns)
)

.....

libraryDependencies ++= Seq(
	"alepro" % "play-quandl-metals_2.11" % "0.1"
)    
```


### Configuring module

On second step lets integrate the module into playframework application.
Add the following to *application.conf*:

```scala
play.modules {
   enabled += alepro.play.quandl.metals.QuandlMetalsModule
}
```

If you are going to call quandl with request API key make sense to add also:

```scala
quandl.api.key = <your_quandl_API_key>
```


### Using module to get current gold prices

Having integration in place we may start getting metal prices. For time being only silver and gold prices supported in *SilverService* and *GoldService* correspondingly. So add to your service:

```scala
import alepro.play.quandl.metals.service.GoldService
import alepro.play.quandl.metals.service.SilverService

.....

@Singleton
class MyService @Inject() (goldService: GoldService, 
                           silverService: SilverService) {                                                     
```


And finally to easely get latest gold price we may use:

```scala
val dataset: Option[GoldDataset] = goldService.price
val price: Option[BigDecimal] = 
     for {
  	  ds <- dataset;
  	  di <- ds.dataItems.headOption;
  	  price <- di.usdPm.orElse(di.usdAm)
    } yield price
```

For futures you may use something like:

```scala
val futureData: Option[FutureDataset] = goldService.futurePrice(5, 2017)
val price: Option[BigDecimal] = 
	for {
  	  ds <- dataset;
  	  di <- ds.dataItems.headOption;
  	  price <- di.settle
    } yield price
```

### Licence
This software is licensed under the Apache 2 license, quoted below.

Copyright 2014 Typesafe, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
