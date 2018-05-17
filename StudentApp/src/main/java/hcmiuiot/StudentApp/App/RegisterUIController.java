package hcmiuiot.StudentApp.App;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import hcmiuiot.StudentApp.DatabaseHandler.DbHandler;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class RegisterUIController implements Initializable {

    @FXML
    private FlowPane main;
    @FXML
    private JFXTreeTableView<Course> treeView;
    @FXML
    private JFXTreeTableView<Course> treeView1;
    @FXML
    private JFXButton addCoursetoDB;

    @FXML
    private JFXButton deleteCourse;
    
	private ResultSet result,result1,result2;
	private ObservableList<Course> Courses1 = FXCollections.observableArrayList();
	@FXML
	private JFXButton addCourse2;
	
	private String studentID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
		loadDB();	
		
        JFXTreeTableColumn<Course, String> courseID = new JFXTreeTableColumn<>("CourseID");
        courseID.setPrefWidth(100);
        courseID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().courseID;
            }
        });

        JFXTreeTableColumn<Course, String> departmentID = new JFXTreeTableColumn<>("Department");
        departmentID.setPrefWidth(100);
        departmentID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().deptID;
            }
        });

        JFXTreeTableColumn<Course, String> Name = new JFXTreeTableColumn<>("Name");
        Name.setPrefWidth(220);
        Name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
            	return param.getValue().getValue().name;
            }
        });
        
        
        JFXTreeTableColumn<Course, String> BeginDate = new JFXTreeTableColumn<>("Begin Date");
        BeginDate.setPrefWidth(100);
        BeginDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().beginDate;
            }
        });
        
        JFXTreeTableColumn<Course, String> EndDate = new JFXTreeTableColumn<>("End Date");
        EndDate.setPrefWidth(100);
        EndDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().endDate;
            }
        });
        
        JFXTreeTableColumn<Course, Number> Fee = new JFXTreeTableColumn<>("Fee");
        Fee.setPrefWidth(90);
        Fee.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().fee;
            }
        });
        
        JFXTreeTableColumn<Course, Number> NumberOfCredits = new JFXTreeTableColumn<>("#Credits");
        NumberOfCredits.setPrefWidth(80);
        NumberOfCredits.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().numberOfCredit;
            }
        });
        
        JFXTreeTableColumn<Course, Number> MaxSlot = new JFXTreeTableColumn<>("Max Slot");
        MaxSlot.setPrefWidth(80);
        MaxSlot.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().maxSlot;
            }
        });
        
        JFXTreeTableColumn<Course, String> Room = new JFXTreeTableColumn<>("Room");
        Room.setPrefWidth(80);
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
        		int availableSlot = 99;
        		courses.add(new Course(courseid,deptid,name,begin_date,end_date,fee,number_of_credits,max_slot,room, availableSlot));
        		
        	}
		} catch (SQLException e) {
			e.printStackTrace();
		}

        final TreeItem<Course> root = new RecursiveTreeItem<Course>(courses, RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(courseID, departmentID, Name,BeginDate,EndDate,Fee,NumberOfCredits,Room,MaxSlot,Slot);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
        
      //===================================================================================================================================================================================//
        // T  A  B  L E 2 //
        
        
        JFXTreeTableColumn<Course, String> selectedCourseID = new JFXTreeTableColumn<>("CourseID");
        selectedCourseID.setPrefWidth(150);
        selectedCourseID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().courseID;
            }
        });

        JFXTreeTableColumn<Course, String> selectedDepartmentID = new JFXTreeTableColumn<>("Department");
        selectedDepartmentID.setPrefWidth(150);
        selectedDepartmentID.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().name;
            }
        });

        JFXTreeTableColumn<Course, String> selectedName = new JFXTreeTableColumn<>("Name");
        selectedName.setPrefWidth(150);
        selectedName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().deptID;
            }
        });
        
        
        JFXTreeTableColumn<Course, String> selectedBeginDate = new JFXTreeTableColumn<>("Begin Date");
        selectedBeginDate.setPrefWidth(150);
        selectedBeginDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().beginDate;
            }
        });
        
        JFXTreeTableColumn<Course, String> selectedEndDate = new JFXTreeTableColumn<>("End Date");
        selectedEndDate.setPrefWidth(150);
        selectedEndDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().endDate;
            }
        });
        
        JFXTreeTableColumn<Course, Number> selectedFee = new JFXTreeTableColumn<>("Fee");
        selectedFee.setPrefWidth(150);
        selectedFee.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().fee;
            }
        });
        
        JFXTreeTableColumn<Course, Number> selectedNumberOfCredits = new JFXTreeTableColumn<>("Number of Credits");
        selectedNumberOfCredits.setPrefWidth(150);
        selectedNumberOfCredits.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().numberOfCredit;
            }
        });
        
        JFXTreeTableColumn<Course, Number> selectedMaxSlot = new JFXTreeTableColumn<>("Max Slot");
        selectedMaxSlot.setPrefWidth(150);
        selectedMaxSlot.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().maxSlot;
            }
        });
        
        JFXTreeTableColumn<Course, Number> selectedAvailableSlot = new JFXTreeTableColumn<>("Available Slot");
        selectedAvailableSlot.setPrefWidth(150);
        selectedAvailableSlot.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TreeTableColumn.CellDataFeatures<Course,Number> param) {
                return param.getValue().getValue().availableSlot;
            }
        });
        
        JFXTreeTableColumn<Course, String> selectedRoom = new JFXTreeTableColumn<>("Room");
        selectedRoom.setPrefWidth(150);
        selectedRoom.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Course, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Course, String> param) {
                return param.getValue().getValue().room;
            }
        });

//        ObservableList<Course> Courses1 = FXCollections.observableArrayList();
//        try {
//        	System.out.println(" ăn lồn");
//        	//while (result1.next()) {
//        	for(int i = 0 ; i <= result1.getMetaData().getColumnCount() ; i++) {
//        			result2 = DbHandler.getConnection().createStatement().executeQuery("SELECT * FROM topicS.Course WHERE courseID LIKE '%"+result1.getString("CourseID")+"%'");
//        			result2.first();
//        			System.out.println(result2.getString("name"));
//        			String courseid = result2.getString("courseID");
//            		String deptid = result2.getString("deptID");
//            		String name = result2.getString("name");
//            		String begin_date = result2.getString("begin_date");
//            		String end_date = result2.getString("end_date");
//            		double fee = result2.getDouble("fee");
//            		int number_of_credits = result2.getInt("num_of_credits");
//            		int max_slot = result2.getInt("max_slot");
//            		String room = result2.getString("room");
//            		ResultSet available = DbHandler.getConnection().createStatement().executeQuery("select topicS.GetAvaSlot('"+courseid+"')");
//    				available.first();
//    				int availableSlot = available.getInt("topicS.GetAvaSlot('"+courseid+"')");
//            		//int availableSlot =  result2.getInt("a");
//            		Courses1.add(new Course(courseid,deptid,name,begin_date,end_date,fee,number_of_credits,max_slot,room, availableSlot));
//        			System.out.println(result1.getString("CourseID"));
//        			result1.next();
//        			}
//        	 final TreeItem<Course> root1 = new RecursiveTreeItem<Course>(Courses1, RecursiveTreeObject::getChildren);
//			 treeView1.setRoot(root1);
//		        treeView1.setShowRoot(false);
//        			
////        			System.out.println("dm chay coi dm");
//        		
//       		
//    
//        	
//		} catch (SQLException e) {
//			e.printStackTrace();
//			
//		}        
//        try {
//        	System.out.println(" ăn lồn");
//        	while (result1.next()) {
//        	//for(int i = 0 ; i <= result1.getMetaData().getColumnCount() ; i++) {
//        		DbHandler.getInstance();
//					//	Connection connect = DbHandler.getConnection();
//        			result2 = DbHandler.ExecSQL("SELECT * FROM topicS.Course WHERE courseID LIKE '%"+result1.getString("CourseID")+"%'");
//        			result2.first();
//        			System.out.println(result2.getString("name"));
//        			String courseid = result2.getString("courseID");
//            		String deptid = result2.getString("deptID");
//            		String name = result2.getString("name");
//            		String begin_date = result2.getString("begin_date");
//            		String end_date = result2.getString("end_date");
//            		double fee = result2.getDouble("fee");
//            		int number_of_credits = result2.getInt("num_of_credits");
//            		int max_slot = result2.getInt("max_slot");
//            		String room = result2.getString("room");
////            		ResultSet available = connect.createStatement().executeQuery("select topicS.GetAvaSlot('"+courseid+"')");
////    				available.first();
////    				int availableSlot = available.getInt("topicS.GetAvaSlot('"+courseid+"')");
//            		int availableSlot = 99;
//            		//int availableSlot =  result2.getInt("a");
//            		Courses1.add(new Course(courseid,deptid,name,begin_date,end_date,fee,number_of_credits,max_slot,room, availableSlot));
//        			System.out.println(result1.getString("CourseID"));
//    			result1.next();
//        			}
//        	 final TreeItem<Course> root1 = new RecursiveTreeItem<Course>(Courses1, RecursiveTreeObject::getChildren);
//			 treeView1.setRoot(root1);
//		        treeView1.setShowRoot(false);
//        			
////        			System.out.println("dm chay coi dm");
//        		
//       		
//    
//        	
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
        loadSelectedCourse();
        treeView1.getColumns().setAll(selectedCourseID, selectedDepartmentID, selectedName,selectedBeginDate,selectedEndDate,selectedFee,selectedNumberOfCredits,selectedMaxSlot,selectedAvailableSlot,selectedRoom);
       
        treeView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                onEdit();
            }
        });
        
    }
	
    @FXML
    private void filter(ActionEvent event) {
    }


    @FXML
    void addCoursetoDB(MouseEvent event) {
    String StudentID = LoginController.getID();
    for(int i = 0 ; i< Courses1.size();i++) {   	
    String khoa = Courses1.get(i).getCourseID();
    try {
		insertCourse(StudentID, khoa);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
	
   

    System.out.println(khoa);
    		}
    }
    
    @FXML
    void deleteCourseFromDB(MouseEvent event) {
    	TreeItem<Course> selection = treeView1.getSelectionModel().getSelectedItem();
    	if (selection != null) {
    		 String courseid = selection.getValue().getCourseID();
    		 String studentid = LoginController.getID();
    		 try {
				deleteCourse(courseid,studentid);
				loadSelectedCourse();
				 final TreeItem<Course> root1 = new RecursiveTreeItem<Course>(Courses1, RecursiveTreeObject::getChildren);
				 treeView1.setRoot(root1);
			        treeView1.setShowRoot(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	
    		
    	}
    }
    
    
    
    public void onEdit() {
    	TreeItem<Course> selection = treeView.getSelectionModel().getSelectedItem();
    	System.out.println("ĐMCS");
    	if (selection != null) {
    		 
    		 String courseid = selection.getValue().getCourseID();
				String deptid = selection.getValue().getDepartmentID();
				String name = selection.getValue().getDepartmentID();
				String begin_date = selection.getValue().getDepartmentID();
				String end_date = selection.getValue().getDepartmentID();
				double fee = selection.getValue().getFee();
				int number_of_credits = selection.getValue().getNumberOfCredit();
				int max_slot = selection.getValue().getMaxSlot();
				String room = selection.getValue().getRoom();
				int availableSlot =selection.getValue().getAvailableSlot();
			Courses1.add(new Course(courseid,deptid,name,begin_date,end_date,fee,number_of_credits,max_slot,room, availableSlot));
			 final TreeItem<Course> root1 = new RecursiveTreeItem<Course>(Courses1, RecursiveTreeObject::getChildren);
			 treeView1.setRoot(root1);
		        treeView1.setShowRoot(false);
    		
    	}
    }
    

    public void loadDB() {
		try {
			DbHandler.getInstance();
			//			Connection connect = null;
//			connect.createStatement().executeQuery("USE topicS");
			result = DbHandler.ExecSQL("SELECT * FROM topicS.Course");
			result.first();

			result1 = DbHandler.getConnection().createStatement().executeQuery("SELECT * FROM topicS.Enroll WHERE studentID LIKE '%"+LoginController.getID()+"%'");
			result1.first();

			}
		 catch (SQLException e) {
			e.printStackTrace();
		}	
    }
    public void insertCourse(String StudentID,String CourseID) throws SQLException , Exception {
    	Connection connect = DbHandler.getConnection();
    	System.out.println("Adding");
    	PreparedStatement pr = connect.prepareStatement("INSERT IGNORE INTO `topicS`.`Enroll`(`studentID`,`courseID`) VALUES (?,?)");
    	pr.setString(1, StudentID);
    	pr.setString(2, CourseID);
    	pr.execute();
    	pr.close();
    }
    public void deleteCourse(String CourseID, String StudentID) throws SQLException , Exception {
    	Connection connect = DbHandler.getConnection();
    	System.out.println("deleting");
    	PreparedStatement pr = connect.prepareStatement("DELETE FROM `topicS`.`Enroll` WHERE `courseID`=? AND `studentID`=?");
    	pr.setString(1, CourseID);
    	pr.setString(2, StudentID);
    	pr.execute();
    	pr.close();
    }
    public void loadSelectedCourse() {
    	 ObservableList<Course> Courses1 = FXCollections.observableArrayList();
         try {
         	System.out.println(" ăn lồn");
         	//while (result1.next()) {
         	for(int i = 0 ; i <= result1.getMetaData().getColumnCount() ; i++) {
         			result2 = DbHandler.getConnection().createStatement().executeQuery("SELECT * FROM topicS.Course WHERE courseID LIKE '%"+result1.getString("CourseID")+"%'");
         			result2.first();
         			System.out.println(result2.getString("name"));
         			String courseid = result2.getString("courseID");
             		String deptid = result2.getString("deptID");
             		String name = result2.getString("name");
             		String begin_date = result2.getString("begin_date");
             		String end_date = result2.getString("end_date");
             		double fee = result2.getDouble("fee");
             		int number_of_credits = result2.getInt("num_of_credits");
             		int max_slot = result2.getInt("max_slot");
             		String room = result2.getString("room");
             		ResultSet available = DbHandler.getConnection().createStatement().executeQuery("select topicS.GetAvaSlot('"+courseid+"')");
     				available.first();
     				int availableSlot = available.getInt("topicS.GetAvaSlot('"+courseid+"')");
             		//int availableSlot =  result2.getInt("a");
             		Courses1.add(new Course(courseid,deptid,name,begin_date,end_date,fee,number_of_credits,max_slot,room, availableSlot));
         			System.out.println(result1.getString("CourseID"));
         			result1.next();
         			}
         	 final TreeItem<Course> root1 = new RecursiveTreeItem<Course>(Courses1, RecursiveTreeObject::getChildren);
 			 treeView1.setRoot(root1);
 		        treeView1.setShowRoot(false);
         			
//         			System.out.println("dm chay coi dm");
         		
        		
     
         	
 		} catch (SQLException e) {
 			e.printStackTrace();
 			
 		}        
         try {
         	System.out.println(" ăn lồn");
         	while (result1.next()) {
         	//for(int i = 0 ; i <= result1.getMetaData().getColumnCount() ; i++) {
         		DbHandler.getInstance();
 					//	Connection connect = DbHandler.getConnection();
         			result2 = DbHandler.ExecSQL("SELECT * FROM topicS.Course WHERE courseID LIKE '%"+result1.getString("CourseID")+"%'");
         			result2.first();
         			System.out.println(result2.getString("name"));
         			String courseid = result2.getString("courseID");
             		String deptid = result2.getString("deptID");
             		String name = result2.getString("name");
             		String begin_date = result2.getString("begin_date");
             		String end_date = result2.getString("end_date");
             		double fee = result2.getDouble("fee");
             		int number_of_credits = result2.getInt("num_of_credits");
             		int max_slot = result2.getInt("max_slot");
             		String room = result2.getString("room");
//             		ResultSet available = connect.createStatement().executeQuery("select topicS.GetAvaSlot('"+courseid+"')");
//     				available.first();
//     				int availableSlot = available.getInt("topicS.GetAvaSlot('"+courseid+"')");
             		int availableSlot = 99;
             		//int availableSlot =  result2.getInt("a");
             		Courses1.add(new Course(courseid,deptid,name,begin_date,end_date,fee,number_of_credits,max_slot,room, availableSlot));
         			System.out.println(result1.getString("CourseID"));
     			result1.next();
         			}
         	 final TreeItem<Course> root1 = new RecursiveTreeItem<Course>(Courses1, RecursiveTreeObject::getChildren);
 			 treeView1.setRoot(root1);
 		        treeView1.setShowRoot(false);
         			
//         			System.out.println("dm chay coi dm");
         		
        		
     
         	
 		} catch (SQLException e) {
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
        public String getCourseID () {
        	return courseID.get();
        }
        public String getDepartmentID () {
        	return deptID.get();
        }
        public String getName () {
        	return name.get();
        }
        public String getBeginDate() {
        	return beginDate.get();
        }
        public String getEndDate() {
        	return endDate.get();
        }
        public  double getFee () {
        	return  (double) fee.getValue();
        }
        public int getNumberOfCredit  () {
        	return numberOfCredit.get();
        }
        public int getMaxSlot  () {
        	return maxSlot.get();
        }
        public int getAvailableSlot  () {
        	return availableSlot.get();
        }
        public String getRoom () {
        	return room.get();
        }
    }
}
