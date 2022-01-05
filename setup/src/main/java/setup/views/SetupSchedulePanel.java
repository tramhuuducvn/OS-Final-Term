package setup.views;

import setup.MainFrame;
import setup.models.Schedule;

import javax.swing.*;
import java.awt.*;

public class SetupSchedulePanel extends NodePanel{
    private static SetupSchedulePanel instance;
    private JLabel titleLb;
    private JLabel fromLb, toLb, durationLb, interuptLb, sumLb;
    private JTextField fromTF, toTF, durationTF, interuptTF, sumTF;

    public static SetupSchedulePanel getInstance(){
        if(instance == null){
            synchronized (MainFrame.class){
                if(instance == null){
                    instance = new SetupSchedulePanel();
                }
            }
        }
        return instance;
    }

    private SetupSchedulePanel(){
        nextPanel = null;
        setLayout(null);
        setPreferredSize(new Dimension(500, 400));
        setMinimumSize(new Dimension(500, 400));
        setMaximumSize(new Dimension(500, 400));
        initWidget();
    }

    public void initWidget(){
        titleLb = new JLabel("<html><h1>Setup Schedule</h1><html>");

        fromLb = new JLabel("From (hh:mm):");
        toLb = new JLabel("To (hh:mm):");
        durationLb = new JLabel("Duration (minutes):");
        interuptLb = new JLabel("Interrupt Time (minutes):");
        sumLb = new JLabel("Sum (minutes):");

        fromTF = new JTextField();
        toTF = new JTextField();
        durationTF = new JTextField();
        interuptTF = new JTextField();
        sumTF = new JTextField();

        titleLb.setBounds(150, 10, 300, 40);

        fromLb.setBounds(10, 50, 230, 40);
        toLb.setBounds(250, 50, 230, 40);
        fromTF.setBounds(10, 95, 230, 40);
        toTF.setBounds(250, 95, 230, 40);

        durationLb.setBounds(10, 150, 230, 40);
        interuptLb.setBounds(250, 150, 230, 40);
        durationTF.setBounds(10, 195, 230, 40);
        interuptTF.setBounds(250, 195, 230, 40);

        sumLb.setBounds(10, 250, 230, 40);
        sumTF.setBounds(10, 295, 230, 40);

        add(titleLb);
        add(fromLb);
        add(toLb);
        add(durationLb);
        add(interuptLb);
        add(sumLb);
        add(fromTF);
        add(toTF);
        add(durationTF);
        add(interuptTF);
        add(sumTF);
    }

    @Override
    public boolean submit() {
        super.submit();
        String from, to;
        int duration, interupt, sum;
        from = fromTF.getText();
        to = toTF.getText();
        try {
            duration = Integer.parseInt(durationTF.getText());
            interupt = Integer.parseInt(interuptTF.getText());
            sum = Integer.parseInt(sumTF.getText());
        }
        catch (Exception e){
            duration = interupt = sum = 0;
        }

        if(from == null || from.isEmpty()){
            JOptionPane.showMessageDialog(null, "From time field cannot be empty ","Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if(to == null || to.isEmpty()){
            JOptionPane.showMessageDialog(null, "To time field cannot be empty ","Warning", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        fromTF.setEnabled(false);
        toTF.setEnabled(false);ESysdsfsdfsd
        durationTF.setEnabled(false);
        interuptTF.setEnabled(false);
        sumTF.setEnabled(false);

        MainFrame.getInstance().setSchedule(new Schedule(from, to, duration, sum, interupt));
        return true;
    }
}
