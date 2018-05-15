package hcmiuiot.StudentApp.App;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.apache.commons.codec.digest.DigestUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import hcmiuiot.StudentApp.DatabaseHandler.DbHandler;
import javafx.animation.PauseTransition;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController implements Initializable{
	private Label label;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private StackPane rootPane;
    @FXML
    private ImageView imgProgress;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		handleValidation();
        imgProgress.setVisible(false);
	}
	
	@FXML
    private void login(ActionEvent event) {
		
      String user = txtUsername.getText();
      String pwd = txtPassword.getText();
      
      try {
    	ResultSet result = DbHandler.getInstance().ExecSQL("SELECT * FROM topicS.Student where studentID='" + user + "'");
    	if (result.next()) 
    	{
    		result.first();
    		String salt = result.getString("salt");
    		String pwdfromDB = result.getString("password");
    		pwd +=salt;
    		String sha256 = DigestUtils.sha256Hex(pwd);
    		System.out.println(sha256);
    		System.out.println(pwdfromDB);
    		if (sha256.equals(pwdfromDB)) 
    		{
    			  imgProgress.setVisible(true);
    		        PauseTransition pauseTransition = new PauseTransition();
    		        pauseTransition.setDuration(Duration.seconds(3));
    		        pauseTransition.setOnFinished(ev -> {
    		            completeLogin();

    		        });
    		        pauseTransition.play();
    		}
    		else
    		{
    			Alert al = new Alert(AlertType.ERROR);
        		al.setTitle("Warning");
        		al.setContentText("Please input correct id and pass ");
        		al.showAndWait();
    		}
    		
    	}
    	else {
    		Alert al = new Alert(AlertType.ERROR);
    		al.setTitle("Warning");
    		al.setContentText(" Please input correct id and pass");
    		al.showAndWait();
    	}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
 }

    private void handleValidation() {
        RequiredFieldValidator fieldValidator = new RequiredFieldValidator();
        fieldValidator.setMessage("Input required");
        fieldValidator.setIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES));
        txtUsername.getValidators().add(fieldValidator);
        txtUsername.focusedProperty().addListener((ObservableValue<? extends Boolean> o, Boolean oldVal, Boolean newVal) -> {
            if (!newVal) {
                txtUsername.validate();

            }
        });
        RequiredFieldValidator fieldValidator2 = new RequiredFieldValidator();
        fieldValidator2.setMessage("Input required");
        fieldValidator2.setIcon(new FontAwesomeIconView(FontAwesomeIcon.TIMES));
        txtPassword.getValidators().add(fieldValidator2);
        txtPassword.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue) {
                txtPassword.validate();
            }
        });

    }

    private void completeLogin() {
        btnLogin.getScene().getWindow().hide();
        try {
            imgProgress.setVisible(false);
            Stage dashboardStage = new Stage();
            dashboardStage.setTitle("");
            Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
            Scene scene = new Scene(root);
            dashboardStage.setScene(scene);
            dashboardStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
