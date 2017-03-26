package scala

import java.util.concurrent._
import java.util.{Collections, Properties}

import kafka.consumer.KafkaStream
import kafka.utils.Logging
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import play.api.libs.json.Json

import scala.collection.JavaConversions._
import scala.com.magic.insightedge.events.Events.CarEvent

/**
  * Created by tal on 3/21/17.
  */
class KafkaConsumerExample(val brokers: String,
                           val groupId: String,
                           val topic: String) {

  val props = createConsumerConfig(brokers, groupId)
  val consumer = new KafkaConsumer[String, String](props)
  var executor: ExecutorService = null
  //  val TOPIC_CAR = "topic.car"
  //  implicit val carEventReads = Json.reads[CarEvent]


  def shutdown() = {
    if (consumer != null)
      consumer.close()
    if (executor != null)
      executor.shutdown()
  }

  def createConsumerConfig(brokers: String, groupId: String): Properties = {

    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId)
    //    props.put(TOPIC_CAR,"car_events")
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")
    props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    props
  }

  def run() = {
    consumer.subscribe(Collections.singletonList(this.topic))
    //    consumer.subscribe(Collections.singletonList("car_events"))

    Executors.newSingleThreadExecutor.execute(new Runnable {
      override def run(): Unit = {
        while (true) {
          val records = consumer.poll(1000)

          for (record <- records) {
            println("Sending message: ( %s ,  %s) at offset %d".format(record.key(), record.value(), record.offset()))
//            val eventJson = Json.parse(record.value())//Json.toJson(record).toString()
            val eventJson = Json.toJson(record.value()).toString()

            println(s"Sending JSON: ${eventJson}")
//            val restHost = "Tals-MacBook-Pro.local"
//            val restPort = "8090"
            val client = HttpClientBuilder.create().build()

//            val httpPost = new HttpPost("http://localhost:8091/v1");
            println(s"---------------Broker is: http://${brokers}")

            val httpPost = new HttpPost("http://" + brokers  );
            httpPost.setEntity(new StringEntity(eventJson, "UTF-8"));
            httpPost.setHeader("Content-type", "application/json; charset=UTF-8");
            client.execute(httpPost);
            println(s"JSON Sent: ${eventJson}")

            //            System.out.println("Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset())
          }
        }
      }
    })
  }
}

object KafkaConsumerExample extends App {
  val example = new KafkaConsumerExample(args(0), args(1), args(2))
  example.run()
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