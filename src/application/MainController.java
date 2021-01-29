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
    	String months [] = infirmaryList.keySet().toArray(new String[0]);
    	listView.getItems().clear();
    	for(String month: months) {
    		listView.getItems().add(month);
    	}
    }

    @SuppressWarnings({ "unchecked" })
	@FXML
    void listViewOnClick(MouseEvent event) {
    	lineChart.getData().clear();
    	String month = listView.getSelectionModel().getSelectedItem();
    	List<Integer> data = infirmaryList.get(month);
    	Integer rol [] = data.toArray(new Integer[0]);
    	
    	for(int i = 0; i < rol.length / 2; i++){
    	    int temp = rol[i];
    	    rol[i] = rol[rol.length - i - 1];
    	    rol[rol.length - i - 1] = temp;
    	}

    	//Make List to save your XYChartSeries
    	@SuppressWarnings("rawtypes")
		List<XYChart.Series> seriesList = new ArrayList<>();
    	
    	@SuppressWarnings("rawtypes")
		XYChart.Series series = new XYChart.Series();
    	series.setName(month);
    	
    	//Iterate over your Data
    	for (int i = 0; i<rol.length; i++) {
    	     series.getData().add(new XYChart.Data<String, Integer>(String.format("%d", i+1), rol[i]));
    	  seriesList.add(series);
    	}
    	lineChart.getData().add(seriesList.get(seriesList.size() - 1));
    }

}

