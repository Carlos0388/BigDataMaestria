// Assessment 1 / Practica 1
// 1. Implementa un programa en sacala que calcule el radio de in circulo
// 2. Implementa un programa en scala que me diga si un numero es primo
// 3. Dada la variable bird = "tweet", utiliza interpolacion de strings para imprimir "Estoy escribiendo un tweet"
// 4. Dada la variable mensaje = "Hola Luke yo soy tu padre!" utiliza slice para extraer la secuencia "Luke"
// 5. Cual es la diferencia entre value (val) una variable (var) en scala?
// 6. Dada la tupla (2,4,5,1,2,3.1416,3,7) imprime "3.1416"

    // 1. Implementa un programa en scala que calcule el radio de un circulo
    def calcularRadio(diametro: Double): Double = diametro / 2
    println(s"El radio de un círculo con diámetro 10 es: ${calcularRadio(10)}")

    // 2. Implementa un programa en scala que me diga si un numero es primo
    def esPrimo(numero: Int): Boolean = {
      if (numero <= 1) false
      else !(2 until numero).exists(x => numero % x == 0)
    }
    println(s"El número 7 es primo: ${esPrimo(7)}")

    // 3. Dada la variable bird = "tweet", utiliza interpolacion de strings para imprimir "Estoy escribiendo un tweet"
    val bird = "tweet"
    println(s"Estoy escribiendo un $bird")

    // 4. Dada la variable mensaje = "Hola Luke yo soy tu padre!" utiliza slice para extraer la secuencia "Luke"
    val mensaje = "Hola Luke yo soy tu padre!"
    println(mensaje.slice(5, 9))

    // 5. Cual es la diferencia entre value (val) una variable (var) en scala?
    // val es inmutable, var es mutable
    val inmutable = "No puedo cambiar"
    var mutable = "Puedo cambiar"
    mutable = "He cambiado"

    // 6. Dada la tupla (2, 4, 5, 1, 2, 3.1416, 3, 7) imprime "3.1416"
    val tupla = (2, 4, 5, 1, 2, 3.1416, 3, 7)
    println(tupla._6)
// END SOLUTION
