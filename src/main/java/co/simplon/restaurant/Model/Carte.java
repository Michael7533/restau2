package co.simplon.restaurant.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Carte {
    int id;
    public String plat;
    double pu;

    public Carte(int id, String plat, double pu) {
        this.id = id;
        this.plat = plat;
        this.pu = pu;


               }
//    @Override
//    public String toString() {
//        return " "+id + "Article: " + plat + "Prix untiaire " + pu +" ";
//    }
//
//    public static List<Carte> getCartes(Connection connection) throws SQLException {
//
//
//        Statement ordreSQL = connection.createStatement();
//        ResultSet resultats = ordreSQL.executeQuery("SELECT * from Carte");
//
//        List<Carte> carteList = new ArrayList<>();
//
//        while (resultats.next()) {
//            Carte dbCarte = new Carte(resultats.getInt("id"),
//                    resultats.getString("plat"),
//                    resultats.getDouble("pu"));
//            carteList.add(dbCarte);
//
//            System.out.println(dbCarte);
//        }
//
//        resultats.close();
//        ordreSQL.close();
//
//        return carteList;
    //}

    public void afficheCarte(Connection connection) throws SQLException {
        Statement ordreSQL = connection.createStatement();
        ResultSet resultats = ordreSQL.executeQuery("select * into carte");
        while (resultats.next()) {
            System.out.println(resultats.getInt(id));
        }
        resultats.close();
        ordreSQL.close();

    }

    }

