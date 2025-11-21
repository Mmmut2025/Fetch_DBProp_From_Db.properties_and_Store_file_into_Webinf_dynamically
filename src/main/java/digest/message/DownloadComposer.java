package digest.message;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zul.Filedownload;

public class DownloadComposer extends SelectorComposer<Component> {

    @Listen("onClick = #downloadLink")
    public void downloadFile() throws IOException {

        // 1. Get real path of file inside project
        String path = Sessions.getCurrent()
                .getWebApp()
                .getRealPath("/WEB-INF/resources/photos/Tour_Claim_Form.pdf");

        File file = new File(path);

        if (file.exists()) {

            FileInputStream fis = new FileInputStream(file);
            byte[] fileBytes = fis.readAllBytes();
            fis.close();

            // 2. Download the file
            //Filedownload.save(fileBytes, "application/pdf", "sample.pdf");
            Filedownload.save(new FileInputStream(file), null, file.getName());

        } else {
            throw new IOException("File not found: " + path);
        }
    }
}
