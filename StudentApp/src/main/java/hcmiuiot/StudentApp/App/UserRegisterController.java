package hcmiuiot.StudentApp.App;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.protocol.Resultset;

import hcmiuiot.StudentApp.DatabaseHandler.DbHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class UserRegisterController {
	 	
		@FXML
	    private JFXTextField txtid;

	    @FXML
	    private JFXPasswordField txtpwd;

	    @FXML
	    private JFXTextField txtFirstName;

	    @FXML
	    private JFXTextField txtdept;

	    @FXML
	    private JFXTextField txtLastName;

	    @FXML
	    private JFXTextField txtBirthDay;
	    @FXML
	    private JFXButton btnOK;

	    @FXML
	    private ImageView logo;
	    
	    @FXML
	    public void ok(ActionEvent event) {
	    
	    String fname = txtFirstName.getText();
	    String lname = txtLastName.getText();
	    String id = txtid.getText();
	    String DOB = txtBirthDay.getText();
	    String dept = txtdept.getText();
	    String pwd =txtpwd.getText();
    	String query = "INSERT INTO `topicS`.`Student` (`studentID`, `fName`, `lName`, `birthday`,`password`, `deptID`) VALUES ('"+ id +"'," + "'"+ fname
    			+ "'," + "'" + lname +"'," + "'" + DOB + "',"+ "'" +pwd +"'," +"'" + dept +"')" ;
   // 	System.out.println(query);
    	DbHandler.getInstance().execUpdate(query);
    	//	DbHandler.execUpdate(query);
	    }
	    
		public void initialize(URL location, ResourceBundle resources) {
			System.out.println("qq");
		}
	    

}