package spark

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

object ScalaWordCount {
  def main(args: Array[String]) {

    val sc = new SparkContext(conf)
    //First test
//    test1(sc, args)

//    test2(sc, args)

    test3(sc)




    val tal = 1
    sc.stop()
  }

  def test4(sc: SparkContext): Unit ={

    val logFile = "/Users/tal/InsightEdge/gigaspaces-insightedge-1.0.0-premium/README.md" // Should be some file on your system

  }

  def test3(sc: SparkContext): Unit ={
    val logFile = "/Users/tal/InsightEdge/gigaspaces-insightedge-1.0.0-premium/README.md" // Should be some file on your system

    //Load our input data
    val input = sc.textFile(logFile)
    //Split it up into words
    val words = input.flatMap(line => line.split(" "))
    //Transform into pairs and cound
    val counts  = words.map(word => (word, 1)).reduceByKey{case (x,y) => x + y}
    //Print the word count
//    println(counts)
    counts.saveAsTextFile("/Users/tal/InsightEdge/gigaspaces-insightedge-1.0.0-premium/outputFile")


  }

  def test2(sc : SparkContext, args: Array[String]): Unit = {

    val logFile = "/Users/tal/InsightEdge/gigaspaces-insightedge-1.0.0-premium/README.md" // Should be some file on your system

    val logData = sc.textFile(logFile, 2).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")

  }

  val conf = {
    new SparkConf()
      .setAppName("Spark Count")
      .setMaster("local[*]")
  }

  def test1(sc : SparkContext, args: Array[String]): Unit = {
    val sc = new SparkContext(conf)
    val threshold = args(1).toInt

    // split each document into words
    val tokenized = sc.textFile(args(0)).flatMap(_.split(" "))

    // count the occurrence of each word
    val wordCounts = tokenized.map((_, 1)).reduceByKey(_ + _)

    // filter out words with less than threshold occurrences
    val filtered = wordCounts.filter(_._2 >= threshold)

    // count characters
    val charCounts = filtered.flatMap(_._1.toCharArray).map((_, 1)).reduceByKey(_ + _)

    System.out.println(charCounts.collect().mkString(", "))
  }
}