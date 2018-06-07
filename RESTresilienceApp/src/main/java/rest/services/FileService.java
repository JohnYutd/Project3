package rest.services;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Locale;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import resilience.BadFileFormatException;
import resilience.Resilience;

@Path("/uploadDownload")
public class FileService {
	
	private static String sep = File.separator;       // declare the separator '/' as 'sep'
	
	/* The path to the folder where we want to store the uploaded files 
	 * (not used)
	 * */
	//private static final String UPLOAD_FOLDER = "E:" + sep + "Users" + sep + "user" + sep + "resilience" + sep + "uploadedFiles" + sep;
	
	//the path of client data files
	private String infosClientsFileName = "E:" + sep + "eclipse-workspace"+ sep + "ProjectInFrance" + sep + "Users" + sep + "user" + sep + "resilience" + sep + "infos_clients.csv";
	
	private void uploadFile(FormDataBodyPart bodyPart, String targetFile){
		BodyPartEntity bodyPartEntity = (BodyPartEntity) bodyPart.getEntity(); //get the Entity in the form
		try {
			saveFile(bodyPartEntity.getInputStream(), targetFile);    //call the function to save the file
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("file " + targetFile + " saved");
	}
	
	@POST
	@Path("/uploadFilesDownload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_PLAIN)
	public Response uploadFilesDownload(final FormDataMultiPart multiPart) throws FileNotFoundException, IOException, BadFileFormatException{
			
			//response to the client
			Response response = null;
			
			//the way to upload files in Jersey to get the file in the form
			FormDataBodyPart bBodyPart = multiPart.getField("file1");
			FormDataBodyPart fBodyPart = multiPart.getField("file2");
			FormDataBodyPart lBodyPart = multiPart.getField("file3");
			FormDataBodyPart keyEnteredBodyPart = multiPart.getField("key");
			
			//get the value of key as a string
			String keyEntered = keyEnteredBodyPart.getValue();
			
			try (BufferedReader br = new BufferedReader(new FileReader(infosClientsFileName))){
				String line;
				while ((line = br.readLine()) != null) {
					
					String [] str = line.split(",");
					String clientkey = str[0];   //  cle1 cle2 ....
					String clientRep = str[2];   //  path of the current user
					if (keyEntered.equals(clientkey)){   //if matched the key
						
						System.out.println(clientkey + " " + clientRep);
						Locale.setDefault(Locale.ENGLISH);
		
						String bPath = clientRep + "bFile.txt";
						String fPath = clientRep + "fFile.txt";
						String lPath = clientRep + "lFile.txt";
						String oPath = clientRep + "oFile.txt";  //the output file
						
						//call for the function to upload and save 3 data files
						uploadFile(bBodyPart,bPath);
						uploadFile(fBodyPart,fPath);
						uploadFile(lBodyPart,lPath);
						
						File bFile = new File(bPath);
						File fFile = new File(fPath);
						File lFile = new File(lPath);
						File oFile = new File(oPath);
		
						try {
							Resilience R = new Resilience(bFile,fFile,lFile,oFile);    //resilience of the input data files
							R.compute();                                               //compute the ORi
						} 
						
						catch (FileNotFoundException e) {
							e.printStackTrace();
						} 
						catch (IOException e) {
							e.printStackTrace();
						} 
						catch (BadFileFormatException e) {
							e.printStackTrace();
						}
						
						//not used
						final java.nio.file.Path b_Path = bFile.toPath();
						final java.nio.file.Path f_Path = fFile.toPath();
						final java.nio.file.Path l_Path = lFile.toPath();
						
						final java.nio.file.Path o_Path = oFile.toPath();
						
						//save the output file
						StreamingOutput outputStr = new StreamingOutput(){
							@Override
							public void write(OutputStream output) throws IOException, WebApplicationException {
								try {
									Files.copy(o_Path, output);
								}
								finally {
								}
							}
						};
		
						response = Response.ok().entity(outputStr).build();   //output the output file in response
						break;
					}
		
					else{
						response = Response.ok().entity("The Key you have entered is not valid try again").build();
						continue;
					}
				}//end of while
				
				br.close();
			}//end of try
			
			return response;
	}
	
	//try to find the path (not used)
	private void createFolderIfNotExists(String dirName)throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}
	
	//output the file and save it
	private void saveFile(InputStream inStream, String target)throws IOException{
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		out = new FileOutputStream(new File(target));   //locate the output path to the target file
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}
}


