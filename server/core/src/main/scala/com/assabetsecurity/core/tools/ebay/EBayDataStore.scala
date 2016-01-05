package com.assabetsecurity.core.tools.ebay

import java.io.File

import com.fasterxml.jackson.annotation.{JsonProperty, JsonIgnoreProperties}
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.novus.salat.annotations.Key
import org.joda.time.DateTime
import org.slf4s.Logging

import scala.xml.XML

/**
 * Created by alyas on 12/10/15.
 */

object EBayDataStore extends Logging {
  val mapper = {
    val m = new XmlMapper()
    m.registerModule(DefaultScalaModule)
    m.registerModule(new JodaModule());
    m
  }

  def loadCategories:Categories = {
    mapper.readValue(new File("./data/ebay_categories_full.xml"), classOf[Categories])
  }

  def fromXmlString(s:String):FindResponse = {
    //log.debug("")
    mapper
      .readValue[FindResponse](
        s.replaceAllLiterally("&", "&amp;"),
        classOf[FindResponse])

    //json.findItemsAdvancedResponse.searchResult.item
    //List.empty
  }
  def getSingleItemResponseFromXml(s:String):GetSingleItemResponse = {

    val xml = XML.loadString(s)
    mapper
      .readValue[GetSingleItemResponse](
        xml.toString(),
        classOf[GetSingleItemResponse])
  }

}
