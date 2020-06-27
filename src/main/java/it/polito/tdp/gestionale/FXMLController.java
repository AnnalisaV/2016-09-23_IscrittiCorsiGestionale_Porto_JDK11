package it.polito.tdp.gestionale;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.gestionale.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

//controller del turno A --> switchare al master_turnoB per turno B

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCorsiFrequentati(ActionEvent event) {

    	txtResult.clear();
    	
    	model.creaGrafo();
    	
    	Map<Integer, Integer> frequenze= model.getFrequenza(); 
    	for (Integer i : frequenze.keySet()) {
    		txtResult.appendText("A "+i+" corsi sono iscritti "+frequenze.get(i)+" studenti\n");
    	}
    }

    @FXML
    void doVisualizzaCorsi(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DidatticaGestionale.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
