////////////////////////////////////////////
//// LINEAR REGRESSION EXERCISE ///////////
/// Coplete las tareas comentadas ///
/////////////////////////////////////////

// Import LinearRegression
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql.SparkSession


// Opcional: Utilice el siguiente codigo para configurar errores
import org.apache.log4j._
Logger.getLogger("org").setLevel(Level.ERROR)


// Inicie una simple Sesion Spark
val spark = SparkSession.builder.appName("LinearRegression").getOrCreate()

// Utilice Spark para el archivo csv Clean-Ecommerce.
val data = spark.read.option("header","true").option("inferSchema","true")csv("Clean-Ecommerce.csv")

// Imprima el schema en el DataFrame.
data.printSchema()


// Imprima un renglon de ejemplo del DataFrane.
val colnames = data.columns
val firstrow = data.head(1)(0)
println("\n")
println("Example Data Row")
for(ind <- Range(1, colnames.length)){
  println(colnames(ind))
  println(firstrow(ind))
  println("\n")
}

//////////////////////////////////////////////////////
//// Configure el DataFrame para Machine Learning ////
//////////////////////////////////////////////////////

// Transforme el data frame para que tome la forma de ("label","features")
val df = data.select("Price","Quantity","Discount","Shipping","Tax","Country","Region","Category","Sub-Category","Sales")
.withColumn("label", col("Price"))


// Importe VectorAssembler y Vectors
import org.apache.spark.ml.feature.VectorAssembler

// Renombre la columna Yearly Amount Spent como "label"
val df = data.select("Yearly Amount Spent as label")

// Tambien de los datos tome solo la columa numerica
val df = df.select("label", "Age", "Annual Income (k$)", "Spending Score (1-100)")

// Deje todo esto como un nuevo DataFrame que se llame df
val df = data.select(data("Yearly Amount Spent").as("label"), $"Avg Session Length", $"Time on App", $"Time on Website", $"Length of Membership")


// Que el objeto assembler convierta los valores de entrada a un vector
val assembler = new VectorAssembler().setInputCols(df.columns.filter(_ != "label")).setOutputCol("features")


// Utilice el objeto VectorAssembler para convertir la columnas de entradas del df a una sola columna de salida de un arreglo llamado  "features"
val output = assembler.transform(df).select($"label",$"features")

// Configure las columnas de entrada de donde se supone que leemos los valores.
val assembler = new VectorAssembler().setInputCols(Array("Avg Session Length", "Time on App", "Time on Website", "Length of Membership")).setOutputCol("features")



// Llamar a esto nuevo assambler.
val assembler = new VectorAssembler().setInputCols(df.columns.filter(_ != "label")).setOutput

// Utilice el assembler para transform nuestro DataFrame a dos columnas: label and features
val output = assembler.transform(df).select($"label",$"features")


// Crear un objeto para modelo de regresion linea.
val lr = new LinearRegression()



// Ajuste el modelo para los datos y llame a este modelo lrModelo
val lrModel = lr.fit(output)


// Imprima the  coefficients y intercept para la regresion lineal
println(s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")


// Resuma el modelo sobre el conjunto de entrenamiento imprima la salida de algunas metricas!
val summary = lrModel.summary
// Utilize metodo .summary de nuestro  modelo para crear un objeto
val summary = lrModel.summary

// Imprima el resumen del modelo
val trainingSummary = lrModel.summary
println(s"RMSE: ${summary.rootMeanSquaredError} R-squared: ${summary.r2}")

// llamado trainingSummary
val trainingSummary = lrModel.summary
// Imprima el resumen del modelo
trainingSummary.residuals.show()
println(s"RMSE: ${trainingSummary.rootMeanSquaredError}")
println(s"MSE: ${trainingSummary.meanSquaredError}")
println(s"R^2: ${trainingSummary.r2}")



// Muestre los valores de residuals, el RMSE, el MSE, y tambien el R^2 .
println(s"Residuals: ${trainingSummary.residuals} RMSE: ${trainingSummary.rootMeanSquaredError} MSE: ${trainingSummary.meanSquaredError} R^2: ${trainingSummary.r2}")



// Buen trabajo!
