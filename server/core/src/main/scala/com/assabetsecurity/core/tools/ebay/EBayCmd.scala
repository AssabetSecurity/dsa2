package com.assabetsecurity.core.tools.ebay

import com.assabetsecurity.core.tools.ebay.EBayConnector.EBayItemsIterator
import com.mongodb.MongoClient
import com.novus.salat._
import org.slf4s.Logging
import com.novus.salat._
import com.mongodb.casbah.Imports._

/**
 * Created by alyas on 1/5/16.
 */
object EBayCmd extends App with Logging {
  log.info("Start...")
  implicit val ctx: Context = new Context {
    val name = "DataRecord"
    override val typeHintStrategy = StringTypeHintStrategy(when = TypeHintFrequency.Always, typeHint = TypeHint)
  }
  val c = EBayDataStore.loadCategories
  val client = new MongoClient("localhost")
  val ebayCollection = client.getDB("import").getCollection("ebay")

  c.categories.filter(_.id==58058).flatMap(_.allChildren).toList.sortBy(_.id).foreach(c=>{
    try {
      log.info(s"Load Category ${c}")
      loadPages(c.id)
    } catch {
      case _ =>
    }
  })

  def loadPages(c:Int) = {

    val it = new EBayItemsIterator(c)
    while(it.hasNext) {
      val s = it.next()
      //log.info("" + s)
      val response = EBayDataStore.fromXmlString(s)
      //log.debug("" + items)
      response.searchResult.foreach(item=>{
        log.debug(""+item.itemId)
        //val dbobj =  grater[Item].asDBObject(item)
        ebayCollection.save(grater[Item].asDBObject(item))
      })
    }
  }
}
