package children;

import children.models.Schedule;
import children.models.User;
import children.views.BackgroundPanel;
import children.views.LoginPanel;
import com.google.api.client.json.Json;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.tools.FileObject;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.Enumeration;

public class MainFrame extends JFrame{
    private static MainFrame instance;
    private User user;
    private Schedule schedule;
    BackgroundPanel backgroundPanel;

    private MainFrame(){
        loadingStatus();
        setupDatabase();
        backgroundPanel = new BackgroundPanel();
        getContentPane().add(backgroundPanel);
        backgroundPanel.setContentPanel(new LoginPanel());

        setTitle("Children Pogram");
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
        setSize(1000,800);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

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

    private String computerId;
    public void loadingStatus(){
        try{
            FileInputStream fileInputStream = new FileInputStream("res/data/computerId.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            computerId = (String) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setupDatabase(){
        try{
            // Listening data from Firebase
            FileInputStream serviceAccount =  new FileInputStream("config-database.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://process-memory-management-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .build();
            FirebaseApp.initializeApp(options);

            DatabaseReference data = FirebaseDatabase.getInstance().getReference();
            data.child("users").child(computerId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String computerId = dataSnapshot.child("computerId").getValue().toString();
                    String name = dataSnapshot.child("name").getValue().toString();
                    String childrenKey = dataSnapshot.child("childrenKey").getValue().toString();
                    String parentKey = dataSnapshot.child("parentKey").getValue().toString();
                    user = new User(computerId, name, childrenKey, parentKey);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            data.child("schedules").child(computerId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int s = Integer.parseInt(dataSnapshot.child("s").getValue().toString());
                    int d = Integer.parseInt(dataSnapshot.child("d").getValue().toString());
                    int i = Integer.parseInt(dataSnapshot.child("i").getValue().toString());
                    String f = dataSnapshot.child("f").getValue().toString();
                    String t = dataSnapshot.child("t").getValue().toString();
                    schedule = new Schedule(f,t,d,s,i);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    JOptionPane.showMessageDialog(null, "Check your network connection!", "Warning", JOptionPane.WARNING_MESSAGE);
                    System.out.println(databaseError.getMessage());
                }
            });
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }

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
//				LoginPanel main = new LoginPanel();
                MainFrame mainFrame = MainFrame.getInstance();
			}
		});
	}
}

