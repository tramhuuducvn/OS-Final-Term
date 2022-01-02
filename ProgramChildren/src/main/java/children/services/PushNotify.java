package children.services;

import children.MainFrame;
import children.models.Schedule;
import children.models.User;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PushNotify {
    public static void main(String[] args){
//        try{
//            FileInputStream fileInputStream = new FileInputStream("res/data/computerId.dat");
//            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//            String userid = (String) objectInputStream.readObject();
//            System.out.println(userid);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        MainFrame.getInstance().setVisible(true);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    FileInputStream serviceAccount =  new FileInputStream("config-database.json");
                    FirebaseOptions options = new FirebaseOptions.Builder()
                            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                            .setDatabaseUrl("https://process-memory-management-default-rtdb.asia-southeast1.firebasedatabase.app")
                            .build();

                    FirebaseApp.initializeApp(options);
                    DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("schedules").child("abc");

                    data.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            int s = Integer.parseInt(dataSnapshot.child("s").getValue().toString());
                            int d = Integer.parseInt(dataSnapshot.child("d").getValue().toString());
                            int i = Integer.parseInt(dataSnapshot.child("i").getValue().toString());
                            String f = dataSnapshot.child("f").getValue().toString();
                            String t = dataSnapshot.child("t").getValue().toString();
                            Schedule schedule = new Schedule(f,t,d,s,i);
                            System.out.println(s);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            System.out.println("FAILED!");
                        }
                    });
                    try {
                        Thread.sleep(3000);
                    }
                    catch (Exception e){
                    }
                }
                catch (Exception exception){
                    exception.printStackTrace();
                }


            }
        });
        t.start();

    }
}
