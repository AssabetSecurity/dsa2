package com.assabetsecurity.core.tools.ebay

import com.fasterxml.jackson.annotation.{JsonProperty, JsonIgnoreProperties}
import com.fasterxml.jackson.dataformat.xml.annotation.{JacksonXmlProperty, JacksonXmlElementWrapper}
import com.novus.salat.annotations._
import org.joda.time.DateTime

/**
 * Created by alyas on 1/5/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
case class Item(
                 @Key("_id") //mongo id
                 //@JacksonXmlProperty(localName="itemId")


                 itemId:String,
                 title:String,
                 subtitle:Option[String] = None,
                 productId:Option[String] = None,
                 description:Option[String] = None,
                 globalId:String,
                 country:String,
                 primaryCategory:CategoryEmbedded,
                 sellingStatus:SellingStatus//Map[String, Any]
                 )

@JsonIgnoreProperties(ignoreUnknown = true)
case class FindResponseRoot(findItemsAdvancedResponse:FindResponse)

//@JsonIgnoreProperties(ignoreUnknown = true)
case class GetSingleItemResponseRoot(getSingleItemResponse:GetSingleItemResponse)

@JsonIgnoreProperties(ignoreUnknown = true)
case class SingleItemDetails(ItemID:String, Description:Option[String] = None )

@JsonIgnoreProperties(ignoreUnknown = true)
case class GetSingleItemResponse(
                                    @JacksonXmlProperty(localName="Ack")
                                    ack:String ="",
                                    @JacksonXmlProperty(localName="Timestamp")
                                    timestamp:DateTime = DateTime.now(),
                                    @JacksonXmlProperty(localName="Item")
                                    item:Option[SingleItemDetails] = None
                                    )
case class FindResponse(ack:String
                        , version:String
                        , timestamp:DateTime
  @JacksonXmlElementWrapper(localName = "searchResult")
  @JsonProperty("item")
                        , searchResult:List[Item],
                        paginationOutput:Map[String, Any],
                        itemSearchURL:String
                         )

@JsonIgnoreProperties(ignoreUnknown = true)
case class SearchResult(
                         //@JsonProperty("@count")
                         //count:Int,
                         item:List[Item])



case class CategoryEmbedded(categoryId:String, categoryName:String)

@JsonIgnoreProperties(ignoreUnknown = true)
case class SellingStatus(

                          currentPrice:Map[String, String],
                          convertedCurrentPrice:Map[String, String],
                          sellingState:String, timeLeft:String)