package children.views;

import children.MainFrame;
import children.services.PushNotify;
import children.services.TimeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;

public class LoginPanel extends JPanel{
    private JButton loginBtn;
	private int failedPass;
	private JLabel headingLb;
	private JLabel passwordLb;
	private JPasswordField passwordTextField;

	public LoginPanel(){
		failedPass = 0;
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

		loginBtn.setEnabled(false);
	}

	public void setEnableLoging(){
		if(loginBtn != null){
			loginBtn.setEnabled(true);
			loginBtn.addMouseListener(loginBtnAction);
		}
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
					PushNotify.notice("Parent", "Re-enter password after 60 minutes");
					TimeManager.NoticeReEnterPassword();
					passwordTextField.setText("");
				}
				else {
					if(TimeManager.isTimeForChildren()){
						if(MainFrame.getInstance().getUser().getChildrenKey().equals(pass)) {
							failedPass = 0;
							int timebreak = MainFrame.getInstance().getAppStatus().getBreakTime();
//							System.out.println("Timbreak:" + timebreak);
							long remainBreakTime = -1;
							Date shutdownDate = MainFrame.getInstance().getAppStatus().getTimeShutdown();
							if(shutdownDate != null) {
								long differMiliseconds = Calendar.getInstance().getTime().getTime() - shutdownDate.getTime();
								remainBreakTime = timebreak - differMiliseconds / 60000;
							}

							if(remainBreakTime <= 0 ){
								MainFrame.getInstance().setVisible(false);
								MainFrame.getInstance().setChildrenLoged(true);
								MainFrame.getInstance().getAppStatus().setBreakTime(0);
								TimeManager.MonitoringMode();
							}
							else {
								JOptionPane.showMessageDialog(null, "Re-start computer after " + remainBreakTime + " minutes", "Warning", JOptionPane.WARNING_MESSAGE);
								TimeManager.shutdownNow(false);
							}
						}
						else{
							failedPass += 1;
							if(failedPass == 3){
								failedPass = 0;
								MainFrame.getInstance().getAppStatus().setBreakTime(10);
								MainFrame.getInstance().getAppStatus().setTimeShutdown(Calendar.getInstance().getTime());
								JOptionPane.showMessageDialog(null, "Wrong password 3 times. Try again after 10 minutes!", "Warning", JOptionPane.WARNING_MESSAGE);
								TimeManager.shutdownNow(true);
							}
							else {
								JOptionPane.showMessageDialog(loginBtn, "Your password is not valid!\n Try again", "Warning", JOptionPane.WARNING_MESSAGE);
							}
						}
					}
					else {
						TimeManager.NoticeTimeForChildren();
						TimeManager.shutDown(15);
//						JOptionPane.showMessageDialog(null, "Warning", "", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		}
	};
}
