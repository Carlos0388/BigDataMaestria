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
