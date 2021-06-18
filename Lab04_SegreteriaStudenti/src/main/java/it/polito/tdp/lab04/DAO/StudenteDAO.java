package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	public Studente studenteDaMatricola(Integer matricola) {
		String sql="SELECT * "
				+ "FROM studente s "
				+ "WHERE s.matricola=? ";
		Studente studente;
		try {Connection conn=ConnectDB.getConnection();
			PreparedStatement st=conn.prepareStatement(sql);
			st.setInt(1, matricola);
			ResultSet rs=st.executeQuery();
			if(rs.next()) {
				studente=new Studente(rs.getInt("matricola"),rs.getString("cognome"),rs.getString("nome"),rs.getString("CDS"));
			return studente;} 
			else return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean esisteStudente(Integer matricola) {
		boolean esiste=false;
		String sql="SELECT * "
				+ "FROM studente s "
				+ "WHERE s.matricola=? ";
		try {Connection conn=ConnectDB.getConnection();
		PreparedStatement st=conn.prepareStatement(sql);
		st.setInt(1, matricola);
		ResultSet rs=st.executeQuery();
		if(rs.next())
			esiste=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return esiste;
		
	}
	
	public List<Corso> corsiDelloStudente(Integer matricola){
		String sql="SELECT c.codins,c.crediti,c.nome,c.pd "
				+ "FROM iscrizione i, corso c "
				+ "WHERE i.codins=c.codins AND i.matricola=? ";
		List<Corso>stampa=new ArrayList<Corso>();
		try {Connection conn=ConnectDB.getConnection();
		PreparedStatement st=conn.prepareStatement(sql);
		st.setInt(1, matricola);
		ResultSet rs=st.executeQuery();
		while(rs.next()) {
			Corso c=new Corso(rs.getString("codins"),rs.getInt("crediti"),rs.getString("nome"),rs.getInt("pd"));
			stampa.add(c);
		}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stampa;
	}
	
	
}
