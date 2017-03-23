package alepro.play.quandl.metals

import java.time.LocalDate

import org.scalatestplus.play.PlaySpec

class FutureQuotesSpec extends PlaySpec {
   
  val from = LocalDate.of(2017, 1 ,15)
  
  "FutureQuotes from Jan 15 2017" must {
    "return Feb 2017 quote G2017" in {
      FutureQuotes.of(from.plusMonths(1)) mustBe "G2017"
    }
    "return Jan 2018 quote F2018" in {
      FutureQuotes.of(from.plusMonths(12)) mustBe "F2018"
    }
    "return March 2017 quote H2017" in {
      FutureQuotes.of(from.plusMonths(2)) mustBe "H2017"
    }
    "return April 2017 quote J2017" in {
      FutureQuotes.of(from.plusMonths(3)) mustBe "J2017"
    }
    "return May 2017 quote K2017" in {
      FutureQuotes.of(from.plusMonths(4)) mustBe "K2017"
    }
    "return June 2017 quote M2017" in {
      FutureQuotes.of(from.plusMonths(5)) mustBe "M2017"
    }
    "return July 2017 quote N2017" in {
      FutureQuotes.of(from.plusMonths(6)) mustBe "N2017"
    }
    "return August 2017 quote Q2017" in {
      FutureQuotes.of(from.plusMonths(7)) mustBe "Q2017"
    }
    "return Sept 2017 quote U2017" in {
      FutureQuotes.of(from.plusMonths(8)) mustBe "U2017"
    }
    "return Oct 2017 quote V2017" in {
      FutureQuotes.of(from.plusMonths(9)) mustBe "V2017"
    }
    "return Nov 2017 quote X2017" in {
      FutureQuotes.of(from.plusMonths(10)) mustBe "X2017"
    }
    "return Dec 2017 quote Z2017" in {
      FutureQuotes.of(from.plusMonths(11)) mustBe "Z2017"
    }
  }
  
  "FutureQuotes from Jan 15 2017 for gold" must {
    "return code CME/GCG2017 for Feb 2017 " in {
      FutureQuotes.cmeGoldCode(2, 2017) mustBe "CME/GCG2017"
    }
  }
  
  "FutureQuotes from Jan 15 2017 for silver" must {
    "return code CME/SIG2017 for Feb 2017 " in {
      FutureQuotes.cmeSilverCode(2, 2017) mustBe "CME/SIG2017"
    }
  }
  
}