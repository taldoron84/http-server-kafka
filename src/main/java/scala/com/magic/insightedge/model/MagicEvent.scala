package scala.com.magic.insightedge.model

import java.util
import java.util.Date


/**
  * @author kobi on 13/02/17.
  * @since 12.1
  */
abstract class MagicEvent(ID: Long) {
  def this() = this(-1)
}

case class CarEvent(
                                          var ID: Int,
//                     var ID: String,

                     var RECHNERBEZ: String,

                                          var IsSentByHttp: Boolean

                   ) extends MagicEvent(ID) {
  def this() = this(-1, null, false)
}


//case class CarEvent(
//var ID: Long,
//var RECHNERBEZ: String,
//var DCELINK_ID: String,
//var DATE_TIME: String,
//var SCHWEISSGERAETE_NAME: String,
//var ORTSNUMMER: String,
//var WIP: Int, //] [bit] NOT NULL,
//var ANZAHL_WIPWOP_PROG: String,
//var ANZAHL_WOP_PROG: String,
//var BOLZEN_ID: String,
//var KAROSSERIE_ID: String,
//var AUSGANGS_ID: String,
//var SCHWEISSPROGRAMM_ID: String,
//var SCHWEISSSTROM_IST: String,
//var SCHWEISSSTROM_SOLL: String,
//var SCHWEISSSTROM_TP: String,
//var SCHWEISSSTROM_TM: String,
//var SCHWEISSZEIT_IST: Long,
//var SCHWEISSZEIT_SOLL: String,
//var SCHWEISSZEIT_TP: String,
//var SCHWEISSZEIT_TM: String,
//var UL_HAUPTSTROM_IST: Long,
//var UL_HAUPTSTROM_SOLL: String,
//var UL_HAUPTSTROM_TP: String,
//var UL_HAUPTSTROM_TM: String,
//var UL_VORSTROM_IST: Long,
//var UL_VORSTROM_SOLL: String,
//var UL_VORSTROM_TP: String,
//var UL_VORSTROM_TM: String,
//var HUBHOEHE_GUELTIG: String,
//var HUBHOEHE_IST: Long,
//var HUBHOEHE_SOLL: String,
//var HUBHOEHE_TP: String,
//var HUBHOEHE_TM: String,
//var ABFALLZEIT_IST: Long,
//var ABFALLZEIT_SOLL: String,
//var DURCHDRINGUNG_GUELTIG: String,
//var DURCHDRINGUNG_IST: Long,
//var DURCHDRINGUNG_SOLL: String,
//var DURCHDRINGUNG_TP: String,
//var DURCHDRINGUNG_TM: String,
//var SCHWEISSENERGIE_IST: Long,
//var SCHWEISSENERGIE_SOLL: String,
//var SCHWEISSENERGIE_TP: String,
//var SCHWEISSENERGIE_TM: String,
//var SCHWEISSSTROM2_IST: String,
//var SCHWEISSSTROM2_SOLL: String,
//var SCHWEISSSTROM3_IST: String,
//var SCHWEISSSTROM3_SOLL: String,
//var UL_HAUPTSTROM2_IST: String,
//var UL_HAUPTSTROM3_IST: String,
//var SCHWEISSZEIT2_IST: String,
//var SCHWEISSZEIT3_IST: String,
//var SPANNUNGSSPITZE_IST: String,
//var SPANNUNGSEINBRUCH_IST: String,
//var BOLZENUEBERSTAND_GUELTIG: String,
//var BOLZENUEBERSTAND_IST: String,
//var DISTANZNULLLINIE_GUELTIG: String,
//var DISTANZNULLLINIE_IST: String,
//var LMR_WINKEL_GUELTIG: String,
//var LMR_WINKEL_IST: String,
//var PROCESSDATA_INTERFACE_ID: String,
//var ADDITIONAL01: String,
//var ADDITIONAL02: String,
//var ADDITIONAL03: String,
//var ADDITIONAL04: String,
//var ADDITIONAL05: String,
//var PRODSERIES_COMBINATION_ID: String
//
//                          ) extends MagicEvent(ID)
//
//{
//  def this() = this(
//    -1,
//    null,
//    null,
//    null,
//    null,
//    null,
//    0,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    -1,
//    null,
//    null,
//    null,
//    -1,
//    null,
//    null,
//    null,
//    -1,
//    null,
//    null,
//    null,
//    null,
//    -1,
//    null,
//    null,
//    null,
//    -1,
//    null,
//    null,
//    -1,
//    null,
//    null,
//    null,
//    -1,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null,
//    null
//  )
//}


//case class AggregatedCar(
//                     @BeanProperty
//                     @SpaceId
//                     var aggregationId: String,
//
//                     @BeanProperty
//                     var date: Date,
//
//                     @BeanProperty
//                     var modelIdAgg: util.HashMap[Int, Long]
//                   )  {
//  def this() = this(null, null, null)
//}
