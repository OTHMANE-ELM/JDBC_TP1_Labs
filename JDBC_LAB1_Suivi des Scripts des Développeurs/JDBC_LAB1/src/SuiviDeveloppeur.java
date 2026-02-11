
import java.sql.*;

public class SuiviDeveloppeur {

    private static final String URL = "jdbc:mysql://localhost:3306/ateliersuividev?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Pas de Driver");
            e.printStackTrace();
            return;
        }

        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stm = conn.createStatement()) {

            System.out.println("On est connecté au Driver");

            
            stm.executeUpdate("DROP TABLE IF EXISTS DONNESDEVELOPPEUR");
            stm.executeUpdate("CREATE TABLE DONNESDEVELOPPEUR (" + "Developpeurs VARCHAR(32) NOT NULL, " + "Jour VARCHAR(16) NOT NULL, " + "NbScripts INT NOT NULL" + ")"
            );
                                         System.out.println("TABLE bien créée");

            
            stm.executeUpdate("INSERT INTO DONNESDEVELOPPEUR VALUES ('AMINE', 'Lundi', 1)");
            stm.executeUpdate("INSERT INTO DONNESDEVELOPPEUR VALUES ('LHSSEN', 'Lundi', 2)");
            stm.executeUpdate("INSERT INTO DONNESDEVELOPPEUR VALUES ('MSSTAFA', 'Mardi', 9)");
            stm.executeUpdate("INSERT INTO DONNESDEVELOPPEUR VALUES ('AMINE', 'Mardi', 3)");
            stm.executeUpdate("INSERT INTO DONNESDEVELOPPEUR VALUES ('LHSSEN', 'Mardi', 4)");
            stm.executeUpdate("INSERT INTO DONNESDEVELOPPEUR VALUES ('MSSTAFA', 'Mercredi', 2)");
            
                                     System.out.println("Données bien insérées\n");

            
           
            
            try (ResultSet rst = stm.executeQuery("SELECT Jour, Developpeurs, MAX(NbScripts) AS MaxScripts " + "FROM DONNESDEVELOPPEUR GROUP BY Jour, Developpeurs")) {
                   while (rst.next()) {
                       
                                 System.out.println(rst.getString("Jour") + " -- " + rst.getString("Developpeurs") + " -- " + rst.getInt("MaxScripts"));
                }
            }

           
            
            
            try (ResultSet rs = stm.executeQuery("SELECT Developpeurs, SUM(NbScripts) AS Total FROM DONNESDEVELOPPEUR GROUP BY Developpeurs ORDER BY Total DESC")) {
                while (rs.next()) {
                    
                                System.out.println(rs.getString("Developpeurs") + " | " + rs.getInt("Total"));
                }
            }

            
            try (ResultSet rs = stm.executeQuery("SELECT SUM(NbScripts) AS TotalSemaine FROM DONNESDEVELOPPEUR")) {
                if (rs.next()) {
                    
                                System.out.println("Total : " + rs.getInt("TotalSemaine"));
                }
            }

            
           
                String devRecherche = "LHSSEN";
                
            
            try (PreparedStatement ps = conn.prepareStatement("SELECT SUM(NbScripts) AS TotalDev FROM DONNESDEVELOPPEUR WHERE Developpeurs = ?")) {
                       ps.setString(1, devRecherche);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        
                                  System.out.println("Total pour " + devRecherche + " : " + rs.getInt("TotalDev"));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Ereur dans les requetes SQL" );
            e.printStackTrace();
        }
    } 
} 