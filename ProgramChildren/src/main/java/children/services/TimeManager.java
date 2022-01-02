package children.services;

import children.MainFrame;
import children.models.Schedule;
import children.utils.TextToSpeech;

import javax.swing.*;

public class TimeManager{
    public  static void NoticeReEnterPassword(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                MainFrame.getInstance().setVisible(false);
                try{
                    Thread.sleep(30000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                MainFrame.getInstance().setVisible(true);
                JOptionPane.showMessageDialog(null, "Please re enter your password to continue using !", "Warning", JOptionPane.WARNING_MESSAGE);
                TextToSpeech.speek("Please re-enter your password to continue using!");
            }
        });
        t.start();
    }

    public static boolean isTimeForChildren(){
        return false;
    }
}
