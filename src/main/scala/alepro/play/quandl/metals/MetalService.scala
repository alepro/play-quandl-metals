package alepro.play.quandl.metals

import scala.util.Try

import play.api.Configuration
import play.api.libs.json.Reads

abstract class MetalService(config: Configuration, parser: QuandlJsonParser) {
  
  lazy val apiKey = Try(config.underlying.getString("quandl.api.key")).toOption
  
  def price: Option[Dataset[_]]
  def prices(limit: Int): Option[Dataset[_]]
  def futurePrice(month: Int, year: Int): Option[FutureDataset]
  def futurePrices(month: Int, year: Int, limit: Int): Option[FutureDataset]
  
  protected def parse[T: Reads](url: String): Option[T] = parser.parse[T](url)
  
}