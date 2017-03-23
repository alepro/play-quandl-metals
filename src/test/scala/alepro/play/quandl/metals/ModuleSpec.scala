package alepro.play.quandl.metals

import java.time.LocalDate
import java.time.Month.FEBRUARY

import org.scalatestplus.play.PlaySpec

class ModuleSpec extends PlaySpec {
  
  val date = LocalDate.of(2017, FEBRUARY, 1)
  
  "Chicago Mercantile Exchange Futures (CME)" must {
    val goldQuote = FutureQuotes.cmeGoldCode(2, 2017)
    
    "have gold url without api key is https://www.quandl.com/data/CME/GCG2017" in {
      quandlApiUrl(goldQuote, 1, None) mustBe "https://www.quandl.com/api/v3/datasets/CME/GCG2017.json?rows=1"
    }
    "have gold url with api key is https://www.quandl.com/data/CME/GCG2017?api_key=abc" in {
      quandlApiUrl(goldQuote, 1, Some("abc")) mustBe "https://www.quandl.com/api/v3/datasets/CME/GCG2017.json?rows=1&api_key=abc"
    }
    "have silver api url is https://www.quandl.com/data/CME/SIG2017?api_key=abc" in {
      val silverQuote = FutureQuotes.cmeSilverCode(2, 2017)
      quandlApiUrl(silverQuote, 3, Some("abc")) mustBe "https://www.quandl.com/api/v3/datasets/CME/SIG2017.json?rows=3&api_key=abc"
    }
  }
  
  "London Bullion Market Association (LBMA)" must {
    "have gold api url is https://www.quandl.com/api/v3/datasets/LBMA/GOLD.json?rows=1&api_key=abc" in {
      quandlApiUrl("LBMA/GOLD", 1, Some("abc")) mustBe "https://www.quandl.com/api/v3/datasets/LBMA/GOLD.json?rows=1&api_key=abc"
    }
    "have silver api url is https://www.quandl.com/api/v3/datasets/LBMA/SILVER.json?rows=5&api_key=abc" in {
      quandlApiUrl("LBMA/SILVER", 5, Some("abc")) mustBe "https://www.quandl.com/api/v3/datasets/LBMA/SILVER.json?rows=5&api_key=abc"
    }
  }
  
}