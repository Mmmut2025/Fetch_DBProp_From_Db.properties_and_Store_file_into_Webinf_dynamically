package digest.message;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vbox;

public class GenerateFileDigest extends SelectorComposer<Component> {
	@Wire
	Button fileUpload, submit;
	@Wire
	Label degestText;
	@Wire
	Combobox algo, format, fileType;
	@Wire
	Checkbox toggle;
	@Wire
	Groupbox macBox;
	@Wire
	Vbox fileBox, urlBox;
	@Wire
	Textbox urlText,key;

	private String algoType;
	private byte[] fileData;

	
	 @Listen("onSelect=#fileType") 
	 public void chageFileType() throws IOException{ 
		 if(fileType.getSelectedItem().getLabel().equals("URL")) {
			 urlBox.setVisible(true); 
			 fileBox.setVisible(false);
		 } else {
			 urlBox.setVisible(false);
			 fileBox.setVisible(true); 
		 } 
	}
	

	@Listen("onCheck = #toggle")
	public void toggleClick() {
		boolean value = toggle.isChecked();
		if (value) {
			macBox.setVisible(true);
		} else {
			macBox.setVisible(false);
		}
	}

	@Listen("onUpload=#fileUpload")
	public void getFileData(UploadEvent event) throws IOException, NoSuchAlgorithmException {
		Media media = event.getMedia();
		fileData = media.getByteData();
		Messagebox.show("file uploaded");
	}

	@Listen("onClick=#submit")
	public void showData() throws NoSuchAlgorithmException, WrongValueException, IOException, InvalidKeyException {
		if (algo.getSelectedItem() == null) {
			Messagebox.show("Please select algo type first");
			return;
		}
		if (format.getSelectedItem() == null) {
			Messagebox.show("Please select digest type first");
			return;
		}
		if (fileType.getSelectedItem() == null) {
			Messagebox.show("Please select file type first");
			return;
		}

		if (fileType.getSelectedItem().getLabel().equals("URL")) {
			URL url = new URL(urlText.getValue());

			// Open connection
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			// Read bytes safely
			try (InputStream in = connection.getInputStream()) {
				fileData = in.readAllBytes();
			}
		}

		algoType = algo.getSelectedItem().getLabel();
		
		String digestMessage;
		if(toggle.isChecked()) {
			//means you need to generate the mac not digest
			String secretKey = key.getValue();
			digestMessage = GenerateMAC_and_DIGEST.generateMAC(fileData, secretKey, "Hmac"+algoType.substring(0,3)+algoType.substring(4));
		}
		else {
			digestMessage = GenerateMAC_and_DIGEST.generateDigest(fileData, algoType);
		}
		

		if (format.getSelectedItem().getLabel().equals("Hex(Lower Case)")) {
			digestMessage = digestMessage.toLowerCase();
		} else {
			digestMessage = digestMessage.toUpperCase();
		}

		degestText.setValue(digestMessage + " => " + digestMessage.length());
	}
}
