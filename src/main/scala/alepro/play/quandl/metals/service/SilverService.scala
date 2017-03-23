package alepro.play.quandl.metals.service

import alepro.play.quandl.metals.FutureDataset
import alepro.play.quandl.metals.FutureQuotes
import alepro.play.quandl.metals.MetalService
import alepro.play.quandl.metals.QuandlJsonParser
import alepro.play.quandl.metals.QuandlJsonParser.futureDatasetReads
import alepro.play.quandl.metals.QuandlJsonParser.silverDatasetReads
import alepro.play.quandl.metals.SilverDataset
import alepro.play.quandl.metals.quandlApiUrl
import javax.inject.Inject
import play.api.Configuration

class SilverService @Inject() (config: Configuration, parser: QuandlJsonParser) extends MetalService(config, parser) {
  
  def price: Option[SilverDataset] = prices(1)
  
  def prices(limit: Int): Option[SilverDataset] = parse[SilverDataset](quandlApiUrl("LBMA/SILVER", limit, apiKey))
  
  def futurePrice(month: Int, year: Int) = futurePrices(month, year, 1)

  def futurePrices(month: Int, year: Int, limit: Int): Option[FutureDataset] = parse[FutureDataset](quandlApiUrl(FutureQuotes.cmeSilverCode(month, year), limit, apiKey))
  
}