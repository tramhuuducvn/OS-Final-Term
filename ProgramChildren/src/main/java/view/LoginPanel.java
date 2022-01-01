package view;

import utils.ImageTool;
import utils.OsCheck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class LoginPanel extends JPanel{
    private JButton login;
	private JPasswordField passTextField;

	public LoginPanel(){


		setVisible(true);
	}
	private void setupWidget(){
		login = new JButton("Login");
		passTextField = new JPasswordField();
	}

    public void initFrame() {
		JPanel floor = new JPanel();
		floor.setLayout(new BoxLayout(floor, BoxLayout.Y_AXIS));
		floor.add(Box.createRigidArea(new Dimension(0,10)));

		Color c = new Color(227, 140, 0);


		JLabel title2 = new JLabel("Children Program");
		title2.setForeground(c);
		floor.add(title2);
		floor.add(Box.createRigidArea(new Dimension(0,15)));

		JPanel floor5 = new JPanel();
		floor5.setLayout(new BoxLayout(floor5, BoxLayout.X_AXIS));
		JLabel taikhoanLabel = new JLabel(" User :         ");
		JTextField taikhoanTextField = new JTextField();
		floor5.add(taikhoanLabel);
		floor5.add(taikhoanTextField);
		floor5.setMaximumSize(new Dimension(2000, 37));
		floor.add(floor5);

		JPanel floor6 = new JPanel();
		floor6.setLayout(new BoxLayout(floor6, BoxLayout.X_AXIS));
		JLabel matkhauLabel = new JLabel(" Password : ");
		JPasswordField matkhauTextField = new JPasswordField();
		floor6.setMaximumSize(new Dimension(2000, 37));
		floor6.add(matkhauLabel);
		floor6.add(matkhauTextField);
		floor.add(floor6);
		floor.add(Box.createVerticalGlue());

		JPanel floor7 = new JPanel();
		floor7.setLayout(new BoxLayout(floor7, BoxLayout.X_AXIS));
		JLabel status = new JLabel();
		status.setVisible(false);
		floor7.add(status);

		floor7.add(Box.createHorizontalGlue());
		JButton cancel = new JButton("Exit"); cancel.setForeground(c);
		floor7.add(cancel);
		cancel.setIcon(ImageTool.getImageIcon("res/images/cancel_icon.png", 20, 20));
		cancel.setPreferredSize(new Dimension(100, 100));
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					Runtime runtime = Runtime.getRuntime();
					try {
						System.out.println("Shutting down the PC after 3 minutes.");
						OsCheck.OSType osType = OsCheck.getOperatingSystemType();
						switch (osType){
							case Linux:
								runtime.exec("shutdown -P +0");
								break;
							case Windows:
								runtime.exec("shutdown -s -t 0");
								break;
							case MacOS:
								runtime.exec("sudo shutdown -s +0");
								break;
						}
					}
					catch(IOException e1) {
						System.out.println("Exception: " +e1);
					}
				}
			}
		});

		login = new JButton("Login");
		login.setPreferredSize(new Dimension(100, 100));
		login.setForeground(c);
//		login.setBackground(c);
		Icon login_icon = ImageTool.getImageIcon("res/images/login_icon.png", 20, 20);
		login.setIcon(login_icon);
		login.setOpaque(true);
		login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {

				}
			}
		});


		floor7.add(login);
		floor7.add(Box.createRigidArea(new Dimension(15,0)));
		floor.add(floor7);
		floor.add(Box.createRigidArea(new Dimension(0,15)));

		add(floor);
	}
}
