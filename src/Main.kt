import java.io.File

fun getnumber():Int{ // Función encargada de conseguir le número aleatorio
    var n = (1..6).toList()
    n = n.shuffled() // Mezcla los números de la lista
    println()
    var numSecreto = ""

    for (i in 0 until 4){ // Escoje solo los 4 primeros números de la lista
        numSecreto+=n[i]
    }

    //Archivo de memoria
    var fileName = "Numero_Almacenado.txt"
    val file = File(fileName)

    file.writeText(numSecreto + "\n")

    return numSecreto.toInt() // Devuelve el número secreto
}

fun comprobacion(entrada:String, numero:String) { // Re-hacer
    var acertados = ""
    var coincidentes = ""

    for (i in 0..2) { // Coicidentes
        for (k in i + 1..3) { // No misma posición
            if (entrada[i] == numero[k]) {
                coincidentes += entrada[i] + " "
            }
        }
        if (i > 0) {
            for (k in i - 1..3) {
                if (entrada[i] == numero[k]) {
                    coincidentes += entrada[i].toString() + " "
                }
            }
        }
    }

    if (coincidentes=="") {
        print("|| Numeros coincidentes: " + 0 + "|| ")
    } else { print("|| Numeros coincidentes: " + coincidentes + "|| ") }

    for (i in 0..3) {
        if (entrada[i] == numero[i]) { // Totalemente correctos (acertados)
            acertados += entrada[i].toString() + " "
        }
    }

    if (acertados=="") {
        print("Numeros acertados: " + 0 )
    } else { print("Numeros acertados: " + acertados)}
}

    fun iniciar_juego() { // Función encargada de ejecutar el juego
        val number = getnumber()
        var maxIntentos = 3
        var intentos = maxIntentos
        val file = File("Numero_Almacenado.txt")

        println("- - JUEGO - -")

        while (intentos != -1) {
            print("Teclee un número de 4 cifras sin números repetidos: ")
            var entrada = readln().toInt() // Número adivinado por el usuario

            // Forzar que sean 4 números
            if (entrada.toString().length != 4) {
                print("El número es inválido. Por favor, inténtelo de nuevo.")
                println()
            } else {
                file.appendText(entrada.toString() + "\n") // Añade una línea de texto nueva si es un número válido
                println()

                when {
                    entrada == number -> { // Si la entrada coincide con el numero esperado
                        print("$entrada ")
                        comprobacion(entrada.toString(), number.toString())
                        println()
                        println("Enhorabuena, has adivinado el número.")
                    }

                    (entrada != number && intentos >= 1) -> { // Si no sucede pero aún hay intentos restantes
                        print("$entrada ")
                        comprobacion(entrada.toString(), number.toString())
                        println()
                        println("Lo siento, no adivinaste el número secreto.")
                        println("Intentos restantes: $intentos")
                        println("- - - - - -")
                    }

                    (entrada != number && intentos == 0) -> { // Se han terminado los intentos
                        print("$entrada ")
                        comprobacion(entrada.toString(), number.toString())
                        println()
                        println("Lo siento, no adivinaste el número secreto $number en $maxIntentos intentos.")
                        println("-- FIN DEL JUEGO --")
                        println()
                    }
                }
                intentos -= 1
            }
        }
    }

fun leerlinea(linea:Int):String?{
    val lineas = File("Numero_Almacenado.txt").readLines()

    // Verificar si la línea existe
    return if (linea in 1..lineas.size) {
        lineas[linea - 1] // Kotlin usa índices base 0
    } else {
        null // Si la línea no existe
    }
}

    fun ultimo_intento() { // Función encargada de almazenar y entregar el último resultado
        val file = File("Numero_Almacenado.txt") // Localiza el archivo
        val intentos = 3
        var countup = 1

2
        if (file.exists()) {
            // Lee el archivo
            println("Número secreto: " + leerlinea(1))

            for (i in 2..intentos+2) {
                val data1 = leerlinea(i)
                println("Intento $countup: " + data1)
                countup += 1
                }
        } else {
            println("No ha habido intentos anteriores en esta sesión.")
        }
    }

    fun main() {
        var juego = 1

        while (juego != 0) {
            // Menú
            println("- - - - - -")
            println("1. Jugar")
            println("2. Ver traza de último intento")
            println("3. Salir")
            print("opción: ")

            var opcion = readln().toInt()

            println("- - - - - -")

            when (opcion) {
                1 -> iniciar_juego() // Invoca la función que ejecuta el juego
                2 -> ultimo_intento() // Invoca la función que lee el archivo cuyo contenido es el número anterior
                3 -> {
                    juego = 0
                    val file = File("Numero_Almacenado.txt")
                    file.delete()
                }
            }
        }
    }
