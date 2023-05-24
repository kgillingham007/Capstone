package gillingham.capstone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**Main application class. Starts the program
 * @author Kenneth Gillingham
 * @version 2.0
 */
public class Main extends Application {

    /**Launches the main screen of the application
     *
     * @param stage input stage
     * @throws IOException if there is an issue accessing the main screen
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("loginscreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 700);
        stage.setTitle("Software Two - C195");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
