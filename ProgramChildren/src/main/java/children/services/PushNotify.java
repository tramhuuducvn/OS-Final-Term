package children.services;

import children.MainFrame;
import children.models.Schedule;
import children.models.User;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.gson.Gson;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PushNotify {

    public static void notice(String title, String mess){
        JFrame frame = new JFrame();
        frame.setLayout(null);

        JLabel titleLb = new JLabel("<html><h1>"+ title + "</h1><html>");
        JLabel lb = new JLabel("<html><p>" + mess + "</p><html>");
        titleLb.setBounds(10,10,380,37);
        lb.setBounds(10, 40, 380, 60);
        frame.add(titleLb);
        frame.add(lb);

        frame.setSize(400, 100);
        frame.setUndecorated(true);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                frame.setVisible(true);
                try{
                    Thread.sleep(5000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                frame.dispose();
            }
        });
        t.start();
    }

    public static void main(String[] args){
//        notice("See you later! \n This computer will be shutdown after 15s!");
    }
}
