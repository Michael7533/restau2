package co.simplon.restaurant.Model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class commandes {
    int commandeid;
    int numfactureid;
    int platid;
    int quantite;

    public commandes(int numfactureid, int platid, int quantite) {

        this.numfactureid = numfactureid;
        this.platid = platid;
        this.quantite = quantite;
    }

    public void saveCommande (Connection connection, int numfactureid) throws SQLException {
        Statement ordreSQL = connection.createStatement();
        ordreSQL.execute("INSERT INTO commandes ( numfactureid, platid,quantité) VALUES ('" + numfactureid + "','" + platid + "','"  + quantite + "')");
        ordreSQL.close();
    }
}
