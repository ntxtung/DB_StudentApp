package hcmiuiot.StudentApp.App;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToolbar;

import hcmiuiot.StudentApp.DatabaseHandler.DbHandler;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class DashboardController implements Initializable {
	
	@FXML
    private Label lblDash;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane holderPane;
    @FXML
    private AnchorPane sideAnchor;
    @FXML
    private Label lblMenu;
    @FXML
    private JFXToolbar toolBar;
    @FXML
    private HBox toolBarRight;
    @FXML
    private VBox overflowContainer;
    @FXML
    private ToggleButton menuHome;
    @FXML
    private ToggleButton menuAdd;
    @FXML
    private ToggleButton menuList;
    @FXML
    private ToggleButton menuLogg;

    private Parent home, add, list;
    @FXML
    private JFXButton btnLogOut;
    @FXML
    private JFXButton btnExit;
    @FXML
    private JFXButton btnHome;
    @FXML
    private JFXButton btnAdd;
    @FXML
    private JFXButton btnView;
    @FXML
    private JFXButton btnLogout;
    @FXML
    private JFXButton btnClose;
    
    @FXML
    private Label std_name;

    @FXML
    private Label id;
    
    @FXML
    private ImageView avatar;
    
    private static String studentID;
    
    public String getStudentID() {
    	return this.studentID;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createPages();
       
        try {
            ResultSet result = DbHandler.getInstance().execQuery("SELECT studentID, fName, lName, img FROM topicS.Student WHERE studentID LIKE '%"+stdId+"%'");
            result.first();
            studentID = result.getString("studentID");
            std_name.setText(result.getString("fName")+" "+result.getString("lName"));
			id.setText(studentID);
			//System.out.println(result.getBlob("img"));
			avatar.setImage(DbHandler.convertBlob2Image(result.getBlob("img")));
		
//			ResultSet logos = DbHandler.getInstance().ExecSQL("SELECT logo FROM topicS.Department where deptID='BA'");
//				    if (logos.next())
//				   	image.setImage(DbHandler.convertBlob2Image(logos.getBlob(1)));
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    //Set selected node to a content holder
    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(200));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    private void createPages() {
        try {
            home = FXMLLoader.load(getClass().getResource("Overview.fxml"));
            add = FXMLLoader.load(getClass().getResource("Register.fxml"));
//            list = FXMLLoader.load(getClass().getResource("SubjectRegistration.fxml"));
            setNode(home);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void openHome(ActionEvent event) {
        setNode(home);
    }

    @FXML
    private void openAddStudent(ActionEvent event) {
//        setNode(add);
    	 
		try {
//			FXMLLoader fxmlLoader = new FXMLLoader();
//			fxmlLoader.setLocation(getClass().getResource("FXMLDocument.fxml"));
//			holderPane = fxmlLoader.load();
//			FXMLDocumentController c = (FXMLDocumentController) fxmlLoader.getController();
			 Parent fxml = FXMLLoader.load(getClass().getResource("RegisterUI.fxml"));
			 holderPane.getChildren().removeAll();
	    	 holderPane.getChildren().setAll(fxml);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    private void openListStudent(ActionEvent event) {
        setNode(list);
    }

    @FXML
    private void logOff(ActionEvent event) {

    }

    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
    }

}
