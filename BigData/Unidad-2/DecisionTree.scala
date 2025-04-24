/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.examples.ml

// Se importan las clases de la biblioteca spark.ml 
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.DecisionTreeClassificationModel
import org.apache.spark.ml.classification.DecisionTreeClassifier
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorIndexer}


// $example off$
import org.apache.spark.sql.SparkSession

// Se define un objeto con el método main, que es el punto de entrada del programa y se crea una SparkSession, es la interfaz principal para trabajar con Spark.
object DecisionTreeClassificationExample {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder.appName("DecisionTreeClassificationExample").getOrCreate()

    // Se cargan datos desde el archivo LIBSVM.
    val data = spark.read.format("libsvm").load("sample_libsvm_data.txt")
    
    //Convierte las respuestas en índices numéricos.
    val labelIndexer = new StringIndexer().setInputCol("label").setOutputCol("indexedLabel").fit(data)

    // Identifica automáticamente características categóricas en el vector de entrada "features" y las convierte en índices, si tiene más de 4 valores distintos (setMaxCategories(4)), se trata como continua; de lo contrario, se indexa.
    val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(data)

    // Divide el conjunto de datos en dos partes: 70% para entrenamiento y 30% para prueba.
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

    // Crea un clasificador de árbol de decisión usando etiquetas indexadas y las características indexadas.
    val dt = new DecisionTreeClassifier().setLabelCol("indexedLabel").setFeaturesCol("indexedFeatures")

    // Convierte las predicciones numéricas del modelo de vuelta a las etiquetas originales y toma la columna "prediction" como entrada y genera "predictedLabel" como salida.
    val labelConverter = new IndexToString().setInputCol("prediction").setOutputCol("predictedLabel").setLabels(labelIndexer.labelsArray(0))

    // Un Pipeline encadena las etapas del flujo: preprocesamiento, modelo (árbol de decisión) y postprocesamiento (conversión de etiquetas). Ejecutando las etapas en ese orden.
    val pipeline = new Pipeline().setStages(Array(labelIndexer, featureIndexer, dt, labelConverter))

    // Ajusta el pipeline a los datos de entrenamiento, ejecutando todas las etapas definidas.
    val model = pipeline.fit(trainingData)

    // MUsa el modelo entrenado para hacer predicciones sobre los datos de prueba.
    val predictions = model.transform(testData)

    // Muestra las primeras 5 filas de las columnas “predictedLabel”, "label" y "features“.
    predictions.select("predictedLabel", "label", "features").show()

    // Evalúa la precisión del modelo comparando "indexedLabel" con "prediction“ y calcula el error de prueba como 1 - precisión.
    val evaluator = new MulticlassClassificationEvaluator().setLabelCol("indexedLabel").setPredictionCol("prediction").setMetricName("accuracy")
    val accuracy = evaluator.evaluate(predictions)
    println(s"Test Error = ${(1.0 - accuracy)}")

    // Extrae el modelo de árbol de decisión del pipeline (etapa 2) y lo convierte al tipo DecisionTreeClassificationModel e imprime una representación del árbol aprendido.
    val treeModel = model.stages().asInstanceOf[DecisionTreeClassificationModel]
    println(s"Learned classification tree model:\n ${treeModel.toDebugString}")
    
    spark.stop()
  }
}
