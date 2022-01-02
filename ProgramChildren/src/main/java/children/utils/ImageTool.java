package children.utils;

import javax.swing.*;
import java.awt.*;

public class ImageTool {
    public static ImageIcon getImageIcon(String srcFile, int x, int y) {
		ImageIcon src = new ImageIcon(srcFile);
		Image scaled = src.getImage().getScaledInstance(x, y, Image.SCALE_SMOOTH);
		return new ImageIcon(scaled);
	}
}
