# Ejercicio del Algoritmo Multilayer Perceptron

```scala
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.{StringIndexer, OneHotEncoder, VectorAssembler}
import org.apache.spark.ml.classification.MultilayerPerceptronClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.Pipeline

object MultilayerPerceptronExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Multilayer Perceptron Example").master("local[*]").getOrCreate()
```
Terminal:
```
scala> val spark = SparkSession.builder().appName("Multilayer Perceptron Example").master("local[*]").getOrCreate()
25/05/19 11:02:23 WARN SparkSession: Using an existing Spark session; only runtime SQL configurations will take effect.
val spark: org.apache.spark.sql.SparkSession = org.apache.spark.sql.SparkSession@16494888 

```
En este código, se importan las bibliotecas necesarias para crear un entorno de Spark
Se crea una sesión de Spark con el nombre "Multilayer Perceptron Example" y s
e establece el modo de ejecución en local con un número de hilos igual a la cantidad
de núcleos del procesador.

```scala
    // Load the dataset
    val data = spark.read.option("header", "true").option("inferSchema", "true").option("delimiter", ";").csv("bank-full.csv")
```
Terminal:
```
scala> val data = spark.read.option("header", "true").option("inferSchema", "true").option("delimiter", ";").csv("bank-full.csv")
val data: org.apache.spark.sql.DataFrame = [age: int, job: string ... 15 more fields]
```
En este código, se carga el conjunto de datos desde un archivo CSV llamado "bank-full.csv
Se especifican las opciones para leer el archivo CSV, incluyendo la presencia de una línea
de encabezado, la inferencia del esquema y el delimitador de campos.

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
En este ejemplo, se muestra cómo limpiar los datos nulos utilizando el método `drop()`
del DataFrame. El método `drop()` elimina las filas con valores nulos en las column
as especificadas. En este caso, no se especifican columnas, por lo que se elimin
an las filas con valores nulos en cualquier columna.

```scala
    // Indexar la columna objetivo "y" (NO uses .fit aquí)
    val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("label")
```
Terminal:
```
scala> val labelIndexer = new StringIndexer().setInputCol("y").setOutputCol("label")
val labelIndexer: org.apache.spark.ml.feature.StringIndexer = strIdx_81d0c403fafe
```
En este ejemplo, se crea un índice de etiquetas para la columna "y" utilizando
el método `fit()` de la clase `StringIndexer`. El método `fit()` crea un
índice de etiquetas para la columna especificada y lo asigna a la variable `label

```scala
    // List of categorical columns to index and encode
    val categoricalCols = Array("job", "marital", "education", "default", "housing", "loan", "contact", "month", "poutcome")
```
Terminal:
```
scala> val categoricalCols = Array("job", "marital", "education", "default", "housing", "loan", "contact", "month", "poutcome")
val categoricalCols: Array[String] = Array(job, marital, education, default, housing, loan, contact, month, poutcome)
```
En este ejemplo, se define un arreglo de columnas categóricas que se van a
índice y codificar utilizando el método `fit()` de la clase `StringIndexer`.

```scala
    // Index categorical columns
    val indexers = categoricalCols.map { colName =>
      new StringIndexer().setInputCol(colName).setOutputCol(colName + "Index").fit(data)
    }
```
Terminal:
```
val indexers: Array[org.apache.spark.ml.feature.StringIndexerModel] = Array(StringIndexerModel: uid=strIdx_29c941b21f33, handleInvalid=error, StringIndexerModel: uid=strIdx_23d5101113b4, handleInvalid=error, StringIndexerModel: uid=strIdx_bcc47d8bbbaa, handleInvalid=error, StringIndexerModel: uid=strIdx_be3923b9817d, handleInvalid=error, StringIndexerModel: uid=strIdx_53f5df205f8a, handleInvalid=error, StringIndexerModel: uid=strIdx_5e0a5240ce10, handleInvalid=error, StringIndexerModel: uid=strIdx_65f2111f341f, handleInvalid=error, StringIndexerModel: uid=strIdx_9b6842ba202a, handleInvalid=error, StringIndexerModel: uid=strIdx_292da2b9d679, handleInvalid=error)
```
En este ejemplo, se crea un arreglo de índices de etiquetas para las columnas
categóricas utilizando el método `fit()` de la clase `StringIndexer`. El
método `fit()` crea un índice de etiquetas para la columna especificada y
lo asigna a la variable `indexers`.

```scala
    // OneHotEncode indexed categorical columns
    val encoders = categoricalCols.map { colName =>
      new OneHotEncoder().setInputCol(colName + "Index").setOutputCol(colName + "Vec")
    }
```
Terminal:
```
val encoders: Array[org.apache.spark.ml.feature.OneHotEncoder] = Array(oneHotEncoder_052346883ec6, oneHotEncoder_ff61aa565e15, oneHotEncoder_fc52acf9d701, oneHotEncoder_680854e99fee, oneHotEncoder_9c90845e7d43, oneHotEncoder_21445d80dc69, oneHotEncoder_d3c62557e68d, oneHotEncoder_d7c068b0f47a, oneHotEncoder_3378603727af)
```
En este ejemplo, se crea un arreglo de codificadores de OneHot para las columnas
categóricas utilizando el método `fit()` de la clase `OneHotEncoder`. El
método `fit()` crea un codificador de OneHot para la columna especificada y
lo asigna a la variable `encoders`.

```scala
    // Numeric columns to be used as features
    val numericCols = Array("age", "duration", "campaign", "pdays", "previous")
```
Terminal:
```
scala> val numericCols = Array("age", "duration", "campaign", "pdays", "previous")
val numericCols: Array[String] = Array(age, duration, campaign, pdays, previous)
```
En este ejemplo, se define un arreglo de columnas numéricas que se van a utilizar como
características.

```scala
    // Assemble all features into a single vector column
    val assembler = new VectorAssembler().setInputCols(encoders.map(_.getOutputCol) ++ numericCols).setOutputCol("features")
```
Terminal:
```
scala> val assembler = new VectorAssembler().setInputCols(encoders.map(_.getOutputCol) ++ numericCols).setOutputCol("features")
val assembler: org.apache.spark.ml.feature.VectorAssembler = VectorAssembler: uid=vecAssembler_0932fe0ecba2, handleInvalid=error, numInputCols=14
```
En este ejemplo, se crea un ensamblador de vectores que combina las columnas
categóricas codificadas con OneHot y las columnas numéricas en una
columna de vector.

```scala
    // Define layers for the neural network:
    // input layer size: number of features
    // two intermediate layers with 5 and 4 nodes
    // output layer size: 2 (binary classification)
    val indexedDF = indexers.foldLeft(dataClean)((df, idx) => idx.transform(df))

    // Ajusta (fit) los encoders sobre el DataFrame indexado
    val encoderModels = encoders.map(_.fit(indexedDF))

    val encodedDF = encoderModels.foldLeft(indexedDF)((df, enc) => enc.transform(df))

    val sampleFeatures = assembler.transform(encodedDF).select("features").head().getAs[org.apache.spark.ml.linalg.Vector](0)

    val inputLayerSize = sampleFeatures.size

    val layers = Array[Int](inputLayerSize, 5, 4, 2)
```
Terminal:
```
scala> val indexedDF = indexers.foldLeft(dataClean)((df, idx) => idx.transform(df))
val indexedDF: org.apache.spark.sql.DataFrame = [age: int, job: string ... 24 more fields]

scala> val encoderModels = encoders.map(_.fit(indexedDF))
val encoderModels: Array[org.apache.spark.ml.feature.OneHotEncoderModel] = Array(OneHotEncoderModel: uid=oneHotEncoder_5805b3a6fcee, dropLast=true, handleInvalid=error, OneHotEncoderModel: uid=oneHotEncoder_98e97f437a43, dropLast=true, handleInvalid=error, OneHotEncoderModel: uid=oneHotEncoder_4ad027da7d33, dropLast=true, handleInvalid=error, OneHotEncoderModel: uid=oneHotEncoder_3b728b44f2e3, dropLast=true, handleInvalid=error, OneHotEncoderModel: uid=oneHotEncoder_3dd1b2232563, dropLast=true, handleInvalid=error, OneHotEncoderModel: uid=oneHotEncoder_8d832ed361ae, dropLast=true, handleInvalid=error, OneHotEncoderModel: uid=oneHotEncoder_e56306c925a2, dropLast=true, handleInvalid=error, OneHotEncoderModel: uid=oneHotEncoder_dec295d47189, dropLast=true, handleInvalid=error, OneHotEncode...

scala> val encodedDF = encoderModels.foldLeft(indexedDF)((df, enc) => enc.transform(df))
val encodedDF: org.apache.spark.sql.DataFrame = [age: int, job: string ... 33 more fields]

scala> val sampleFeatures = assembler.transform(encodedDF).select("features").head().getAs[org.apache.spark.ml.linalg.Vector](0)
25/05/19 12:15:23 WARN package: Truncated the string representation of a plan since it was too large. This behavior can be adjusted by setting 'spark.sql.debug.maxToStringFields'. 
val sampleFeatures: org.apache.spark.ml.linalg.Vector = (40,[1,11,14,16,17,18,20,21,32,35,36,37,38],[1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0,58.0,261.0,1.0,-1.0])

scala> val inputLayerSize = sampleFeatures.size
val inputLayerSize: Int = 40

scala> val layers = Array[Int](inputLayerSize, 5, 4, 2)
val layers: Array[Int] = Array(40, 5, 4, 2)

```
En este ejemplo, se define un ensamblador de vectores que combina las columnas
categóricas codificadas con OneHot y las columnas numéricas en una
columna de vector. Luego, se ajustan los encoders sobre el DataFrame indexado y
se transforma el DataFrame con los encoders ajustados. Finalmente, se obtiene
una muestra de características y se determina el tamaño de la capa de entrada del
neural network.

```scala
    // Create the trainer and set its parameters
    val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100).setFeaturesCol("features").setLabelCol("label")
```
Terminal:
```
scala> val trainer = new MultilayerPerceptronClassifier().setLayers(layers).setBlockSize(128).setSeed(1234L).setMaxIter(100).setFeaturesCol("features").setLabelCol("label")
val trainer: org.apache.spark.ml.classification.MultilayerPerceptronClassifier = mlpc_d981e249a542
```
En este ejemplo, se crea un entrenador de red neuronal y se establecen sus parámetros
La red neuronal se entrenará con un bloque de tamaño 128, una semilla de 123
4, un máximo de iteraciones de 100, y las características y etiquetas se
obtendrán de las columnas "features" y "label", respectivamente.

```scala
    // Crear las etapas del pipeline (agrega labelIndexer al inicio)
    val stages = Array(labelIndexer) ++ indexers ++ encoders ++ Array(assembler, trainer)

    val pipeline = new Pipeline().setStages(stages)
```
Terminal:
```
scala> val stages = Array(labelIndexer) ++ indexers ++ encoders ++ Array(assembler, trainer)
val stages: Array[org.apache.spark.ml.PipelineStage with org.apache.spark.ml.util.MLWritable{def copy(extra: org.apache.spark.ml.param.ParamMap): org.apache.spark.ml.PipelineStage with org.apache.spark.ml.util.MLWritable{def copy(extra: org.apache.spark.ml.param.ParamMap): org.apache.spark.ml.PipelineStage with org.apache.spark.ml.util.MLWritable}}] = Array(StringIndexerModel: uid=strIdx_29c941b21f33, handleInvalid=error, StringIndexerModel: uid=strIdx_23d5101113b4, handleInvalid=error, StringIndexerModel: uid=strIdx_bcc47d8bbbaa, handleInvalid=error, StringIndexerModel: uid=strIdx_be3923b9817d, handleInvalid=error, StringIndexerModel: uid=strIdx_53f5df205f8a, handleInvalid=error, StringIndexerModel: uid=strIdx_5e0a5240ce10, handleInvalid=error, StringIndexerModel: uid=strIdx_65f2111f34...

scala> val pipeline = new Pipeline().setStages(stages)
val pipeline: org.apache.spark.ml.Pipeline = pipeline_c31be4bd7a20
```
En este ejemplo, se crean las etapas del pipeline. Las etapas incluyen los
encoders de categorías, el ensamblador de características y el entrenador de red neuronal
La etapa del pipeline se crea con las etapas definidas anteriormente.

```scala
    // Split data into training and test sets
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3), seed = 1234L)
```
Terminal:
```
scala> val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3), seed = 1234L)

val trainingData: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [age: int, job: string ... 15 more fields]
val testData: org.apache.spark.sql.Dataset[org.apache.spark.sql.Row] = [age: int, job: string ... 15 more fields]
```
En este ejemplo, se divide la data en conjuntos de entrenamiento y prueba con una proporc
ión de 70% para el conjunto de entrenamiento y 30% para el conjunto de prueba

```scala
    // Train the model
    val model = pipeline.fit(trainingData)
```
Terminal:
```
scala> val model = pipeline.fit(trainingData)
25/05/19 11:32:07 WARN InstanceBuilder: Failed to load implementation from:dev.ludovic.netlib.blas.JNIBLAS
val model: org.apache.spark.ml.PipelineModel = pipeline_2ae01003caae
```
En este ejemplo, se ajusta el modelo al conjunto de entrenamiento.
La salida del modelo se almacena en la variable `model`.

```scala
    // Make predictions
    val predictions = model.transform(testData)
```
Terminal:
```
scala> val predictions = model.transform(testData)
val predictions: org.apache.spark.sql.DataFrame = [age: int, job: string ... 38 more fields]
```
En este ejemplo, se hacen predicciones con el modelo ajustado en el conjunto de prueba.
La salida de las predicciones se almacena en la variable `predictions`.

```scala
    // Show sample predictions
    predictions.select("label", "features", "prediction", "probability").show(10)
```
Terminal:
```
scala> predictions.select("label", "features", "prediction", "probability").show(10)
+-----+--------------------+----------+--------------------+
|label|            features|prediction|         probability|
+-----+--------------------+----------+--------------------+
|  1.0|(40,[10,12,15,16,...|       0.0|[0.63011161266642...|
|  0.0|(40,[10,12,15,16,...|       0.0|[0.94014854626919...|
|  0.0|(40,[10,12,15,16,...|       0.0|[0.65299420897564...|
|  0.0|(40,[0,11,15,16,1...|       0.0|[0.99640413094239...|
|  1.0|(40,[0,12,13,16,1...|       0.0|[0.73354226263077...|
|  0.0|(40,[4,12,13,16,1...|       0.0|[0.99575767828419...|
|  0.0|(40,[10,11,16,18,...|       0.0|[0.97615692958690...|
|  1.0|(40,[10,11,16,18,...|       0.0|[0.58222614425719...|
|  0.0|(40,[10,12,13,16,...|       0.0|[0.99476973913698...|
|  0.0|(40,[10,12,13,16,...|       0.0|[0.97188183721170...|
+-----+--------------------+----------+--------------------+
only showing top 10 rows
```
En este ejemplo, se muestran las primeras 10 predicciones realizadas con el
modelo ajustado. La salida de las predicciones se almacena en la variable `predictions

```scala
    // Evaluate the model
    val evaluator = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("accuracy")

    val accuracy = evaluator.evaluate(predictions)
    println(s"Test set accuracy = $accuracy")

    spark.stop()
  }
}
```
Terminal:
```
scala> val evaluator = new MulticlassClassificationEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("accuracy")
val evaluator: org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator = MulticlassClassificationEvaluator: uid=mcEval_8e776be83f5f, metricName=accuracy, metricLabel=0.0, beta=1.0, eps=1.0E-15

scala> val accuracy = evaluator.evaluate(predictions)
val accuracy: Double = 0.8946979766651898

scala>     println(s"Test set accuracy = $accuracy")
Test set accuracy = 0.8946979766651898
```
En este ejemplo, se evalúa el modelo utilizando el evaluador de clasificación multiclase.
La precisión del conjunto de prueba se almacena en la variable `accuracy` y se impr
ime en la consola.

```scala
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
```
En este ejemplo, se ejecutan 30 veces el modelo y se calcula la precisión promedia 
de las 30 ejecuciones. La precisión promedio se imprime en la consola
La precisión promedio se calcula sumando todas las precisiones y dividiendo por el
número de ejecuciones.
  

[⬅️ Volver al Índice](../README.md)

