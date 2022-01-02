// https://coderanch.com/t/598106/java/dynamically-resize-background-image-JPanel
package children.views;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class BackgroundPanel extends JPanel {
    private static final long serialVersionUID = 7210857306625744220L;
    private Image mainImage = null;
    private Image scaledImage = null;
    private String path = "res/images/background.png";
    private JPanel containerPanel;

    public BackgroundPanel() {
        containerPanel = new JPanel();
        containerPanel.setOpaque(false);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalGlue());
        this.add(containerPanel);
        this.add(Box.createVerticalGlue());
        setContentPanel(new JPanel());

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        this.setPreferredSize(toolkit.getScreenSize());
        try {
            mainImage = ImageIO.read(new File(path));
            mainImage = mainImage.getScaledInstance(
                    this.getPreferredSize().width,
                    this.getPreferredSize().height, Image.SCALE_REPLICATE);
        }
        catch (IOException e){
            e.printStackTrace();
        }

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                scaledImage = mainImage.getScaledInstance(
                        BackgroundPanel.this.getWidth(),
                        BackgroundPanel.this.getHeight(), Image.SCALE_REPLICATE);
                BackgroundPanel.this.repaint();
                super.componentResized(e);
            }
        });
    }

    public void setContentPanel(JPanel contentPanel) {
        containerPanel.removeAll();
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4, true));
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));
        containerPanel.add(Box.createHorizontalGlue());
        containerPanel.add(contentPanel);
        containerPanel.add(Box.createHorizontalGlue());
        containerPanel.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d = (Graphics2D) g2d.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (scaledImage == null) {
            g2d.drawImage(mainImage, 0, 0, this);
        }
        else {
            g2d.drawImage(scaledImage, 0, 0, this);
        }
        g2d.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new BackgroundPanel());
        frame.pack();
        frame.setVisible(true);
    }
}