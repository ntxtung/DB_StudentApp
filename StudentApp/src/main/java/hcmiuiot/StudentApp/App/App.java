package hcmiuiot.StudentApp.App;

import java.sql.ResultSet;

import hcmiuiot.StudentApp.DatabaseHandler.DbHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("LoginForm.fxml"));
		//ResultSet result = DbHandler.getInstance().ExecSQL("SELECT studentID, password, salt FROM topicS.Student where studentID='ITITIU16101'");
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("College Database System");
		primaryStage.setResizable(false);
		primaryStage.show();
	}
}
