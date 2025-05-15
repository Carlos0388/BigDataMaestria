import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

object PracticaEvaluatoriaUnidad2 {
  def main(args: Array[String]): Unit = {
    // Initialize Spark session
    val spark = SparkSession.builder.appName("MultilayerPerceptronClassifierIris").getOrCreate()

    import spark.implicits._

    // 1. Load Iris.csv dataset from local file
    val rawDF = spark.read.option("header", "true").option("inferSchema", "true").csv("iris.csv")

    // Data cleaning: drop rows with nulls (if any)
    val df = rawDF.na.drop()

    // 2. Print column names
    println("Column names:")
    df.columns.foreach(println)

    // 3. Print schema
    println("Schema:")
    df.printSchema()

    // 4. Print first 5 rows
    println("First 5 rows:")
    df.show(5)

    // 5. Use describe() to learn more about the data
    println("Describe summary:")
    df.describe().show()

    // 6. Transform categorical label column (assumed to be "species") to numeric index
    val labelIndexer = new StringIndexer().setInputCol("species").setOutputCol("label").fit(df)
    val indexedDF = labelIndexer.transform(df)

    // Assemble features into a vector column "features"
    val featureCols = Array("sepal_length", "sepal_width", "petal_length", "petal_width")
    val assembler = new VectorAssembler().setInputCols(featureCols).setOutputCol("features")
    val finalDF = assembler.transform(indexedDF).select("features", "label")

    // Split data into training and test sets
    val Array(trainingData, testData) = finalDF.randomSplit(Array(0.7, 0.3), seed = 1234L)

    // 7. Build the Multilayer Perceptron Classifier model
    // Architecture: input layer size = 4 (features), two hidden layers with 5 and 4 nodes, output layer size = 3 (classes)
    val layers = Array[Int](4, 5, 4, 3)

    val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100).setFeaturesCol("features").setLabelCol("label")

    val model = trainer.fit(trainingData)

    // 8. Print model results and observations
    val result = model.transform(testData)
    val predictionAndLabels = result.select("prediction", "label")

    val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")

    val accuracy = evaluator.evaluate(predictionAndLabels)
    println(s"Test set accuracy = $accuracy")

    println("Sample predictions:")
    result.select("features", "label", "prediction").show()

    // Stop Spark session
    spark.stop()
  }
}
