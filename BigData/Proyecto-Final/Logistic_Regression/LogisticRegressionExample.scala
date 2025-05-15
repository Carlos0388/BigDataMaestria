import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{StringIndexer, OneHotEncoder, VectorAssembler}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.{BinaryClassificationEvaluator, MulticlassClassificationEvaluator}
import org.apache.spark.ml.Pipeline

object LogisticRegressionExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Logistic Regression Example").master("local[*]").getOrCreate()

    // Load the dataset
    val data = spark.read.option("header", "true").option("inferSchema", "true").option("delimiter", ";").csv("bank-full.csv")

    // Print schema and sample data
    data.printSchema()
    data.show(5)

    // Target column is "y", convert it to label 0/1
    val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("label").fit(data)

    // List of categorical columns to index and encode
    val categoricalCols = Array("job", "marital", "education", "default", "housing", "loan", "contact", "month", "poutcome")

    // Index categorical columns
    val indexers = categoricalCols.map { colName =>
      new StringIndexer().setInputCol(colName).setOutputCol(colName + "Index").fit(data)
    }

    // OneHotEncode indexed categorical columns
    val encoders = categoricalCols.map { colName =>
      new OneHotEncoder().setInputCol(colName + "Index").setOutputCol(colName + "Vec")
    }

    // Numeric columns to be used as features
    val numericCols = Array("age", "duration", "campaign", "pdays", "previous", "emp.var.rate", "cons.price.idx", "cons.conf.idx", "euribor3m", "nr.employed")

    // Assemble all features into a single vector column
    val assembler = new VectorAssembler().setInputCols(encoders.map(_.getOutputCol) ++ numericCols).setOutputCol("features")

    // Create logistic regression model
    val lr = new LogisticRegression().setFeaturesCol("features").setLabelCol("label").setMaxIter(100).setRegParam(0.01)

    // Create pipeline stages
    val stages = indexers ++ encoders ++ Array(assembler, lr)

    val pipeline = new Pipeline().setStages(stages)

    // Split data into training and test sets
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3), seed = 1234L)

    // Fit the model
    val model = pipeline.fit(trainingData)

    // Make predictions
    val predictions = model.transform(testData)

    // Show sample predictions
    predictions.select("label", "features", "prediction", "probability").show(10)

    // Evaluate the model
    val evaluator = new BinaryClassificationEvaluator().setLabelCol("label").setRawPredictionCol("rawPrediction").setMetricName("areaUnderROC")

    val auc = evaluator.evaluate(predictions)
    println(s"Test Area Under ROC: $auc")

    val accuracyEvaluator = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("accuracy")

    val accuracy = accuracyEvaluator.evaluate(predictions)
    println(s"Test Accuracy: $accuracy")

    spark.stop()
  }
}
