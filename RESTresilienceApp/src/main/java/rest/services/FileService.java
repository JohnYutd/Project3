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
	
	private static String sep = File.separator;
	/* The path to the folder where we want to store the uploaded files */
	//private static final String UPLOAD_FOLDER = "C:" + sep + "Users" + sep + "user" + sep + "resilience" + sep + "uploadedFiles" + sep;
	
	//读取client数据信息
	private String infosClientsFileName = "E:" + sep + "eclipse-workspace"+ sep + "project3" + sep + "Users" + sep + "user" + sep + "resilience" + sep + "infos_clients.csv";
	
	private void uploadFile(FormDataBodyPart bodyPart, String targetFile){
		BodyPartEntity bodyPartEntity = (BodyPartEntity) bodyPart.getEntity();
		try {
			saveFile(bodyPartEntity.getInputStream(), targetFile);
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

			Response response = null;
			FormDataBodyPart bBodyPart = multiPart.getField("file1");
			FormDataBodyPart fBodyPart = multiPart.getField("file2");
			FormDataBodyPart lBodyPart = multiPart.getField("file3");
			FormDataBodyPart keyEnteredBodyPart = multiPart.getField("key");
			String keyEntered = keyEnteredBodyPart.getValue();
			
			try (BufferedReader br = new BufferedReader(new FileReader(infosClientsFileName))){
				String line;
				while ((line = br.readLine()) != null) {
					
					String [] str = line.split(",");
					String clientkey = str[0];
					String clientRep = str[2];
					if (keyEntered.equals(clientkey)){
						
						System.out.println(clientkey + " " + clientRep);
						Locale.setDefault(Locale.ENGLISH);
		
						String bPath = clientRep + "bFile.txt";
						String fPath = clientRep + "fFile.txt";
						String lPath = clientRep + "lFile.txt";
						String oPath = clientRep + "oFile.txt";
						
						uploadFile(bBodyPart,bPath);
						uploadFile(fBodyPart,fPath);
						uploadFile(lBodyPart,lPath);
						
						File bFile = new File(bPath);
						File fFile = new File(fPath);
						File lFile = new File(lPath);
						File oFile = new File(oPath);
		
						try {
							Resilience R = new Resilience(bFile,fFile,lFile,oFile);
							R.compute();
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
		
						final java.nio.file.Path b_Path = bFile.toPath();
						final java.nio.file.Path f_Path = fFile.toPath();
						final java.nio.file.Path l_Path = lFile.toPath();
						final java.nio.file.Path o_Path = oFile.toPath();
		
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
		
						response = Response.ok().entity(outputStr).build();
						break;
					}
		
					else{
						response = Response.ok().entity("The Key you have entered is not valid try again").build();
						continue;
					}
				}
				
				br.close();
			}
			
		return response;
	}
	
	private void createFolderIfNotExists(String dirName)throws SecurityException {
		File theDir = new File(dirName);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}
	
	private void saveFile(InputStream inStream, String target)throws IOException{
		OutputStream out = null;
		int read = 0;
		byte[] bytes = new byte[1024];
		out = new FileOutputStream(new File(target));
		while ((read = inStream.read(bytes)) != -1) {
			out.write(bytes, 0, read);
		}
		out.flush();
		out.close();
	}
}


