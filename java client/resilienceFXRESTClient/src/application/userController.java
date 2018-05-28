package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class userController implements Initializable{
	
	@FXML
	private Button btnfileb;
	
	@FXML
	private TextField pathTextb;
	
	@FXML
	private Button btnfilef;
	
	@FXML
	private TextField pathTextf;
	
	@FXML
	private Button btnfilel;
	
	@FXML
	private TextField pathTextl;

	@FXML
	private Button btnupload;
	
	@FXML
	private TextField key;
	
	private String filePath = "";
	
	
	@FXML
	private void handlebtnfilebAction(){
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        if(file!=null) {
            setFilePath(file.getPath());
            pathTextb.setText(filePath);
        }
	}
	
	@FXML
	private void handlebtnfilefAction(){
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        if(file!=null) {
            setFilePath(file.getPath());
            pathTextf.setText(filePath);
        }
	}
	
	@FXML
	private void handlebtnfilelAction(){
		FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        //Show open file dialog
        File file = fileChooser.showOpenDialog(null);
        if(file!=null) {
            setFilePath(file.getPath());
            pathTextl.setText(filePath);
        }
	}
	
	@FXML
	private void handlebtnuploadAction(){
		
		//request url
		String requestURL = "http://localhost:8080/RESTresilienceApp/rest/uploadDownload/uploadFilesDownload";
		
		File bFile = new File(pathTextb.getText());
		File fFile = new File(pathTextf.getText());
		File lFile = new File(pathTextl.getText());
		
		FileInputStream fis1 = null;
		FileInputStream fis2 = null;
		FileInputStream fis3 = null;
				
		try {
					
			fis1 = new FileInputStream(bFile);
			fis2 = new FileInputStream(fFile);
			fis3 = new FileInputStream(lFile);
			HttpClient httpClient = HttpClients.createDefault();
							
			// server back-end URL
			HttpPost httppost = new HttpPost(requestURL);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();        

			/* example for setting a HttpMultipartMode */
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE); 
					
			// set the file input stream and file name as arguments
			builder.addPart("file1", new InputStreamBody(fis1, bFile.getName()));
			builder.addPart("file2", new InputStreamBody(fis2, fFile.getName()));
			builder.addPart("file3", new InputStreamBody(fis3, lFile.getName()));
			builder.addPart("key", new StringBody(key.getText(), ContentType.MULTIPART_FORM_DATA));
			HttpEntity entity = builder.build();
			httppost.setEntity(entity);
					
			// execute the request
			HttpResponse response = httpClient.execute(httppost);
							
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			String responseString = EntityUtils.toString(responseEntity);
							
			System.out.println(responseString);
							
		} 
				
		catch (ClientProtocolException e) {
			System.err.println("Unable to make connection");
			e.printStackTrace();
		} 
				
		catch (IOException e) {
			System.err.println("Unable to read file");
			e.printStackTrace();
		} 
				
		finally {
			try {
				if (fis1 != null) fis1.close();
				if (fis2 != null) fis2.close();
				if (fis3 != null) fis3.close();
			} 
			
			catch (IOException e) {
				
			}
		}	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
