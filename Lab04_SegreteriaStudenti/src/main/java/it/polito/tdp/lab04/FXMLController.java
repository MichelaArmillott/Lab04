
 package it.polito.tdp.lab04;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.CheckBox;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	public void setModel(Model model) {
		this.model=model;
		comboBox.getItems().addAll(model.tuttiICorsi());
	}

    @FXML
    private ResourceBundle resources;
    
    @FXML
    private CheckBox cheeeck;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> comboBox;

    @FXML
    private TextField txtMatricola;
    
    @FXML
    private TextField txtCognome;

    @FXML
    private TextField txtNome;

    @FXML
    private TextArea txtRisultato;

    @FXML
    void cercaCorsi(ActionEvent event) {
    	txtRisultato.clear();
    	String matricolaString=txtMatricola.getText();
    	Integer matricola=Integer.parseInt(matricolaString);
    	if(model.esisteStudente(matricola)==false) {
    		txtRisultato.setText("non esiste uno studente con questa matricola");
    		txtMatricola.clear();
    		return;
    	}
    	List <Corso> stampa=model.elencoCorsi(matricola);
    	if(stampa.size()==0) {
    		txtRisultato.setText("lo studente non è iscritto a nessun corso");
    		return;
    	}
    	String stringaDaStampare="";
    	for(Corso c:stampa) {
    		stringaDaStampare=stringaDaStampare+c.getCodins()+" "+c.getNome()+" "+c.getCrediti()+"  crediti "+c.getPd()+" periodo\n";
    	}
    	
    	txtRisultato.setText(stringaDaStampare);
    	 }

    @FXML
    void cercaIscritti(ActionEvent event) {
    	txtRisultato.clear();
    	if(comboBox.getValue()==null) {
    		txtRisultato.setText("devi scegliere un corso");
    		return ;}
    	Corso corsoScelto=comboBox.getValue();
    	String codice=model.ottieniCodiceCorso(corsoScelto);
    	if(codice.length()==0) {
    		txtRisultato.setText("questo codice non è presente nel db");
    		return;
    	}
    	List<Studente>stampa=model.studentiIscritti(codice);
    	if(stampa.size()==0) {
    		txtRisultato.setText("il corso non ha iscritti");
    		return ;
    	}
    	for(Studente s:stampa) {
    		txtRisultato.appendText(s+"\n");
    	}
    	

    }

    @FXML
    void checkAction(ActionEvent event) {
    	txtRisultato.clear();
    	String matricolaString;
    	matricolaString=txtMatricola.getText();
    	
    	if(matricolaString.length()==0) {
    		txtRisultato.setText("devi inserire una matricola ");
    		return ;
    	}
    	Integer matricola;
    	try{ matricola=Integer.parseInt(matricolaString);}catch(NumberFormatException e) {
    		txtRisultato.setText("la matricola contiene solo numeri!!");
    		txtMatricola.clear();
    		return;
    	}
    	Studente studente=model.studenteDaMatricola(matricola);
    	if(studente==null) {
    		txtRisultato.setText("lo studente non esiste");
    		txtMatricola.clear();
    		return ;
    	}
    	txtCognome.setText(studente.getCognome());
    	txtNome.setText(studente.getNome());
    	if(comboBox.getValue()==null) {
    		comboBox.setDisable(true);
    		txtMatricola.setDisable(true);
    	}
    	if(comboBox.getValue()!=null) {
    		Corso corsoScelto=comboBox.getValue();
        	String codice=model.ottieniCodiceCorso(corsoScelto);
    		boolean verifica=model.eIscritto(matricola, codice);
    		if(verifica==true)
    			txtRisultato.setText("lo studente è iscritto al corso");
    		else
    			txtRisultato.setText("lo studente  NON è iscritto al corso");	
    		
    	}

    }

    @FXML
    void iscrivi(ActionEvent event) {
    	txtRisultato.clear();
    	String matricolaString;
    	matricolaString=txtMatricola.getText();
    	
    	if(matricolaString.length()==0) {
    		txtRisultato.setText("devi inserire una matricola ");
    		return ;
    	}
    	Integer matricola;
    	try{ matricola=Integer.parseInt(matricolaString);}catch(NumberFormatException e) {
    		txtRisultato.setText("la matricola deve contenere solo numeri!!");
    		txtMatricola.clear();
    		return;
    	}
    	if(model.esisteStudente(matricola)==false) {
    		txtRisultato.setText("tale studente non esiste");
    		return ;
    	}
    	if(comboBox.getValue()==null) {
    		txtRisultato.setText("devi scegliere un corso");
    		return ;}
    	Corso corsoScelto=comboBox.getValue();
    	if(model.eIscritto(matricola,corsoScelto.getCodins())==true) {
    		txtRisultato.setText("lo studente è già iscritto al corso");
    		return ;
    	}
    	model.inserisciStudente(matricola,corsoScelto);
    	txtRisultato.setText("studente iscritto");
    }

    @FXML
    void reset(ActionEvent event) {
    	txtRisultato.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	txtMatricola.clear();
    	comboBox.setValue(null);
    	comboBox.setDisable(false);
    	txtMatricola.setDisable(false);}
    	//devi veder come si toglie la spunta alla checkbox

  

    @FXML
    void initialize() {
        assert comboBox != null : "fx:id=\"comboBox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cheeeck != null : "fx:id=\"cheeeck\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}
