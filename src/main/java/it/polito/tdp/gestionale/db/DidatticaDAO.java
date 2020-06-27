package it.polito.tdp.gestionale.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.gestionale.model.Corso;
import it.polito.tdp.gestionale.model.CorsoStudente;
import it.polito.tdp.gestionale.model.Studente;

public class DidatticaDAO {

	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(String codins) {

		final String sql = "SELECT * FROM corso where codins=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, codins);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Corso corso = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"),
						rs.getInt("pd"));
				return corso;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	/*
	 * Data una matricola ottengo lo studente.
	 */
	public Studente getStudente(int matricola) {

		final String sql = "SELECT * FROM studente where matricola=?";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {

				Studente studente = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"),
						rs.getString("cds"));
				return studente;
			}

			return null;

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}


	public void getAllCorsi(Map<String, Corso> idMap){
		String sql="SELECT codins, nome, crediti, pd " + 
				"FROM corso " + 
				""; 
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(!idMap.containsKey(rs.getString("codins"))) {
					Corso c = new Corso(rs.getString("codins"),rs.getInt("crediti"), rs.getString("nome"), 
							 rs.getInt("pd"));
					idMap.put(c.getCodins(), c); 
					
				}

				
			}
			conn.close();


		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	public void getAllStudenti(Map<Integer, Studente> idMap){
		String sql="SELECT matricola, nome, cognome, cds " + 
				"FROM studente " + 
				""; 
		
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(!idMap.containsKey(rs.getInt("matricola"))) {
					Studente s = new Studente(rs.getInt("matricola"),rs.getString("cognome"), rs.getString("nome"), 
							 rs.getString("cds"));
					idMap.put(s.getMatricola(), s); 
					
				}

				
			}
			conn.close(); 


		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
	
	public List<CorsoStudente> getAdiacenze(Map<Integer, Studente> idMapStudente, Map<String, Corso> idMapCorso ){
		String sql="SELECT matricola, codins " + 
				"FROM iscrizione"; 
		
		List<CorsoStudente> lista= new ArrayList<>(); 
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Corso c= idMapCorso.get(rs.getString("codins")); 
				Studente s= idMapStudente.get(rs.getInt("matricola"));
				
				lista.add(new CorsoStudente(c, s)); 

				
			}
			conn.close();
			return lista; 


		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}
}
