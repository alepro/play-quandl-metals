package alepro.play.quandl.metals.service

import alepro.play.quandl.metals.FutureDataset
import alepro.play.quandl.metals.FutureQuotes
import alepro.play.quandl.metals.GoldDataset
import alepro.play.quandl.metals.MetalService
import alepro.play.quandl.metals.QuandlJsonParser
import alepro.play.quandl.metals.QuandlJsonParser.futureDatasetReads
import alepro.play.quandl.metals.QuandlJsonParser.goldDatasetReads
import alepro.play.quandl.metals.quandlApiUrl
import javax.inject.Inject
import javax.inject.Singleton
import play.api.Configuration

@Singleton
class GoldService @Inject() (config: Configuration, parser: QuandlJsonParser) extends MetalService(config, parser) {
  
  def price: Option[GoldDataset] = prices(1)
  
  def prices(limit: Int): Option[GoldDataset] = parse[GoldDataset](quandlApiUrl("LBMA/GOLD", limit, apiKey))
  
  def futurePrice(month: Int, year: Int): Option[FutureDataset] = futurePrices(month, year, 1)
  
  def futurePrices(month: Int, year: Int, limit: Int): Option[FutureDataset] = parse[FutureDataset](quandlApiUrl(FutureQuotes.cmeGoldCode(month, year), limit, apiKey))
  
}