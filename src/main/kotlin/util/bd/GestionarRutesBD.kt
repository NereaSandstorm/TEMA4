package util.bd

import exercicis.EX3_2.Ruta
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement


class GestionarRutesBD {
    var urlRutes = ""
    var st1 :Statement
    var st2 :Statement
    val con :Connection

    init {
        this.urlRutes = "jdbc:sqlite:Rutes.sqlite"
        this.con = DriverManager.getConnection(this.urlRutes)
        this.st1 = con.createStatement()
        this.st2 = con.createStatement()

    }

    fun close() {
        con.close()
    }

    fun inserir(r: Ruta) {
        val ultimoNUmeroRutes = "SELECT MAX(num_r) FROM RUTES"
        val rsruta = st1.executeQuery(ultimoNUmeroRutes)
        val num_r = rsruta.getInt(1) + 1
        println(num_r)
        val nomR = r.nom
        val desnivell = r.desnivell
        val desnac = r.desnivellAcumulat
        val pr= "INSERT INTO RUTES VALUES ($num_r, '$nomR', $desnivell, $desnac)"
        st1.executeUpdate("INSERT INTO RUTES VALUES ($num_r, '$nomR', $desnivell, $desnac)")
        val ultimoNUmeroPunts= "SELECT MAX(num_p) FROM PUNTS"
        val rspunts = st1.executeQuery(ultimoNUmeroPunts)
        var num_p = rspunts.getInt(1)

        for (r in r.llistaDePunts) {
            num_p++
            val nom_p = r.nom
            val latitud = r.coord.latitud
            val longitud = r.coord.longitud

//            val pr2 = "INSERT INTO PUNTS VALUES ($num_r, '$num_p', $nom_p, $latitud, $longitud)"
            st2.executeUpdate("INSERT INTO PUNTS VALUES ($num_r, '$num_p', $nom_p, $latitud, $longitud)")

//            println(pr2)
        }

//        fun buscar(i: Int): Ruta {
//
//        }



    }

}