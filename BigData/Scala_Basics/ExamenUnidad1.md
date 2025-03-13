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
val df = spark.read.option("header","true").option("inferSchema","true").csv("Netflix_2011_2016.csv")

//Resultado
val df: org.apache.spark.sql.DataFrame = [Date: date, Open: double ... 5 more fields]
```

3. ¿Cuáles son los nombres de las columnas?
```scala
df.columns

//Resultado
val res0: Array[String] = Array(Date, Open, High, Low, Close, Volume, Adj Close)
```

4. ¿Cómo es el esquema?
```scala
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
df.head(5)

//Resultado
val res2: Array[org.apache.spark.sql.Row] = Array([2011-10-24,119.100002,120.28000300000001,115.100004,118.839996,120460200,16.977142], [2011-10-25,74.899999,79.390001,74.249997,77.370002,315541800,11.052857000000001], [2011-10-26,78.73,81.420001,75.399997,79.400002,148733900,11.342857], [2011-10-27,82.179998,82.71999699999999,79.249998,80.86000200000001,71190000,11.551428999999999], [2011-10-28,80.280002,84.660002,79.599999,84.14000300000001,57769600,12.02]) 
```

6. Usa el método describe () para aprender sobre el DataFrame.
```scala
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

val df2 = df.withColumn("HV Ratio",df("High")/df("Volume"))
//Resultado
val df2: org.apache.spark.sql.DataFrame = [Date: date, Open: double ... 6 more fields]

//df.orderBy($"High".desc).show(1)
//Resultado
//scala> df.orderBy($"High".desc).show(5)
//+----------+-----------------+-----------------+----------+-----------------+--------+------------------+
//|      Date|             Open|             High|       Low|            Close|  Volume|         Adj Close|
//+----------+-----------------+-----------------+----------+-----------------+--------+------------------+
//|2015-07-13|686.6900019999999|       716.159996|686.550026|       707.610001|33205200|101.08714300000001|
//|2015-07-14|       708.900017|       711.449982|697.569984|       702.600006|19736500|        100.371429|
//|2015-06-24|       700.099976|       706.239983|674.779984|678.6099780000001|77138600|         96.944283|
//|2015-06-10|       653.769997|692.7900089999999|652.580009|       671.100006|57121400|         95.871429|
//|2015-07-10|       682.660004|       689.519974|678.300011|       680.599983|21636300|         97.228569|
//+----------+-----------------+-----------------+----------+-----------------+--------+------------------+
//only showing top 5 rows
```

8. ¿Qué día tuvo el pico más alto en la columna “Open”?


9. ¿Cuál es el significado de la columna Cerrar “Close” en el contexto de información
financiera, explíquelo no hay que codificar nada?

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

