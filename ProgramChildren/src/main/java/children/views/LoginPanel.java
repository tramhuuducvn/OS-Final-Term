package children.views;

import children.MainFrame;
import children.models.Schedule;
import children.services.TimeManager;
import children.utils.ImageTool;
import children.utils.OsCheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class LoginPanel extends JPanel{
    private JButton loginBtn;
	private JLabel headingLb;
	private JLabel passwordLb;
	private JPasswordField passwordTextField;

	public LoginPanel(){
		setLayout(null);
		setPreferredSize(new Dimension(500, 170));
		setMinimumSize(new Dimension(500, 170));
		setMaximumSize(new Dimension(500, 170));

		setupWidget();
	}
	private void setupWidget(){
		headingLb = new JLabel("Welcome to Children Programme!");
		passwordLb = new JLabel("Enter your password:");
		passwordTextField = new JPasswordField();
		loginBtn = new JButton("Login");

		headingLb.setBounds(10, 10, 480, 30);
		passwordLb.setBounds(10, 50, 480, 30);
		passwordTextField.setBounds(10, 82, 480, 30);
		loginBtn.setBounds(290, 130, 200, 30);

		add(headingLb);
		add(passwordLb);
		add(passwordTextField);
		add(loginBtn);

		loginBtn.addMouseListener(loginBtnAction);
	}

	MouseAdapter loginBtnAction = new MouseAdapter() {
		@Override
		public void mouseReleased(MouseEvent e) {
			if(SwingUtilities.isLeftMouseButton(e)) {
				String pass = String.valueOf(passwordTextField.getPassword());
				if(pass == null || pass.isEmpty()){
					JOptionPane.showMessageDialog(loginBtn, "Please enter your password!", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if(MainFrame.getInstance().getUser().getParentKey().equals(pass)){
					TimeManager.NoticeReEnterPassword();
					passwordTextField.setText("");
				}
				else {
					Schedule schedule = MainFrame.getInstance().getSchedule();

					if(MainFrame.getInstance().getUser().getChildrenKey().equals(pass)) {

					}
					else{

					}
				}
			}
		}
	};
}
