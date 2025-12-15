import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UcakBiletApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bilet_ekrani.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 1000, 650);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("Uçak Bilet Rezervasyon Sistemi");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
