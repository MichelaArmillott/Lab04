package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				Corso c=new Corso(codins,numeroCrediti,nome,periodoDidattico);
				corsi.add(c);
			
			}

			conn.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public String getCorso(Corso corso) {
		String stampa="";
		String sql="SELECT * "
				+ "FROM corso c "
				+ "WHERE c.codins=? ";
		Connection conn=ConnectDB.getConnection();
		try {
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1,corso.getCodins());
			ResultSet rs=st.executeQuery();
			if(rs.next())
				stampa=stampa+rs.getString("codins");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stampa;
		
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List<Studente> getStudentiIscrittiAlCorso(String codice) {
		String sql="SELECT  s.matricola,s.cognome,s.nome,s.CDS "
				+ "FROM studente s,iscrizione i "
				+ "WHERE i.matricola=s.matricola AND i.codins=? ";
		List<Studente>stampa=new ArrayList<Studente>();
		Connection conn=ConnectDB.getConnection();
		try {
			PreparedStatement st=conn.prepareStatement(sql);
			st.setString(1, codice);
			ResultSet rs=st.executeQuery();
			while(rs.next()) {
				Studente s=new Studente(rs.getInt("matricola"),rs.getString("cognome"),rs.getString("nome"),rs.getString("CDS"));
				stampa.add(s);}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stampa;
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public void inscriviStudenteACorso(Integer matricola, Corso corso) {
		String sql="INSERT INTO iscrizione VALUES(?,?) ";
		Connection conn=ConnectDB.getConnection();
		try {
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, matricola);
			st.setString(2, corso.getCodins());
			
			ResultSet rs=st.executeQuery();
			
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

}
