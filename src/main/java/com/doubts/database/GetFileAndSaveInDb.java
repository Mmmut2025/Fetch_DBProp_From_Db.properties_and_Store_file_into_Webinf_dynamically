package com.doubts.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Fileupload;

public class GetFileAndSaveInDb extends SelectorComposer<Component>{
	@Wire Button fileUpload;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		super.doAfterCompose(comp);
	}
	
	@Listen("onUpload = #fileUpload")
	public void uploadFile(UploadEvent event) throws IOException {
		Media media = event.getMedia();
		
		//get folder path explicitelty
		//String path = "/home/administrator/eclipse-workspace/GetDbProp/src/main/webapp/WEB-INF/resources/photos/";
		String path = Sessions.getCurrent().getWebApp().getResource("/WEB-INF/resources/photos/").getPath();
		
		
		// 2. Create file object with folder + file name
	    File file = new File(path + media.getName());

	    // 3. Write file bytes
	    FileInputStream input = (FileInputStream) media.getStreamData();
	    FileOutputStream fo = new FileOutputStream(file);
	    
	    fo.write(input.readAllBytes());
	    
	    System.out.println("File uploaded to : " + file.getAbsolutePath());
	}
}
