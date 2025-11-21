package digest.message;

import java.awt.Event;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

public class GenerateFileDigest extends SelectorComposer<Component>{
	@Wire Button fileUpload,submit;
	@Wire Label degestText;
	@Wire Combobox algo,format,fileType;
	
	private String digestMessage;
	private String algoType;
	private String filetype;
	private byte[] fileData;

	
	@Listen("onUpload=#fileUpload")
	public void getFileData(UploadEvent event) throws IOException, NoSuchAlgorithmException {
		Media media = event.getMedia();
		fileData =  media.getByteData();
		Messagebox.show("file uploaded");
	}
	
	public String generateDigest(byte[] fileData , String algoType) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algoType);
		byte[] digest = md.digest(fileData);
		
		StringBuffer sb = new StringBuffer();
		for(byte b : digest) {
			sb.append(Integer.toHexString(0xFF & b));
		}
		
		return sb.toString();
	}
	
	@Listen("onClick=#submit")
	public void showData() throws NoSuchAlgorithmException {
		if(algo.getSelectedItem() == null) {
			Messagebox.show("Please select algo type first");
			return;
		}
		if(format.getSelectedItem() == null) {
			Messagebox.show("Please select digest type first");
			return;
		}
		if(fileType.getSelectedItem() == null) {
			Messagebox.show("Please select file type first");
			return;
		}
		
		algoType = algo.getSelectedItem().getLabel();
		filetype = fileType.getSelectedItem().getLabel();
		
		
		String digestMessage = generateDigest(fileData,algoType);
		
		if(format.getSelectedItem().getLabel().equals("Hex(Lower Case)")) {
			digestMessage = digestMessage.toLowerCase();
		}else {
			digestMessage = digestMessage.toUpperCase();
		}
		
		degestText.setValue(digestMessage + " => " + digestMessage.length());
	}
}
