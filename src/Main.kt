import java.io.File

// Códigos de colores (fondos + color de letra)
const val RESET = "\u001B[0m"
const val BG_GREEN = "\u001B[42m"
const val BG_YELLOW = "\u001B[43m"
const val BG_WHITE = "\u001B[47m"
const val BLACK = "\u001B[30m"

fun getnumber():Int{ // Función encargada de conseguir el número aleatorio
    var n = (1..6).toList()
    n = n.shuffled() // Mezcla los números de la lista
    println()
    var numSecreto = ""

    for (i in 0 until 4){ // Escoge solo los 4 primeros números de la lista
        numSecreto+=n[i]
    }

    //Archivo de memoria
    val fileName = "Numero_Almacenado.txt"
    val file = File(fileName)

    file.writeText(numSecreto + "\n")

    return numSecreto.toInt() // Devuelve el número secreto
}

fun comprobacion(entrada:String, numeroSecret:String) { // Re-hacer
    var aciertos = 0
    var coincidencias = 0

    val noAcierto = mutableListOf<Char>() // Una vez comprobados, almacenaremos los números que NO son aciertos

    for (i in 0 until 4) {
        if (entrada[i] == numeroSecret[i]) { // Misma posicion (ACERTADO)
            aciertos++
        } else { noAcierto.add(numeroSecret[i]) }
    }

    for (i in 0 until 4) { // No misma posición (COINCIDENTE)
        if ((numeroSecret[i] != entrada[i]) && noAcierto.contains(entrada[i])) {
            coincidencias++
            noAcierto.remove(entrada[i]) // Lo eliminamos para evitar que se vuelva a comprobar
        }
    }

    print("${BG_GREEN} $aciertos " + "${RESET} " + "${BG_YELLOW} $coincidencias " + "${RESET}") // Color e impresión
}

    fun iniciar_juego(maxIntentos:Int) { // Función encargada de ejecutar el juego
        val number = getnumber()
        var intentos = maxIntentos
        val file = File("Numero_Almacenado.txt")

        println("- - JUEGO - -")

        while (intentos != -1) {
            print("Teclee un número de 4 cifras sin números repetidos: ")
            val entrada = readln().toInt() // Número adivinado por el usuario

            // Forzar que sean 4 números
            if (entrada.toString().length != 4) {
                print("El número es inválido. Por favor, inténtelo de nuevo.")
                println()
            } else {
                file.appendText(entrada.toString() + "\n") // Añade una línea de texto nueva si es un número válido
                println()

                when {
                    entrada == number -> { // Si la entrada coincide con el numero esperado
                        print("${BLACK}${BG_WHITE} $entrada " + "${RESET} ")
                        comprobacion(entrada.toString(), number.toString())
                        println()
                        println("Enhorabuena, has adivinado el número.")
                    }

                    (entrada != number && intentos >= 1) -> { // Si no sucede pero aún hay intentos restantes
                        print("${BLACK}${BG_WHITE} $entrada " + "${RESET} ")
                        comprobacion(entrada.toString(), number.toString())
                        println()
                        println("Lo siento, no adivinaste el número secreto.")
                        println("Intentos restantes: $intentos")
                        println("- - - - - -")
                    }

                    (entrada != number && intentos == 0) -> { // Se han terminado los intentos
                        print("${BLACK}${BG_WHITE} $entrada " + "${RESET} ")
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
        lineas[linea - 1] // Ya que Kotlin inicia la cuenta en 0
    } else {
        null // Si la línea no existe
    }
}

    fun ultimo_intento(maxIntentos: Int) { // Función encargada de almazenar y entregar el último resultado
        val file = File("Numero_Almacenado.txt") // Localiza el archivo
        var countup = 1

        if (file.exists()) {
            // Lee el archivo
            println("Número secreto: " + leerlinea(1))

            for (i in 2..maxIntentos+2) { // Leeremos el archivo linea por linea
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
        val maxIntentos = 3 // CAMBIAR NUMERO MAXIMO DE INTENTOS

        while (juego != 0) {
            // Menú
            println("- - - - - -")
            println("1. Jugar")
            println("2. Ver traza de último intento")
            println("3. Salir")
            print("opción: ")

            val opcion = readln().toInt()

            println("- - - - - -")

            when (opcion) {
                1 -> iniciar_juego(maxIntentos) // Invoca la función que ejecuta el juego
                2 -> ultimo_intento(maxIntentos) // Invoca la función que lee el archivo cuyo contenido es el número anterior
                3 -> {
                    juego = 0
                    val file = File("Numero_Almacenado.txt")
                    file.delete()
                }
            }
        }
    }
