package exercicis.EX3_2

import exercicis.EX3_2.Ruta
import java.io.EOFException
import java.io.FileInputStream
import java.io.ObjectInputStream

fun main(args: Array<String>) {
    var read = ObjectInputStream(FileInputStream("src/main/kotlin/exercicis/Rutes.obj"))

    try {
        while (true) {
            val e = read.readObject() as Ruta
            e.mostrarRuta()
        }
    } catch (eof: EOFException) {
        read.close()

    }
}