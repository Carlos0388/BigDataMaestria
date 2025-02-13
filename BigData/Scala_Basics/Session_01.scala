//100 spark-shell
//2.5
//1+1
//2*5
// Exponentials
math.pow(4, 2)
(1 + 2) * (3 + 4)

// feet to meters
val feet = 10
3*0.3048

// Variables in scala
// Values (val) are inmutable, once they assigned they
// cannot be changed
// Variables (var) can be reassigned.
// Note, when reassigning you must used the same data type!

// Data Types
// Int
// Double
// String
// Boolean
// Collenctions

// val <name> : <type> = <literal> value
// var <name> : <type> = <literal> value

var myVar: Int = 10
val myVal: Double = 2.5

// Can not do this val 23myString my.String

// Booleans and comparison operators
true
false
1 > 2 // false
1 < 2 // true
1 <= 30 // true
2 == 2 // true
1 >= 2 // false
2 != 4 // true

4 % 2 // 0
5 % 2 // 1
6 % 2 // 0

//Strings
println("Hello World")

val greeting = "Hello " + "there!"
println(greeting) // prints "Hello there!"
"dance"*10

val st = "Hello"
val name = "Carlos"
val greet = s"${st} ${name}"
println(greet) // prints "Hello Carlos"
val greet2 = s"$st $name"
println(greet2) // prints "Hello Carlos"

printf("A string %s, an integer %d, a float %f", st, 10, 3.1416)
// prints "A string Hello, an integer 10, a float 3.141600"
printf("A float %1.2f", 1.2345)
// prints "A float 1.23"

val st = "This is a long string"
st.charAt(0)
st.charAt(3)
st.indexOf("a")
st slice (0,4)
st slice (10, 14)
// Grab the word long of st
val word = st.split("long")
println(word) // prints "This is a "