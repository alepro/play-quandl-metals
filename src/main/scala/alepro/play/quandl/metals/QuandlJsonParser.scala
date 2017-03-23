package alepro.play.quandl.metals

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import scala.util.Failure
import scala.util.Success

import javax.inject.Inject
import javax.inject.Singleton
import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.Format
import play.api.libs.json.JsPath
import play.api.libs.json.JsResult
import play.api.libs.json.JsString
import play.api.libs.json.JsSuccess
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Reads

@Singleton
private[metals] class QuandlJsonParser @Inject()(contentProvider: ContentProvider) {
  
  def parse[T: Reads](url: String): Option[T] = {
    val tryRez = for {
      strJson <- contentProvider.get(url)
      val json = Json.parse(strJson)
      val jsResult = json.validate[T]
    } yield jsResult.asOpt
    
    tryRez match { // replace with fold
      case Success(v) => v
      case Failure(e) => 
        println(s"Failed to fetch data from $url. ${e.getMessage}")
        None
    } 
  }
  
}
   

object QuandlJsonParser {
  implicit object DateFormat extends Format[LocalDate] {
  	val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    def reads(json: JsValue): JsResult[LocalDate] = JsSuccess(LocalDate.parse(json.as[String], formatter))
    def writes(date: LocalDate) = JsString(date.format(formatter))
  }
  
  def datasetReads[T:Reads]: Reads[Dataset[T]] = {
    (
        (JsPath \ "dataset" \ "name").read[String] and
  	    (JsPath \ "dataset" \ "data").read[List[T]]
    )(Dataset.apply (_,_))
  }
  
  implicit val futureDataItemReads = new Reads[FutureDataItem] {
    def reads(json: JsValue): JsResult[FutureDataItem] = {
      JsSuccess(FutureDataItem(
          (json)(0).as[LocalDate],
          (json)(1).asOpt[BigDecimal],
          (json)(2).asOpt[BigDecimal],
          (json)(3).asOpt[BigDecimal],
          (json)(4).asOpt[BigDecimal],
          (json)(5).asOpt[Double],
          (json)(6).asOpt[BigDecimal],
          (json)(7).asOpt[Int],
          (json)(8).asOpt[Int]
          ))
    }
  }
  
  implicit val goldDataItemReads = new Reads[GoldDataItem] {
    def reads(json: JsValue): JsResult[GoldDataItem] = {
      JsSuccess(GoldDataItem(
          (json)(0).as[LocalDate],
          (json)(1).asOpt[BigDecimal],
          (json)(2).asOpt[BigDecimal],
          (json)(3).asOpt[BigDecimal],
          (json)(4).asOpt[BigDecimal],
          (json)(5).asOpt[BigDecimal],
          (json)(6).asOpt[BigDecimal]
          ))
    }
  }
  
  implicit val silverDataItemReads = new Reads[SilverDataItem] {
    def reads(json: JsValue): JsResult[SilverDataItem] = {
      JsSuccess(SilverDataItem(
          (json)(0).as[LocalDate],
          (json)(1).asOpt[BigDecimal],
          (json)(2).asOpt[BigDecimal],
          (json)(3).asOpt[BigDecimal]
          ))
    }
  }
  
  implicit val futureDatasetReads = datasetReads[FutureDataItem](futureDataItemReads)
  
  implicit val goldDatasetReads = datasetReads[GoldDataItem](goldDataItemReads)
  
  implicit val silverDatasetReads = datasetReads[SilverDataItem](silverDataItemReads)
} 