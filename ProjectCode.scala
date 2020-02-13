// Check out the README file first
// Specify the schema of the incoming Wikipedia clickstream and parse method
import scala.util.Try

case class Click(prev: String, curr: String, link: String, n: Long)

def parseVal(x: Array[Byte]): Option[Click] = {
    val split: Array[String] = new Predef.String(x).split("\\t")
    if (split.length == 4) {
      Try(Click(split(0), split(1), split(2), split(3).toLong)).toOption
    } else
      None
}

// Setup structured streaming to read from Kafka
val records = spark.readStream.format("kafka").option("subscribe", "clicks").option("failOnDataLoss", "false").option("kafka.bootstrap.servers", "localhost:9092").load()

// Process the records
val messages = records.select("value").as[Array[Byte]].flatMap(x => parseVal(x)).groupBy("curr").agg(Map("n" -> "sum")).sort($"sum(n)".desc)

// Output the console and start streaming data
val query = messages.writeStream.outputMode("complete").option("truncate", "false").format("console").start()

// To start streamig the output on the console, visit Kafka folder and use 'tail' command to push the data
tail -200 data/2017_01_en_clickstream.tsv | bin/kafka-console-producer.sh --broker-list localhost:9092 --topic clicks --producer.config=config/producer.properties

// Note: I have taken only 200 values from the input data and generated output accordingly, you can change it to any number