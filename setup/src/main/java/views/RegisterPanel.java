package views;

import main.MainFrame;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegisterPanel extends NodePanel{
    private static RegisterPanel instance;

    private JLabel titleLb;
    private JLabel accountLb, nameLb, parrentKeyLb, chidldrenKeyLb, reinput_parrentKeyLb, reinput_chidldrenKeyLb;
    private JTextField accountTF, nameTF;
    private JPasswordField parrentKeyPF, chidldrenKeyPF, reinput_parrentKeyPF, reinput_chidldrenKeyPF;

    public static RegisterPanel getInstance(){
        if(instance == null){
            synchronized (MainFrame.class){
                if(instance == null){
                    instance = new RegisterPanel();
                }
            }
        }
        return instance;
    }


    private RegisterPanel(){
        nextPanel = SetupSchedulePanel.getInstance();
        setLayout(null);
        setPreferredSize(new Dimension(500, 400));
        setMinimumSize(new Dimension(500, 400));
        setMaximumSize(new Dimension(500, 400));
        initWidget();
    }

    public void initWidget(){
        titleLb = new JLabel("<html><h1>Register Form</h1><html>");

        accountLb = new JLabel("Account:");
        nameLb = new JLabel("Computer name:");
        parrentKeyLb = new JLabel("Parent password:");
        chidldrenKeyLb = new JLabel("Children password:");
        reinput_parrentKeyLb = new JLabel("Re-enter parent password");
        reinput_chidldrenKeyLb = new JLabel("Re-enter children password");

        accountTF = new JTextField();
        nameTF = new JTextField();

        parrentKeyPF = new JPasswordField();
        chidldrenKeyPF = new JPasswordField();
        reinput_parrentKeyPF = new JPasswordField();
        reinput_chidldrenKeyPF = new JPasswordField();

        titleLb.setBounds(150, 10, 300, 40);

        accountLb.setBounds(10, 50, 230, 40);
        nameLb.setBounds(250, 50, 230, 40);
        accountTF.setBounds(10, 95, 230, 40);
        nameTF.setBounds(250, 95, 230, 40);

        parrentKeyLb.setBounds(10, 150, 230, 40);
        reinput_parrentKeyLb.setBounds(250, 150, 230, 40);
        parrentKeyPF.setBounds(10, 195, 230, 40);
        reinput_parrentKeyPF.setBounds(250, 195, 230, 40);

        chidldrenKeyLb.setBounds(10, 250, 230, 40);
        reinput_chidldrenKeyLb.setBounds(250, 250, 230, 40);
        chidldrenKeyPF.setBounds(10, 295, 230, 40);
        reinput_chidldrenKeyPF.setBounds(250, 295, 230, 40);

        add(titleLb);
        add(accountLb);
        add(nameLb);
        add(parrentKeyLb);
        add(chidldrenKeyLb);
        add(reinput_parrentKeyLb);
        add(reinput_chidldrenKeyLb);
        add(accountTF);
        add(nameTF);
        add(parrentKeyPF);
        add(chidldrenKeyPF);
        add(reinput_parrentKeyPF);
        add(reinput_chidldrenKeyPF);
    }

    @Override
    public boolean submit() {
        super.submit();
        String computerID, name, childrenKey, parentKey, rechildrenKey, reparentKey;
        computerID = accountTF.getText();
        name = nameTF.getText();
        childrenKey = String.valueOf(chidldrenKeyPF.getPassword());
        rechildrenKey = String.valueOf(reinput_chidldrenKeyPF.getPassword());
        reparentKey = String.valueOf(reinput_parrentKeyPF.getPassword());
        parentKey = String.valueOf((parrentKeyPF.getPassword()));

        if(computerID == null || computerID.isEmpty()){
            JOptionPane.showMessageDialog(null, "Account field cannot be empty ","Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(name == null || name.isEmpty()){
            JOptionPane.showMessageDialog(null, "Computer name field cannot be empty ","Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(childrenKey == null || childrenKey.isEmpty()){
            JOptionPane.showMessageDialog(null, "Children password field cannot be empty ","Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(parentKey == null || parentKey.isEmpty()){
            JOptionPane.showMessageDialog(null, "Parent password field cannot be empty ","Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(!childrenKey.equals(rechildrenKey)){
            JOptionPane.showMessageDialog(null, "Re-enter children password is not valid","Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if(!parentKey.equals(reparentKey)){
            JOptionPane.showMessageDialog(null, "Re-enter parent password is not valid","Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        MainFrame.getInstance().setUser(new User(computerID, name,childrenKey, parentKey));
        return true;
    }
}
