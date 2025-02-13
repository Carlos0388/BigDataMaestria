val st = "item1 item2 item3"
st matches "item1 item2 item3"
st matches "item2"
st contains "item2"

//Tuplas
var t = ["item1", "item2", "item3"]
t[0] = "item4"

(1, 2.3, "Hello")
val myTup = (1, 2.2, "hello", 23.2, true)
myTup._3
val myTup2 = (3, 1, (2,6))
myTup2._3._2

// Assessment 1 / Practica 1
// 1. Implementa un programa en sacala que calcule el radio de in circulo
// 2. Implementa un programa en scala que me diga si un numero es primo
// 3. Dada la variable bird = "tweet", utiliza interpolacion de strings para imprimir "Estoy escribiendo un tweet"
// 4. Dada la variable mensaje = "Hola Luke yo soy tu padre!" utiliza slice para extraer la secuencia "Luke"
// 5. Cual es la diferencia entre value (val) una variable (var) en scala?
// 6. Dada la tupla (2,4,5,1,2,3.1416,3,7) imprime "3.1416"