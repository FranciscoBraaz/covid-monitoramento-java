package application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Classe respons�vel por controlar o View principal (Que exibe o gr�fico de linhas, o gr�fico de pizza e seleciona o arquivo a ser lido)
 */

public class MainController {
	
	Map<String, List<Integer>> infirmaryListLine = new LinkedHashMap<>();
	Map<String, List<Integer>> icuListLine = new LinkedHashMap<>();
	Map<String, List<Integer>> infirmaryListPie = new LinkedHashMap<>();
	Map<String, List<Integer>> icuListPie = new LinkedHashMap<>();
	Integer [] rolInfirmaryLine;
	Integer [] rolIcuLine;
	Integer [] rolInfirmaryPie;
	Integer [] rolIcuPie;
	
    @FXML
    private MenuItem btnFileOpen;

    @FXML
    private MenuItem btnClose;

    @FXML
    private Button btnPieChart;
    
    @FXML
    private LineChart<String, Integer> lineChart;

    @FXML
    private CategoryAxis axisMonth;

    @FXML
    private NumberAxis axisValues;

    @FXML
    private ListView<String> listView;

    @FXML
    void btnCloseOnAction(ActionEvent event) {
    	Platform.exit();
    }
    
    
    /**
     * Chama a Scene do gr�fico de Pizza caso o bot�o btnPieChart (Pizza na view) � pressionado
     * e passa os valores (porcentagens) para a classe PieChartController;
     * @param event
     */
    @FXML
    void btnPieChartOnAction(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("PieChartView.fxml"));
    		Parent root = (Parent) loader.load();
    		
    		PieChartController pie = loader.getController();
    		pie.setData(rolInfirmaryPie, rolIcuPie);
    		
    		Stage stage = new Stage();
    		stage.setScene(new Scene(root));
    		root.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    		stage.show();
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    }

    
    /**
     * M�todo para selecionar o arquivo armazenado no dispositivo e colocar os meses na ListView
     * @param event
     */
    @FXML
    void btnFileOpenOnAction(ActionEvent event) {
    	FileChooser filechooser = new FileChooser();
    	File selectedFile = filechooser.showOpenDialog(null);
    	infirmaryListLine = Infirmary.parseListInfirmary(selectedFile, "line");
    	icuListLine = Icu.parseListIcu(selectedFile, "line");
    	infirmaryListPie = Infirmary.parseListInfirmary(selectedFile, "pie");
    	icuListPie = Icu.parseListIcu(selectedFile, "pie");
    	String months [] = infirmaryListLine.keySet().toArray(new String[0]);
    	listView.getItems().clear();
    	for(String month: months) {
    		listView.getItems().add(month);
    	}
    	
    }
    
    
    /**
     * M�todo para exibir o gr�fico de linhas  e para pegar as informa��es que ser�o utilizadas no PieChart;
     * Vari�veis com o final 'Pie' ser�o utilizadas pelo PieChart e com o final 'Line' pelo LineChart
     * @param event
     */
	@FXML
    void listViewOnClick(MouseEvent event) {
    	lineChart.getData().clear();
    	String month = listView.getSelectionModel().getSelectedItem();
  
    	rolInfirmaryLine = infirmaryListLine.get(month).toArray(new Integer[0]);
    	rolIcuLine = icuListLine.get(month).toArray(new Integer[0]);
    	rolInfirmaryPie = infirmaryListPie.get(month).toArray(new Integer[0]);
    	rolIcuPie = icuListPie.get(month).toArray(new Integer[0]);
    	
    	reverseRol(rolInfirmaryLine);
    	reverseRol(rolIcuLine);
    	reverseRol(rolInfirmaryPie);
    	reverseRol(rolIcuPie);
    	
    	
    	/**
    	 * Trecho respons�vel por criar uma lista para salvar as XYChartSeries
    	 */
    	@SuppressWarnings("rawtypes")
		List<XYChart.Series> seriesListInfirmary = new ArrayList<>();
    	@SuppressWarnings("rawtypes")
    	List<XYChart.Series> seriesListIcu = new ArrayList<>();
    	
    	@SuppressWarnings("rawtypes")
		XYChart.Series seriesInfirmary = new XYChart.Series();
    	@SuppressWarnings("rawtypes")
    	XYChart.Series seriesIcu = new XYChart.Series();
    	seriesInfirmary.setName("Enfermaria");
    	seriesIcu.setName("UTI");
    	
    	lineChart.getXAxis().setLabel("Dias");
    	lineChart.getYAxis().setLabel("Porcentagem de ocupa��o");
    	
    	createData(lineChart, seriesListInfirmary, seriesInfirmary, rolInfirmaryLine);
    	createData(lineChart, seriesListIcu, seriesIcu, rolIcuLine);
    	
}
	
	/**
	 * M�todo respons�vel por inverter o array de dados. Como foi utilizado o LinkedHashTree, os dados s�o organizados na ordem que s�o lidos 
	 * e como a leitura � feita pelo in�cio do arquivo, os dados mais recentes s�o registrados primeiro (Os meses s�o lidos na ordem inversa). 
	 * Portanto  como � necess�rio exibir as informa��es a partir do in�cio do m�s (dia 1) � preciso inverter o array que armazena esses valores.
	 * @param rol
	 */
    public static void reverseRol(Integer[] rol) {
    	for(int i = 0; i < rol.length / 2; i++){
    	    int temp = rol[i];
    	    rol[i] = rol[rol.length - i - 1];
    	    rol[rol.length - i - 1] = temp;
    	} 
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    /**
	 * M�todo respons�vel por popular o conte�do da Series, adicionando os dados com o XYChartDATA
     * @param lineChart
     * @param listData
     * @param seriesData
     * @param rol
     */
	public static void createData(LineChart<String, Integer> lineChart, List<XYChart.Series> listData, XYChart.Series seriesData, Integer[] rol) {
    	for (int i = 0; i<rol.length; i++) {
    		seriesData.getData().add(new XYChart.Data<String, Integer>(String.format("%d", i+1), rol[i]));
    		listData.add(seriesData);
    	}
    	lineChart.getData().add(listData.get(listData.size() - 1));
    }

}

