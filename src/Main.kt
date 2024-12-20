import java.io.File

// Códigos de colores (fondos + color de letra)
const val RESET = "\u001B[0m"
const val BG_GREEN = "\u001B[42m"
const val BG_YELLOW = "\u001B[43m"
const val BG_WHITE = "\u001B[47m"
const val BLACK = "\u001B[30m"

// Variables Globales
val intentosBase = 5 // CAMBIAR NUMERO DE INTENTOS
val cifraNum = 4 // CIFRAS DEL NUMERO
val file = File("Numero_Almacenado.txt")

fun getnumber():Int{ // Función encargada de conseguir el número aleatorio
    val n = (1..6).toList().shuffled() // Mezcla los números de la lista

    var numSecreto = ""

    for (i in 0 until cifraNum){ // Escoge solo los 4 primeros números de la lista
        numSecreto+=n[i]
    }

    //Archivo de memoria
    file.writeText(numSecreto + "\n")

    return numSecreto.toInt() // Devuelve el número secreto
}

fun comprobacion(entrada:String, numeroSecret:String) { // Función encargada de comprobar los num correctos/coincidentes
    var aciertos = 0
    var coincidencias = 0

    for (i in 0 until cifraNum) {
        if (entrada[i] == numeroSecret[i]) { // Misma posicion (ACERTADO)
            aciertos++
        } else {
            if (numeroSecret.contains(entrada[i])){
                coincidencias++
            }
        }
    }

    print("${BG_GREEN} $aciertos " + "${RESET} " + "${BG_YELLOW} $coincidencias " + "${RESET}") // Color e impresión
}


    fun iniciar_juego() { // Función encargada de ejecutar el juego
        val number = getnumber()
        var countdown = intentosBase-1

        println("\n- - JUEGO - -")

        while (countdown != -1) {
            print("Asegurándose de que cada dígito esté entre el 0 y el 7 (ambos excluídos), teclee un número de $cifraNum cifras sin números repetidos: ")
            val entradaString = readln() // Número adivinado por el usuario

            var entrada = 0

            if (entradaString.isBlank() || (entradaString.toIntOrNull() == null)) { // Nos aseguramos de que la entrada no esté vacía o de que no sea una letra
            } else {
                entrada = entradaString.toInt()
            }

            var valideztamanho = true
            var validezDigito = true
            val digitosUnicos = entradaString.toSet() // No permite números repetidos

            if (entradaString.length!=cifraNum){ // Comprueba el tamaño del String
                valideztamanho = false
            } else {
                for (digito in entradaString) {
                    if (digito !in '1'..'6') { // Forzar que ninguno sea mayor que 6 o menor que 1
                        validezDigito = false
                        break
                    }
                }
            }

            if ((digitosUnicos.size != cifraNum) || (valideztamanho == false)) {
                println("El número es inválido. Por favor, inténtelo de nuevo.")
                println("- - - - - -")
            } else if (validezDigito != true) {
                    println("El número es inválido. Por favor, asegúrese de que cada uno de los digitos es menor que 7 y mayor que 0 e inténtelo de nuevo.")
                    println("- - - - - -")
                } else {
                    file.appendText(entrada.toString() + "\n") // Añade una línea de texto nueva si es un número válido
                    println("  NM    A   C")

                    when {
                        entrada == number -> { // Si la entrada coincide con el numero esperado
                            print("${BLACK}${BG_WHITE} $entrada " + "${RESET} ")
                            comprobacion(entrada.toString(), number.toString())
                            println()
                            println("Enhorabuena, has adivinado el número.")
                            break
                        }

                        (countdown >= 1) -> { // Si no sucede pero aún hay intentos restantes
                            print("${BLACK}${BG_WHITE} $entrada " + "${RESET} ")
                            comprobacion(entrada.toString(), number.toString())
                            println()
                            println("Lo siento, no adivinaste el número secreto.")
                            println("Intentos restantes: $countdown")
                            println("- - - - - -")
                        }

                        (countdown == 0) -> { // Se han terminado los intentos
                            print("${BLACK}${BG_WHITE} $entrada " + "${RESET} ")
                            comprobacion(entrada.toString(), number.toString())
                            println()
                            println("Lo siento, no adivinaste el número secreto $number en $intentosBase intentos.")
                            println("-- FIN DEL JUEGO --")
                            println()
                        }
                    }
                    countdown -= 1
                }
            }


        }

fun leerlinea(linea:Int):String{
    val lineas = file.readLines()

    // Verificar si la línea existe

    return if(linea in 1..intentosBase+2 && linea <= lineas.size) {
        lineas[linea - 1] // Ya que Kotlin inicia la cuenta en 0
    } else {
        "No existe." // Si la línea no existe
    }
}

    fun ultimo_intento() { // Función encargada de almazenar y entregar el último resultado
        var countup = 1

        println("\n- - - - LOGS - - - -")

        if (file.exists()) { // Localiza el archivo
            println("Número secreto: " + leerlinea(1))

            // Lee el archivo
            for (i in 2..intentosBase+1) { // Leeremos el archivo linea por linea
                val data1 = leerlinea(i)
                println("Intento $countup: $data1")
                countup += 1
                }
            println("- - - LOGS END - - -\n")
        } else { // Ya que eliminamos el archivo siempre que se cierre el juego
            print("No ha habido intentos anteriores en esta sesión.\n")
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

            val entrada = readln()

            if (entrada != "1" && entrada != "2" && entrada != "3") {
                print("- - - - - -")
                println("\nERROR. Introduzca una opción correcta.")
            } else {
                val opcion = entrada.toInt()

                print("- - - - - -")

                when (opcion) {
                    1 -> {
                        println()
                        iniciar_juego() // Invoca la función que ejecuta el juego
                    }

                    2 -> {
                        println()
                        ultimo_intento() // Invoca la función que lee el archivo cuyo contenido es el número anterior
                    }

                    3 -> {
                        print(" - - - - ")
                        println(
                            "\nGracias por jugar!\n- - - - - - - - - -" +
                                    ""
                        )
                        juego = 0
                        file.delete()
                    }
                }
            }
        }
    }