package main;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import models.Schedule;
import models.User;
import views.LicensePanel;
import views.NodePanel;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Enumeration;

public class MainFrame extends JFrame {
    private static MainFrame instance;

    private User user;
    private Schedule schedule;

    private JPanel containerPanel;
    private NodePanel currentPanel;

    private JButton cancelBtn;
    private JButton nextBtn;
    private JButton installBtn;

    public static MainFrame getInstance(){
        if(instance == null){
            synchronized (MainFrame.class){
                if(instance == null){
                    instance = new MainFrame();
                }
            }
        }
        return instance;
    }

    public MainFrame(){
        setLayout(null);
        initWidget();
        initWidgetsAction();
        setTitle("Children Program Installer");
        setSize(510,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void initWidget(){
        cancelBtn = new JButton("Cancel");
        nextBtn = new JButton("Next");
        installBtn = new JButton("Install");
        nextBtn.setEnabled(false);
        installBtn.setEnabled(false);

        cancelBtn.setBounds(210, 420, 90, 40);
        nextBtn.setBounds(310, 420, 90, 40);
        installBtn.setBounds(410, 420, 90, 40);
        add(cancelBtn);
        add(nextBtn);
        add(installBtn);

        currentPanel = LicensePanel.getInstance();
        containerPanel = new JPanel();
        containerPanel.setBounds(5,5,500,400);
        add(containerPanel);

        setContainerPanel(currentPanel);
    }

    public void setContainerPanel(JPanel panel){
        containerPanel.removeAll();
        containerPanel.setLayout(null);
        panel.setBounds(0,0,500,400);
        containerPanel.add(panel);
        containerPanel.setVisible(false);
        containerPanel.setVisible(true);
    }

    public void initWidgetsAction(){
        cancelBtn.addMouseListener(cancelBtnAction);
        nextBtn.addMouseListener(nextBtnAction);
    }

    private final MouseAdapter cancelBtnAction = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e)) {
                dispose();
                System.exit(0);
            }
        }
    };

    private final MouseAdapter installBtnAction = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e)) {
                installBtn.setText("Installing...");
                try{
                    FileInputStream serviceAccount =  new FileInputStream("config-database.json");
                    FirebaseOptions options = new FirebaseOptions.Builder()
                            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                            .setDatabaseUrl("https://process-memory-management-default-rtdb.asia-southeast1.firebasedatabase.app")
                            .build();

                    FirebaseApp.initializeApp(options);
                    DatabaseReference data = FirebaseDatabase.getInstance().getReference();
                    data.child("users").child(user.getComputerId()).setValueAsync(user);
                    data.child("schedules").child(user.getComputerId()).setValueAsync(schedule);

                    FileOutputStream fileOutputStream = new FileOutputStream("data.dat");
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                    objectOutputStream.writeObject(user);
                    objectOutputStream.close();
                }
                catch (Exception exception){
                    exception.printStackTrace();
                }
                installBtn.setText("Done!");
                JOptionPane.showMessageDialog(null, "Install successed!\nYou need to restart your computer to apply changes!","Warning", JOptionPane.WARNING_MESSAGE);
                dispose();
                System.exit(0);
            }
        }
    };

    private final MouseAdapter nextBtnAction = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e)) {
                if(currentPanel.submit()) {
                    currentPanel = currentPanel.getNextPanel();
                    if(currentPanel != null) {
                        setContainerPanel(currentPanel);
                    }
                    else {
                        nextBtn.setEnabled(false);
                        nextBtn.removeAll();
                        installBtn.addMouseListener(installBtnAction);
                        installBtn.setEnabled(true);
                        return;
                    }
                }
            }
        }
    };

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public JButton getNextBtn() {
        return nextBtn;
    }

    public static void main(String[] args){
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("GTK+".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            Enumeration keys = UIManager.getDefaults().keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object value = UIManager.get(key);
                if (value instanceof FontUIResource) {
                    FontUIResource orig = (FontUIResource) value;
                    Font font = new Font("TimeNewRoman" , Font.PLAIN, 17);
                    UIManager.put(key, new FontUIResource(font));
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (java.lang.InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = MainFrame.getInstance();
            }
        });
    }
}
