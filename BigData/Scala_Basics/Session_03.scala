**OPERACIONES MATEMATICAS**
//Exponencia, en este ejemplo el cuatro se eleva al cuadrado, como resultado 16.
math.pow(4, 2)  //Double = 16.0

//Se pueden sumar variables a las que ya les hayamos asignado un valor.
res0 // Int = 100
res0 + res11 // Int = 200

//Se pueden sumar los números directamente en las operaciones de jerarquía.
1 + 2 * 3 + 4 //Int = 11
(1+2) * (3+4) //Int = 21

//En este ejemplo se convierte el metro a pies
3 * 0.3048  //Double = 0.914400

//Una vez que se le asigna un valor a una variable no puede cambiar por ejemplo, una variable de tipo entero no puede convertirse directamente a tipo string y viceversa.
var myvar: Int = 10
val myval: Double = 2.5

myvar = "hello" //error: type mistmatch
myvar = 100

myval = "hello" //error: reassignment to val
myval = 10.1 //error: reassigment to val
**BOOLEANOS
// Se conoce como booleanos a las operaciones que dan como resultado verdader o falso.
true
false

// Los siguientes ejemplos del mayor que, menor que, menor o igual, igual y diferente de, son los mas utilizados.
1 > 2 // false
1 < 2 // true
1 <= 30 // true
2 == 2 // true
2 != 4 // true

// El residuo que se codifica con un %, divide el valor y si el residuo es 0 o 1.
4 % 2 // 0 mod operator
5  % 2 // 1
6 % 2 // if 0 the number is even

// Se puede imprimir cualquier texto con la sintaxis println(“texto a imprimir en pantalla”)
println("Hello")
println('Hello') //Error unclosed character literal
// Concatenar strings con +, cual si fueran operaciones matemáticas.
val greeting = "Hello " + "there!"
"dance"*5
val st = "hello"
val name = "Christian"
// Interpolacion de cadenas de texto, con el símbolo ${nombre de la variable}
val greet = s"Hello ${name}"
val greet = s"Hello $name"

// Es posible mostrar distintos tipos de valores, como lo son string, integer, booleanos, en una sola cadena.
printf("A string %s, an integer %d, a float %f", "Hi",10,3.1416)
printf("A float %1.2f", 1.2345)
printf("A float %1.2f", 1.2395)

// Podemos crear índices y asignar una posición a cada carácter del string, en el siguiente ejemplo toma el texto y le asigna el valor de la posición iniciando desde el 0, la posición 0 tendra el valor de “T”, el 1 el de “h” y así sucesivamente.
val st = "This a long string"
st.charAt(0)
st.charAt(3)
st.indexOf("a")
st slice  (0,4)

// val es una variable inmutable, esto quiere decir que no cambia su valor después de ser asignada, en este caso st tiene el valor del string "item1 item2 item3".
val st = "item1 item2 item3"

// Se puede comparar el valor con el de otro, en este ejemplo nos regresa el valor de verdadero porque coincide exactamente el valor de st con el texto que se compara y en el otro no coincide.
st matches "item1 item2 item3" // res0: Boolean = true
st matches "item2" // res1: Boolean = false
// Además de comparar todo el texto también podemos definir si contiene cierto texto en toda la cadena o no.
st contains "item1" // res2: true
st contains "tem4" // res3: false

//TUPLAS
// Las tuplas de igual manera son inmutables, contienen un número fijo de elementos, cada uno de ellos puede ser de distinto tipo, como en los siguientes ejemplos que combina, texto, números, booleanos, todo en una sola cadena de elementos.
val my_tup = (1,2.2, "hello,", 23.2, true) 

// El sigueiente ejemplo contiene 3 elementos, un entero de valor 3, otro de valor 1 y el ultimo es una tupla con valor de (1,3) aunque son 2 valores, dentro de la tupla se toma como un solo valor, que es una tupla dentro de otra.
(3,1,(2,3))

// Se puede elegir la posición de la tupla que queramos acceder, con la sintaxis ._# como en el siguiente ejemplo que pide el tercer valor de la tupla y el quinto.
my_tup._3 // res6: String = hello
my_tup._5 // res7: Boolean = true

//Para acceder a una tupla completa en el siguiente ejemplo pide el tercer valor, después pide el segundo valor del tercer valor que es la tupla, ósea que debería dar el valor 6 que esta anidado en la tupla.
val my_tup = (3, 1, (2, 6))
my_tup._3 // respuesta (2,6)

my_tup._3._2, // respuesta (6)

// LISTAS Y ARRAYS
// Las listas son colecciones ordenadas de elementos, en el siguiente ejemplo se crea una lista de enteros, específicamente de números pares.
val evens = List(2,4,6,8,10) //evens: List[Int] = List(2,4,6,8,10)

// De igual manera las listas pueden tener diferentes tipos de elementos.
List(1,2.2,true)  // res8: List[AnyVal] = List(1, 2.2, true)

//Para acceder a algún elemento se utiliza la sintaxis evens, iniciando desde la posición 0.
evens(0)  //res9: Int = 2
evens(4)  //res10: Int =10

// Head devuelve el primer valor de la lista
evens.head //res11: Int = 2

//Tail devuelve la lista sin el primer valor.
evens.tail //res12: List[Int] = List(4, 6, 8, 10)

//Se crea una lista que tiene 2 listas.
val my_list = List(List(1,2,3),List(3,2,1))

//Crea una lista de tuplas con string y entero cada una.
val my_list = List(("a",1), ("b",2),("c",3))

//Crea una lista de enteros
val my_list = List(1,5,3,7,6,109)
// Devuelve el ultimo valor de la lista, en este caso 109
my_list.last //109

//Ordena la lista de menor a mayor
my_list.sorted //List(1, 3, 5, 6, 7, 109)

// Devuelve el numero de elementos en la lista, en este caso son 6.
my_list.size //6

//Devuelve el valor máximo de la lista.
my_list.max //109

//Devuelve el valor mínimo de la lista
my_list.min //1

// Suma todos los elementos de la lista.
my_list.sum //131

// Devuelve el producto de los elementos de la lista.
my_list.product // 68670

// Crear una lista que se llame z
val z = List(4,5,6,7)

// Elimina n numero de elementos y muestra el resto.
z.drop(2)
// Devuelve n numeros de la lista.
z.takeRight(1)
z.takeRight(3)

// Hacer una sublista y devuelve un subíndice de cada lista con la sintaxis slice.
val x = List(1,2,3,4,5,6,7,8)
x slice (0,3)
x slice (3, 6)

// ARRAYS
// Los arreglos son colecciones de elementos que si pueden ser mutables.
val arr = Array(3,4,5)
val arr = Array("a","b","c")
val arr = Array("a","b", true, 1.2)

// Se pueden crear con rangos de elementos
Array.range(0, 10)
Array.range(0, 10, 2)

// Sets son conjuntos de elementos que no permiten valores duplicados.
val s = Set()
val s = Set(1,2,3)
val s = Set(2,2,2,3,3,3,5,5,5)

// Permite agregar o eliminar elementos despues de su creación.
val s = collection.mutable.Set(1,2,3)
// Agregar el valor 4 al conjunto
s += 4
//Agregar el valor 5
ims += 5
// Agregar el elemento 6
ims.add(6)
//Devuelve el valor maximo
ims.max
//Devuelve el valor minimo
ims.min

// Convierte una lista en un conjunto, eliminando duplicados.
val mylist = List(1,2,3,1,2,3)
mylist.toSet

// MAPAS son colecciones de pares clave-valor.
val mymap = Map(("saludo", "Hola"), ("pi", 3.1416), ("z", 1.3))

//Como ejemplo devuelve el valor asociado a “PI” que nosotros mismos ya definimos.
mymap("pi")

// Devuelve “Hola”
mymap("saludo")

// Excepcion porque este valor no existe en el mapa.
mymap("ja")
// Mapas mutables, estos permiten agregar o borras valores después de su creación.
val mutmap = collection.mutable.Map(("z", 123), ("a", 5), ("b", 7))

// Agrega el par ("lucky", 777) al mapa ya creado.
mutmap += ("lucky" -> 777)

// Devuelve un iterable con las claves del mapa: Set(z, a, b, lucky).
mutmap.keys

// Devuelve un iterable con los valores del mapa: Iterable(123, 5, 7, 777)
mutmap.values

1. Crea una lista llamada "lista" con los elementos "rojo", "blanco", "negro"
val lista = List("rojo", "blanco", "negro")

2. Añadir 5 elementos mas a "lista" "verde" ,"amarillo", "azul", "naranja", "perla"
val nuevosElementos = List("verde", "amarillo", "azul", "naranja", "perla")
val listaActualizada = lista ++ nuevosElementos

3. Traer los elementos de "lista" "verde", "amarillo", "azul"
val sublista = listaActualizada.slice(3, 6)


// Arrays
// Arrays are mutable, List are not
val arr = Array(3,4,5)
val arr = Array("a","b","c")
val arr = Array("a","b", true, 1.2)

//Create arrays  with range method
Array.range(0, 10)
Array.range(0, 10, 2)

Range(0,5)

//Sets not cotains duplicate elements
val s = Set()
val s = Set(1,2,3)

val s = Set(2,2,2,3,3,3,5,5,5)

val s = collection.mutable.Set(1,2,3)
s += 4

val ims = collection.mutable.Set(2,3,4)
ims += 5
ims.add(6)
ims

ims.max
ims.min

val mylist = List(1,2,3,1,2,3)
mylist.toSet

val newset = mylist.toSet
newset

//Maps key value pair storage

val mymap = Map(("saludo", "Hola"), ("pi", 3.1416), ("z", 1.3))
mymap("pi")
mymap("saludo")
mymap("ja")
mymap get "pi"
mymap get "z"
mymap get "o"

val mutmap = collection.mutable.Map(("z", 123), ("a", 5), ("b", 7))

mutmap += ("lucky" -> 777)
mutmap
mutmap.keys
mutmap.values

//Practice 2
// 1. Crea una lista llamada "lista" con los elementos "rojo", "blanco", "negro"
// 2. Añadir 5 elementos mas a "lista" "verde" ,"amarillo", "azul", "naranja", "perla"
// 3. Traer los elementos de "lista" "verde", "amarillo", "azul"

//Carlos Garcia 

// 4. Crea un arreglo de numero en rango del 1-1000 en pasos de 5 en 5
val arreglo = (1 to 1000 by 5).toArray

//Terminal
scala>val arreglo = (1 to 1000 by 5).toArray
val arreglo: Array[Int] = Array(1, 6, 11, 16, 21, 26, 31, 36, 41, 46, 51, 56, 61, 66, 71, 76, 81, 86, 91, 96, 101, 
106, 111, 116, 121, 126, 131, 136, 141, 146, 151, 156, 161, 166, 171, 176, 181, 186, 191, 196, 201, 206, 211, 216, 
221, 226, 231, 236, 241, 246, 251, 256, 261, 266, 271, 276, 281, 286, 291, 296, 301, 306, 311, 316, 321, 326, 331, 
336, 341, 346, 351, 356, 361, 366, 371, 376, 381, 386, 391, 396, 401, 406, 411, 416, 421, 426, 431, 436, 441, 446, 
451, 456, 461, 466, 471, 476, 481, 486, 491, 496, 501, 506, 511, 516, 521, 526, 531, 536, 541, 546, 551, 556, 561, 
566, 571, 576, 581, 586, 591, 596, 601, 606, 611, 616, 621, 626, 631, 636, 641, 646, 651, 656, 661, 666, 671, 676, 
681, 686, 691, 696, 701, 706, 711, 716, 721, 726, 731, 736, 741, 746, 751, 756, 761, 766, 771, 776, 781, 786, 791, 
796, 801, 806, 811, 816, 821, 826, 831, 836, 841, 846, 851, 856, 861, 866, 871, 876, 881, 886, 891, 896, 901, 906, 
911, 916, 921, 926, 931, 936, 941, 946, 951, 956, 961, 966, 971, 976, 981, 986, 991, 996)



// 5. Cuales son los elementos unicos de la lista Lista(1,3,3,4,6,7,3,7) utilice conversion a conjuntos

val lista = List(1, 3, 3, 4, 6, 7, 3, 7)
val elementosUnicos = lista.toSet

//Terminal
scala> val lista = List(1, 3, 3, 4, 6, 7, 3, 7)
val lista: List[Int] = List(1, 3, 3, 4, 6, 7, 3, 7)

scala> val elementosUnicos = lista.toSet
val elementosUnicos: scala.collection.immutable.Set[Int] = Set(1, 6, 3, 7, 4)

List(1, 3, 4, 6, 7)

// 6. Crea una mapa mutable llamado nombres que contenga los siguiente
//     "Jose", 20, "Luis", 24, "Ana", 23, "Susana", "27"

import scala.collection.mutable.Map

val nombres = Map(
  "Jose" -> 20,
  "Luis" -> 24,
  "Ana" -> 23,
  "Susana" -> 27
)

//Terminal
scala> val nombres = Map(
     |   "Jose" -> 20,
     |   "Luis" -> 24,
     |   "Ana" -> 23,
     |   "Susana" -> 27
     | )
val nombres: scala.collection.mutable.Map[String,Int] = HashMap(Susana -> 27, Jose -> 20, Ana -> 23, Luis -> 24)


// 6 a . Imprime todas la llaves del mapa

nombres.keys.foreach(println)

//Terminal
scala> nombres.keys.foreach(println)
Susana
Jose
Ana
Luis

// 6 b . Agrega el siguiente valor al mapa("Miguel", 23)

nombres += ("Miguel" -> 23)
//Terminal

scala> nombres += ("Miguel" -> 23)
val res2: nombres.type = HashMap(Susana -> 27, Miguel -> 23, Jose -> 20, Ana -> 23, Luis -> 24)

scala> nombres.keys.foreach(println)
Susana
Miguel
Jose
Ana
Luis
