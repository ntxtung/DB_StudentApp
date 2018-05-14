package hcmiuiot.StudentApp.App;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;


import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

public class RegisterUIController implements Initializable {

    @FXML
    private FlowPane main;
    @FXML
    private JFXTreeTableView<Course> treeView;
    private DBConnector db;
	private ResultSet result;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    	db = new DBConnector();
		loadDB();
		
		
        JFXTreeTableColumn<Course, String> courseID = new JFXTreeTableColumn<>("CourseID");
        courseID.setPrefWidth(150);
        courseID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().courseID;
            }
        });

        JFXTreeTableColumn<Course, String> departmentID = new JFXTreeTableColumn<>("Department");
        departmentID.setPrefWidth(150);
        departmentID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().deptID;
            }
        });

        JFXTreeTableColumn<Course, String> Name = new JFXTreeTableColumn<>("Name");
        Name.setPrefWidth(150);
        Name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().name;
            }
        });
        
        
        JFXTreeTableColumn<Course, String> BeginDate = new JFXTreeTableColumn<>("Begin Date");
        BeginDate.setPrefWidth(150);
        BeginDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().beginDate;
            }
        });
        
        JFXTreeTableColumn<Course, String> EndDate = new JFXTreeTableColumn<>("End Date");
        EndDate.setPrefWidth(150);
        EndDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().endDate;
            }
        });
        
        JFXTreeTableColumn<Course, Number> Fee = new JFXTreeTableColumn<>("Fee");
        Fee.setPrefWidth(150);
        Fee.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().fee;
            }
        });
        
        JFXTreeTableColumn<Course, Number> NumberOfCredits = new JFXTreeTableColumn<>("Number of Credits");
        NumberOfCredits.setPrefWidth(150);
        NumberOfCredits.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().numberOfCredit;
            }
        });
        
        JFXTreeTableColumn<Course, Number> MaxSlot = new JFXTreeTableColumn<>("Max Slot");
        MaxSlot.setPrefWidth(150);
        MaxSlot.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().maxSlot;
            }
        });
        
        JFXTreeTableColumn<Course, String> Room = new JFXTreeTableColumn<>("Room");
        Room.setPrefWidth(150);
        Room.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().room;
            }
        });
        
        JFXTreeTableColumn<Course, Number> Slot = new JFXTreeTableColumn<>("Available");
        Slot.setPrefWidth(150);
        Slot.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course, Number> param) {
                return param.getValue().getValue().availableSlot;
            }
        });
          
        ObservableList<Course> courses = FXCollections.observableArrayList();
       
        try {
        	while (result.next()) {
        		String courseid = result.getString("courseID");
        		String deptid = result.getString("deptID");
        		String name = result.getString("name");
        		String begin_date = result.getString("begin_date");
        		String end_date = result.getString("end_date");
        		double fee = result.getDouble("fee");
        		int number_of_credits = result.getInt("num_of_credits");
        		int max_slot = result.getInt("max_slot");
        		String room = result.getString("room");
        		int availableSlot =  result.getInt("a");
        		courses.add(new Course(courseid,deptid,name,begin_date,end_date,fee,number_of_credits,max_slot,room, availableSlot));
        		
        	}
		} catch (SQLException e) {
			e.printStackTrace();
		}

        final TreeItem<Course> root = new RecursiveTreeItem<Course>(courses, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(courseID, departmentID, Name,BeginDate,EndDate,Fee,NumberOfCredits,Room,MaxSlot,Slot);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }
	
    @FXML
    private void filter(ActionEvent event) {
    }
    public void loadDB() {
		try {
			Connection connect = db.connectDB();
			connect.createStatement().executeQuery("USE topicS");
			result = connect.createStatement().executeQuery("SELECT *, GetAvaSlot(topicS.Course.courseID) AS a FROM topicS.Course");
			//result.first();
			}
		 catch (SQLException e) {
			e.printStackTrace();
		}	
    }

    class Course extends RecursiveTreeObject<Course> {

        StringProperty deptID;
        StringProperty courseID;
        StringProperty name;
        StringProperty beginDate;
        StringProperty endDate;
        ObservableValue<Number> fee;
        IntegerProperty numberOfCredit;
        IntegerProperty maxSlot;
        StringProperty room;
        IntegerProperty availableSlot;
        
        public Course(String courseID,String deptID,String name,String beginDate,String endDate, double fee, int numberOfCredit,int maxSlot,String room, int availableSlot) {
            this.courseID = new SimpleStringProperty(courseID);
            this.deptID = new SimpleStringProperty(deptID);
            this.name = new SimpleStringProperty(name);
            this.beginDate = new SimpleStringProperty(beginDate);
            this.endDate = new SimpleStringProperty(endDate);
            this.fee = new SimpleDoubleProperty(fee);
            this.numberOfCredit = new SimpleIntegerProperty(numberOfCredit);
            this.maxSlot = new SimpleIntegerProperty(maxSlot);
            this.room = new SimpleStringProperty(room);
            this.availableSlot = new SimpleIntegerProperty(availableSlot);
        }

    }
}
