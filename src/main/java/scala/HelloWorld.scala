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
//object Main {
//  def main(args: Array[String]) {
//    println("Hello, world! " + args.toList)
//  }
//}

//Second example, French Date
//object FrenchDate {
//  def main(args: Array[String]) {
//    val now = new Date
//    val df = {
//      getDateInstance(LONG, Locale.FRANCE)
//    }
//    println(df format now)
//  }
//}

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



//object KafkaReader{
//
////  implicit val locationReads = Json.reads[Events.Location]
//  implicit val carEventReads = Json.reads[CarEvent]
//
//  def main(args: Array[String]): Unit ={
//    println("Starting Car Events Stream")
//    println(s"with params: ${args.toList}")
//
//    val conf = new Conf(args)
//
//    println(s"conf=${conf}")
//
//    println(s"checkpointDir=${conf.checkpointDir()}")
//
//    val ssc = StreamingContext.getOrCreate(conf.checkpointDir(), () => createContext(conf))
//
//    ssc.start()
//    ssc.awaitTermination()
//    println("done")
//  }
//
//  class Conf(args: Array[String]) extends ScallopConf(args) {
//    val masterUrl = opt[String]("master-url", required = true)
//    val spaceName = opt[String]("space-name", required = true)
//    val lookupGroups = opt[String]("lookup-groups", required = true)
//    val lookupLocators = opt[String]("lookup-locators", required = true)
//    val zookeeper = opt[String]("zookeeper", required = true)
//    val groupId = opt[String]("group-id", required = true)
//    val batchDuration = opt[String]("batch-duration", required = true)
//    val checkpointDir = opt[String]("checkpoint-dir", required = true)
//    verify()
//  }
//
//  def createContext(conf: Conf): StreamingContext = {
//    val ieConfig = InsightEdgeConfig(conf.spaceName(), Some(conf.lookupGroups()), Some(conf.lookupLocators()))
//    val scConfig = new SparkConf().setAppName("EventsStream").setMaster(conf.masterUrl()).setInsightEdgeConfig(ieConfig)
//    val kafkaParams = Map("zookeeper.connect" -> conf.zookeeper(), "group.id" -> conf.groupId())
//    val ssc = new StreamingContext(scConfig, Seconds(conf.batchDuration().toInt))
//    ssc.checkpoint(conf.checkpointDir())
//    val sc = ssc.sparkContext
//
//    val rootLogger = Logger.getRootLogger
//    rootLogger.setLevel(Level.ERROR)
//
//    // open Kafka streams
//    val carStream = createCarStream(ssc, kafkaParams)
//
//    carStream
//      .mapPartitions { partitions =>
//        partitions.map { e =>
//          print("_______________________________- " +e)
//          //          val loc = e.location
//          //          model.CarEvent(e.eventId, e.carId, e.modelId, loc.locationId, loc.state, loc.city, loc.latitude, loc.longitude)
//          model.CarEvent(e.ID, e.RECHNERBEZ, e.IsSentByHttp)
//
//        }
//      }
//      .saveToGrid()
//    ssc
//  }
//
//
//}

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

//class Ttt {
//  val boo : HelloServlet = ???
//}


//Third example
object Timer {
  def oncePerSecond(callback: () => Unit) {
    while (true) {
      callback();
      Thread sleep 1000
    }
  }

  def timeFlies() {
    println("time flies like an arrow...")
  }

  def main(args: Array[String]) {
    oncePerSecond(timeFlies)
  }
}

//Fourth example - revisit Timer with Anonymous function
object TimerAnonymous {
  def oncePerSecond(callback: () => Unit) {
    while (true) {
      callback();
      Thread sleep 1000
    }
  }

  def main(args: Array[String]) {
    oncePerSecond(() => println("time flies like an arrow..."))
  }
}

//Fifth example
//class Complex(real: Double, imaginary: Double) {
//  def re() = real
//  def im() = imaginary
//}
//
//
//class Complex(real: Double, imaginary: Double) {
//  def re = real
//  def im = imaginary
//}

//class Complex(real: Double, imaginary: Double) {
//  def re = real
//  def im = imaginary
//  override def toString() = "" + re + (if (im < 0) "" else "+") + im + "i"
//}
//
////Sixth example
//abstract class Tree
//case class Sum(l: Tree, r: Tree) extends Tree
//case class Var(n: String) extends Tree
//case class Const(v: Int) extends Tree
//
//type Environment = String => Int
//
//def eval(t: Tree, env: Environment): Int = t match {
//  case Sum(l, r) => eval(l, env) + eval(r, env)
//  case Var(n) => env(n)
//  case Const(v) => v
//}
//
//def derive(t: Tree, v: String): Tree = t match {
//  case Sum(l, r) => Sum(derive(l, v), derive(r, v))
//  case Var(n) if (v == n) => Const(1)
//  case _ => Const(0)
//}
//
//def main(args: Array[String]) {
//  val exp: Tree = Sum(Sum(Var("x"),Var("x")),Sum(Const(7),Var("y")))
//  val env: Environment = { case "x" => 5 case "y" => 7 }
//  println("Expression: " + exp)
//  println("Evaluation with x=5, y=7: " + eval(exp, env))
//  println("Derivative relative to x:\n " + derive(exp, "x"))
//  println("Derivative relative to y:\n " + derive(exp, "y"))
//}


//trait Ord {
//  def < (that: Any): Boolean
//  def <=(that: Any): Boolean = (this < that) || (this == that)
//  def > (that: Any): Boolean = !(this <= that)
//  def >=(that: Any): Boolean = !(this < that)
//}

//class Date(y: Int, m: Int, d: Int) extends Ord {
//  def year = y
//  def month = m
//  def day = d
//
//  override def toString(): String = year + "-" + month + "-" + day

//  override def equals(that: Any): Boolean =
//    that.isInstanceOf[Date] && {
//      val o = that.asInstanceOf[Date]
//      o.day == day && o.month == month && o.year == year
//    }
//
//  def <(that: Any): Boolean = {
//    if (!that.isInstanceOf[Date])
//      println("cannot compare " + that + " and a Date")
//    val o = that.asInstanceOf[Date]
//    (year < o.year) ||
//      (year == o.year && (month < o.month ||
//        (month == o.month && day < o.day)))
//  }
//}