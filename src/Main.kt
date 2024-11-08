import java.io.File

// Códigos de colores (fondos + color de letra)
const val RESET = "\u001B[0m"
const val BG_GREEN = "\u001B[42m"
const val BG_YELLOW = "\u001B[43m"
const val BG_WHITE = "\u001B[47m"
const val BLACK = "\u001B[30m"

// Variables Globales
val intentosBase = 3 // CAMBIAR NUMERO DE INTENTOS (Siempre es 1 más del que ponemos)
val intentosMax= intentosBase+1 // INTENTOS MAX DE VERDAD

fun getnumber():Int{ // Función encargada de conseguir el número aleatorio
    var n = (1..6).toList()
    n = n.shuffled() // Mezcla los números de la lista

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

fun comprobacion(entrada:String, numeroSecret:String) { // Función encargada de comprobar los num correctos/coincidentes
    var aciertos = 0
    var coincidencias = 0

    val noAcierto = mutableListOf<Char>() // Una vez comprobados, almacenaremos los números que NO son aciertos

    for (i in 0 until intentosMax) {
        if (entrada[i] == numeroSecret[i]) { // Misma posicion (ACERTADO)
            aciertos++
        } else {
            noAcierto.add(numeroSecret[i])
        }
    }

    for (i in 0 until intentosMax) {
        if ((numeroSecret[i] != entrada[i]) && noAcierto.contains(entrada[i])) { // No misma posición (COINCIDENTE)
            coincidencias++
            noAcierto.remove(entrada[i]) // Lo eliminamos para evitar que se vuelva a comprobar
        }
    }

    print("${BG_GREEN} $aciertos " + "${RESET} " + "${BG_YELLOW} $coincidencias " + "${RESET}") // Color e impresión
}

    fun iniciar_juego() { // Función encargada de ejecutar el juego
        val number = getnumber()
        var Countdown = intentosBase
        val file = File("Numero_Almacenado.txt")

        println("\n- - JUEGO - -")

        while (Countdown != -1) {
            print("Teclee un número de 4 cifras sin números repetidos: ")
            val entradaString = readln() // Número adivinado por el usuario
            val entrada = entradaString.toInt()

            var validezTamanho = false
            var validezDigito = true

            // Forzar que sean 4 números
            if (entrada.toString().length != 4) {
                println("El número es inválido. Por favor, inténtelo de nuevo.")
                println("- - - - - -")
            } else {
                validezTamanho = true
                for (digito in entradaString) {
                    if (digito !in '1'..'6') { // Forzar que ninguno sea mayor que 6 o menor que 1
                        validezDigito = false
                    } else if (validezDigito==false) {
                        break
                    }
                }
            }

            if (validezDigito == false) {
                println("El número es inválido. Por favor, asegúrese de que cada uno de los digitos es menor que 6 e inténtelo de nuevo.")
                println("- - - - - -")
            } else if (validezTamanho == true) {
                file.appendText(entrada.toString() + "\n") // Añade una línea de texto nueva si es un número válido
                println()

                when {
                    entrada == number -> { // Si la entrada coincide con el numero esperado
                        print("${BLACK}${BG_WHITE} $entrada " + "${RESET} ")
                        comprobacion(entrada.toString(), number.toString())
                        println()
                        println("Enhorabuena, has adivinado el número.")
                    }

                    (entrada != number && Countdown >= 1) -> { // Si no sucede pero aún hay intentos restantes
                        print("${BLACK}${BG_WHITE} $entrada " + "${RESET} ")
                        comprobacion(entrada.toString(), number.toString())
                        println()
                        println("Lo siento, no adivinaste el número secreto.")
                        println("Intentos restantes: $Countdown")
                        println("- - - - - -")
                    }

                    (entrada != number && Countdown == 0) -> { // Se han terminado los intentos
                        print("${BLACK}${BG_WHITE} $entrada " + "${RESET} ")
                        comprobacion(entrada.toString(), number.toString())
                        println()
                        println("Lo siento, no adivinaste el número secreto $number en $intentosMax intentos.")
                        println("-- FIN DEL JUEGO --")
                        println()
                    }
                }
                Countdown -= 1
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

    fun ultimo_intento() { // Función encargada de almazenar y entregar el último resultado
        val file = File("Numero_Almacenado.txt") // Localiza el archivo
        var countup = 1

        println("\n- - - - LOGS - - - -")

        if (file.exists()) {
            println("Número secreto: " + leerlinea(1))

            // Lee el archivo
            for (i in 2..intentosBase+2) { // Leeremos el archivo linea por linea
                val data1 = leerlinea(i)
                println("Intento $countup: " + data1)
                countup += 1
                }
            println("- - - LOGS END - - -\n")
        } else { // Ya que eliminamos el archivo siempre que se cierre el juego
            println("No ha habido intentos anteriores en esta sesión.\n")
            println("- - - LOGS END - - -\n")
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
            print("Opción: ")

            val opcion = readln().toInt()

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