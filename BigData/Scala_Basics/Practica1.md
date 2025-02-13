# Practica 1 Big Data

Enrrique Jauregui
1. Implementa un programa en scala que calcule el radio de un circulo
    def calcularRadio(diametro: Double): Double = diametro / 2
    println(s"El radio de un círculo con diámetro 10 es: ${calcularRadio(10)}")

scala> def calcularRadio(diametro: Double): Double = diametro / 2
def calcularRadio(diametro: Double): Double

scala> println(s"El radio de un círculo con diámetro 10 es: ${calcularRadio(10)}")
El radio de un círculo con diámetro 10 es: 5.0

//PARA CAULCUAR EL AREA DE UN CIRCULO SE CONTEMPLARON 2 POSIBLES OPCIONES, SABIENDO EL DIAMETRO Y SABIENDO LA CIRCUNFERENCIA
//USANDO LAS 2 FORMULAS DEPENDIENDO EL DATO QUE SE INGRESE.

2. Implementa un programa en scala que me diga si un numero es primo
    def esPrimo(numero: Int): Boolean = {
      if (numero <= 1) false
      else !(2 until numero).exists(x => numero % x == 0)
    }
    println(s"El número 7 es primo: ${esPrimo(7)}")

    scala> def esPrimo(numero: Int): Boolean = {
     |       if (numero <= 1) false
     |       else !(2 until numero).exists(x => numero % x == 0)
     |     }
def esPrimo(numero: Int): Boolean

scala> println(s"El número 7 es primo: ${esPrimo(7)}")
El número 7 es primo: true

//EJECUTA LA FORMULA PARA RECIBIR SI UN NUMERO ES PRIMO, EN EL EJEMPLO SE PONE EL NUMERO 7 QUE SABEMOS QUE SI ES NUMERO PRIMO
//PIDE UNA VALIDACION PARA QUE SOLO SEAN NUMEROS MAYORES A 0


3. Dada la variable bird = "tweet", utiliza interpolacion de strings para imprimir "Estoy escribiendo un tweet"
    val bird = "tweet"
    println(s"Estoy escribiendo un $bird")

scala> val bird = "tweet"
val bird: String = tweet

scala> println(s"Estoy escribiendo un $bird")
Estoy escribiendo un tweet

//SE UTILIZO UN PRINTLN PARA CONCATENAR UN STRING CON UNA VARIABLE DE NOMBRE 'BIRD' QUE TIENE EL VALOR "TWEET"

Carlos Garcia

4. Dada la variable mensaje = "Hola Luke yo soy tu padre!" utiliza slice para extraer la secuencia "Luke"
    val mensaje = "Hola Luke yo soy tu padre!"
    println(mensaje.slice(5, 9))

scala> val mensaje = "Hola Luke yo soy tu padre!"
val mensaje: String = Hola Luke yo soy tu padre!

scala> println(mensaje.slice(5, 9))
Luke



5. Cual es la diferencia entre value (val) una variable (var) en scala?
    val es inmutable, var es mutable
    val inmutable = "No puedo cambiar"
    var mutable = "Puedo cambiar"
    mutable = "He cambiado"

6. Dada la tupla (2, 4, 5, 1, 2, 3.1416, 3, 7) imprime "3.1416"
    val tupla = (2, 4, 5, 1, 2, 3.1416, 3, 7)
    println(tupla._6)

    se puso el texto en la terminal para guardar en memoria las variables
    despues se realizo la consulta con el printl

scala> val tupla = (2, 4, 5, 1, 2, 3.1416, 3, 7)
val tupla: (Int, Int, Int, Int, Int, Double, Int, Int) = (2,4,5,1,2,3.1416,3,7)

scala> println(tupla._6)
3.1416

