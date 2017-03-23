package alepro.play.quandl.metals

import scala.io.Source.fromURL
import scala.util.Try

import javax.inject.Singleton

@Singleton
private[metals] class ContentProvider {
  def get(url: String): Try[String] = Try(fromURL(url).mkString)
}