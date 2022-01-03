package children.utils;

import children.MainFrame;
import com.google.firebase.cloud.StorageClient;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class ImageTool {
    public static ImageIcon getImageIcon(String srcFile, int x, int y) {
		ImageIcon src = new ImageIcon(srcFile);
		Image scaled = src.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH);
		return new ImageIcon(scaled);
	}

	public static void captureScreen(){
		String computerId = MainFrame.getInstance().getComputerId();
		Calendar calendar = Calendar.getInstance();
		String fileName = "Screenshot from " +
				calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + 1 + "-" + calendar.get(Calendar.DAY_OF_MONTH) + " " +
				calendar.get(Calendar.HOUR_OF_DAY) + "-" + calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND) + ".png";
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
			storageClient.bucket().create("images/" + computerId + "/" + fileName, bytes, "image/png");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
