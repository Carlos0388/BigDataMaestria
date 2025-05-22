import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{StringIndexer, OneHotEncoder, VectorAssembler}
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.Pipeline

object MultilayerPerceptronExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Multilayer Perceptron Example").master("local[*]").getOrCreate()

    // Load the dataset
    val data = spark.read.option("header", "true").option("inferSchema", "true").option("delimiter", ";").csv("bank-full.csv")

    // Print schema and sample data
    data.printSchema()
    data.show(5)

    // Target column is "y", convert it to label 0/1
    val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("label")

    // List of categorical columns to index and encode
    val categoricalCols = Array("job", "marital", "education", "default", "housing", "loan", "contact", "month", "poutcome")

    // Index categorical columns
    val indexers = categoricalCols.map { colName =>
      new StringIndexer().setInputCol(colName).setOutputCol(colName + "Index")
    }

    // OneHotEncode indexed categorical columns
    val encoders = categoricalCols.map { colName =>
      new OneHotEncoder().setInputCol(colName + "Index").setOutputCol(colName + "Vec")
    }

    // Numeric columns to be used as features
    //val numericCols = Array("age", "duration", "campaign", "pdays", "previous", "emp.var.rate", "cons.price.idx", "cons.conf.idx", "euribor3m", "nr.employed")
    val numericCols = Array("age", "duration", "campaign", "pdays", "previous", "balance", "day")

    // Assemble all features into a single vector column
    val assembler = new VectorAssembler().setInputCols(encoders.map(_.getOutputCol) ++ numericCols).setOutputCol("features")

    // Pipeline for preprocessing to get the correct input size for the network
    val preprocessingStages = Array(labelIndexer) ++ indexers ++ encoders ++ Array(assembler)
    val preprocessingPipeline = new Pipeline().setStages(preprocessingStages)
    val preprocessedDF = preprocessingPipeline.fit(data).transform(data)
    val inputLayerSize = preprocessedDF.select("features").head().getAs[org.apache.spark.ml.linalg.Vector](0).size

    // Define layers for the neural network:
    // input layer size: number of features
    // two intermediate layers with 5 and 4 nodes
    // output layer size: 2 (binary classification)
    val layers = Array[Int](inputLayerSize, 5, 4, 2)

    // Create the trainer and set its parameters
    val trainer = new MultilayerPerceptronClassifier()
      .setLayers(layers)
      .setBlockSize(128)
      .setSeed(1234L)
      .setMaxIter(100)
      .setFeaturesCol("features")
      .setLabelCol("label")

    // Create pipeline stages (labelIndexer debe ir primero)
    val stages = Array(labelIndexer) ++ indexers ++ encoders ++ Array(assembler, trainer)
    val pipeline = new Pipeline().setStages(stages)

    // Split data into training and test sets
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3), seed = 1234L)

    // Train the model
    val model = pipeline.fit(trainingData)

    // Make predictions
    val predictions = model.transform(testData)

    // Show sample predictions
    predictions.select("label", "features", "prediction", "probability").show(10)

    // Evaluate the model
    val evaluator = new MulticlassClassificationEvaluator()
      .setLabelCol("label")
      .setPredictionCol("prediction")
      .setMetricName("accuracy")

    val accuracy = evaluator.evaluate(predictions)
    println(s"Test set accuracy = $accuracy")

    spark.stop()
  }
}

  def runMultipleEvaluations(numRuns: Int): Unit = {
    val accuracies = scala.collection.mutable.ArrayBuffer[Double]()

    for (i <- 1 to numRuns) {
      // Cambia la semilla en cada iteración para obtener splits diferentes
      val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3), seed = System.currentTimeMillis() + i)
      val model = pipeline.fit(trainingData)
      val predictions = model.transform(testData)

      val evaluator = new MulticlassClassificationEvaluator()
        .setLabelCol("label")
        .setPredictionCol("prediction")
        .setMetricName("accuracy")
      val accuracy = evaluator.evaluate(predictions)
      accuracies += accuracy

      println(s"Run $i: Test set accuracy = $accuracy")
    }

    println(s"\nAccuracy promedio: ${accuracies.sum / accuracies.length}")
  }

  // Llama a la función en tu main:
  runMultipleEvaluations(30)