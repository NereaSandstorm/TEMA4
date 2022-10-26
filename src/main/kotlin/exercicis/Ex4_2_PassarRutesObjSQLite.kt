package exercicis

import exercicis.EX3_2.Ruta
import java.io.EOFException
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.sql.DriverManager

fun main(args: Array<String>) {
    val url = "jdbc:sqlite:Rutes.sqlite"
    val con = DriverManager.getConnection(url)
    val st = con.createStatement()

    var read = ObjectInputStream(FileInputStream("src/main/kotlin/exercicis/Rutes.obj"))
    var contador = 1

    try {
        while (true) {
            val e = read.readObject() as Ruta
            var nombre = e.nom
            var desnivell= e.desnivell
            var desnivellac=e.desnivellAcumulat
            var contaPuntos = 1
//            var sentencia="INSERT INTO RUTES VALUES ($contador, '$nombre', $desnivell, $desnivellac)"
//            println(sentencia)
            st.executeUpdate("INSERT INTO RUTES VALUES ($contador, '$nombre', $desnivell, $desnivellac)")
            for (p in e.llistaDePunts) {
                var nomPunt = p.nom
                var longitud = p.coord.longitud
                var latitud = p.coord.latitud
//                var sentencia2="INSERT INTO PUNTS VALUES ($contador, $contaPuntos, '$nomPunt', $latitud, $longitud)"
//                println(sentencia2)
                st.executeUpdate("INSERT INTO PUNTS VALUES ($contador, $contaPuntos, '$nomPunt', $latitud, $longitud)")
                contaPuntos++
            }



            contador++

        }
    } catch (eof: EOFException) {
        read.close()

    }


    st.close()
    con.close()




}