import models.Schedule;
import models.User;
import views.LicensePanel;
import views.RegisterPanel;
import views.SetupSchedulePanel;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class MainFrame extends JFrame {
    private static MainFrame instance;

    private User user;
    private Schedule schedule;

    private LicensePanel licensePanel;
    private RegisterPanel registerPanel;
    private SetupSchedulePanel setupShedulePanel;

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

        licensePanel = new LicensePanel();
        licensePanel.setBounds(5,5,500, 400);
        add(licensePanel);
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
