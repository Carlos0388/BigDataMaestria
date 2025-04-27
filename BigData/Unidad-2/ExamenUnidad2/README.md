## Practica Evaluatoria Unidad 2

Instrucciones: Desarrollar las siguientes instrucciones en Spark con el lenguaje de programación Scala, utilizando solo la documentación de la librería de Machine Learning Mllib de Spark y Google. 

1. Cargar en un dataframe de la fuente de datos Iris.csv que se encuentra enhttps://github.com/jcromerohdz/iris, elaborar la limpieza de datos necesaria paraser procesado por el siguiente algoritmo (Importante, esta limpieza debe ser pormedio de un script de Scala en Spark).<br>
a. Utilice el algoritmo de Machine Learning Multilayer Perceptron Classifier de la librería Mllib de Spark

```scala
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

val spark = SparkSession.builder.appName("MultilayerPerceptronClassifierIris").getOrCreate()

import spark.implicits._
val rawDF = spark.read.option("header", "true").option("inferSchema", "true").csv("iris.csv")

// Limpieza de datos: eliminar filas con valores nulos (si los hay)
val df = rawDF.na.drop().dropDuplicates()
```
Terminal
```
scala> import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SparkSession

scala> import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions._

scala> import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler}

scala> import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier

scala> import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

scala> val spark = SparkSession.builder.appName("MultilayerPerceptronClassifierIris").getOrCreate()
warning: 1 deprecation (since 2.13.3); for details, enable `:setting -deprecation` or `:replay -deprecation`
25/04/27 15:29:59 WARN SparkSession: Using an existing Spark session; only runtime SQL configurations will take effect.
val spark: org.apache.spark.sql.SparkSession = org.apache.spark.sql.SparkSession@43fd117c

scala> import spark.implicits._
import spark.implicits._

scala> val rawDF = spark.read.option("header", "true").option("inferSchema", "true").csv("iris.csv")
val rawDF: org.apache.spark.sql.DataFrame = [sepal_length: double, sepal_width: double ... 3 more fields]

scala> val df = rawDF.na.drop().dropDuplicates()
val df: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [sepal_length: double, sepal_width: double ... 3 more fields]
```

2. ¿Cuáles son los nombres de las columnas?
```scala
println("Column names:")
df.columns.foreach(println)
```
Terminal
```
scala> println("Column names:")
Column names:

scala> df.columns.foreach(println)
sepal_length
sepal_width
petal_length
petal_width
species

```
3.¿Cómo es el esquema?
```scala
println("Schema:")
df.printSchema()
```
Terminal
```
scala>  println("Schema:")
Schema:

scala>     df.printSchema()
root
 |-- sepal_length: double (nullable = true)
 |-- sepal_width: double (nullable = true)
 |-- petal_length: double (nullable = true)
 |-- petal_width: double (nullable = true)
 |-- species: string (nullable = true)
```
4. Imprime las primeras 5 columnas.
```scala
println("First 5 rows:")
df.show(5)
```
Terminal
```
scala>  println("First 5 rows:")
First 5 rows:

scala>     df.show(5)
+------------+-----------+------------+-----------+-------+
|sepal_length|sepal_width|petal_length|petal_width|species|
+------------+-----------+------------+-----------+-------+
|         5.1|        3.5|         1.4|        0.3| setosa|
|         5.0|        3.4|         1.6|        0.4| setosa|
|         4.4|        3.2|         1.3|        0.2| setosa|
|         4.8|        3.4|         1.6|        0.2| setosa|
|         5.0|        3.3|         1.4|        0.2| setosa|
+------------+-----------+------------+-----------+-------+
only showing top 5 rows
```
5. Usa el método describe () para aprender más sobre los datos del DataFrame.
```scala
println("Describe summary:")
df.describe().show()
```
Terminal
```
scala> println("Describe summary:")
Describe summary:

scala>     df.describe().show()
25/04/27 15:40:21 WARN package: Truncated the string representation of a plan since it was too large. This behavior can be adjusted by setting 'spark.sql.debug.maxToStringFields'.
+-------+------------------+-------------------+------------------+------------------+---------+
|summary|      sepal_length|        sepal_width|      petal_length|       petal_width|  species|
+-------+------------------+-------------------+------------------+------------------+---------+
|  count|               147|                147|               147|               147|      147|
|   mean|5.8564625850340155|   3.05578231292517| 3.780272108843537|1.2088435374149662|     null|
| stddev|0.8290998607345103|0.43700870680343545|1.7591108999509795|0.7578742052400403|     null|
|    min|               4.3|                2.0|               1.0|               0.1|   setosa|
|    max|               7.9|                4.4|               6.9|               2.5|virginica|
+-------+------------------+-------------------+------------------+------------------+---------+

```
6. Haga la transformación pertinente para los datos categóricos los cuales serán nuestras etiquetas a clasificar.
```scala
val labelIndexer = new StringIndexer().setInputCol("species").setOutputCol("label").fit(df)
val indexedDF = labelIndexer.transform(df)

// Reúne las características en una columna vectorial "características"
val featureCols = Array("sepal_length", "sepal_width", "petal_length", "petal_width")
val assembler = new VectorAssembler().setInputCols(featureCols).setOutputCol("features")
val finalDF = assembler.transform(indexedDF).select("features", "label")

// Dividir los datos en conjuntos de entrenamiento y prueba
val Array(trainingData, testData) = finalDF.randomSplit(Array(0.7, 0.3), seed = 1234L)
```
Terminal
```
scala> val labelIndexer = new StringIndexer().setInputCol("species").setOutputCol("label").fit(df)
val labelIndexer: org.apache.spark.ml.feature.StringIndexerModel = StringIndexerModel: uid=strIdx_b0fc80595824, handleInvalid=error

scala> val indexedDF = labelIndexer.transform(df)
val indexedDF: org.apache.spark.sql.DataFrame = [sepal_length: double, sepal_width: double ... 4 more fields]

scala> val featureCols = Array("sepal_length", "sepal_width", "petal_length", "petal_width")
val featureCols: Array[String] = Array(sepal_length, sepal_width, petal_length, petal_width)

scala> val assembler = new VectorAssembler().setInputCols(featureCols).setOutputCol("features")
val assembler: org.apache.spark.ml.feature.VectorAssembler = VectorAssembler: uid=vecAssembler_863498b7b1bf, handleInvalid=error, numInputCols=4

scala> val finalDF = assembler.transform(indexedDF).select("features", "label")
val finalDF: org.apache.spark.sql.DataFrame = [features: vector, label: double]

scala> val Array(trainingData, testData) = finalDF.randomSplit(Array(0.7, 0.3), seed = 1234L)
val trainingData: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [features: vector, label: double]
val testData: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [features: vector, label: double]

```
7. Construya el modelo de clasificación y explique su arquitectura.
```scala
// Arquitectura: tamaño de la capa de entrada = 4 (características), dos capas ocultas con 5 y 4 nodos, tamaño de la capa de salida = 3 (clases)
val layers = Array[Int](4, 5, 4, 3)

val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100).setFeaturesCol("features").setLabelCol("label")

val model = trainer.fit(trainingData)
```
Terminal
```
scala> val layers = Array[Int](4, 5, 4, 3)
val layers: Array[Int] = Array(4, 5, 4, 3)

scala> val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100).setFeaturesC
ol("features").setLabelCol("label")
val trainer: org.apache.spark.ml.classification.MultilayerPerceptronClassifier = mlpc_9fc5112346ae

scala> val model = trainer.fit(trainingData)
val model: org.apache.spark.ml.classification.MultilayerPerceptronClassificationModel = MultilayerPerceptronClassificationModel: uid=mlpc_9fc5112346ae, numLayers=4, numClasses=3, numFeatures=4

```
8. Imprima los resultados del modelo y de sus observaciones.
```scala
val result = model.transform(testData)
val predictionAndLabels = result.select("prediction", "label")
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
val accuracy = evaluator.evaluate(predictionAndLabels)
println(s"Test set accuracy = $accuracy")
println("Sample predictions:")
result.select("features", "label", "prediction").show()

// Cerrar la session de Spark
spark.stop()
```
Terminal
```
scala> val result = model.transform(testData)
val result: org.apache.spark.sql.DataFrame = [features: vector, label: double ... 3 more fields]

scala> val predictionAndLabels = result.select("prediction", "label")
val predictionAndLabels: org.apache.spark.sql.DataFrame = [prediction: double, label: double]

scala> val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")
val evaluator: org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator = MulticlassClassificationEvaluator: uid=mcEval_feb10d60831e, metricName=accuracy, metricLabel=0.0, beta=1.0, eps=1.0E-15

scala> val accuracy = evaluator.evaluate(predictionAndLabels)
val accuracy: Double = 0.96

scala> println(s"Test set accuracy = $accuracy")
Test set accuracy = 0.96

scala> println("Sample predictions:")
Sample predictions:

scala> result.select("features", "label", "prediction").show()
+-----------------+-----+----------+
|         features|label|prediction|
+-----------------+-----+----------+
|[4.3,3.0,1.1,0.1]|  2.0|       2.0|
|[4.4,2.9,1.4,0.2]|  2.0|       2.0|
|[4.5,2.3,1.3,0.3]|  2.0|       2.0|
|[5.0,2.3,3.3,1.0]|  0.0|       0.0|
|[5.0,3.3,1.4,0.2]|  2.0|       2.0|
|[5.0,3.4,1.5,0.2]|  2.0|       2.0|
|[5.0,3.4,1.6,0.4]|  2.0|       2.0|
|[5.0,3.5,1.3,0.3]|  2.0|       2.0|
|[5.0,3.6,1.4,0.2]|  2.0|       2.0|
|[5.1,3.3,1.7,0.5]|  2.0|       2.0|
|[5.1,3.4,1.5,0.2]|  2.0|       2.0|
|[5.1,3.8,1.5,0.3]|  2.0|       2.0|
|[5.4,3.4,1.5,0.4]|  2.0|       2.0|
|[5.4,3.7,1.5,0.2]|  2.0|       2.0|
|[5.5,2.4,3.8,1.1]|  0.0|       0.0|
|[5.5,4.2,1.4,0.2]|  2.0|       2.0|
|[5.6,2.5,3.9,1.1]|  0.0|       0.0|
|[5.6,2.7,4.2,1.3]|  0.0|       0.0|
|[5.7,2.5,5.0,2.0]|  1.0|       1.0|
|[5.7,2.6,3.5,1.0]|  0.0|       0.0|
+-----------------+-----+----------+
only showing top 20 rows

```