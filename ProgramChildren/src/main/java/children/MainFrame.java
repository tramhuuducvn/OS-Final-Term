package children;

import children.models.AppStatus;
import children.models.CustomTime;
import children.models.Schedule;
import children.models.User;
import children.services.TimeManager;
import children.utils.OsCheck;
import children.views.BackgroundPanel;
import children.views.LoginPanel;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class MainFrame extends JFrame{
    private static MainFrame instance;
    private User user;
    private AppStatus appStatus;
    private boolean isParent;
    private boolean isChildrenLoged;
    private Schedule currentSchedule;
    private ArrayList<Schedule> schedules;
    BackgroundPanel backgroundPanel;
    LoginPanel loginPanel;



    private MainFrame(){
        isParent = false;
        isChildrenLoged = false;
        loadingStatus();
        setupDatabase();
        listenScheduleChange();
        // init background
        backgroundPanel = new BackgroundPanel();
        getContentPane().add(backgroundPanel);
        loginPanel = new LoginPanel();
        backgroundPanel.setContentPanel(loginPanel);

        setTitle("Children Pogram");
//        setExtendedState(JFrame.MAXIMIZED_BOTH);
//        setUndecorated(true);
        setSize(1000,800);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                saveStatus();
            }
        });
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
    public String getComputerId(){
        return computerId;
    }
    public void loadingStatus(){
        try{
            // get computer_id
            FileInputStream fileInputStream = new FileInputStream("res/data/computerId.dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            computerId = (String) objectInputStream.readObject();
            objectInputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        try(FileInputStream statusFile = new FileInputStream("res/data/status.dat")) {
            ObjectInputStream os = new ObjectInputStream(statusFile);
            appStatus = (AppStatus) os.readObject();
            Date date = Calendar.getInstance().getTime();
            if(!appStatus.isSameDate(date)){
                appStatus.setUsedTime(0);
                appStatus.setBreakTime(0);
            }
        }
        catch (Exception e){
            appStatus = new AppStatus();
            saveStatus();
        }
    }

    private void setupDatabase(){
        try{
            // Listening data from Firebase
            FileInputStream serviceAccount =  new FileInputStream("./config-database.json");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://process-memory-management-default-rtdb.asia-southeast1.firebasedatabase.app")
                    .setStorageBucket("process-memory-management.appspot.com")
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
                    loginPanel.setEnableLoging();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void listenScheduleChange(){
        try{
            DatabaseReference data = FirebaseDatabase.getInstance().getReference();
            data.child("schedules").child(computerId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    schedules = new ArrayList<>();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        int s = 477484495, d = 477484495, i = 0;
                        String f="", t="";
                        if(snapshot.hasChild("s")){
                            s = Integer.parseInt(snapshot.child("s").getValue().toString());
                        }
                        if(snapshot.hasChild("d")) {
                            d = Integer.parseInt(snapshot.child("d").getValue().toString());
                        }
                        if(snapshot.hasChild("i")) {
                            i = Integer.parseInt(snapshot.child("i").getValue().toString());
                        }
                        if(snapshot.hasChild("f")) {
                            f = snapshot.child("f").getValue().toString();
                        }
                        if(snapshot.hasChild("t")){
                            t = snapshot.child("t").getValue().toString();
                        }
                        Schedule newSchedule = new Schedule(f, t, d, s, i);
                        schedules.add(newSchedule);
                    }

                    if(!isParent && isChildrenLoged){
                        TimeManager.stopMonitoringMode();
                        TimeManager.MonitoringMode();
                    }
                    sortSchedule(schedules);
                    System.out.println(schedules);
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

    public void sortSchedule(ArrayList<Schedule> schedules){
        Collections.sort(schedules, new Comparator<Schedule>() {
            @Override
            public int compare(Schedule A, Schedule B) {
                return A.getF().compareTo(B.getF());
            }
        });
    }

    public boolean saveStatus(){
        try(FileOutputStream outputStream = new FileOutputStream("res/data/status.dat")){
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(appStatus);
        }
        catch (Exception e1){
            e1.printStackTrace();
            return false;
        }
        return true;
    }

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<Schedule> schedules) {
        this.schedules = schedules;
    }

    public boolean isChildrenLoged() {
        return isChildrenLoged;
    }

    public void setChildrenLoged(boolean childrenLoged) {
        isChildrenLoged = childrenLoged;
    }

    public AppStatus getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(AppStatus appStatus) {
        this.appStatus = appStatus;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Schedule getCurrentSchedule() {
        return currentSchedule;
    }

    public void setCurrentSchedule(Schedule currentSchedule) {
        this.currentSchedule = currentSchedule;
    }

    public static void main(String[] args){
        try {
            String theme = "GTK+";
            OsCheck.OSType type = OsCheck.getOperatingSystemType();
            if(type == OsCheck.OSType.Windows){
                theme = "Nimbus";
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
                    FontUIResource orig = (FontUIResource) value;
                    Font font = new Font("TimeNewRoman" , Font.PLAIN, 17);
                    UIManager.put(key, new FontUIResource(font));
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex ) {
            java.util.logging.Logger.getLogger(JFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }catch (Exception e){
            e.printStackTrace();
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

