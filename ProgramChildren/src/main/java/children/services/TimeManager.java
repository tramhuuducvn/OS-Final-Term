package children.services;

import children.MainFrame;
import children.models.AppStatus;
import children.models.CustomTime;
import children.models.Schedule;
import children.utils.ImageTool;
import children.utils.OsCheck;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Calendar;

public class TimeManager{
    final static int MINUTE = 60000;
    final static int SIXTY_MINUTES = 60* MINUTE;

    public  static void NoticeReEnterPassword(){
        Thread t = new Thread(() -> {
            MainFrame.getInstance().setParent(true);
            MainFrame.getInstance().setVisible(false);
            try {
                Thread.sleep(SIXTY_MINUTES);
            } catch (Exception e){
                e.printStackTrace();
            }
            MainFrame.getInstance().setParent(false);
            MainFrame.getInstance().setVisible(true);
            JOptionPane.showMessageDialog(null, "Please re enter your password to continue using !", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        });
        t.start();
    }

    public static boolean isTimeForChildren(){
        ArrayList<Schedule> schedules = MainFrame.getInstance().getSchedules();
        for(Schedule schedule : schedules) {
            CustomTime f = new CustomTime(schedule.getF());
            CustomTime t = new CustomTime(schedule.getT());
            Calendar calendar = Calendar.getInstance();
            int h = calendar.get(Calendar.HOUR_OF_DAY);
            int m = calendar.get(Calendar.MINUTE);
            if (f.getHours() < h && h < t.getHours()) {
                MainFrame.getInstance().setCurrentSchedule(schedule);
                return true;
            }
            if (f.getHours() == h && m > f.getMinutes()) {
                MainFrame.getInstance().setCurrentSchedule(schedule);
                return true;
            }
            if (t.getHours() == h && m < t.getMinutes()) {
                MainFrame.getInstance().setCurrentSchedule(schedule);
                return true;
            }
        }
        return false;
    }

    public static void shutDown(int seconds){
        Thread t = new Thread(() -> {
            try {
                for(int i = 0; i < seconds; ++i){
                    Thread.sleep(1000);

                    if (MainFrame.getInstance().isParent()) {
                        return;
                    }
                }
                shutdownNow(true);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
        t.start();
    }

    public static void shutdownNow(boolean save){
        if(!isTimeForChildren()){
            MainFrame.getInstance().getAppStatus().setUsedTime(0);
        }
        if(save) {
            MainFrame.getInstance().saveStatus();
        }

        try {
            OsCheck.OSType type = OsCheck.getOperatingSystemType();
            switch (type) {
                case Windows:
                    Runtime.getRuntime().exec("shutdown -s -t 0");
                    break;
                case Linux:
                    Runtime.getRuntime().exec("sleep 5");
                    System.exit(0);
//                    Runtime.getRuntime().exec("shutdown now");
                    break;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void NoticeTimeForChildren(){
        ArrayList<Schedule> schedules = MainFrame.getInstance().getSchedules();
        String mess = "";
        Calendar calendar = Calendar.getInstance();
        int tomorrowHours = 25, tomorrowMin = 25;
        for(int i = 0; i < schedules.size(); ++i) {
            Schedule schedule = schedules.get(i);
            CustomTime F = new CustomTime(schedule.getF());

            if (calendar.get(Calendar.HOUR_OF_DAY) < F.getHours()) {
                mess = "You can use this computer at " + F.getHours() + " hours " + F.getMinutes() + " minutes!";
                break;
            }

            if (calendar.get(Calendar.HOUR_OF_DAY) <= F.getHours() && calendar.get(Calendar.MINUTE) < F.getMinutes()) {
                mess = "You can use this computer at " + F.getHours() + " hours " + F.getMinutes() + " minutes!";
                break;
            }
            if(tomorrowHours > F.getHours()){
                tomorrowHours = F.getHours();
            }
            if(tomorrowMin > F.getMinutes()){
                tomorrowMin = F.getMinutes();
            }
        }

        if (mess.isEmpty()) {
            mess = "You can use this computer at " + tomorrowHours + " hours " + tomorrowMin + " minutes in tomorrow!";
        }
        JOptionPane.showMessageDialog(null, mess + "\n This computer will be shutdown after 15s", "Not time for children", JOptionPane.WARNING_MESSAGE);
    }

    private static int currentMinUsed = 0;
    private static String timeComeback = "";
    public static int getRemainTime(){
        if(!isTimeForChildren()){
            shutDown(15);
            NoticeTimeForChildren();
            return 0;
        }

        Schedule schedule = MainFrame.getInstance().getCurrentSchedule();
        AppStatus appStatus = MainFrame.getInstance().getAppStatus();
        CustomTime F = new CustomTime(schedule.getF());
        CustomTime T = new CustomTime(schedule.getT());
        int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int m = Calendar.getInstance().get(Calendar.MINUTE);

        int diffWithSum = schedule.getS() - appStatus.getUsedTime();
        int dur = schedule.getD() - currentMinUsed;
        int diffWithTimeEnd = T.getHours()*60 + T.getMinutes() - h*60 - m;
        int k = Math.min(diffWithTimeEnd, dur);
        int timeRemain = Math.min(k, diffWithSum);

        if(timeRemain == dur){
            timeComeback = ((h*60 + m + timeRemain + schedule.getI())/60)%24 +" hours " + (h*60 + m + timeRemain + schedule.getI())%60 + " minutes";
        }
        if(timeRemain == diffWithSum || timeRemain == diffWithTimeEnd){
            ArrayList<Schedule> schedules = MainFrame.getInstance().getSchedules();
            for(Schedule sche:schedules){
                CustomTime temp = new CustomTime(sche.getF());
                if(F.getHours() < temp.getHours()){
                    timeComeback = temp.getHours() + " hours " + temp.getMinutes()  + " minutes";
                    break;
                }
                if(F.getHours() <= temp.getHours() && F.getMinutes() < temp.getMinutes()){
                    timeComeback = temp.getHours() + " hours " + temp.getMinutes()  + " minutes";
                    break;
                }
            }

        }

        return timeRemain;
    }

    private static boolean isChange = false;
    public static void stopMonitoringMode(){
        isChange = true;
    }

    public static void MonitoringMode(){
        int timeRemain = getRemainTime();
        if(timeRemain <= 0){
            shutDown(5);
            JOptionPane.showMessageDialog(null,"Exceed the time limit of use!\n Shutdown in 5s", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        PushNotify.notice("Time remaining " + timeRemain + " minutes ", "You can comeback at " + timeComeback);

        Thread thread = new Thread(() -> {
            try {
                for (int i = timeRemain; i > 1; --i) {
                    MainFrame.getInstance().getAppStatus().increaseTimeUsed();
                    ImageTool.captureScreen();
                    Thread.sleep(MINUTE);
                    currentMinUsed++;
//                        System.out.println("Current Min Used: " + currentMinUsed);
                    if(isChange){
//                            System.out.println("Schedule has been changed!");
                        isChange = false;
                        return;
                    }
                }
                PushNotify.notice("Time remaining 1 minutes ", "You can comeback at " + timeComeback);
                ImageTool.captureScreen();

                currentMinUsed++;
                MainFrame.getInstance().getAppStatus().increaseTimeUsed();
                if (currentMinUsed >= MainFrame.getInstance().getCurrentSchedule().getD()){
                    MainFrame.getInstance().getAppStatus().setBreakTime(MainFrame.getInstance().getCurrentSchedule().getI());
                    MainFrame.getInstance().getAppStatus().setTimeShutdown(Calendar.getInstance().getTime());
                }
                Thread.sleep(MINUTE);
                shutdownNow(true);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
