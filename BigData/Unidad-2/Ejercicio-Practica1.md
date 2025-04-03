```scala
////////////////////////////////////////////
//// LINEAR REGRESSION EXERCISE ///////////
/// Complete las tareas comentadas ///
/////////////////////////////////////////

scala> import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.regression.LinearRegression

scala> import org.apache.log4j._
import org.apache.log4j._

scala> Logger.getLogger("org").setLevel(Level.ERROR)

scala> import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SparkSession

scala> val spark = SparkSession.builder().appName("LinearRegressionEcommerce").getOrCreate()
25/04/02 17:01:42 WARN SparkSession: Using an existing Spark session; only runtime SQL configurations will take effect.
val spark: org.apache.spark.sql.SparkSession = org.apache.spark.sql.SparkSession@70d4ea55

scala> val data = spark.read.option("header", "true").option("inferSchema", "true").csv("Clean-Ecommerce.csv")
val data: org.apache.spark.sql.DataFrame = [Email: string, Avatar: string ... 5 more fields]

scala> data.printSchema()
root
 |-- Email: string (nullable = true)
 |-- Avatar: string (nullable = true)
 |-- Avg Session Length: double (nullable = true)
 |-- Time on App: double (nullable = true)
 |-- Time on Website: double (nullable = true)
 |-- Length of Membership: double (nullable = true)
 |-- Yearly Amount Spent: double (nullable = true)


scala> data.head(1)
val res2: Array[org.apache.spark.sql.Row] = Array([mstephenson@fernandez.com,Violet,34.49726772511229,12.65565114916675,39.57766801952616,4.0826206329529615,587.9510539684005])

//////////////////////////////////////////////////////
//// Configure el DataFrame para Machine Learning ////
//////////////////////////////////////////////////////

scala> import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.feature.VectorAssembler

scala> import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.linalg.Vectors

scala> val df = data.select(data("Yearly Amount Spent").as("label"), 
     |                      $"Avg Session Length", $"Time on App", $"Time on Website", $"Length of Membership")
val df: org.apache.spark.sql.DataFrame = [label: double, Avg Session Length: double ... 3 more fields]

scala> val assembler = new VectorAssembler().setInputCols(Array("Avg Session Length", "Time on App", 
     |                                                          "Time on Website", "Length of Membership"))
val assembler: org.apache.spark.ml.feature.VectorAssembler = VectorAssembler: uid=vecAssembler_d0a6e9c5575c, handleInvalid=error, numInputCols=4

scala>                                     .setOutputCol("features")
val res3: assembler.type = VectorAssembler: uid=vecAssembler_d0a6e9c5575c, handleInvalid=error, numInputCols=4

scala> val output = assembler.transform(df).select($"label", $"features")
val output: org.apache.spark.sql.DataFrame = [label: double, features: vector]

scala> val lr = new LinearRegression()
val lr: org.apache.spark.ml.regression.LinearRegression = linReg_1c2b9fabcaa3

scala> val lrModelo = lr.fit(output)
25/04/02 17:04:05 WARN Instrumentation: [cbbe0f03] regParam is zero, which might cause numerical instability and overfitting.
25/04/02 17:04:06 WARN InstanceBuilder: Failed to load implementation from:dev.ludovic.netlib.lapack.JNILAPACK
val lrModelo: org.apache.spark.ml.regression.LinearRegressionModel = LinearRegressionModel: uid=linReg_1c2b9fabcaa3, numFeatures=4

scala> println(s"Coefficients: ${lrModelo.coefficients} Intercept: ${lrModelo.intercept}")
Coefficients: [25.734271084670716,38.709153810828816,0.43673883558514964,61.57732375487594] Intercept: -1051.5942552990748

scala> val trainingSummary = lrModelo.summary
val trainingSummary: org.apache.spark.ml.regression.LinearRegressionTrainingSummary = org.apache.spark.ml.regression.LinearRegressionTrainingSummary@1236d6f8

scala> println(s"RMSE: ${trainingSummary.rootMeanSquaredError}")
RMSE: 9.923256785022229

scala> println(s"MSE: ${trainingSummary.meanSquaredError}")
MSE: 98.47102522148971

scala> println(s"R2: ${trainingSummary.r2}")
R2: 0.9843155370226727
```