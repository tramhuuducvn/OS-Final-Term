package children.utils;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;

public class ImageTool {
    public static ImageIcon getImageIcon(String srcFile, int x, int y) {
		ImageIcon src = new ImageIcon(srcFile);
		Image scaled = src.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH);
		return new ImageIcon(scaled);
	}

	public static void captureScreen(){
		Date date = Calendar.getInstance().getTime();
		String path = "res/images/";
		String fileName = "Screenshot from " +
				date.getYear() + "-" + date.getMonth() + 1 + "-" + date.getDate() + "_" +
				date.getHours() + "-" + date.getMinutes() + "-" + date.getSeconds() + ".png";
		try {
//			FileInputStream serviceAccount =  new FileInputStream("config-database.json");
//			FirebaseOptions options = new FirebaseOptions.Builder()
//					.setCredentials(GoogleCredentials.fromStream(serviceAccount))
//					.setDatabaseUrl("https://process-memory-management-default-rtdb.asia-southeast1.firebasedatabase.app")
//					.setStorageBucket("process-memory-management.appspot.com")
//					.build();
//			FirebaseApp.initializeApp(options);

			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage capture = new Robot().createScreenCapture(screenRect);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(capture, "png", byteArrayOutputStream);
			byte[] bytes = byteArrayOutputStream.toByteArray();

			StorageClient storageClient = StorageClient.getInstance();
			storageClient.bucket().create("images/" + fileName, bytes, "image/png");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
