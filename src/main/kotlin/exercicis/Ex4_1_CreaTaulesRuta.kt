package exercicis

import java.sql.DriverManager

fun main(args: Array<String>) {
    val url = "jdbc:sqlite:Rutes.sqlite"
    val con = DriverManager.getConnection(url)
    val st = con.createStatement()

    val sentSQL = "CREATE TABLE RUTES(" +
            "num_r INTEGER CONSTRAINT cp_rut PRIMARY KEY, " +
            "nom_r TEXT, " +
            "desn INTEGER, " +
            "desn_ac INTEGER " +
            ")"

    val st2 = con.createStatement()

    val sentSQL2 = "CREATE TABLE PUNTS(" +
            "num_r INTEGER, " +
            "num_p INTEGER, " +
            "nom_p TEXT, " +
            "latitud real, " +
            "longitud real, " +
            "CONSTRAINT cp_pun primary key(num_r, num_p) " +
            "foreign key (num_r) references RUTES " +
            ")"


    st.executeUpdate(sentSQL)
    st2.executeUpdate(sentSQL2)
    st.close();
    st2.close()
    con.close()
}