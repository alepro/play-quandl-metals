package alepro.play.quandl

import java.time.LocalDate
import java.time.Month
import java.time.Month.APRIL
import java.time.Month.AUGUST
import java.time.Month.DECEMBER
import java.time.Month.FEBRUARY
import java.time.Month.JANUARY
import java.time.Month.JULY
import java.time.Month.JUNE
import java.time.Month.MARCH
import java.time.Month.MAY
import java.time.Month.NOVEMBER
import java.time.Month.OCTOBER
import java.time.Month.SEPTEMBER

package object metals {
  
  case class Dataset[T](name: String, dataItems: List[T])
  
  type FutureDataset = Dataset[FutureDataItem]
  
  case class FutureDataItem(date: LocalDate, open: Option[BigDecimal], high: Option[BigDecimal], 
      low: Option[BigDecimal], last: Option[BigDecimal], change: Option[Double], settle: Option[BigDecimal], 
      volume: Option[Int], prevDayOpenInterest: Option[Int])
  
  type GoldDataset = Dataset[GoldDataItem]
      
  case class GoldDataItem(date: LocalDate, usdAm: Option[BigDecimal], usdPm: Option[BigDecimal], 
      gbpAm: Option[BigDecimal], gbpPm: Option[BigDecimal], euroAm: Option[BigDecimal], euroPm: Option[BigDecimal])

  type SilverDataset = Dataset[SilverDataItem]
  
  case class SilverDataItem(date: LocalDate, usd: Option[BigDecimal], gbp: Option[BigDecimal], euro: Option[BigDecimal])
      
  
  private[metals] object FutureQuotes {

    private val monthToQuote = Map(JANUARY -> "F", FEBRUARY -> "G", MARCH -> "H", APRIL -> "J",
      MAY -> "K", JUNE -> "M", JULY -> "N", AUGUST -> "Q", SEPTEMBER -> "U",
      OCTOBER -> "V", NOVEMBER -> "X", DECEMBER -> "Z")

    def of(month: Month, year: Int): String = s"${monthToQuote(month)}$year"

    def of(at: LocalDate): String = of(at.getMonth, at.getYear)

    private def quandlFutCode: String => Month => Int => String = p => m => y => p + of(m, y)

    def metalFutureCode(f: Month => Int => String, month: Month, year: Int): String = f(month)(year)

    // Gold
    def cmeGoldCode(month: Int, year: Int): String =  metalFutureCode(quandlFutCode("CME/GC"), Month.of(month), year)

    //Silver
    def cmeSilverCode(month: Int, year: Int): String = metalFutureCode(quandlFutCode("CME/SI"), Month.of(month), year)
  }
  
  def quandlApiUrl(quote: String, items: Int, optApiKey: Option[String]): String = {
    val apiKey = optApiKey.fold("")("&api_key="+ _)
    s"https://www.quandl.com/api/v3/datasets/$quote.json?rows=$items$apiKey"
  }

}