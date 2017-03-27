package scala

//import com.mashape.unirest.http.Unirest
//import com.mashape.unirest.http.Unirest
//import org.apache.http.client.methods.{CloseableHttpResponse, HttpPost}
//import org.apache.http.entity.StringEntity
//import org.apache.http.impl.client.{HttpClientBuilder, HttpClients, CloseableHttpClient}
import java.util.stream.Collectors

//import com.magic.events.Events
//import com.magic.events.Events.CarEvent
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.rogach.scallop.ScallopConf
import play.api.libs.json.Json
import scala.com.magic.insightedge.events.Events
import scala.com.magic.insightedge.events.Events.CarEvent

import scala.io
import java.util.{Date, Locale}
import java.text.DateFormat
import java.text.DateFormat._

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import org.eclipse.jetty.server.{ Handler, Server => JettyServer }
import org.eclipse.jetty.server.handler.{ DefaultHandler, HandlerList, ResourceHandler }
import org.eclipse.jetty.servlet.{ ServletHandler, ServletHolder }
import javax.servlet.http.{ HttpServlet, HttpServletRequest, HttpServletResponse }
import java.net.InetSocketAddress
import org.rogach.scallop.ScallopConf


/**
  * Created by tal on 1/30/17.
  */
class HelloWorld {

}



//First example, Hello World
object Main {
  def main(args: Array[String]): Unit = {

    println("-- Running CSV producer")

    val bufferedSource = io.Source.fromFile("/Users/tal/Downloads/temp2.csv")
    //drop the headers first line
    for (line <- bufferedSource.getLines.drop(1)) {
      val cols = line.split(",").map(_.trim)
      val tempCar = CarEvent(cols(0).toInt, cols(1), false)
      val eventJson = Json.toJson(tempCar).toString()
      println(s"sent event with the following fields: ${cols(0)} | ${cols(1)}")
      println(s"JSON is: ${eventJson}")

      val restHost = "Tals-MacBook-Pro.local"
      val restPort = "8090"
      val client = HttpClientBuilder.create().build()

      val httpPost = new HttpPost("http://localhost:8080/v1");
      httpPost.setEntity(new StringEntity(eventJson, "UTF-8"));
      httpPost.setHeader("Content-type", "application/json; charset=UTF-8");
      client.execute(httpPost);



    }
    bufferedSource.close

  }
}


object HttpServer {

  def main(args: Array[String]) {
    val server =  new Server(8091)
    val handler = new ServletHandler()
    server.setHandler(handler)
    handler.addServletWithMapping(new ServletHolder(new HelloServlet), "/*")

    // Start things up!
    server.start();
    server.join();
  }


  class HelloServlet extends HttpServlet
  {
    override def doPost(req: HttpServletRequest, resp: HttpServletResponse): Unit = {
      val temp: String = req.getReader.lines().collect(Collectors.joining())
      println(s"STRING in server: ${temp}")
    }
  }

}