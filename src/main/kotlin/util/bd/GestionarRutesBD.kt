package util.bd

import exercicis.EX3_2.Coordenadas
import exercicis.EX3_2.PuntGeo
import exercicis.EX3_2.Ruta
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement
import java.util.LinkedList

class GestionarRutesBD {
    private var url=""
    var con : Connection
    var st : Statement
    var st2 : Statement


    init {
        this.url= "jdbc:sqlite:Rutes.sqlite"
        this.con= DriverManager.getConnection(url)
        this.st= con.createStatement()
        this.st2= con.createStatement()

        val sentql= "CREATE TABLE IF NOT EXISTS RUTES (" +
                "num_r INTEGER CONSTRAINT cp_numruta PRIMARY KEY, " +
                "nom_r TEXT, " +
                "desn INTEGER, " +
                "desn_ac INTEGER " +
                ")"

        val sentql2= "CREATE TABLE IF NOT EXISTS PUNTS (" +
                "num_r INTEGER, " +
                "num_p INTEGER, " +
                "nom_p TEXT, " +
                "latitud REAL, " +
                "longitud REAL, " +
                "CONSTRAINT cp_punts PRIMARY KEY (num_r,num_p), " +
                "CONSTRAINT ce_arutes FOREIGN KEY (num_r) REFERENCES RUTES" +
                ")"

        st.executeUpdate(sentql)
        st.executeUpdate(sentql2)

    }

    fun close() {
        con.close()
    }

    fun inserir(r: Ruta) {
        val sentencia = "SELECT MAX(num_r) FROM RUTES"
        val rs = st2.executeQuery(sentencia)
        var numeromax = rs.getInt(1) + 1
        val nomr = r.nom
        val desn = r.desnivell
        val desnac= r.desnivellAcumulat
        st.executeUpdate("INSERT INTO RUTES VALUES ($numeromax, '$nomr', $desn, $desnac)")
        var contador = 0

        for (e in r.llistaDePunts) {
            contador++
            val nomp=e.nom
            val lat = e.coord.latitud
            val longi = e.coord.longitud
            st.executeUpdate("INSERT INTO PUNTS VALUES ($numeromax, $contador, '$nomp', $lat, $longi)")

        }

        st2.close()
        st.close()
    }

    fun buscar(e: Int):Ruta {
        val sentencia = "SELECT * FROM RUTES WHERE num_r = $e"
        val rs = st.executeQuery(sentencia)
        val sentencia2 = "SELECT * FROM PUNTS WHERE num_r = $e"
        val rs2 = st2.executeQuery(sentencia2)
        var ruta: Ruta
        var llista= mutableListOf<PuntGeo>()

        while (rs2.next()) {
            var punt = PuntGeo(rs2.getString(3), Coordenadas(rs2.getFloat(4).toDouble(), rs2.getFloat(5).toDouble()))
            llista.add(punt)
        }

        ruta = Ruta(rs.getString(2), rs.getInt(3), rs.getInt(4), llista)
        return ruta

    }

    fun llistat(): ArrayList<Ruta> {
        val sentencia = "SELECT * FROM RUTES"
        val rs = st.executeQuery(sentencia)
        var llistaRutas= arrayListOf<Ruta>()


        var ruta: Ruta

        while (rs.next()) {
            var llista= mutableListOf<PuntGeo>()
            val num_r= rs.getInt(1)

            val sentencia2 = "SELECT * FROM PUNTS WHERE num_r = $num_r"
            val rs2 = st2.executeQuery(sentencia2)
            while (rs2.next()) {
                var punt = PuntGeo(rs2.getString(3), Coordenadas(rs2.getFloat(4).toDouble(), rs2.getFloat(5).toDouble()))
                llista.add(punt)
            }
            ruta = Ruta(rs.getString(2), rs.getInt(3), rs.getInt(4), llista)
            llistaRutas.add(ruta)
        }

        return llistaRutas
    }

    fun borrar(e: Int) {
        st.executeUpdate("DELETE FROM PUNTS WHERE num_r = $e")
        st.executeUpdate("DELETE FROM RUTES WHERE num_r = $e")

    }
}