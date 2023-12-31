package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import config.ApplicationConfig;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.util.Duration;
import service.JobService;
import service.NotificationService;
import service.UserService;
import support.dto.User;
import support.dto.ChangePasswordRequest;
import support.dto.Job;
import support.dto.Notification;
import support.dto.Role;
import support.result.CreationResult;
import support.result.UpdateResult;
import support.session.UserSession;
import javafx.stage.FileChooser;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

public class MainPageController {

	private UserService userService = ApplicationConfig.getUserService();
	
	private JobService jobService = ApplicationConfig.getJobService();
	
	private NotificationService notificationService = ApplicationConfig.getNotificationService();

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;
	
	@FXML
	private TextField companyNameText;
	
	@FXML
	private TextField searchTextField;
	
	@FXML
	private TextField myJobSearchTextField;

	@FXML
	private Label addressLabel;

	@FXML
	private TextArea addressText;

	@FXML
	private AnchorPane anchorpane;

	@FXML
	private Button applyButton;

	@FXML
	private Rectangle background;

	@FXML
	private Label cPasswordLabel;

	@FXML
	private Button changePassButton;

	@FXML
	private PasswordField currentPasswordText;

	@FXML
	private Label cvLabel;

	@FXML
	private TextField headerTextField;

	@FXML
	private DatePicker jobDate;

	@FXML
	private Pane jobListPane;
	
	@FXML
	private Pane applicationsPane;
	
    @FXML
    private TableColumn<Job, String> header;
    
    
    @FXML
    private TableColumn<User, String> applicationFullName;
    
    
    @FXML
    private TableColumn<User, String> applicationEmail;
    
    @FXML
    private TableColumn<User, String> applicationPhone;
    
    @FXML
    private TableColumn<Job, String> myAppCompanyName;
    
    @FXML
    private TableColumn<Job, String> myAppHeader;
    
    @FXML
    private TableColumn<Job, String> myAppSalary;
    
    @FXML
    private TableColumn<Job, String> myAppDate;
    
    @FXML
    private TableColumn<User, String> applicationAddress;

	@FXML
	private TableView<Job> jobListTable;
	
	@FXML
	private TableView<User> applicationList;

	@FXML
	private TextField jobSalaryField;

	@FXML
	private TextArea jobTextArea;

	@FXML
	private Label jobs;

	@FXML
	private Label jobs1;

	@FXML
	private Label logout;

	@FXML
	private Pane menu;

	@FXML
	private Label menuLabel;

	@FXML
	private Label myApplications;

	@FXML
	private Pane myApplicationsPane;

	@FXML
	private TableView<Job> myApplicationsTableView;

	@FXML
	private TableView<Job> myJobListTableView;

	@FXML
	private Spinner<Integer> myJobSalaryField;

	@FXML
	private Button seeApplicationsButton;

	@FXML
	private TextField myJobsHeaderText;
	
	@FXML
	private TextField companyNameTextField;

	@FXML
	private Pane myJobsPane;
	

    @FXML
    private TableColumn<Notification, Integer> notificationId;

    
    @FXML
    private TableColumn<Notification, String> notificationMessage;
    
    @FXML
    private TableColumn<Notification, Timestamp> notificationDate;
    
    @FXML
    private Label switchToAdminButton;
    
    
	@FXML
	private TextArea myJobsTextArea;

	@FXML
	private Label nPasswordLabel;

	@FXML
	private Label nPasswordLabel2;

	@FXML
	private Label nameLabel;

	@FXML
	private TextField nameText;

	@FXML
	private Rectangle navbar;

	@FXML
	private PasswordField newPasswordText;

	@FXML
	private PasswordField newPasswordText2;

	@FXML
	private Button newPostButton;

	@FXML
	private Label notificationCounter;

	@FXML
	private Pane notificationPane;

	@FXML
	private TableView<Notification> notificationTableView;

	@FXML
	private Label notifications;

	@FXML
	private Label phoneLabel;

	@FXML
	private TextField phoneText;
	
	@FXML
	private TextField salaryText;

	@FXML
	private Label profile;

	@FXML
	private Pane profilePane;

	@FXML
	private Button removeApplicationButton;

	@FXML
	private Button removePostButton;

	@FXML
	private Label surnameLabel;

	@FXML
	private TextField surnameText;

	@FXML
	private Button updateJobButton;
	
    @FXML
    private Button viewApplicatorCVButton;
    

	@FXML
	private Button updateProfileButton;

	@FXML
	private Button uploadCvButton;

	@FXML
	private Label viewCV;
	
    @FXML
    private TableColumn<Job, String> myJobCompanyName;
    
    @FXML
    private TableColumn<Job, String> myJobHeader;
    
    @FXML
    private TableColumn<Job, String> myJobDescription;
    
    @FXML
    private TableColumn<Job, String> myJobSalary;

	@FXML
	void initialize() {
		profilePane.setVisible(true);

		fillUserDetails();
		fillJobListTableView();
		applyButton.setVisible(false);
		newPostButton.setVisible(true);
		 Date now = new Date();
         Timestamp time = new Timestamp(now.getTime()); 
		jobDate.setValue(time.toLocalDateTime().toLocalDate());
		if(UserSession.getUser().getRole() == Role.ADMIN) {
			switchToAdminButton.setVisible(true);
		}else {
			switchToAdminButton.setVisible(false);
		}
		fillMyJobListTableView();
	}

	private void fillJobListTableView() {	
		header.setCellValueFactory(new PropertyValueFactory<>("jobHeaderWithCompanyName"));
		jobListTable.setItems(jobService.getJobList());
	}
	
	private void fillMyJobListTableView() {
		myJobCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
		myJobHeader.setCellValueFactory(new PropertyValueFactory<>("jobHeader"));
		myJobDescription.setCellValueFactory(new PropertyValueFactory<>("jobText"));
		myJobSalary.setCellValueFactory(new PropertyValueFactory<>("jobSalary"));
		
		myJobListTableView.setItems(jobService.getJobListByUserId(UserSession.getUserId()));
	}
	
	@FXML
	private void myJobListClicked() {
		int index = myJobListTableView.getSelectionModel().getSelectedIndex();
		Job j = jobService.getJobListByUserId(UserSession.getUserId()).get(index);
		myJobsHeaderText.setText(j.getJobHeader());
		myJobsTextArea.setText(j.getJobText());
		companyNameText.setText(j.getCompanyName());
		salaryText.setText(j.getJobSalary() + "");
	}
	
	@FXML
	private void updateJobPost() {
		int index = myJobListTableView.getSelectionModel().getSelectedIndex();
		Job j = jobService.getJobListByUserId(UserSession.getUserId()).get(index);
		j.setJobHeader(myJobsHeaderText.getText())
		.setCompanyName(companyNameText.getText())
		.setJobText(myJobsTextArea.getText())
		.setJobSalary(Integer.parseInt(salaryText.getText()));
		UpdateResult result = jobService.updateJobPost(j);
		if(result.isSuccess()) {
			Alert alert1 = new Alert(AlertType.INFORMATION);
			alert1.setTitle("SUCCESS");
			alert1.setHeaderText("Success");
			alert1.setContentText("Job has been updated.");
			alert1.showAndWait();
			fillMyJobListTableView();
		}else {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("ERROR");
			alert1.setHeaderText("Failed");
			alert1.setContentText(result.getMessage());
			alert1.showAndWait();
		}
	}
	
	@FXML
	private void removePost() {
		int index = myJobListTableView.getSelectionModel().getSelectedIndex();
		Job j = jobService.getJobListByUserId(UserSession.getUserId()).get(index);
		jobService.deleteJobPost(j);
		fillMyJobListTableView();
	}
	
	@FXML
	private void jobSelected() {
		headerTextField.setEditable(false);
		jobTextArea.setEditable(false);
		jobSalaryField.setEditable(false);
		companyNameTextField.setEditable(false);
		jobDate.setEditable(false);
		
		int index = jobListTable.getSelectionModel().getSelectedIndex();
		Job j = jobService.getJobList().get(index);
		headerTextField.setText(j.getJobHeader());
		jobTextArea.setText(j.getJobText());
		companyNameTextField.setText(j.getCompanyName());
		jobSalaryField.setText(j.getJobSalary() + "");
		jobDate.setValue(j.getJobPostedDate().toLocalDateTime().toLocalDate());
		
		applyButton.setVisible(true);
		newPostButton.setVisible(false);
	}
	
	@FXML
	private void postNewJobLabelClick() {
		headerTextField.setEditable(true);
		jobTextArea.setEditable(true);
		jobSalaryField.setEditable(true);
		companyNameTextField.setEditable(true);
		headerTextField.clear();
		jobTextArea.clear();
		jobSalaryField.clear();
		companyNameTextField.clear();
		Date now = new Date();
        Timestamp time = new Timestamp(now.getTime()); 
		jobDate.setValue(time.toLocalDateTime().toLocalDate());
		
		applyButton.setVisible(false);
		newPostButton.setVisible(true);
		
	}

	
	@FXML
	private void viewUserCV() {
		int index = applicationList.getSelectionModel().getSelectedIndex();
		if(index < 0) {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("ERROR");
			alert1.setHeaderText("Failed");
			alert1.setContentText("Select an applicator from list.");
			alert1.showAndWait();
			return;
		}
		int userId = jobService.getApplications(jobId).get(index).getUserId();
		String url = userService.getCV(userId).getFilePath();
		if (url == null) {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("ERROR");
			alert1.setHeaderText("Failed");
			alert1.setContentText("User doesn't have any uploaded cv!");
			alert1.showAndWait();
		} else {
			try {
				Job j = jobService.getJobByJobId(jobId);
				Notification n = new Notification()
						.setNotificationUserId(userId)
						.setNotificationMessage(j.getJobHeaderWithCompanyName() +  " has viewed your cv.");
				notificationService.sendNotification(n);
				String os = System.getProperty("os.name").toLowerCase();
				if (os.indexOf("win") >= 0) {
					Runtime rt = Runtime.getRuntime();
					rt.exec("rundll32 url.dll,FileProtocolHandler " + url);

				} else if (os.indexOf("mac") >= 0) {
					Runtime rt = Runtime.getRuntime();
					rt.exec("open " + url);
				} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
					Runtime rt = Runtime.getRuntime();
					String[] browsers = { "google-chrome", "firefox", "mozilla", "epiphany", "konqueror", "netscape",
							"opera", "links", "lynx" };

					StringBuffer cmd = new StringBuffer();
					for (int i = 0; i < browsers.length; i++)
						if (i == 0)
							cmd.append(String.format("%s \"%s\"", browsers[i], url));
						else
							cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
					rt.exec(new String[] { "sh", "-c", cmd.toString() });
				}
			} catch (Exception e) {
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("ERROR");
				alert1.setHeaderText("Failed");
				alert1.setContentText(e.getMessage());
				alert1.showAndWait();
			}
		}
	}
	
	
	@FXML
	private void applyForJob() {
		int index = jobListTable.getSelectionModel().getSelectedIndex();
		if(index >= 0) {
			Job j = jobService.getJobList().get(index);
			CreationResult result = jobService.applyForJob(j,UserSession.getUserId());
			if(result.isSuccess()) {
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("SUCCESS");
				alert1.setHeaderText("Success");
				alert1.setContentText("Applied for job.");
				alert1.showAndWait();
				fillJobListTableView();
				Notification n = new Notification()
						.setNotificationUserId(j.getJobUserId())
						.setNotificationMessage(UserSession.getUser().getFullName() + " has applied for " + j.getJobHeaderWithCompanyName() + ".");
				notificationService.sendNotification(n);
			}else {
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("ERROR");
				alert1.setHeaderText("Failed");
				alert1.setContentText(result.getMessage());
				alert1.showAndWait();
			}
		}else {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("ERROR");
			alert1.setHeaderText("Failed");
			alert1.setContentText("Select job from list");
			alert1.showAndWait();
		}

	}
	
	@FXML
	private void postNewJob() {
		if(areJobFieldsEmpty()) {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("ERROR");
			alert1.setHeaderText("Failed");
			alert1.setContentText("Fields must not be empty");
			alert1.showAndWait();
		}else {
			Date now = new Date();
	        Timestamp time = new Timestamp(now.getTime()); 
			Job j = new Job()
					.setJobHeader(headerTextField.getText())
					.setCompanyName(companyNameTextField.getText())
					.setJobText(jobTextArea.getText())
					.setJobSalary(Integer.parseInt(jobSalaryField.getText()))
					.setJobPostedDate(time)
					.setJobUserId(UserSession.getUserId());
			
			CreationResult result = jobService.postNewJob(j);
			if(result.isSuccess()) {
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("SUCCESS");
				alert1.setHeaderText("Success");
				alert1.setContentText("Job posted successfully");
				alert1.showAndWait();
				fillJobListTableView();
			}else {
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("ERROR");
				alert1.setHeaderText("Failed");
				alert1.setContentText(result.getMessage());
				alert1.showAndWait();
			}
		}
	}
	
	private boolean areJobFieldsEmpty() {
		boolean isFieldsEmpty = headerTextField.getText().isEmpty() || jobTextArea.getText().isEmpty() ||  jobSalaryField.getText().isEmpty() || companyNameTextField.getText().isEmpty();
		return isFieldsEmpty;
	}

	private void fillUserDetails() {
		nameText.setText(UserSession.getUserName());
		surnameText.setText(UserSession.getUser().getSurname());
		phoneText.setText(UserSession.getUser().getPhoneNumber());
		addressText.setText(UserSession.getUser().getAdress());
		if (!UserSession.getUser().hasUserCV()) {
			viewCV.setVisible(false);
		} else {
			viewCV.setVisible(true);
		}
	}

	@FXML
	void menuClick(MouseEvent event) {
		if (menu.isVisible()) {
			menu.setVisible(false);
			setTransition(menuLabel, 93, 14, 6, 6, 400);

		} else {
			setTransition(menu, -1024, -65, 0, 0, 400);
			setTransition(menuLabel, 6, 6, 93, 14, 450);
			updateNotificationCounter();
			
			menu.setVisible(true);
		}

	}

	private void updateNotificationCounter() {
		Integer counter = notificationService.getNotificationCount(UserSession.getUserId());
		if(counter == 0) {
			notificationCounter.setVisible(false);
		}else {
			notificationCounter.setVisible(true);
			notificationCounter.setText(counter.toString());
		}
	}

	private void setTransition(Node n, double locationFromX, double locationFromY, double locationToX,
			double locationToY, double millis) {
		TranslateTransition transition = new TranslateTransition();
		transition.setNode(n);
		Duration d = Duration.millis(millis);
		transition.setDuration(d);
		transition.setFromX(locationFromX - n.getLayoutX());
		transition.setFromY(locationFromY - n.getLayoutY());
		transition.setToX(locationToX - n.getLayoutX());
		transition.setToY(locationToY - n.getLayoutY());
		transition.play();
	}

	@FXML
	private void openProfile() {
		setPanesVisibleFalse();
		setTransition(profilePane, profilePane.getLayoutX(), profilePane.getLayoutY() - 1000, profilePane.getLayoutX(),
				profilePane.getLayoutY(), 500);
		profilePane.setVisible(true);
		fillUserDetails();
	}

	@FXML
	private void openJobList() {
		setPanesVisibleFalse();
		fillJobListTableView();
		setTransition(jobListPane, jobListPane.getLayoutX(), jobListPane.getLayoutY() - 1000, jobListPane.getLayoutX(),
				jobListPane.getLayoutY(), 400);
		jobListPane.setVisible(true);
	}

	@FXML
	void openMyJobList() {
		setPanesVisibleFalse();
		fillMyJobListTableView();
		setTransition(myJobsPane, myJobsPane.getLayoutX(), myJobsPane.getLayoutY() - 1000, myJobsPane.getLayoutX(),
				myJobsPane.getLayoutY(), 400);
		myJobsPane.setVisible(true);

	}

	@FXML
	void openNotificationPane() {
		setPanesVisibleFalse();
		setTransition(notificationPane, notificationPane.getLayoutX(), notificationPane.getLayoutY() - 1000,
				notificationPane.getLayoutX(), notificationPane.getLayoutY(), 400);
		notificationPane.setVisible(true);
		fillNotificationTable();
	}
	
	private void fillNotificationTable() {
		ObservableList<Notification> notificationList = notificationService.getNotifications(UserSession.getUserId());
		
		notificationId.setCellValueFactory(new PropertyValueFactory<>("notificationLocalId"));
		notificationMessage.setCellValueFactory(new PropertyValueFactory<>("notificationMessage"));
		notificationDate.setCellValueFactory(new PropertyValueFactory<>("notificationDate"));
		
		notificationTableView.setItems(notificationList);
		
	}

	@FXML
	private void deleteNotificationClick() {
		
		Alert alert1 = new Alert(AlertType.CONFIRMATION);
		alert1.setTitle("Confirmation");
		alert1.setHeaderText("Confirmation");
		alert1.setContentText("Do you want to delete notification?");
		ButtonType buttonYes = new ButtonType("Yes");
		ButtonType buttonNo = new ButtonType("No");
		alert1.getButtonTypes().clear();
		alert1.getButtonTypes().add(buttonYes);
		alert1.getButtonTypes().add(buttonNo);
		Optional<ButtonType> result = alert1.showAndWait();
		int index = notificationTableView.getSelectionModel().getSelectedIndex();
		if(result.isPresent() && result.get() == buttonYes && index >= 0) {
			Notification n = notificationService.getNotifications(UserSession.getUserId()).get(index);
			notificationService.deleteNotification(n.getNotificationId());
			fillNotificationTable();
			updateNotificationCounter();
		}else if(index < 0){
			Alert alert2 = new Alert(AlertType.ERROR);
			alert2.setTitle("ERROR");
			alert2.setHeaderText("Error");
			alert2.setContentText("Select notification from list");
			alert2.showAndWait();
		}
		
	}
	
	@FXML
	void openApplicationsPage() {
		int index = myJobListTableView.getSelectionModel().getSelectedIndex();
		if(index < 0) {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("ERROR");
			alert1.setHeaderText("Error");
			alert1.setContentText("Select one item from list.");
			alert1.showAndWait();
			return ;
		}
		Job j = jobService.getJobListByUserId(UserSession.getUserId()).get(index);
		if(j != null) {
			System.out.println(j.getJobId() + "");
			setPanesVisibleFalse();
			fillApplicationListTable(j.getJobId());
			setTransition(applicationsPane, applicationsPane.getLayoutX(), applicationsPane.getLayoutY() - 1000,
					applicationsPane.getLayoutX(), applicationsPane.getLayoutY(), 400);
			applicationsPane.setVisible(true);
		}else {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("ERROR");
			alert1.setHeaderText("Failed");
			alert1.setContentText("Select job to view applications");
			alert1.showAndWait();
		}
	}
	
	private int jobId;
	
	private void fillApplicationListTable(int index) {
		this.jobId = index;
		applicationFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
		applicationEmail.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
		applicationPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		applicationAddress.setCellValueFactory(new PropertyValueFactory<>("adress"));
		
		applicationList.setItems(jobService.getApplications(index));
		
	}

	@FXML
	void openMyApplicationsPage() {
		setPanesVisibleFalse();
		setTransition(myApplicationsPane, myApplicationsPane.getLayoutX(), myApplicationsPane.getLayoutY() - 1000,
				myApplicationsPane.getLayoutX(), myApplicationsPane.getLayoutY(), 400);
		myApplicationsPane.setVisible(true);
		fillMyApplicatonsTable();
	}

	private void fillMyApplicatonsTable() {
		ObservableList<Job> myJobList = jobService.getApplicationsByUserId(UserSession.getUserId());
		myAppCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
		myAppHeader.setCellValueFactory(new PropertyValueFactory<>("jobHeader"));
		myAppSalary.setCellValueFactory(new PropertyValueFactory<>("jobSalary"));
		myAppDate.setCellValueFactory(new PropertyValueFactory<>("jobPostedDate"));
		
		myApplicationsTableView.setItems(myJobList);
		
	}
	
	@FXML
	private void removeMyApplication() {
		int index = myApplicationsTableView.getSelectionModel().getSelectedIndex();
		
		if(index >= 0) {
			int applicationId = jobService.getApplicationsByUserId(UserSession.getUserId()).get(index).getJobId();
			jobService.deleteJobApplication(applicationId);
			fillMyApplicatonsTable();
		}else {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("ERROR");
			alert1.setHeaderText("Failed");
			alert1.setContentText("Select job to remove application");
			alert1.showAndWait();
		}
	
	}

	private void setPanesVisibleFalse() {
		applicationsPane.setVisible(false);
		profilePane.setVisible(false);
		myJobsPane.setVisible(false);
		jobListPane.setVisible(false);
		notificationPane.setVisible(false);
		myApplicationsPane.setVisible(false);
	}

	@FXML
	void updateProfile() {
		User user = new User().setName(nameText.getText()).setSurname(surnameText.getText())
				.setPhoneNumber(phoneText.getText()).setAdress(addressText.getText());

		UpdateResult result = userService.updateProfile(user);
		if (result.isSuccess()) {
			Alert alert1 = new Alert(AlertType.INFORMATION);
			alert1.setTitle("Information");
			alert1.setHeaderText("Success");
			alert1.setContentText("User has been updated successfully!");
			alert1.showAndWait();
		} else {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("ERROR");
			alert1.setHeaderText("Failed");
			alert1.setContentText("Update operation failed! Reason : " + result.getMessage());
			alert1.showAndWait();
		}

	}

	@FXML
	void uploadCV() {
		Stage stage = new Stage();
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

		File selectedFile = fileChooser.showOpenDialog(stage);

		if (selectedFile != null) {
			System.out.println("Choosen File: " + selectedFile.getAbsolutePath());
			userService.uploadCV(selectedFile);
			fillUserDetails();
		} else {
			System.out.println("No file choosed.");
		}
	}

	@FXML
	void openCV() throws IOException {
		String url = userService.getCV().getFilePath();
		if (url == null) {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("ERROR");
			alert1.setHeaderText("Failed");
			alert1.setContentText("You dont have any uploaded cv!");
			alert1.showAndWait();
		} else {
			try {
				String os = System.getProperty("os.name").toLowerCase();
				if (os.indexOf("win") >= 0) {
					Runtime rt = Runtime.getRuntime();
					rt.exec("rundll32 url.dll,FileProtocolHandler " + url);

				} else if (os.indexOf("mac") >= 0) {
					Runtime rt = Runtime.getRuntime();
					rt.exec("open " + url);
				} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
					Runtime rt = Runtime.getRuntime();
					String[] browsers = { "google-chrome", "firefox", "mozilla", "epiphany", "konqueror", "netscape",
							"opera", "links", "lynx" };

					StringBuffer cmd = new StringBuffer();
					for (int i = 0; i < browsers.length; i++)
						if (i == 0)
							cmd.append(String.format("%s \"%s\"", browsers[i], url));
						else
							cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
					rt.exec(new String[] { "sh", "-c", cmd.toString() });
				}
			} catch (Exception e) {
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("ERROR");
				alert1.setHeaderText("Failed");
				alert1.setContentText(e.getMessage());
				alert1.showAndWait();
			}
		}
	}

	@FXML
	void changePasswordButtonClick() {
		if (newPasswordText.getText().equals(newPasswordText2.getText()) && !currentPasswordText.getText().isEmpty()
				&& !newPasswordText.getText().isEmpty() && !newPasswordText2.getText().isEmpty()) {
			ChangePasswordRequest request = new ChangePasswordRequest(currentPasswordText.getText(),
					newPasswordText.getText());
			UpdateResult result = userService.changePassword(request);
			if (result.isSuccess()) {
				Alert alert1 = new Alert(AlertType.INFORMATION);
				alert1.setTitle("INFORMATION");
				alert1.setHeaderText("Success");
				alert1.setContentText("You have changed password successfully");
				alert1.showAndWait();
			} else {
				Alert alert1 = new Alert(AlertType.ERROR);
				alert1.setTitle("ERROR");
				alert1.setHeaderText("Failed");
				alert1.setContentText(result.getMessage());
				alert1.showAndWait();
			}
		} else {
			Alert alert1 = new Alert(AlertType.ERROR);
			alert1.setTitle("ERROR");
			alert1.setHeaderText("Failed");
			alert1.setContentText("Check fields!");
			alert1.showAndWait();
		}

	}
	
	@FXML
	private void logout() {
		Alert alert1 = new Alert(AlertType.CONFIRMATION);
		alert1.setTitle("Confirmation");
		alert1.setHeaderText("Confirmation");
		alert1.setContentText("Do you want to log out?");
		ButtonType buttonYes = new ButtonType("Yes");
		ButtonType buttonNo = new ButtonType("No");
		alert1.getButtonTypes().clear();
		alert1.getButtonTypes().add(buttonYes);
		alert1.getButtonTypes().add(buttonNo);
		Optional<ButtonType> result = alert1.showAndWait();
		if(result.isPresent() && result.get() == buttonYes) {
			 try {
				 	UserSession.clear();
					Image icon = new Image(getClass().getResourceAsStream("icon.png"));
		        	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
		        	Stage activeStage =(Stage) logout.getScene().getWindow();
		        	activeStage.close();
		            Stage stage = new Stage();
		            stage.setTitle("Login Page");
		            stage.setScene(new Scene(root, 700, 500));
		            stage.setResizable(false);
		            stage.getIcons().add(icon);
		            stage.setMaximized(false);
		            stage.show();

		        }
		        catch (Exception e) {
		            e.printStackTrace();
		        }
		}
	}
	
	
    @FXML
    void searchAction(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER) {
    		header.setCellValueFactory(new PropertyValueFactory<>("jobHeaderWithCompanyName"));
    		jobListTable.setItems(jobService.getJobList(searchTextField.getText()));
    	}
    }
    
    @FXML
    void searchMyJobTable(KeyEvent event) {
    	if(event.getCode() == KeyCode.ENTER) {
    		myJobCompanyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
    		myJobHeader.setCellValueFactory(new PropertyValueFactory<>("jobHeader"));
    		myJobDescription.setCellValueFactory(new PropertyValueFactory<>("jobText"));
    		myJobSalary.setCellValueFactory(new PropertyValueFactory<>("jobSalary"));
    		
    		myJobListTableView.setItems(jobService.getJobListByUserId(UserSession.getUserId(),myJobSearchTextField.getText()));
    	}
    }
    
    @FXML
    void switchToAdminPage() {
    	boolean result;
    	Alert alert1 = new Alert(AlertType.CONFIRMATION);
		alert1.setTitle("Confirmation");
		alert1.setHeaderText("Confirmation");
		alert1.setContentText("Do you want to switch to Admin Page?");
		alert1.getButtonTypes().clear();
		ButtonType yesButton = new ButtonType("Yes");
		ButtonType noButton = new ButtonType("No");
		alert1.getButtonTypes().add(yesButton);
		alert1.getButtonTypes().add(noButton);
		
		Optional<ButtonType> alertResult = alert1.showAndWait();
		result =  alertResult.isEmpty() ? false : (alertResult.get() == yesButton ? true : false);
		
    	if(result) {
            try {
    			Image icon = new Image(getClass().getResourceAsStream("icon.png"));
            	AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("AdminPage.fxml"));
            	Stage activeStage =(Stage) jobs.getScene().getWindow();
            	activeStage.close();
                Stage stage = new Stage();
                stage.setTitle("Admin Page");
                stage.setScene(new Scene(root, 1024, 661));
                stage.setResizable(false);
                stage.getIcons().add(icon);
                stage.setMaximized(false);
                stage.show();

            }
            catch (Exception e) {
                e.printStackTrace();
            }
    	}
    }

}
