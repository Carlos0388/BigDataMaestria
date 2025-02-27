// def isEven(num:Int): Boolean = {
//     return num%2 == 0
// }
// def isEven(num:Int): num%2 == 0
// println(isEven(6))
// println(isEven(3))
// Practice 3, analyse the following code with your own words
//------------------------------------

//Este codigo lo que hace es que recorre una lista de enteros y determina si cada número es par o impar. Luego, se aplica esta función a dos listas diferentes
//La función listEvens toma una lista de enteros (List[Int]) como parámetro y devuelve una cadena (String).
//Condicional if-else para determinar si un número es par o impar
//Basandose en el residuo de la operacion, n % 2 == 0 verifica si el número n es par (el residuo de la división entre n y 2 es 0).
//

def listEvens(list:List[Int]): String ={
    for(n <- list){
        if(n%2==0){
            println(s"$n is even")
        }else{
            println(s"$n is odd")
        }
    }
    return "Done"
}

val l = List(1,2,3,4,5,6,7,8)
val l2 = List(4,3,22,55,7,8)

listEvens(l)
    1 is odd
    2 is even
    3 is odd
    4 is even
    5 is odd
    6 is even
    7 is odd
    8 is even

listEvens(l2)
    4 is even
    3 is odd
    22 is even
    55 is odd
    7 is odd
    8 is even


//------------------------------------
//3 7 afortunado

//Recorre una lista de enteros y suma sus valores. Sin embargo, si encuentra el número 7, lo considera "afortunado" y suma 14 en lugar de 7. 
//Finalmente, se llama a la función con una lista de ejemplo y se imprime el resultado.
//La función afortunado toma una lista de enteros (List[Int]) como parámetro y devuelve un entero (Int).
//Se declara una variable mutable res (resultado) inicializada en 0. Esta variable acumulará la suma de los valores de la lista.
//Si el número n es igual a 7, se suma 14 a res (considerando el número como "afortunado").
//Si el número n no es 7, se suma su valor normal a res.

def afortunado(list:List[Int]): Int={
    var res=0
    for(n <- list){
        if(n==7){
            res = res + 14
        }else{
            res = res + n
        }
    }
    return res
}

val af= List(1,7,7)
println(afortunado(af))

    29

//Carlos Garcia
//-------------------------------------
def balance(list:List[Int]): Boolean={
    var primera = 0
    var segunda = 0

    segunda = list.sum

    for(i <- Range(0,list.length)){
        primera = primera + list(i)
        segunda = segunda - list(i)

        if(primera == segunda){
            return true
        }
    }
    return false 
}

val bl = List(3,2,1)
val bl2 = List(2,3,3,2)
val bl3 = List(10,30,90)

balance(bl)
balance(bl2)
balance(bl3)

//Terminal

scala> def balance(list:List[Int]): Boolean={
     |     var primera = 0
     |     var segunda = 0
     | 
     |     segunda = list.sum
     | 
     |     for(i <- Range(0,list.length)){
     |         primera = primera + list(i)
     |         segunda = segunda - list(i)
     | 
     |         if(primera == segunda){
     |             return true
     |         }
     |     }
     |     return false 
     | }
def balance(list: List[Int]): Boolean

scala> val bl = List(3,2,1)
val bl: List[Int] = List(3, 2, 1)

scala> val bl2 = List(2,3,3,2)
val bl2: List[Int] = List(2, 3, 3, 2)

scala> val bl3 = List(10,30,90)
val bl3: List[Int] = List(10, 30, 90)

scala> balance(bl)
val res7: Boolean = true

scala> balance(bl2)
val res8: Boolean = true

scala> balance(bl3)
val res9: Boolean = false

// Observaciones
// el codigo verifica el balance de una lista de enteros y regresa un valor boleano,
// si exite un punto en la lista donde se suma los elementos de la lista hasta ese punto y 
// se resta los elementos a la izquierda es igual a la suma de los elementos de la derecha
// para el primer resultado es true ya que 3+2 = 5 y 1 = 1
// para el segundo resultado es true ya que 2+3 = 5 y 3+2 = 5
// para el tercer resultado es false ya que 10+30 = 40 y 90 = 90 por lo que no existe un punto de balance en la lista




//--------------------------------------
def palindromo(palabra:String):Boolean ={
    return (palabra == palabra.reverse)
}

val palabra = "OSO"
val palabra2 = "ANNA"
val palabra3 = "JUAN"

println(palindromo(palabra))
println(palindromo(palabra2))
println(palindromo(palabra3))

//Terminal
scala> def palindromo(palabra:String):Boolean ={
     |     return (palabra == palabra.reverse)
     | }
def palindromo(palabra: String): Boolean

scala> val palabra = "OSO"
val palabra: String = OSO

scala> val palabra2 = "ANNA"
val palabra2: String = ANNA

scala> val palabra3 = "JUAN"
val palabra3: String = JUAN

scala> println(palindromo(palabra))
true

scala> println(palindromo(palabra2))
true

scala> println(palindromo(palabra3))
false

//Observaciones
//lo que hace el codigo es verificar si la cadena de conectores el string es un palindromo
//es decir si se lee igual de izquierda a derecha que de derecha a izquierda y lo regresa en un booleano
//si la palabra es igual en lectura en ambas direcciones es palindromo y regresa true si no es palindromo lo regresa en false
