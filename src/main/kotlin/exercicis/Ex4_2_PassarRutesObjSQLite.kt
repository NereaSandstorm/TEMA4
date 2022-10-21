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
            st.executeUpdate("INSERT INTO RUTES VALUES ($contador, '$nombre')")
            e.mostrarRuta()
            contador++

        }
    } catch (eof: EOFException) {
        read.close()

    }


    st.close()
    con.close()




    val st2 = con.createStatement()
}