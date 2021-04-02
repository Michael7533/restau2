package co.simplon.restaurant;


import co.simplon.restaurant.Model.commandes;
import co.simplon.restaurant.Model.facture;

import java.sql.*;
import java.util.Scanner;


public class App {


    public static void main(String[] args) throws SQLException {

        Scanner sc = new Scanner(System.in);

        // connection a la base

        String url = "jdbc:postgresql://localhost:5432/restaurant";
        String user = "postgres";
        String password = "postgres";

        Connection connection = DriverManager.getConnection(url, user, password);

        // what do you want to do?

        System.out.println("Bonjour, que voulez-vous faire?:");
        System.out.println();
        System.out.println("     Pour créer une nouvelle note, tapez 1");
        System.out.println("     Pour afficher une note en cours , tapez 2");
        System.out.println("     Pour alimenter une note existante, tapez 3");
        System.out.println("     Pour cloturer une note, tapez 4");
        System.out.println("     Pour afficher les trois meilleures tables tapez 5 ");
        int choice = sc.nextInt();

        switch (choice) {

            case 1:
                CreerNouvelleFacture(connection);
                break;

            case 2:
                System.out.println();
                AfficheFactureEncours(connection);
                System.out.println();
                System.out.println("quelle note voulez-vous afficher?");
                int factChoice = sc.nextInt();
                AfficherUneFacture(connection, factChoice);
                break;

            case 3:

                System.out.println("___________________________");
                System.out.println("Sur laquelle de ces notes souhaitez vous ajouter des articles?");
                System.out.println();

                AfficheFactureEncours(connection);

                int factureselect = sc.nextInt();

                System.out.println();

                CreerNouvelleCommande(connection, factureselect);

                System.out.println("___________________________");
                System.out.println("Note en cours mise à jour:");
                System.out.println();
                AfficherUneFacture(connection, factureselect);
                break;

            case 4:
                AfficheFactureEncours(connection);
                System.out.println("quelle facture cloturer?:");
                int factChoic = sc.nextInt();
                CloturerUneFacture(connection, factChoic);
                break;

            case 5:
                ListerTroisMeilleurstables(connection);
                break;

        }
        // fermeture connection

        connection.close();


    }

    private static void AfficheFactureEncours(Connection connection) throws SQLException {

        Statement ordreSQL = connection.createStatement();
        ResultSet resultats = ordreSQL.executeQuery("select numero, nomtable, nom, prenom from facture join serveur s on s.id = facture.serveurid join tableref t on t.id = facture.tableid where (etat='O') order by numero");
        while (resultats.next()) {


            System.out.print("Facture numero: " + resultats.getString("numero") + "  / ");
            System.out.print(resultats.getString("nomtable"));
            System.out.println(" servi par " + resultats.getString("prenom"));
        }
        resultats.close();
        ordreSQL.close();
    }

    private static void AfficherUneFacture(Connection connection, int factureselect) throws SQLException {

        Statement ordreSQL = connection.createStatement();
        ResultSet resultats = ordreSQL.executeQuery("select plat, pu, quantité from commandes join facture f on f.numero = commandes.numfactureid join carte c on c.id = commandes.platid where numfactureid = '" + factureselect + "' ");


        System.out.print("facture numero : " + factureselect);
        System.out.println();

        while (resultats.next()) {


            System.out.print(resultats.getString("plat") + "  / ");
            System.out.print(resultats.getInt("quantité") + " X ");
            System.out.println(resultats.getDouble("pu") + " Eur");
        }
        resultats.close();
        ordreSQL.close();
    }

    private static void AfficheLeMenu(Connection connection) throws SQLException {
        Statement ordreSQL = connection.createStatement();
        ResultSet resultats = ordreSQL.executeQuery("select id,plat,pu from carte order by id");
        while (resultats.next()) {


            System.out.print("ref article:" + resultats.getString("id") + "  / ");
            System.out.print(resultats.getString("plat"));
            System.out.println(" " + resultats.getString("pu") + "Eur");
        }
        resultats.close();
        ordreSQL.close();
    }

    private static void ListerTroisMeilleurstables(Connection connection) throws SQLException {
        Statement ordreSQL = connection.createStatement();
        ResultSet resultats = ordreSQL.executeQuery("select nomtable, sum(pu*commandes.quantité) from commandes " +
                "join facture f on f.numero = commandes.numfactureid " +
                "join tableref t on t.id = f.tableid " +
                "join carte c on c.id = commandes.platid " +
                "group by nomtable order by sum(pu*commandes.quantité) desc limit 3");

        while (resultats.next()) {
            System.out.print(resultats.getString("nomtable") + "  / ");

        }

        resultats.close();
        ordreSQL.close();
    }

    private static void AfficherLesServeurs(Connection connection) throws SQLException {
        Statement ordreSQL = connection.createStatement();
        ResultSet resultats = ordreSQL.executeQuery("select id,nom,prenom from serveur order by id");
        while (resultats.next()) {


            System.out.print("ref:" + resultats.getString("id") + "  / ");
            System.out.print(resultats.getString("nom"));
            System.out.println(" " + resultats.getString("prenom"));
        }
        resultats.close();
        ordreSQL.close();
    }

    private static void CreerNouvelleFacture(Connection connection) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("factures toujours en cours:");
        AfficheFactureEncours(connection);
        System.out.println();
        System.out.println("entrez le numero de la nouvelle facture");
        int numero = sc.nextInt();
        System.out.println("entrez le numero de table");
        int tableid = sc.nextInt();
        System.out.println("entrez le nombre de convives");
        int nbconvives = sc.nextInt();
        System.out.println("Serveurs disponibles:");
        System.out.println();
        AfficherLesServeurs(connection);
        System.out.println();
        System.out.println("entrez l'id du serveur");
        int serveurid = sc.nextInt();

        facture newFacture = new facture(numero, tableid, nbconvives, serveurid);

        newFacture.saveFacture(connection);
    }

    private static void CreerNouvelleCommande(Connection connection, int numfac) throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Menu:");
        AfficheLeMenu(connection);
        System.out.println("entrez la reference de l'article à ajouter");
        int id = sc.nextInt();
        System.out.println("entrez la quantité à ajouter à la note");
        int quantite = sc.nextInt();

        commandes newCommande = new commandes(numfac, id, quantite);

        newCommande.saveCommande(connection, numfac);
    }

    private static void CloturerUneFacture (Connection connection, int numfact) throws SQLException {

        Statement ordreSQL = connection.createStatement();
        ordreSQL.execute("update facture set etat = '" + "X" +"' where numero = '" + numfact + " '");
        System.out.println("Factures restantes en cours aprés cette clôture:");
        System.out.println();
        AfficheFactureEncours(connection);
    }
}



