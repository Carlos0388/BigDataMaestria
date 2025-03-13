# DATAFRAME PROJECT
# 1.0.0
# 2025-03-12
# Author: [Carlos Garcia, Enrrique Jauregui]
# Examen Unidad 1 Big Data

Iniciar con el programa para poder resolver las preguntas del examen <br>
1. Comienza una simple sesión Spark.
   
Scala
```scala

import org.apache.spark.sql.SparkSession

val spark = SparkSession.builder().getOrCreate()

//Resultado
val spark: org.apache.spark.sql.SparkSession = org.apache.spark.sql.SparkSession@695d804
```

2. Cargue el archivo Netflix Stock CSV en dataframe llamado df, haga que Spark
infiera los tipos de datos.

```scala
// Spark es la instancia de SparkSession, que es el punto de entrada para trabajar con DataFrames en Spark, ademas hace que Spark infiera automáticamente el tipo de datos de cada columna, sin esta opción, todas las columnas se tratarían como cadenas de texto.
val df = spark.read.option("header","true").option("inferSchema","true").csv("Netflix_2011_2016.csv")

//Resultado
val df: org.apache.spark.sql.DataFrame = [Date: date, Open: double ... 5 more fields]
```

3. ¿Cuáles son los nombres de las columnas?
```scala
// Pedimos los datos de las columnas del DataFrame con el llamado "columns"
df.columns

//Resultado
val res0: Array[String] = Array(Date, Open, High, Low, Close, Volume, Adj Close)
```

4. ¿Cómo es el esquema?
```scala
//Muestra el esquema del DataFrame: Imprime en la consola la estructura del DataFrame, incluyendo, los nombres de las columnas, los tipos de datos de cada columna y si las columnas permiten valores nulos (nullable).
df.printSchema()

//Resultado
root
 |-- Date: date (nullable = true)
 |-- Open: double (nullable = true)
 |-- High: double (nullable = true)
 |-- Low: double (nullable = true)
 |-- Close: double (nullable = true)
 |-- Volume: integer (nullable = true)
 |-- Adj Close: double (nullable = true)
```

5. Imprime las primeras 5 renglones.
```scala
// Head selecciona los primeros valores, le asignamos solo 5
df.head(5)

//Resultado
val res2: Array[org.apache.spark.sql.Row] = Array([2011-10-24,119.100002,120.28000300000001,115.100004,118.839996,120460200,16.977142], [2011-10-25,74.899999,79.390001,74.249997,77.370002,315541800,11.052857000000001], [2011-10-26,78.73,81.420001,75.399997,79.400002,148733900,11.342857], [2011-10-27,82.179998,82.71999699999999,79.249998,80.86000200000001,71190000,11.551428999999999], [2011-10-28,80.280002,84.660002,79.599999,84.14000300000001,57769600,12.02]) 
```

6. Usa el método describe () para aprender sobre el DataFrame.
```scala
// Muestra la descripcion del DataFrame
df.describe().show()

//Resultado
+-------+------------------+------------------+------------------+------------------+--------------------+------------------+
|summary|              Open|              High|               Low|             Close|              Volume|         Adj Close|
+-------+------------------+------------------+------------------+------------------+--------------------+------------------+
|  count|              1259|              1259|              1259|              1259|                1259|              1259|
|   mean|230.39351086656092|233.97320872915006|226.80127876251044|  230.522453845909|2.5634836060365368E7|55.610540036536875|
| stddev|164.37456353264244| 165.9705082667129| 162.6506358235739|164.40918905512854| 2.306312683388607E7|35.186669331525486|
|    min|         53.990001|         55.480001|             52.81|              53.8|             3531300|          7.685714|
|    max|        708.900017|        716.159996|        697.569984|        707.610001|           315541800|        130.929993|
+-------+------------------+------------------+------------------+------------------+--------------------+------------------+
```

7. Crea un nuevo dataframe con una columna nueva llamada “HV Ratio” que es la
relación que existe entre el precio de la columna “High” frente a la columna
“Volumen” de acciones negociadas por un día. Hint - es una operación

```scala
// Ordena el DataFrame df por la columna "High" de forma descendente
val df2: org.apache.spark.sql.DataFrame = [Date: date, Open: double ... 6 more fields]
df.orderBy($"High".desc).show(1)

// Muestra la tabla ya ordenada
df2.show()
+----------+-----------------+------------------+----------+-----------------+---------+------------------+--------------------+
|      Date|             Open|              High|       Low|            Close|   Volume|         Adj Close|            HV Ratio|
+----------+-----------------+------------------+----------+-----------------+---------+------------------+--------------------+
|2011-10-24|       119.100002|120.28000300000001|115.100004|       118.839996|120460200|         16.977142|9.985040951285156E-7|
|2011-10-25|        74.899999|         79.390001| 74.249997|        77.370002|315541800|11.052857000000001|2.515989989281927E-7|
|2011-10-26|            78.73|         81.420001| 75.399997|        79.400002|148733900|         11.342857|5.474206014903126E-7|
|2011-10-27|        82.179998| 82.71999699999999| 79.249998|80.86000200000001| 71190000|11.551428999999999|1.161960907430818...|
|2011-10-28|        80.280002|         84.660002| 79.599999|84.14000300000001| 57769600|             12.02|1.465476686700271...|
|2011-10-31|83.63999799999999|         84.090002| 81.450002|        82.080003| 39653600|         11.725715|2.120614572195210...|
|2011-11-01|        80.109998|         80.999998|     78.74|        80.089997| 33016200|         11.441428|2.453341026526372E-6|
|2011-11-02|        80.709998|         84.400002| 80.109998|        83.389999| 41384000|         11.912857|2.039435578967717E-6|
|2011-11-03|        84.130003|         92.600003| 81.800003|        92.290003| 94685500|13.184285999999998| 9.77974483949496E-7|
|2011-11-04|91.46999699999999| 92.89000300000001| 87.749999|        90.019998| 84483700|             12.86|1.099502069629999...|
|2011-11-07|             91.0|         93.839998| 89.979997|        90.830003| 47485200|         12.975715|1.976194645910725...|
|2011-11-08|91.22999899999999|         92.600003| 89.650002|        90.470001| 31906000|         12.924286|2.902275528113834...|
|2011-11-09|        89.000001|         90.440001| 87.999998|        88.049999| 28756000|         12.578571|3.145082800111281E-6|
|2011-11-10|        89.290001| 90.29999699999999| 84.839999|85.11999899999999| 39614400|             12.16|2.279474054889131E-6|
|2011-11-11|        85.899997|         87.949997|      83.7|        87.749999| 38140200|         12.535714|2.305965805108520...|
|2011-11-14|        87.989998|              88.1|     85.45|        85.719999| 21811300|         12.245714|4.039190694731629...|
|2011-11-15|            85.15|         87.050003| 84.499998|        86.279999| 21372400|         12.325714|4.073010190713256...|
|2011-11-16|        86.460003|         86.460003| 80.890002|        81.180002| 34560400|11.597142999999999|2.501707242971725E-6|
|2011-11-17|            80.77|         80.999998| 75.789999|        76.460001| 52823400|         10.922857|1.533411291208063...|
|2011-11-18|             76.7|         78.999999| 76.039998|        78.059998| 34729100|         11.151428|2.274749388841058...|
+----------+-----------------+------------------+----------+-----------------+---------+------------------+--------------------+
only showing top 20 rows
//+----------+-----------------+-----------------+----------+-----------------+--------+------------------+
```

8. ¿Qué día tuvo el pico más alto en la columna “Open”?

```scala
// Encuentra el valor máximo en la columna "Open"
val maxOpenRow = df.agg(max("Open")).first()
val maxOpenRow: org.apache.spark.sql.Row = [708.900017]

// Filtra la fila donde ocurrió ese valor máximo
val maxOpenValue = maxOpenRow.getAs[Double]("max(Open)")
val maxOpenValue: Double = 708.900017
val maxDayRow = df.filter(df("Open") === maxOpenValue).first()
val maxDayRow: org.apache.spark.sql.Row = [2015-07-14,708.900017,711.449982,697.569984,702.600006,19736500,100.371429]

//Imprime la fecha correspondiente a ese valor máximo:
println(s"El día con el pico más alto en la columna 'Open' es: ${maxDayRow.getAs[String]("Date")}")

//Resultado
El día con el pico más alto en la columna 'Open' es: 2015-07-14
```


9. ¿Cuál es el significado de la columna Cerrar “Close” en el contexto de información
financiera, explíquelo no hay que codificar nada?

La columna "Cerrar" representa el precio de cierre de un activo en un día de negociación específico. Este precio es el último precio al que se negoció el activo antes de que el mercado cerrara ese día.

```Scala
df.select(mean("Close")).show()
//Resultado
+----------------+
|      avg(Close)|
+----------------+
|230.522453845909|
+----------------+
```

Preguntas del Exmamen

10.- ¿Cuál es el máximo y mínimo de la columna “Volumen”?

```scala
df.select(max("Volume")).show()
df.select(min("Volume")).show()
```
Terminal
```
scala> df.select(max("Volume")).show()
+-----------+
|max(Volume)|
+-----------+
|  315541800|
+-----------+


scala> df.select(min("Volume")).show()
+-----------+
|min(Volume)|
+-----------+
|    3531300|
+-----------+
```
>[!NOTE]
>


11).- Con Sintaxis Scala/Spark $ conteste lo siguiente:

```scala
import spark.implicits._
//Resultado se realizo la importacion 
```

a).- ¿Cuántos días fue la columna “Close” inferior a $ 600?
```scala
df.filter($"Close"<600).count()
```
Terminal
```
scala> df.filter($"Close"<600).count()
val res7: Long = 1218
```
>[!NOTE]
>


b).- ¿Qué porcentaje del tiempo fue la columna “High” mayor que $ 500?

```scala
(df.filter($"High">500).count()*1.0/df.count())*100
```
Terminal
```
scala> (df.filter($"High">500).count()*1.0/df.count())*100
val res8: Double = 4.924543288324067
```
>[!NOTE]
>


c).- ¿Cuál es la correlación de Pearson entre columna “High” y la columna
“Volumen”?

```scala
df.select(corr("High","Volume")).show()
```
Terminal
```
scala> df.select(corr("High","Volume")).show()
+--------------------+
|  corr(High, Volume)|
+--------------------+
|-0.20960233287942157|
+--------------------+
```
>[!NOTE]
>


d).- ¿Cuál es el máximo de la columna “High” por año?

```scala
val yeardf = df.withColumn("Year",year(df("Date")))
val yearmaxs = yeardf.select($"Year",$"High").groupBy("Year").max()
yearmaxs.select($"Year",$"max(High)").show()
```
Terminal
```
scala> val yeardf = df.withColumn("Year",year(df("Date")))
val yeardf: org.apache.spark.sql.DataFrame = [Date: date, Open: double ... 6 more fields]

scala> val yearmaxs = yeardf.select($"Year",$"High").groupBy("Year").max()
val yearmaxs: org.apache.spark.sql.DataFrame = [Year: int, max(Year): int ... 1 more field]

scala> yearmaxs.select($"Year",$"max(High)").show()
+----+------------------+
|Year|         max(High)|
+----+------------------+
|2015|        716.159996|
|2013|        389.159988|
|2014|        489.290024|
|2012|        133.429996|
|2016|129.28999299999998|
|2011|120.28000300000001|
+----+------------------+
```
>[!NOTE]
>


e).- ¿Cuál es el promedio de la columna “Close” para cada mes del calendario?

```scala
val monthdf = df.withColumn("Month",month(df("Date")))
val monthavgs = monthdf.select($"Month",$"Close").groupBy("Month").mean()
monthavgs.select($"Month",$"avg(Close)").show()
```
Terminal
```
scala> val monthdf = df.withColumn("Month",month(df("Date")))
val monthdf: org.apache.spark.sql.DataFrame = [Date: date, Open: double ... 6 more fields]

scala> val monthavgs = monthdf.select($"Month",$"Close").groupBy("Month").mean()
val monthavgs: org.apache.spark.sql.DataFrame = [Month: int, avg(Month): double ... 1 more field]

scala> monthavgs.select($"Month",$"avg(Close)").show()
+-----+------------------+
|Month|        avg(Close)|
+-----+------------------+
|   12| 199.3700942358491|
|    1|212.22613874257422|
|    6| 295.1597153490566|
|    3| 249.5825228971963|
|    5|264.37037614150944|
|    9|206.09598121568627|
|    4|246.97514271428562|
|    8|195.25599892727263|
|    7|243.64747528037387|
|   10|205.93297300900903|
|   11| 194.3172275445545|
|    2| 254.1954634020619|
+-----+------------------+
```

>[!NOTE]
>

Plus.- Investigar otras funciones para resolver las dudas del programa y de la base de datos de netflix

