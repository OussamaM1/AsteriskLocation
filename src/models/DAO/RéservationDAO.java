package models.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Réservation;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;

public class RéservationDAO extends DAO<Réservation>{

    public RéservationDAO(Connection conn) throws SQLException {
        super(conn);
    }

    @Override
    public boolean create(Réservation obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("INSERT INTO reservation(dateReservation,dateDepart,dateRetour,idClient,idVehicule,etatReservation) VALUES(?,?,?,?,?,?)");
            LocalDate dateD = obj.getDateDépart();
            Date dateDepart = Date.valueOf(dateD);
            LocalDate dateR = obj.getDateRetour();
            Date dateRetour = Date.valueOf(dateR);
            LocalDate date = obj.getDateReservation();
            Date dateReservation = Date.valueOf(date);
            preparedStmt.setObject(1,dateReservation);
            preparedStmt.setObject(2,dateDepart);
            preparedStmt.setObject(3,dateRetour);
            preparedStmt.setObject(4,obj.getIdClient());
            preparedStmt.setObject(5,obj.getIdVehicule());
            preparedStmt.setObject(6,obj.getEtatReservation());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean delete(Réservation obj) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("DELETE FROM reservation WHERE codeReservation=?");
            preparedStmt.setInt(1,obj.getCodeRéservation());
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public boolean update(Réservation obj, int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("UPDATE reservation SET dateDepart=?,dateRetour=?,idClient=?,idVehicule=?,etatReservation=? WHERE codeReservation=?");
            LocalDate dateD = obj.getDateDépart();
            Date dateDepart = Date.valueOf(dateD);
            LocalDate dateR = obj.getDateRetour();
            Date dateRetour = Date.valueOf(dateR);
            preparedStmt.setObject(1,dateDepart);
            preparedStmt.setObject(2,dateRetour);
            preparedStmt.setObject(3,obj.getIdClient());
            preparedStmt.setObject(4,obj.getIdVehicule());
            preparedStmt.setObject(5,obj.getEtatReservation());
            preparedStmt.setInt(6,id);
            preparedStmt.execute();
            return true;
        }
        catch(SQLException e)
        {
            return false;
        }
    }

    @Override
    public Réservation find(int id) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM reservation WHERE codeReservation=?");
            preparedStmt.setInt(1,id);
            ResultSet resultSet = preparedStmt.executeQuery();
            while(resultSet.next()) {
                Date dateD = resultSet.getDate("dateDepart");
                LocalDate dateDepart = dateD.toLocalDate();
                Date dateR = resultSet.getDate("dateRetour");
                LocalDate dateRetour = dateR.toLocalDate();
                Date date = resultSet.getDate("dateReservation");
                LocalDate dateReservation = date.toLocalDate();
                return new Réservation(id, dateReservation, dateDepart, dateRetour, resultSet.getInt("idClient"), resultSet.getInt("idVehicule"), resultSet.getString("etatReservation"));
            }
        }
        catch(SQLException e)
        {
            return new Réservation(id, null, null,null,0, 0, "");
        }
            return new Réservation(id, null, null,null,0, 0, "");
    }

    @Override
    public ObservableList<Réservation> list() {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM reservation");
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Réservation> listRéservations = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                Date dateD = resultSet.getDate("dateDepart");
                LocalDate dateDepart = dateD.toLocalDate();
                Date dateR = resultSet.getDate("dateRetour");
                LocalDate dateRetour = dateR.toLocalDate();
                Date date = resultSet.getDate("dateReservation");
                LocalDate dateReservation = date.toLocalDate();
                listRéservations.add(new Réservation(resultSet.getInt("codeReservation"), dateReservation, dateDepart,dateRetour,resultSet.getInt("idClient"), resultSet.getInt("idVehicule"), resultSet.getString("etatReservation")));
            }
            Collections.sort(listRéservations, Comparator.comparing(Réservation::getDateDépart).reversed());
            return listRéservations;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
    public boolean checker(int idClient , int idVehicule , LocalDate dateReservation , LocalDate dateDepart , LocalDate dateRetour) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM reservation WHERE dateDepart=? AND dateRetour=? AND idClient=? AND idVehicule=? AND dateReservation=?");
            Date dateD = Date.valueOf(dateDepart);
            Date dateR = Date.valueOf(dateRetour);
            Date date = Date.valueOf(dateReservation);
            preparedStmt.setObject(1, dateD);
            preparedStmt.setObject(2, dateR);
            preparedStmt.setObject(3, idClient);
            preparedStmt.setObject(4, idVehicule);
            preparedStmt.setObject(5, date);
            ResultSet resultSet = preparedStmt.executeQuery();
            while(resultSet.next()) {
                return true;
            }
        }
        catch(SQLException e)
        {
            return false;
        }
            return false;
    }
    public ObservableList<Réservation> listReservation(String etat) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM reservation WHERE etatReservation=?");
            preparedStmt.setString(1,etat);
            ResultSet resultSet = preparedStmt.executeQuery();
            ObservableList<Réservation> listRéservations = FXCollections.observableArrayList();
            while(resultSet.next())
            {
                Date dateD = resultSet.getDate("dateDepart");
                LocalDate dateDepart = dateD.toLocalDate();
                Date dateR = resultSet.getDate("dateRetour");
                LocalDate dateRetour = dateR.toLocalDate();
                Date date = resultSet.getDate("dateReservation");
                LocalDate dateReservation = date.toLocalDate();
                listRéservations.add(new Réservation(resultSet.getInt("codeReservation"), dateReservation, dateDepart,dateRetour,resultSet.getInt("idClient"), resultSet.getInt("idVehicule"), resultSet.getString("etatReservation")));
            }
            Collections.sort(listRéservations, Comparator.comparing(Réservation::getDateDépart).reversed());
            return listRéservations;
        }
        catch(SQLException e)
        {
            return null;
        }
    }
    public boolean disponibiliteVehicule(int matricule) {
        try
        {
            PreparedStatement preparedStmt = connect.prepareStatement("SELECT * FROM reservation WHERE idVehicule=?");
            preparedStmt.setObject(1, matricule);
            ResultSet resultSet = preparedStmt.executeQuery();
            while(resultSet.next()) {
                return true;
            }
        }
        catch(SQLException e)
        {
            return false;
        }
        return false;
    }
}
