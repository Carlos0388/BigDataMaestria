
// Importar las clases necesarias para realizar la regresión lineal y el procesamiento.
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import org.apache.log4j._

// Opcional: Configurar los logs para minimizar la salida de error en la consola.
Logger.getLogger("org").setLevel(Level.ERROR)

// Iniciar una sesión simple de Spark.
val spark = SparkSession.builder()
  .appName("LinearRegressionEcommerce")
  .getOrCreate()

// Utilice Spark para cargar el archivo CSV "Clean-Ecommerce.csv".
// Se asume que el archivo tiene encabezado y se infiere el esquema automáticamente.
val data = spark.read
  .option("header", "true")
  .option("inferSchema", "true")
  .csv("Clean-Ecommerce.csv")

// Imprimir el esquema del DataFrame.
data.printSchema()

// Imprimir una línea de ejemplo del DataFrame.
data.show(1)

// ---------------------------------------------------------------
// Configuración del DataFrame para Machine Learning
// ---------------------------------------------------------------

// Se supone que el archivo contiene la columna "Yearly Amount Spent" junto con otras columnas.
// Renombramos "Yearly Amount Spent" a "label" para que el modelo lo reconozca como nuestra variable a predecir.
// Además, seleccionamos únicamente las columnas numéricas que queremos usar como características.
// En este ejemplo se consideran que las columnas numéricas son: 
// "Avg Session Length", "Time on App", "Time on Website", "Length of Membership".
val df = data.withColumnRenamed("Yearly Amount Spent", "label")
             .select("label", "Avg Session Length", "Time on App", "Time on Website", "Length of Membership")

// Se importa VectorAssembler para combinar varias columnas de entrada en un solo vector de características.
val assembler = new VectorAssembler()
  .setInputCols(Array("Avg Session Length", "Time on App", "Time on Website", "Length of Membership"))
  .setOutputCol("features")

// Utilice el objeto VectorAssembler para transformar el DataFrame a la forma ("label", "features").
val output = assembler.transform(df).select("label", "features")
output.show(5)

// ---------------------------------------------------------------
// Creación y ajuste del modelo de regresión lineal
// ---------------------------------------------------------------

// Crear un objeto para el modelo de regresión lineal.
val lr = new LinearRegression()

// Ajustar el modelo para los datos y llamar al modelo ajustado "lrModel".
val lrModel = lr.fit(output)

// Imprimir los coeficientes y el intercepto resultante de la regresión lineal.
println(s"Coefficients: ${lrModel.coefficients}")
println(s"Intercept: ${lrModel.intercept}")

// Resumir el modelo sobre el conjunto de entrenamiento.
// Se utiliza el método .summary del modelo para obtener métricas de ajuste.
val trainingSummary = lrModel.summary

// Mostrar los valores de residuals, RMSE, MSE y R².
println("Resumen del modelo:")
trainingSummary.residuals.show(5)
println(s"RMSE: ${trainingSummary.rootMeanSquaredError}")
println(s"MSE: ${trainingSummary.meanSquaredError}")
println(s"R2: ${trainingSummary.r2}")