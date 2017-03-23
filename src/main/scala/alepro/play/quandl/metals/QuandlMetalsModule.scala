package alepro.play.quandl.metals

import com.google.inject.AbstractModule

import alepro.play.quandl.metals.service.GoldService
import alepro.play.quandl.metals.service.SilverService

class QuandlMetalsModule extends AbstractModule {
    override def configure() = {
      bind(classOf[GoldService])
      bind(classOf[SilverService])
      bind(classOf[ContentProvider])
      bind(classOf[QuandlJsonParser])
    }
  }