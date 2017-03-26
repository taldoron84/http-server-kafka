package scala.com.magic.insightedge.events

import play.api.libs.json.{Writes, Json}

/**
  * Created by tal on 3/16/17.
  */
object Events {
  case class CarEvent(ID: Int, RECHNERBEZ: String, IsSentByHttp: Boolean)

  implicit val CarEventWrites = new Writes[CarEvent] {
    def writes(carEvent: CarEvent) = Json.obj(
      "ID" -> carEvent.ID,
      "RECHNERBEZ" -> carEvent.RECHNERBEZ,
      "IsSentByHttp" -> carEvent.IsSentByHttp
    )
  }
}
