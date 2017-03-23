package alepro.play.quandl.metals

import java.time.LocalDate
import java.time.LocalDate.{ of => dt }

import scala.io.Source
import scala.util.Try

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.OneServerPerSuite
import org.scalatestplus.play.PlaySpec

import alepro.play.quandl.metals.service.GoldService
import alepro.play.quandl.metals.service.SilverService
import play.api.Configuration
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder


class MetalServiceSpec extends PlaySpec with MockitoSugar with OneServerPerSuite {
  
  val contentServiceMock = mock[ContentProvider]
  
  implicit override lazy val app = new GuiceApplicationBuilder()
  .configure(Configuration("quandl.api.key" -> "xyz"))
  .bindings(new QuandlMetalsModule)
  .overrides(bind[ContentProvider].toInstance(contentServiceMock))
  .build
  
  val date = LocalDate.of(2017, 10, 1)
  
  "GoldService" should {
    val service = app.injector.instanceOf(classOf[GoldService])
    
    "get latest gold future price" in {
      when(contentServiceMock.get("https://www.quandl.com/api/v3/datasets/CME/GCV2017.json?rows=1&api_key=xyz")).thenReturn(trySource("jsons/goldFuture1.json"))
      service.futurePrice(10, 2017) mustBe Some(Dataset[FutureDataItem]("Gold Futures, October 2017 (GCV2017)", List(
          FutureDataItem(dt(2017, 3, 9), Some(1216.7), Some(1218.3), Some(1211), Some(1211.2), Some(6.2), Some(1212.8), Some(512), Some(4458)))))
    }
    
    "get latest 3 gold future prices" in {
      when(contentServiceMock.get("https://www.quandl.com/api/v3/datasets/CME/GCV2017.json?rows=3&api_key=xyz")).thenReturn(trySource("jsons/goldFuture3.json"))
      service.futurePrices(10, 2017, 3) mustBe Some(Dataset[FutureDataItem]("Gold Futures, October 2017 (GCV2017)",
          List(
              FutureDataItem(dt(2017, 3, 9), Some(1216.7), Some(1218.3), Some(1211), Some(1211.2), Some(6.2), Some(1212.8), Some(512), Some(4458)), 
              FutureDataItem(dt(2017, 3, 8), Some(1227.6), Some(1227.6), Some(1216.3), Some(1216.8), Some(6.7), Some(1219), Some(267), Some(4464)), 
              FutureDataItem(dt(2017, 3, 7), Some(1231.3), Some(1231.3), Some(1223.7), Some(1225.4), Some(9.4), Some(1225.7), Some(217), Some(4287))
              )))
    }
    
    "failed to parse gold future prices" in {
      when(contentServiceMock.get("https://www.quandl.com/api/v3/datasets/CME/GCV2017.json?rows=3&api_key=xyz")).thenReturn(Try("{}"))
      service.futurePrices(10, 2017, 3) mustBe None
    }
    
    "get latest gold price" in {
      when(contentServiceMock.get("https://www.quandl.com/api/v3/datasets/LBMA/GOLD.json?rows=1&api_key=xyz")).thenReturn(trySource("jsons/goldPrice1.json"))
      service.price mustBe Some(Dataset[GoldDataItem]("Gold Price: London Fixing", 
          List(
              GoldDataItem(dt(2017, 3, 10), Some(1196.55), Some(1202.65), Some(983.56), Some(988.94), Some(1127.15), Some(1131.09)))
          ))
    }
    
    "get latest 2 gold prices" in {
      when(contentServiceMock.get("https://www.quandl.com/api/v3/datasets/LBMA/GOLD.json?rows=2&api_key=xyz")).thenReturn(trySource("jsons/goldPrices2.json"))
      service.prices(2) mustBe Some(Dataset[GoldDataItem]("Gold Price: London Fixing", 
          List(
              GoldDataItem(dt(2017, 3, 10), Some(1196.55), Some(1202.65), Some(983.56), Some(988.94), Some(1127.15), Some(1131.09)),
              GoldDataItem(dt(2017, 3, 9), Some(1204.6), Some(1206.55), Some(991.39), Some(990.82), Some(1140.64), Some(1139.35)))
          ))
    }
  }
  
  "SilverService" should {
    val service = app.injector.instanceOf(classOf[SilverService])
    
    "get latest silver future price" in {
      when(contentServiceMock.get("https://www.quandl.com/api/v3/datasets/CME/SIG2017.json?rows=1&api_key=xyz"))
      .thenReturn(trySource("jsons/silverFuture1.json"))
      service.futurePrice(2, 2017) mustBe Some(Dataset[FutureDataItem]("Silver Futures, February 2017 (SIG2017)", List(
          FutureDataItem(dt(2017, 2, 24), None, None, None, None, Some(0.222), Some(18.335), Some(0), Some(67)))))
    }
    
    "get latest silver price" in {
      when(contentServiceMock.get("https://www.quandl.com/api/v3/datasets/LBMA/SILVER.json?rows=1&api_key=xyz")).thenReturn(trySource("jsons/silverPrice1.json"))
      service.price mustBe Some(Dataset[SilverDataItem]("Silver Price: London Fixing", 
          List(
              SilverDataItem(dt(2017, 3, 10), Some(16.89), Some(13.9104), Some(15.9219)))
          ))
    }
    
    
  }
  
  
  private def trySource(file: String) = Try(Source.fromInputStream(getClass.getClassLoader.getResourceAsStream(file)).getLines.mkString)
  
}