package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	private StudenteDAO studenteDAO;
	private CorsoDAO corsoDAO;
	public Model() {
		studenteDAO=new StudenteDAO();
		corsoDAO=new CorsoDAO();
	}
 public Studente studenteDaMatricola(Integer matricola) {
	 return studenteDAO.studenteDaMatricola(matricola);
 }
 
 public List<Corso> tuttiICorsi(){
	 return corsoDAO.getTuttiICorsi();
 }
 
 public String ottieniCodiceCorso(Corso c) {
	 return corsoDAO.getCorso(c);
 }
 
 public List<Studente>studentiIscritti(String codice){
	 return corsoDAO.getStudentiIscrittiAlCorso(codice);}
 
 public boolean esisteStudente(Integer matricola) {
	 return studenteDAO.esisteStudente(matricola);
 }
 
 public List<Corso> elencoCorsi(Integer matricola){
	 return studenteDAO.corsiDelloStudente(matricola);
 }
 
 public boolean eIscritto(Integer matricola,String codice) {
	 boolean iscritto=false;
	 Studente s=this.studenteDaMatricola(matricola);
	 List<Corso> corsiDelloStudente=this.elencoCorsi(matricola);
	 for(Corso c:corsiDelloStudente) {
		 if(c.getCodins().equals(codice))
			 iscritto=true;
	 }
	 return iscritto;
	 }
 
   public void inserisciStudente(Integer matricola,Corso corso) {
	   if(this.eIscritto(matricola, corso.getCodins())==false && this.esisteStudente(matricola)==true) {
		   corsoDAO.inscriviStudenteACorso(matricola, corso);
	   }
   }
 }

