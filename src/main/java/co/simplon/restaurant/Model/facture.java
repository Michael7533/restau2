package co.simplon.restaurant.Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class facture {
    int id;
    int numero;
    int tableid;
    int nbconvives;
    int serveurid;

    public facture(int numero, int tableid, int nbconvives, int serveurid) {
        this.numero = numero;
        this.tableid = tableid;
        this.nbconvives = nbconvives;
        this.serveurid = serveurid;
    }

    public void saveFacture (Connection connection) throws SQLException {
        Statement ordreSQL = connection.createStatement();
        ordreSQL.execute("INSERT INTO facture (numero, tableid, nbconvives,serveurid,etat) VALUES ('" + numero + "','" + tableid + "','" + nbconvives + "','" + serveurid + "','O')");
        ordreSQL.close();
    }

}
