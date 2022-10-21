package exercicis.EX3_2

import java.io.DataInputStream
import java.io.EOFException
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectOutputStream

fun main(args: Array<String>) {
    val f = DataInputStream(FileInputStream("src/main/kotlin/exercicis/Rutes.dat"))
    var numPuntos = 0
    var nom: String=""
    var desnivell: Int=0
    var desnivellAcumulat: Int=0
    var objeto = ObjectOutputStream(FileOutputStream("src/main/kotlin/exercicis/Rutes.obj"))

    try {
        while (f.available() > 0) {
            var lista: MutableList<PuntGeo> = mutableListOf()
            nom = f.readUTF()
            desnivell = f.readInt()
            desnivellAcumulat = f.readInt()
            numPuntos = f.readInt()
            for (i in 1..numPuntos) {
                var nombre = f.readUTF()
                var coordenadas = Coordenadas(f.readDouble(), f.readDouble())
                var punto = PuntGeo(nombre, coordenadas)
                lista.add(punto)
            }
            var ruta = Ruta(nom, desnivell, desnivellAcumulat, lista)
            objeto.writeObject(ruta)
            ruta.mostrarRuta()

        }
    } catch (eof: EOFException) {
        f.close()
        objeto.close()
    }




}