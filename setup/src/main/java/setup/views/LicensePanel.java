package setup.views;


import setup.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LicensePanel extends NodePanel {
    private static LicensePanel instance;
    private JCheckBox confirmLicenseCheckbox;
    private String pathInstall = "";
    private JButton chooseFileBtn;

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
        JLabel titleLb, contentLb, memberLb;
        nextPanel = RegisterPanel.getInstance();
        setLayout(null);

        setPreferredSize(new Dimension(500, 400));
        setMinimumSize(new Dimension(500, 400));
        setMaximumSize(new Dimension(500, 400));

        titleLb = new JLabel("<html><h1>CHILDREN PROGRAM INSTALLER</h1></html>");
        contentLb = new JLabel("<html>" +
                "<h4>You have to fill a lot information before install.</h4>" +
                "<h4>This program can access to your system folder.</h4>" +
                "<h4>This program can change your computer.</h4>" +
                "</html>");

        memberLb = new JLabel("<html>" +
                "<h4>Members:</h4>" +
                "<h4>19120477: Le Van Dinh</h4>" +
                "<h4>19120484: Tram Huu Duc</h4>" +
                "<h4>19120495: Nguyen Nhat Duy</h4>" +
                "</html>");

        chooseFileBtn = new JButton("Choose install folder");
        chooseFileBtn.addMouseListener(chooseFileAction);

        confirmLicenseCheckbox = new JCheckBox("I have read and accepted the above content!");
        confirmLicenseCheckbox.addItemListener(confirmCheckBoxAction);

        titleLb.setBounds(10, 10, 480, 40);
        contentLb.setBounds(10, 60, 480, 90);
        memberLb.setBounds(10, 160, 480, 120);
        chooseFileBtn.setBounds(10, 300, 200, 37);
        confirmLicenseCheckbox.setBounds(10, 350, 480, 30);
        add(titleLb);
        add(contentLb);
        add(memberLb);
        add(confirmLicenseCheckbox);
        add(chooseFileBtn);
    }

    private final MouseAdapter chooseFileAction = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e)) {
                JFileChooser fileChooser = new JFileChooser();
//                fileChooser.setCurrentDirectory(new File(""));
                fileChooser.setDialogTitle("Choose folder where you want install");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                fileChooser.setAcceptAllFileFilterUsed(false);
                if(fileChooser.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION){
//                    System.out.println(fileChooser.getCurrentDirectory());
//                    System.out.println(fileChooser.getSelectedFile().getName());
                    pathInstall = fileChooser.getCurrentDirectory() + "/" + fileChooser.getSelectedFile().getName() + "/";
                }
                else{
                    pathInstall = "";
                    System.out.println("No selection!");
                }
            }
        }
    };

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

    @Override
    public boolean submit() {
        if(pathInstall.isEmpty() || pathInstall == null){
            JOptionPane.showMessageDialog(null, "No folder is selected!","Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        MainFrame.getInstance().setPathInstall(pathInstall);
        return true;
    }
}
