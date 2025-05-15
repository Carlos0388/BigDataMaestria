////////////////////////////////////////////
//// LINEAR REGRESSION EXERCISE ///////////
/// Complete las tareas comentadas ///
/////////////////////////////////////////

// Import LinearRegression
import org.apache.spark.ml.regression.LinearRegression

// Opcional: Utilice el siguiente codigo para configurar errores
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)

// Inicie una simple Sesion Spark
import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder().appName("LinearRegressionEcommerce").getOrCreate()

// Utilice Spark para el archivo csv Clean-Ecommerce 
val data = spark.read.option("header", "true").option("inferSchema", "true").csv("Clean-Ecommerce.csv")

// Imprima el schema en el DataFrame.
data.printSchema()

// Imprima un renglon de ejemplo del DataFrane.
data.head(1)


//////////////////////////////////////////////////////
//// Configure el DataFrame para Machine Learning ////
//////////////////////////////////////////////////////

// Transforme el data frame para que tome la forma de ("label","features")

// Importe VectorAssembler y Vectors
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.linalg.Vectors

// Renombre la columna Yearly Amount Spent como "label"
// Tambien de los datos tome solo la columa numerica 
// Deje todo esto como un nuevo DataFrame que se llame df
val df = data.select(data("Yearly Amount Spent").as("label"), 
                     $"Avg Session Length", $"Time on App", $"Time on Website", $"Length of Membership")

// Que el objeto assembler convierta los valores de entrada a un vector
val assembler = new VectorAssembler().setInputCols(Array("Avg Session Length", "Time on App", 
                                                         "Time on Website", "Length of Membership"))
                                    .setOutputCol("features")

// Utilice el objeto VectorAssembler para convertir la columnas de entradas del df
// a una sola columna de salida de un arreglo llamado "features"
val output = assembler.transform(df).select($"label", $"features")

// Crear un objeto para modelo de regresion linea.
val lr = new LinearRegression()

// Ajuste el modelo para los datos y llame a este modelo lrModelo
val lrModelo = lr.fit(output)

// Imprima the coefficients y intercept para la regresion lineal
println(s"Coefficients: ${lrModelo.coefficients} Intercept: ${lrModelo.intercept}")

// Resuma el modelo sobre el conjunto de entrenamiento imprima la salida de algunas metricas!
// Utilize metodo .summary de nuestro modelo para crear un objeto llamado trainingSummary
val trainingSummary = lrModelo.summary

// Muestre los valores de residuals, el RMSE, el MSE, y tambien el R^2 .
println(s"RMSE: ${trainingSummary.rootMeanSquaredError}")
println(s"MSE: ${trainingSummary.meanSquaredError}")
println(s"R2: ${trainingSummary.r2}")

// Buen trabajo!