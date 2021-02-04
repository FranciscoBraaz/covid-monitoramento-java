package application;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Classe respons�vel por exibir o gr�fico de pizza, chamado a partir da  MainController
 */
public class PieChartController {
	
	@FXML
	protected Integer [] rolInfirmary;
	@FXML
	protected Integer [] rolIcu;
	
    @FXML
    private Button btnBack;

    @FXML
    private ListView<String> listview;

    @FXML
    private PieChart pieChart;

    @FXML
    void btnBackOnAction(ActionEvent event) {
    	Stage stage = (Stage) btnBack.getScene().getWindow();
    	stage.close();
    	
    }
    
    /**
     * M�todo respons�vel por setar os valores do array (porcentagens de cada dia do m�s) necess�rios para o gr�fico de pizza.
     * @param rolInfirmary
     * @param rolIcu
     */
	public void setData(Integer [] rolInfirmary, Integer [] rolIcu) {
		this.rolInfirmary = rolInfirmary;
		this.rolIcu = rolIcu;
		this.days();
	}
    
    
    /**
     * M�todo respons�vel por exibir no ListView os dias do respectivo m�s selecionado
     */
    public void days() {
    	for(int i = 1; i<=rolIcu.length; i++) {
    		listview.getItems().add(String.format("%d", i));	
    	}
    }
    
    
    /**
     * M�todo respons�vel por exibir o gr�fico de pizza com base no dia do m�s selecionado
     * @param event
     */
    @FXML
     void listviewOnClick(MouseEvent event) {
    	
    	String day = listview.getSelectionModel().getSelectedItem();
    	Integer numberDay = Integer.parseInt(day);
    	Integer unoccupiedBeds = 100 - (rolInfirmary[numberDay - 1] + rolIcu[numberDay - 1]);
    	pieChart.setTitle("(%) de ocupa��o em rela��o ao total de leitos");
    
    	ObservableList<PieChart.Data> datas = FXCollections.observableArrayList(
    			new PieChart.Data("Enfermaria - " + rolInfirmary[numberDay - 1] + "%", rolInfirmary[numberDay - 1]),
    			new PieChart.Data("UTI - " + rolIcu[numberDay - 1] + "%", rolIcu[numberDay - 1]),
    			new PieChart.Data("Leitos desocupados - " + unoccupiedBeds + "%", unoccupiedBeds));
    			pieChart.setData(datas);
    			
    }
    
   
}
