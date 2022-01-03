package children.services;

import children.MainFrame;
import children.models.AppStatus;
import children.models.CustomTime;
import children.models.Schedule;
import children.utils.ImageTool;
import children.utils.OsCheck;
import children.utils.TextToSpeech;

import javax.swing.*;
import java.util.Calendar;

public class TimeManager{
    final static int min = 10000;
    final static int sixtymins = 60*min;
    public  static void NoticeReEnterPassword(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                MainFrame.getInstance().setParent(true);
                MainFrame.getInstance().setVisible(false);
                try{
                    Thread.sleep(sixtymins);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                MainFrame.getInstance().setParent(false);
                MainFrame.getInstance().setVisible(true);
                JOptionPane.showMessageDialog(null, "Please re enter your password to continue using !", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        t.start();
    }

    public static boolean isTimeForChildren(){
        Schedule schedule = MainFrame.getInstance().getSchedule();
        CustomTime f = new CustomTime(schedule.getF());
        CustomTime t = new CustomTime(schedule.getT());
        Calendar calendar = Calendar.getInstance();
        int h = calendar.getTime().getHours();
        int m = calendar.getTime().getMinutes();
        if(f.getHours() < h && h < t.getHours()){
            return true;
        }
        if(f.getHours() == h && m > f.getMinutes()){
            return true;
        }
        if(t.getHours() == h && m < t.getMinutes()){
            return true;
        }
        return false;
    }

    public static void shutDown(int seconds){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i = 0; i < seconds; ++i){
                        Thread.sleep(1000);
                        if(MainFrame.getInstance().isParent()){
                            return;
                        }
                    }
                    shutdownNow(true);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public static void shutdownNow(boolean save){
        if(save){
            MainFrame.getInstance().saveStatus();
            System.out.println();
        }

        OsCheck.OSType type = OsCheck.getOperatingSystemType();
        switch (type) {
            case Windows:
                System.out.println("Shutdown Now");
//                            Runtime.getRuntime().exec("shutdown -s -t 0");
                break;
            case Linux:
                System.out.println("Shutdown Now");
//                            Runtime.getRuntime().exec("shutdown now");
                break;
        }
    }

    public static void NoticeTimeForChildren(){
        Schedule schedule = MainFrame.getInstance().getSchedule();
        CustomTime F = new CustomTime(schedule.getF());
        CustomTime T = new CustomTime(schedule.getT());
        String mess = "";
        Calendar calendar = Calendar.getInstance();

        if(calendar.getTime().getHours() <= F.getHours() && calendar.getTime().getMinutes() < F.getMinutes()){
            mess = "You can use this computer at " + F.getHours() + " hours " + F.getMinutes() + " minutes!";
        }
        if(calendar.getTime().getHours() >= T.getHours() && calendar.getTime().getMinutes() > T.getMinutes()){
            mess = "You can use this computer at " + F.getHours() + " hours " + F.getMinutes() + " minutes in tomorrow!";
        }

        JOptionPane.showMessageDialog(null, mess + "\n This computer will be shutdown after 15s", "Not time for children", JOptionPane.WARNING_MESSAGE);
//        PushNotify.notice("Not time for children!", mess + "\nThis computer will be shutdown after 15s");
    }

    private static int currentMinUsed = 0;
    private static String timeComeback = "";
    public static int getRemaingTime(){
        if(!isTimeForChildren()){
            shutDown(15);
            NoticeTimeForChildren();
            return 0;
        }

        Schedule schedule = MainFrame.getInstance().getSchedule();
        AppStatus appStatus = MainFrame.getInstance().getAppStatus();
        CustomTime F = new CustomTime(schedule.getF());
        CustomTime T = new CustomTime(schedule.getT());
        int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int m = Calendar.getInstance().get(Calendar.MINUTE);

        int diffWithSum = schedule.getS() - appStatus.getTimeused();
        System.out.println(appStatus.toString());
        int dur = schedule.getD() - currentMinUsed;
        int diffWithTimeEnd = T.getHours()*60 + T.getMinutes() - h*60 - m;
        int k = (diffWithTimeEnd < dur) ? diffWithTimeEnd:dur;
        int timeRemain = (k < diffWithSum) ? k:diffWithSum;

        if(timeRemain == dur){
            timeComeback = ((h*60 + m + timeRemain + schedule.getI())/60)%24 +" hours " + (h*60 + m + timeRemain + schedule.getI())%60 + " minutes";
        }
        if(timeRemain == diffWithSum || timeRemain == diffWithTimeEnd){
            timeComeback = F.getHours() + " hours " + F.getMinutes()  + " minutes tomorrow!";
        }

        return timeRemain;
    }

    private static boolean ischange = false;
    public static void stopMonitoringMode(){
        ischange = true;
    }

    public static void MonitoringMode(){
        int timeRemain = getRemaingTime();
        if(timeRemain <= 0){
            shutDown(5);
            JOptionPane.showMessageDialog(null,"Exceed the time limit of use!\n Shutdown in 5s", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        PushNotify.notice("Time remaing " + timeRemain + " minutes ", "You can comback at " + timeComeback);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = timeRemain; i > 1; --i) {
                        MainFrame.getInstance().getAppStatus().increaseTimeUsed();
                        ImageTool.captureScreen();
                        Thread.sleep(min);
                        currentMinUsed++;
                        System.out.println("Current Min Used: " + currentMinUsed);
                        if(ischange){
                            System.out.println("Schedule has been changed!");
                            ischange = false;
                            return;
                        }
                    }
                    PushNotify.notice("Time remaing 1 minutes ", "You can comback at " + timeComeback);
                    ImageTool.captureScreen();

                    currentMinUsed++;
                    MainFrame.getInstance().getAppStatus().increaseTimeUsed();
                    if (currentMinUsed >= MainFrame.getInstance().getSchedule().getD()){
                        MainFrame.getInstance().getAppStatus().setBreaktime(MainFrame.getInstance().getSchedule().getI());
                        MainFrame.getInstance().getAppStatus().setTimeShutdown(Calendar.getInstance().getTime());
                        System.out.println("Setted break time: ");
                    }
                    Thread.sleep(min);
                    shutdownNow(true);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
