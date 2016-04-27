package net.andreu.SantsMongo;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class SampleController implements Initializable{
	@FXML
	private TextField lblNom;
	@FXML
	private Button btnNom;
	@FXML
	private ListView<String> lvNom;
	@FXML
	private TextField lblData;
	@FXML
	private Button btnData;
	@FXML
	private ListView<String> lvData;
	
	private MongoClient mongo;
	private MongoCollection<Document> coleccio;

	public void initialize(URL arg0, ResourceBundle arg1) {
		mongo = new MongoClient();
		MongoDatabase bdd = mongo.getDatabase("sants");
		coleccio = bdd.getCollection("noms");
	}
	
	// Event Listener on Button[#btnNom].onMouseClicked
	@SuppressWarnings("unchecked")
	@FXML
	public void ferConsultaPerNom(MouseEvent event) {
			
		Document resultats = coleccio.find(or(eq("catala",lblNom.getText()), eq("castella", lblNom.getText()))).first();
		
		if(resultats==null){
			
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Look, a Warning Dialog");
			alert.setContentText("Careful with the next step!");

			alert.showAndWait();
			lblNom.setText("");
		}else{
			
			List<String> sants = (List<String>) resultats.get("sants");
			
			if((sants.size()==1 && (sants.get(0).equals("1 de gener") || sants.get(0).equals("01 de gener"))) || resultats.get("sants")==null){
				
				String observacio = (String) resultats.get("observacions");
				if (observacio != null) {
					lvNom.getItems().add(observacio);
				} else {
					lvNom.getItems().add("No hi ha cap observació");
				}
			} else {
				
				for (String data : sants) {
					lvNom.getItems().add("- "+data+" -\n");
				}
			}	
		}
	}
	
	// Event Listener on Button[#btnData].onMouseClicked
	@FXML
	public void ferConsultaPerData(MouseEvent event) {
		// TODO Autogenerated
	}
}
