package views;


import main.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LicensePanel extends NodePanel {
    private static LicensePanel instance;
    private JLabel titleLb;
    private JLabel contentLb;
    private JLabel memberLb;
    private JCheckBox confirmLicenseCheckbox;

    public static LicensePanel getInstance(){
        if(instance == null){
            synchronized (MainFrame.class){
                if(instance == null){
                    instance = new LicensePanel();
                }
            }
        }
        return instance;
    }

    private LicensePanel(){
        nextPanel = RegisterPanel.getInstance();
        setLayout(null);

        setPreferredSize(new Dimension(500, 400));
        setMinimumSize(new Dimension(500, 400));
        setMaximumSize(new Dimension(500, 400));

        titleLb = new JLabel("<html><h1>CHILDREN PROGRAM INSTALLER</h1></html>");
        contentLb = new JLabel("<html>" +
                "<h4>You have to fill a lot information before intall.</h4>" +
                "<h4>This program can access to your system folder.</h4>" +
                "<h4>This program can change your compyter.</h4>" +
                "</html>");

        memberLb = new JLabel("<html>" +
                "<h4>Member:</h4>" +
                "<h4>19120477: Le Van Dinh</h4>" +
                "<h4>19120484: Tram Huu Duc</h4>" +
                "<h4>19120495: Nguyen Nhat Duy</h4>" +
                "</html>");

        confirmLicenseCheckbox = new JCheckBox("I have read and accepted the above content!");
        confirmLicenseCheckbox.addItemListener(confirmCheckBoxAction);

        titleLb.setBounds(10, 10, 480, 40);
        contentLb.setBounds(10, 60, 480, 90);
        memberLb.setBounds(10, 160, 480, 120);
        confirmLicenseCheckbox.setBounds(10, 300, 480, 30);
        add(titleLb);
        add(contentLb);
        add(memberLb);
        add(confirmLicenseCheckbox);
    }

    private ItemListener confirmCheckBoxAction = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            if(itemEvent.getStateChange() == ItemEvent.SELECTED){
                MainFrame.getInstance().getNextBtn().setEnabled(true);
            }
            else{
                MainFrame.getInstance().getNextBtn().setEnabled(false);
            }
        }
    };
}
