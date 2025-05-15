```scala
//////////////////////////////////////////////
// Proyecto de regresion logistica //////////////
////////////////////////////////////////////

//  In this project we will be working with a fake advertising data set, indicating whether or not a particular internet user clicked on an Advertisement. We will try to create a model that will predict whether or not they will click on an ad based off the features of that user.
//  This data set contains the following features:
//    'Daily Time Spent on Site': consumer time on site in minutes
//    'Age': cutomer age in years
//    'Area Income': Avg. Income of geographical area of consumer
//    'Daily Internet Usage': Avg. minutes a day consumer is on the internet
//    'Ad Topic Line': Headline of the advertisement
//    'City': City of consumer
//    'Male': Whether or not consumer was male
//    'Country': Country of consumer
//    'Timestamp': Time at which consumer clicked on Ad or closed window
//    'Clicked on Ad': 0 or 1 indicated clicking on Ad

//////////////////////////////////////////////////////////
// Complete las siguientes tareas que estan comentas ////
/////////////////////////////////////////////////////////



////////////////////////
/// Tome los datos //////
//////////////////////

// Importe una  SparkSession con la libreria Logistic Regression
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.sql.SparkSession
import org.apache.log4j._

// Optional: Utilizar el codigo de  Error reporting
Logger.getLogger("org").setLevel(Level.ERROR)

// Cree un sesion Spark 
val spark = SparkSession.builder().getOrCreate()

Terminal:
val spark: org.apache.spark.sql.SparkSession = org.apache.spark.sql.SparkSession@59edc76c

// Utilice Spark para leer el archivo csv Advertising.
val data  = spark.read.option("header","true").option("inferSchema", "true").format("csv").load("advertising.csv")

Terminal:
val data: org.apache.spark.sql.DataFrame = [Daily Time Spent on Site: double, Age: int ... 8 more fields]

// Imprima el Schema del DataFrame
data.printSchema()

Terminal:
scala> data.printSchema()
root
 |-- Daily Time Spent on Site: double (nullable = true)
 |-- Age: integer (nullable = true)
 |-- Area Income: double (nullable = true)
 |-- Daily Internet Usage: double (nullable = true)
 |-- Ad Topic Line: string (nullable = true)
 |-- City: string (nullable = true)
 |-- Male: integer (nullable = true)
 |-- Country: string (nullable = true)
 |-- Timestamp: timestamp (nullable = true)
 |-- Clicked on Ad: integer (nullable = true)

///////////////////////
/// Despliegue los datos /////
/////////////////////

// Imprima un renglon de ejemplo 

data.head(1)

Terminal: val res2: Array[org.apache.spark.sql.Row] = Array([68.95,35,61833.9,256.09,Cloned 5thgeneration orchestration,Wrightburgh,0,Tunisia,2016-03-27 00:53:11.0,0])

val colnames = data.columns
Terminal: val colnames: Array[String] = Array(Daily Time Spent on Site, Age, Area Income, Daily Internet Usage, Ad Topic Line, City, Male, Country, Timestamp, Clicked on Ad)

val firstrow = data.head(1)(0)
Terminal: val firstrow: org.apache.spark.sql.Row = [68.95,35,61833.9,256.09,Cloned 5thgeneration orchestration,Wrightburgh,0,Tunisia,2016-03-27 00:53:11.0,0]

println("\n")
println("Example data row")
for(ind <- Range(1, colnames.length)){
    println(colnames(ind))
    println(firstrow(ind))
    println("\n")
}

Terminal:
Age
35

Area Income
61833.9

Daily Internet Usage
256.09

Ad Topic Line
Cloned 5thgeneration orchestration

City
Wrightburgh

Male
0

Country
Tunisia

Timestamp
2016-03-27 00:53:11.0

Clicked on Ad
0

////////////////////////////////////////////////////
//// Preparar el DataFrame para Machine Learning ////
//////////////////////////////////////////////////

//   Hacer lo siguiente:
//    - Renombre la columna "Clicked on Ad" a "label"
//    - Tome la siguientes columnas como features "Daily Time Spent on Site","Age","Area Income","Daily Internet Usage","Timestamp","Male"
//    - Cree una nueva clolumna llamada "Hour" del Timestamp conteniendo la  "Hour of the click"

val timedata = data.withColumn("Hour",hour(data("Timestamp")))
Terminal: val timedata: org.apache.spark.sql.DataFrame = [Daily Time Spent on Site: double, Age: int ... 9 more fields]

val logregdata = timedata.select(data("Clicked on Ad").as("label"), $"Daily Time Spent on Site", $"Age", $"Area Income", $"Daily Internet Usage", $"Hour", $"Male")
Terminal: val logregdata: org.apache.spark.sql.DataFrame = [label: int, Daily Time Spent on Site: double ... 5 more fields]

// Importe VectorAssembler y Vectors
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

// Cree un nuevo objecto VectorAssembler llamado assembler para los feature
val assembler = (new VectorAssembler()
                  .setInputCols(Array("Daily Time Spent on Site", "Age","Area Income","Daily Internet Usage","Hour","Male"))
                  .setOutputCol("features"))

Terminal: val assembler: org.apache.spark.ml.feature.VectorAssembler = VectorAssembler: uid=vecAssembler_fd3b6b4aadea, handleInvalid=error, numInputCols=6

// Utilice randomSplit para crear datos de train y test divididos en 70/30
val Array(training, test) = logregdata.randomSplit(Array(0.7, 0.3), seed = 12345)

Terminal:
val training: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [label: int, Daily Time Spent on Site: double ... 5 more fields]
val test: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [label: int, Daily Time Spent on Site: double ... 5 more fields]


///////////////////////////////
// Configure un Pipeline ///////
/////////////////////////////

// Importe  Pipeline
import org.apache.spark.ml.Pipeline

// Cree un nuevo objeto de  LogisticRegression llamado lr
val lr = new LogisticRegression()

Terminal:
val lr: org.apache.spark.ml.classification.LogisticRegression = logreg_f68a97a3037e

// Cree un nuevo  pipeline con los elementos: assembler, lr
val pipeline = new Pipeline().setStages(Array(assembler, lr))

Terminal:
val pipeline: org.apache.spark.ml.Pipeline = pipeline_c366832563f5

// Ajuste (fit) el pipeline para el conjunto de training.
val model = pipeline.fit(training)

Terminal:
25/04/23 19:00:16 WARN InstanceBuilder: Failed to load implementation from:dev.ludovic.netlib.blas.JNIBLAS
val model: org.apache.spark.ml.PipelineModel = pipeline_c366832563f5

// Tome los Resultados en el conjuto Test con transform
val results = model.transform(test)

Terminal:
val results: org.apache.spark.sql.DataFrame = [label: int, Daily Time Spent on Site: double ... 9 more fields]


////////////////////////////////////
//// Evaluacion del modelo /////////////
//////////////////////////////////

// Para Metrics y Evaluation importe MulticlassMetrics
import org.apache.spark.mllib.evaluation.MulticlassMetrics

// Convierta los resutalos de prueba (test) en RDD utilizando .as y .rdd
val predictionAndLabels = results.select($"prediction",$"label").as[(Double, Double)].rdd

Terminal: 
val predictionAndLabels: org.apache.spark.rdd.RDD[(Double, Double)] = MapPartitionsRDD[68] at rdd at <console>:1


// Inicialice un objeto MulticlassMetrics 
val metrics = new MulticlassMetrics(predictionAndLabels)

Terminal:
val metrics: org.apache.spark.mllib.evaluation.MulticlassMetrics = org.apache.spark.mllib.evaluation.MulticlassMetrics@5adfe27c


// Imprima la  Confusion matrix
println("Confusion matrix:")
println(metrics.confusionMatrix)

Terminal:
Confusion matrix:

136.0  1.0    
4.0    146.0

metrics.accuracy
Terminal: val res8: Double = 0.9825783972125436
```