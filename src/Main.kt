fun getnumber():Int{ // Función encargada de conseguir le número aleatorio
    var n = (1..9).toList()
    n = n.shuffled() // Mezcla los números de la lista
    println()
    var numSecreto = ""

    for (i in 0 until 4){ // Escoje solo los 4 primeros números de la lista
        numSecreto+=n[i]
    }

    return numSecreto.toInt()
}

fun comprobacion(entrada:String, numero:String) {
    for (i in 0..3) {
        if (entrada[i] == numero[i]) { // Totalemente correctos (acertados)
            print("Numeros acertados: ")
            println(entrada[i])
        }
    }

    for (i in 0..2) { // Coicidentes
        for (k in i+1..3) { // No misma posición
            if (entrada[i] == numero[k]) {
                print("Numeros coincidentes: ")
                println(entrada[i])
            }
        }
    }
}

fun iniciar_juego() { // Función encargada de ejecutar el juego
    val number = getnumber()
    var maxIntentos = 3
    var intentos = maxIntentos

    while (intentos != -1) {
        print("Teclee un número de 4 cifras sin números repetidos: ")
        var entrada = readln().toInt() // Número adivinado por el usuario

        // Forzar que sean 4 números
        if (entrada.toString().length != 4) {
            print("El número es inválido. Por favor, inténtelo de nuevo.")
            println()
        } else {
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

fun ultimo_intento(){ // Función encargada de almazenar y entregar el último resultado

}

fun main(){
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
            1 -> iniciar_juego() // Invoca la función juego
            2 -> ultimo_intento()
            3 -> juego = 0
        }
    }
}