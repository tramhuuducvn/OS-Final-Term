package children.services;

import children.utils.SoundUtils;

import javax.swing.*;
import java.awt.*;

public class PushNotify {

    public static void notice(String title, String mess){
        JFrame frame = new JFrame();
        frame.setLayout(null);

        JLabel titleLb = new JLabel("<html><h1>"+ title + "</h1><html>");
        JLabel lb = new JLabel("<html><p>" + mess + "</p><html>");
        titleLb.setBounds(10,10,440,37);
        lb.setBounds(10, 40, 440, 60);
        frame.add(titleLb);
        frame.add(lb);

        frame.setSize(450, 100);
        frame.setUndecorated(true);

        Thread t = new Thread(() -> {
            try {
                frame.setVisible(true);
                SoundUtils.tone(432,500);
                Thread.sleep(6000);
            } catch (Exception e){
                e.printStackTrace();
            }
            frame.dispose();
        });
        t.start();
    }
}
