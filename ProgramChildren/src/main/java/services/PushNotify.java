package services;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PushNotify {
    public static void main(String[] args){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    for(int i = 0; i < 10; ++i){
                        Thread.sleep(1000);
                    }
                    JOptionPane.showMessageDialog(null, "WARNING.","Warning", JOptionPane.WARNING_MESSAGE);
                }
                catch (Exception e){

                }
            }
        });
        t.start();
    }
}
