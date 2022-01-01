package views;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel{
    private JLabel titleLb;
    private JLabel accountLb, nameLb, parrentKeyLb, chidldrenKeyLb, reinput_parrentKeyLb, reinput_chidldrenKeyLb;
    private JTextField accountTF, nameTF;
    private JPasswordField parrentKeyPF, chidldrenKeyPF, reinput_parrentKeyLbPF, reinput_chidldrenKeyPF;


    public RegisterPanel(){
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
        reinput_parrentKeyLbPF = new JPasswordField();
        reinput_chidldrenKeyPF = new JPasswordField();

        titleLb.setBounds(150, 10, 300, 40);

        accountLb.setBounds(10, 50, 230, 40);
        nameLb.setBounds(250, 50, 230, 40);
        accountTF.setBounds(10, 95, 230, 40);
        nameTF.setBounds(250, 95, 230, 40);

        parrentKeyLb.setBounds(10, 150, 230, 40);
        reinput_parrentKeyLb.setBounds(250, 150, 230, 40);
        parrentKeyPF.setBounds(10, 195, 230, 40);
        reinput_parrentKeyLbPF.setBounds(250, 195, 230, 40);

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
        add(reinput_parrentKeyLbPF);
        add(reinput_chidldrenKeyPF);
    }
}
