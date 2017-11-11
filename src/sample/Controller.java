package sample;

import api.SortImages;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    @FXML
    private Button fromDirectoryBtn;
    @FXML
    private Button toDirectoryBtn;
    @FXML
    private ListView listView;
    @FXML
    private ImageView imgView;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label fromDirectory;
    @FXML
    private Label toDirectory;

    private File fromDirectoryPath;
    private File toDirectoryPath;
    private Map<String, ObservableList<PieChart.Data>> downloadedIndicoData = new HashMap<>();

    @FXML
    private void handleFromDirectoryBtn() {
        Stage primaryStage = (Stage) fromDirectoryBtn.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        fromDirectoryPath = directoryChooser.showDialog(primaryStage);
        directoryChooser.setInitialDirectory(new File("./"));

        if(fromDirectoryPath == null){
            System.out.println("No Directory selected");
        }else{
            fromDirectory.setText("From: " + fromDirectoryPath.getAbsolutePath());
            File[] listOfFiles = fromDirectoryPath.listFiles();
            ObservableList<String> list = FXCollections.observableArrayList();
            for(File file : listOfFiles) {
                list.addAll(file.getName());
            }
            listView.setItems(list);
        }
    }

    @FXML
    private void handleToDirectoryBtn() {
        Stage primaryStage = (Stage) toDirectoryBtn.getScene().getWindow();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        toDirectoryPath = directoryChooser.showDialog(primaryStage);
        directoryChooser.setInitialDirectory(new File("./"));

        if(toDirectoryPath == null){
            System.out.println("No Directory selected");
        }else{
            toDirectory.setText("To: " + toDirectoryPath.getAbsolutePath());
        }
    }

    @FXML
    private void handleListClick() {
        Object x = listView.getSelectionModel().getSelectedItem();
        if(x == null) return;

        FileInputStream input = null;

        System.out.println(fromDirectoryPath.toString() + "/" + x.toString());

        try {
            input = new FileInputStream(fromDirectoryPath.toString() + "/" + x.toString());
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        Image image = new Image(input);
        imgView.setImage(image);

        if(downloadedIndicoData.containsKey(x.toString())) {
            pieChart.setData(downloadedIndicoData.get(x.toString()));
            return;
        }

        Map<String, Double> topFive = api.SortImages.getTopFiveIndicoMatches(fromDirectoryPath.toString() + "/" + x.toString());

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (Map.Entry<String, Double> entry : topFive.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        downloadedIndicoData.put(x.toString(), pieChartData);

        pieChart.setData(pieChartData);
    }

    @FXML
    private void handleSortImagesBtn() {
        if(fromDirectoryPath == null || toDirectoryPath == null) {
            System.out.println("Not all directories are selected!");
            return;
        }

        try {
            SortImages.sortImagesByIndico(fromDirectoryPath.toString(), toDirectoryPath.toString() + "/");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
