// To execute this project locally follow the steps below:

Install Apache Kafka  - https://kafka.apache.org/downloads
For this project -> kafka_2.12-2.2.0

Install Apache Spark - https://spark.apache.org/downloads.html
For this project -> spark-2.4.2-bin-hadoop2.7

Get the Wikipedia Clickstream Data from https://meta.wikimedia.org/wiki/Research:Wikipedia_clickstream#Where_to_get_the_Data and store it in a new data folder inside Kafka folder
For this project -> 2017_01_en_clickstream.tsv.gz

//After you finish all the downloads, follow the below steps to execute the project:

1. Start the Zookeeper inside the Kafka folder
bin/zookeeper-server-start.sh config/zookeeper.properties

2. Start the Kafka server inside the Kafka folder
bin/kafka-server-start.sh config/server.properties

3. Create a topic called "clicks" with single partition and only one replica
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic clicks

To check all the topics created on the Kafka server
bin/kafka-topics.sh --list --bootstrap-server localhost:9092

4. Run the Producer
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic clicks

5. Start a Consumer
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic clicks --from-beginning

6. To create an external file for all the records fetched by Kafka
bin/connect-standalone.sh config/connect-standalone.properties config/connect-file-source.properties config/connect-file-sink.properties

Note: For above steps, change the files connect-file-source.properties and connect-file-sink.properties to respective values

7. To view data in topic
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic connect-clicks --from-beginning

8. Go to Spark install directory and start the spark shell by providing propoer versions of Spark and Kafka
bin/spark-shell --packages org.apache.spark:spark-sql-kafka-0-10_2.12:2.4.0

9. Execute all the commands in ProjectCode.scala file in spark shell

10. To start streamig the output on the console, visit Kafka folder and use 'tail' command to push the data
tail -200 data/2017_01_en_clickstream.tsv | bin/kafka-console-producer.sh --broker-list localhost:9092 --topic clicks --producer.config=config/producer.properties

Note: I have taken only 200 values from the input data and generated output accordingly, you can change it to any number



