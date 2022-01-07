package setup;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.apache.commons.io.FileUtils;
import setup.models.Schedule;
import setup.models.User;
import setup.views.LicensePanel;
import setup.views.NodePanel;
import sun.awt.OSInfo;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Enumeration;

public class MainFrame extends JFrame {
    private static MainFrame instance;

    private User user;
    private Schedule schedule;
    private String pathInstall;
    private String nameFolder = "ProgramChildren/";

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

    private Thread installThread = new Thread(new Runnable() {
        @Override
        public void run() {
            installBtn.setText("Installing...");
            installBtn.setEnabled(false);
            try{
                FileInputStream serviceAccount =  new FileInputStream("config-database.json");
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://process-memory-management-default-rtdb.asia-southeast1.firebasedatabase.app")
                        .build();

                FirebaseApp.initializeApp(options);
                DatabaseReference data = FirebaseDatabase.getInstance().getReference();
                data.child("users").child(user.getComputerId()).setValueAsync(user);
                data.child("schedules").child(user.getComputerId()).child("1").setValueAsync(schedule);
                Thread.sleep(1500);

                FileOutputStream fileOutputStream = new FileOutputStream(nameFolder + "res/data/computerId.dat");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(user.getComputerId());
                objectOutputStream.close();
                Thread.sleep(1000);

                File src = new File(nameFolder);
                File target = new File(pathInstall+nameFolder);
                FileUtils.copyDirectory(src, target);
                Thread.sleep(7000);
                OSInfo.OSType type = OSInfo.getOSType();
                switch (type){
                    case LINUX:
                        installLinux();
                        break;
                    case WINDOWS:
                        installWindow();
                        break;
                }
            }
            catch (Exception exception){
                exception.printStackTrace();
            }
            installBtn.setText("Done!");
            JOptionPane.showMessageDialog(null, "Install successed!\nYou need to restart your computer to apply changes!","Warning", JOptionPane.WARNING_MESSAGE);
            dispose();
            System.exit(0);
        }
    });

    private void installLinux(){
        try{
            FileWriter writerRunFile = new FileWriter(pathInstall + nameFolder + "run.sh");
            writerRunFile.write("cd " + pathInstall + nameFolder + " \n java -jar ProgramChildren.jar");
            writerRunFile.close();

            FileWriter writerStartFile = new FileWriter(pathInstall + nameFolder + "children_program.sh.desktop");
            writerStartFile.write("[Desktop Entry]\n" +
                    " Type=Application\n" +
                    " Exec=" + pathInstall + nameFolder + "run.sh\n" +
                    " Name=run\n" +
                    " Comment=Children program");
            writerStartFile.close();

            FileWriter writerInstallFile = new FileWriter("install.sh");
            writerInstallFile.write("cp " + pathInstall + nameFolder + "children_program.sh.desktop " + "~/.config/autostart/");
            writerInstallFile.close();
            Thread.sleep(750);
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("chmod +x ./install.sh");
            runtime.exec("./install.sh");
            runtime.exec("chmod +x " + pathInstall + nameFolder + "run.sh");
            Thread.sleep(750);
//            runtime.exec("reboot");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void installWindow() {
        try {
            FileWriter writerRunFile = new FileWriter("children_program.bat");
            writerRunFile.write("cd " + pathInstall + nameFolder + " \n java -jar ProgramChildren.jar");
            System.out.println("Installing");
            writerRunFile.close();
            Thread.sleep(1500);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private final MouseAdapter installBtnAction = new MouseAdapter() {
        @Override
        public void mouseReleased(MouseEvent e) {
            if(SwingUtilities.isLeftMouseButton(e)) {
                installThread.start();
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

    public void setPathInstall(String pathInstall) {
        this.pathInstall = pathInstall;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public JButton getNextBtn() {
        return nextBtn;
    }

    public static void main(String[] args){
        try {
            String theme = "GTK+";
            OSInfo.OSType type = OSInfo.getOSType();
            switch (type){
                case LINUX:
                    theme = "GTK+";
                    break;
                case WINDOWS:
                    theme = "Nimbus";
                    break;
            }
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (theme.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
            Enumeration keys = UIManager.getDefaults().keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object value = UIManager.get(key);
                if (value instanceof FontUIResource) {
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

        EventQueue.invokeLater(() -> {
            MainFrame mainFrame = MainFrame.getInstance();
        });
    }
}
