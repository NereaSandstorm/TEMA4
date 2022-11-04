package exercicis



import javax.swing.JFrame
import java.awt.EventQueue
import java.awt.BorderLayout
import javax.swing.JPanel
import java.awt.FlowLayout
import java.sql.DriverManager
import javax.swing.JComboBox
import javax.swing.JButton
import javax.swing.JTextArea
import javax.swing.JLabel

class Finestra : JFrame() {

    init {
        // Sentències per a fer la connexió
        val url = "jdbc:sqlite:Rutes.sqlite"
        val con = DriverManager.getConnection(url)
        val st1 = con.createStatement()


        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setTitle("JDBC: Visualitzar Rutes")
        setSize(450, 450)
        setLayout(BorderLayout())

        val panell1 = JPanel(FlowLayout())
        val panell2 = JPanel(BorderLayout())
        add(panell1, BorderLayout.NORTH)
        add(panell2, BorderLayout.CENTER)

        val llistaRutes = arrayListOf<String>()
        // Sentències per a omplir l'ArrayList amb el nom de les rutes
        val sentenciarutesSQL = "SELECT * FROM RUTES"
        val rsrutes = st1.executeQuery(sentenciarutesSQL)

        while (rsrutes.next()) {
            llistaRutes.add(rsrutes.getString(2))
        }

        rsrutes.close()
        st1.close()



        val combo = JComboBox<String>(llistaRutes.toTypedArray())
        panell1.add(combo)
        val eixir = JButton("Eixir")
        panell1.add(eixir)
        val area = JTextArea()
        panell2.add(JLabel("Llista de punts de la ruta:"),BorderLayout.NORTH)
        panell2.add(area,BorderLayout.CENTER)


        combo.addActionListener() {
            // Sentèncis quan s'ha seleccionat un element del JComboBox
            // Han de consistir en omplir el JTextArea
            val st2 = con.createStatement()
            val numero: Int = combo.selectedIndex + 1
            val sentenciapuntsSQL = "SELECT * FROM PUNTS WHERE num_r = '$numero'"
            val rspunts = st2.executeQuery(sentenciapuntsSQL)

            area.text = ""
            while (rspunts.next()) {
                val nom = rspunts.getString(3)
                val latitud = rspunts.getFloat(4)
                val longitud= rspunts.getFloat(5)
                area.text += nom + " (" + longitud + "-" + latitud + ") \n"

            }
            rspunts.close()
            st2.close()

        }



        eixir.addActionListener(){
            // Sentències per a tancar la connexió i eixir
            con.close()
            System.exit(0)
        }
    }
}

fun main(args: Array<String>) {
    EventQueue.invokeLater {
        Finestra().isVisible = true
    }
}

