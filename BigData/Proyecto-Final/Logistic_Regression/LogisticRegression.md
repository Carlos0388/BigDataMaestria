# Ejercicio del Algoritmo Logistic Regression

```scala
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{StringIndexer, OneHotEncoder, VectorAssembler}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.evaluation.{BinaryClassificationEvaluator, MulticlassClassificationEvaluator}
import org.apache.spark.ml.Pipeline

object LogisticRegressionExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Logistic Regression Example").master("local[*]").getOrCreate()
```
Terminal:
```
scala> val spark = SparkSession.builder().appName("Logistic Regression Example").master("local[*]").getOrCreate()
25/05/19 00:59:08 WARN SparkSession: Using an existing Spark session; only runtime SQL configurations will take effect.
val spark: org.apache.spark.sql.SparkSession = org.apache.spark.sql.SparkSession@13d17025
```
En este punto, hemos creado una sesión de Spark y la hemos configurado para ejecutarse
localmente con un número de hilos igual al número de núcleos del sistema. La
sesión se puede utilizar para crear DataFrames y DataSets, que son los objetos principales
para trabajar con datos en Spark. En el siguiente paso, cargaremos un conjunto de datos
que se utilizará para entrenar el modelo de regresión logística.

```scala
    // Load the dataset
    val data = spark.read.option("header", "true").option("inferSchema", "true").option("delimiter", ";").csv("bank-full.csv")
```
Terminal:
```
scala> val data = spark.read.option("header", "true").option("inferSchema", "true").option("delimiter", ";").csv("bank-full.csv")
val data: org.apache.spark.sql.DataFrame = [age: int, job: string ... 15 more fields]
```
En este punto, hemos cargado un conjunto de datos desde un archivo CSV llamado
"bank-full.csv". El conjunto de datos contiene 41 columnas y 45211 filas
El conjunto de datos se puede visualizar mediante el método `show()` del DataFrame.

```scala
    // Print schema and sample data
    data.printSchema()
    data.show(5)
```
Terminal:
```
scala> data.printSchema()
root
 |-- age: integer (nullable = true)
 |-- job: string (nullable = true)
 |-- marital: string (nullable = true)
 |-- education: string (nullable = true)
 |-- default: string (nullable = true)
 |-- balance: integer (nullable = true)
 |-- housing: string (nullable = true)
 |-- loan: string (nullable = true)
 |-- contact: string (nullable = true)
 |-- day: integer (nullable = true)
 |-- month: string (nullable = true)
 |-- duration: integer (nullable = true)
 |-- campaign: integer (nullable = true)
 |-- pdays: integer (nullable = true)
 |-- previous: integer (nullable = true)
 |-- poutcome: string (nullable = true)
 |-- y: string (nullable = true)


scala>     data.show(5)
+---+------------+-------+---------+-------+-------+-------+----+-------+---+-----+--------+--------+-----+--------+--------+---+
|age|         job|marital|education|default|balance|housing|loan|contact|day|month|duration|campaign|pdays|previous|poutcome|  y|        
+---+------------+-------+---------+-------+-------+-------+----+-------+---+-----+--------+--------+-----+--------+--------+---+        
| 58|  management|married| tertiary|     no|   2143|    yes|  no|unknown|  5|  may|     261|       1|   -1|       0| unknown| no|        
| 44|  technician| single|secondary|     no|     29|    yes|  no|unknown|  5|  may|     151|       1|   -1|       0| unknown| no|        
| 33|entrepreneur|married|secondary|     no|      2|    yes| yes|unknown|  5|  may|      76|       1|   -1|       0| unknown| no|        
| 47| blue-collar|married|  unknown|     no|   1506|    yes|  no|unknown|  5|  may|      92|       1|   -1|       0| unknown| no|        
| 33|     unknown| single|  unknown|     no|      1|     no|  no|unknown|  5|  may|     198|       1|   -1|       0| unknown| no|        
+---+------------+-------+---------+-------+-------+-------+----+-------+---+-----+--------+--------+-----+--------+--------+---+        
only showing top 5 rows
```
```scala
    // Limpiamos los datos nulos
    val dataClean = data.na.drop()
```
Terminal:
```
scala> val dataClean = data.na.drop()
val dataClean: org.apache.spark.sql.DataFrame = [age: int, job: string ... 15 more fields]

```
En este punto, hemos eliminado las filas con valores nulos del conjunto de datos.


```scala
    // Indexar la columna objetivo "y"
    val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("label")
```
```Terminal
scala> val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("label")
val labelIndexer: org.apache.spark.ml.feature.StringIndexer = strIdx_da48eadb6404
```
En este punto, hemos creado un índice de etiquetas para la columna objetivo "y".

```scala
    // List of categorical columns to index and encode
    val categoricalCols = Array("job", "marital", "education", "default", "housing", "loan", "contact", "month", "poutcome")
```
Terminal:
```
scala> val categoricalCols = Array("job", "marital", "education", "default", "housing", "loan", "contact", "month", "poutcome")
val categoricalCols: Array[String] = Array(job, marital, education, default, housing, loan, contact, month, poutcome)
```
En este punto, hemos creado una lista de columnas categóricas que necesitamos
indexar y codificar.

```scala

    // Index categorical columns
    val indexers = categoricalCols.map { colName =>
      new StringIndexer().setInputCol(colName).setOutputCol(colName + "Index").fit(data)
    }
```
Terminal:
```
val indexers: Array[org.apache.spark.ml.feature.StringIndexerModel] = Array(StringIndexerModel: uid=strIdx_4639e2aed14f, handleInvalid=error, StringIndexerModel: uid=strIdx_c01bfd29eed1, handleInvalid=error, StringIndexerModel: uid=strIdx_d6adf83b4b2f, handleInvalid=error, StringIndexerModel: uid=strIdx_207f5784c091, handleInvalid=error, StringIndexerModel: uid=strIdx_51fbe94b1c83, handleInvalid=error, StringIndexerModel: uid=strIdx_8f06c713b43e, handleInvalid=error, StringIndexerModel: uid=strIdx_e49460be96ea, handleInvalid=error, StringIndexerModel: uid=strIdx_03c788eea407, handleInvalid=error, StringIndexerModel: uid=strIdx_25fd0b002295, handleInvalid=error)
```
En este punto, hemos creado un índice para cada columna categórica en la lista

```scala
    // OneHotEncode indexed categorical columns
    val encoders = categoricalCols.map { colName =>
      new OneHotEncoder().setInputCol(colName + "Index").setOutputCol(colName + "Vec")
    }
```
Terminal:
```
val encoders: Array[org.apache.spark.ml.feature.OneHotEncoder] = Array(oneHotEncoder_9b4d1b384b3b, oneHotEncoder_f85c37961dc2, oneHotEncoder_6e643467136c, oneHotEncoder_ec8025e393a2, oneHotEncoder_41ab3b181f2f, oneHotEncoder_af67eb3eed56, oneHotEncoder_88249872733b, oneHotEncoder_f8053e332617, oneHotEncoder_6a15dd38572c)
```
En este punto, hemos creado un codificador para cada columna categórica en la lista

```scala
    // Numeric columns to be used as features
    val numericCols = Array("age", "duration", "campaign", "pdays", "previous")
```
Terminal:
```
scala> val numericCols = Array("age", "duration", "campaign", "pdays", "previous")
val numericCols: Array[String] = Array(age, duration, campaign, pdays, previous)
```
En este punto, hemos creado una lista de columnas numéricas que se utilizarán como características

```scala
    // Assemble all features into a single vector column
    val assembler = new VectorAssembler().setInputCols(encoders.map(_.getOutputCol) ++ numericCols).setOutputCol("features")
```
Terminal:
```
scala> val assembler = new VectorAssembler().setInputCols(encoders.map(_.getOutputCol) ++ numericCols).setOutputCol("features")
val assembler: org.apache.spark.ml.feature.VectorAssembler = VectorAssembler: uid=vecAssembler_0cbd5bfbab6e, handleInvalid=error, numInputCols=14
```
En este punto, hemos creado un ensamblador que se encargará de combinar todas
las características en una sola columna vectorial

```scala
    // Create logistic regression model
    val lr = new LogisticRegression().setFeaturesCol("features").setLabelCol("label").setMaxIter(100).setRegParam(0.01)
```
Terminal:
```
scala> val lr = new LogisticRegression().setFeaturesCol("features").setLabelCol("label").setMaxIter(100).setRegParam(0.01)
val lr: org.apache.spark.ml.classification.LogisticRegression = logreg_8c15a2f984e0
```
En este punto, hemos creado un modelo de regresión logística que utilizará las características vector

```scala
    // Create pipeline stages
    val stages = Array(labelIndexer) ++ indexers ++ encoders ++ Array(assembler, lr)

    val pipeline = new Pipeline().setStages(stages)
```
Terminal:
```
scala> val stages = Array(labelIndexer) ++ indexers ++ encoders ++ Array(assembler, lr)
val stages: Array[org.apache.spark.ml.PipelineStage with org.apache.spark.ml.util.MLWritable{def copy(extra: org.apache.spark.ml.param.ParamMap): org.apache.spark.ml.PipelineStage with org.apache.spark.ml.util.MLWritable{def copy(extra: org.apache.spark.ml.param.ParamMap): org.apache.spark.ml.PipelineStage with org.apache.spark.ml.util.MLWritable}}] = Array(StringIndexerModel: uid=strIdx_4639e2aed14f, handleInvalid=error, StringIndexerModel: uid=strIdx_c01bfd29eed1, handleInvalid=error, StringIndexerModel: uid=strIdx_d6adf83b4b2f, handleInvalid=error, StringIndexerModel: uid=strIdx_207f5784c091, handleInvalid=error, StringIndexerModel: uid=strIdx_51fbe94b1c83, handleInvalid=error, StringIndexerModel: uid=strIdx_8f06c713b43e, handleInvalid=error, StringIndexerModel: uid=strIdx_e49460be96...

scala> val pipeline = new Pipeline().setStages(stages)
val pipeline: org.apache.spark.ml.Pipeline = pipeline_56f96d279628
```
En este punto, hemos creado una secuencia de etapas para nuestro pipeline, que incluye los
codificadores, el ensamblador y el modelo de regresión logística

```scala
    // Split data into training and test sets
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3), seed = 1234L)
```
Terminal:
```
val trainingData: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [age: int, job: string ... 15 more fields]
val testData: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [age: int, job: string ... 15 more fields]
```
En este punto, hemos dividido nuestros datos en conjuntos de entrenamiento y prueba

```scala
    // Fit the model
    val model = pipeline.fit(trainingData)
```
Terminal:
```
scala> val model = pipeline.fit(trainingData)
25/05/19 09:43:33 WARN InstanceBuilder: Failed to load implementation from:dev.ludovic.netlib.blas.JNIBLAS
val model: org.apache.spark.ml.PipelineModel = pipeline_5b3578149e99
```
En este punto, hemos entrenado nuestro modelo en el conjunto de entrenamiento

```scala
    // Make predictions
    val predictions = model.transform(testData)
```
Terminal:
```
val predictions: org.apache.spark.sql.DataFrame = [age: int, job: string ... 38 more fields]
```
En este punto, hemos hecho predicciones en el conjunto de prueba

```scala
    // Show sample predictions
    predictions.select("label", "features", "prediction", "probability").show(10)
```
Terminal:
```
scala> predictions.select("label", "features", "prediction", "probability").show(10)
25/05/19 09:44:33 WARN package: Truncated the string representation of a plan since it was too large. This behavior can be adjusted by setting 'spark.sql.debug.maxToStringFields'. 
+-----+--------------------+----------+--------------------+
|label|            features|prediction|         probability|
+-----+--------------------+----------+--------------------+
|  1.0|(40,[10,12,15,16,...|       0.0|[0.82984668051595...|
|  0.0|(40,[10,12,15,16,...|       0.0|[0.93178031217390...|
|  0.0|(40,[10,12,15,16,...|       0.0|[0.84027168911057...|
|  0.0|(40,[0,11,15,16,1...|       0.0|[0.99261439946493...|
|  1.0|(40,[0,12,13,16,1...|       0.0|[0.78042123168036...|
|  0.0|(40,[4,12,13,16,1...|       0.0|[0.98096110792490...|
|  0.0|(40,[10,11,16,18,...|       0.0|[0.90436074676081...|
|  1.0|(40,[10,11,16,18,...|       0.0|[0.73994326594159...|
|  0.0|(40,[10,12,13,16,...|       0.0|[0.96602855658301...|
|  0.0|(40,[10,12,13,16,...|       0.0|[0.92959894389442...|
+-----+--------------------+----------+--------------------+
only showing top 10 rows
```
En este punto, hemos hecho predicciones en el conjunto de prueba y mostramos algunas de ellas

```scala
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
```
terminal:
```
scala> val evaluator = new BinaryClassificationEvaluator().setLabelCol("label").setRawPredictionCol("rawPrediction").setMetricName("areaUnderROC")
val evaluator: org.apache.spark.ml.evaluation.BinaryClassificationEvaluator = BinaryClassificationEvaluator: uid=binEval_17e2a3ad7b8a, metricName=areaUnderROC, numBins=1000   

scala> val auc = evaluator.evaluate(predictions)
val auc: Double = 0.9092475315568294

scala>     println(s"Test Area Under ROC: $auc")
Test Area Under ROC: 0.9092475315568294

scala> val accuracyEvaluator = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("accuracy")
val accuracyEvaluator: org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator = MulticlassClassificationEvaluator: uid=mcEval_4d4ce64115aa, metricName=accuracy, metricLabel=0.0, beta=1.0, eps=1.0E-15

scala> val accuracy = accuracyEvaluator.evaluate(predictions)
val accuracy: Double = 0.901270122581598

scala>     println(s"Test Accuracy: $accuracy")
Test Accuracy: 0.901270122581598

```
En este punto, hemos evaluado el modelo y mostramos el área bajo la curva ROC y
La precisión del modelo. Finalmente, hemos detenido el contexto de Spark.

```scala
  def runMultipleEvaluations(numRuns: Int): Unit = {
    val aucResults = scala.collection.mutable.ArrayBuffer[Double]()
    val accuracyResults = scala.collection.mutable.ArrayBuffer[Double]()

    for (i <- 1 to numRuns) {
      val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3), seed = System.currentTimeMillis() + i)
      val model = pipeline.fit(trainingData)
      val predictions = model.transform(testData)

      val evaluator = new BinaryClassificationEvaluator()
        .setLabelCol("label")
        .setRawPredictionCol("rawPrediction")
        .setMetricName("areaUnderROC")
      val auc = evaluator.evaluate(predictions)

      val accuracyEvaluator = new MulticlassClassificationEvaluator()
        .setLabelCol("label")
        .setPredictionCol("prediction")
        .setMetricName("accuracy")
      val accuracy = accuracyEvaluator.evaluate(predictions)

      aucResults += auc
      accuracyResults += accuracy

      println(s"Run $i: AUC = $auc, Accuracy = $accuracy")
    }

    println(s"\nAUC promedio: ${aucResults.sum / aucResults.length}")
    println(s"Accuracy promedio: ${accuracyResults.sum / accuracyResults.length}")
  }

  // Llama a la función en tu main:
  runMultipleEvaluations(30)
```
En este ejemplo, hemos creado una función `runMultipleEvaluations` que ejecuta el modelo 30 veces con diferentes conjuntos de datos de entrenamiento y prueba. Después de cada ejecución, calculamos el área bajo la curva ROC y la precisión del modelo y los almacenamos en arrays. Finalmente, imprimimos el área bajo la curva ROC y la precisión promedio de los 30 ejecuciones. Esto nos da una idea de la estabilidad y consistencia del modelo.



