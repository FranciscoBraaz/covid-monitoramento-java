package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainController {
	
	Map<String, List<Integer>> infirmaryList = new LinkedHashMap<>();
	Map<String, List<Integer>> icuList = new LinkedHashMap<>();

    @FXML
    private MenuItem btnFileOpen;

    @FXML
    private MenuItem btnClose;

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

    @FXML
    void btnFileOpenOnAction(ActionEvent event) {
    	FileChooser filechooser = new FileChooser();
    	File selectedFile = filechooser.showOpenDialog(null);
    	infirmaryList = Infirmary.parseListInfirmary(selectedFile);
    	icuList = Icu.parseListIcu(selectedFile);
    	String months [] = infirmaryList.keySet().toArray(new String[0]);
    	listView.getItems().clear();
    	for(String month: months) {
    		listView.getItems().add(month);
    	}
    }
    
	@FXML
    void listViewOnClick(MouseEvent event) {
    	lineChart.getData().clear();
    	String month = listView.getSelectionModel().getSelectedItem();
    	List<Integer> dataInfirmary = infirmaryList.get(month);
    	List<Integer> dataIcu = icuList.get(month);
    	Integer rolInfirmary [] = dataInfirmary.toArray(new Integer[0]);
    	Integer rolIcu [] = dataIcu.toArray(new Integer[0]);
    	
    	reverseRol(rolInfirmary);
    	reverseRol(rolIcu);

    	//Make List to save your XYChartSeries
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
    	lineChart.getYAxis().setLabel("Porcentagem de ocupação");
    	
    	createData(lineChart, seriesListInfirmary, seriesInfirmary, rolInfirmary);
    	createData(lineChart, seriesListIcu, seriesIcu, rolIcu);
    	
}

    public static void reverseRol(Integer[] rol) {
    	for(int i = 0; i < rol.length / 2; i++){
    	    int temp = rol[i];
    	    rol[i] = rol[rol.length - i - 1];
    	    rol[rol.length - i - 1] = temp;
    	} 
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void createData(LineChart<String, Integer> lineChart, List<XYChart.Series> listData, XYChart.Series seriesData, Integer[] rol) {
    	for (int i = 0; i<rol.length; i++) {
    		seriesData.getData().add(new XYChart.Data<String, Integer>(String.format("%d", i+1), rol[i]));
    		listData.add(seriesData);
    	}
    	lineChart.getData().add(listData.get(listData.size() - 1));
    }
    
}

