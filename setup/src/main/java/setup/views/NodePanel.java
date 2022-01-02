package setup.views;

import javax.swing.*;

public class NodePanel extends JPanel {
    protected NodePanel nextPanel;

    public NodePanel getNextPanel() {
        return nextPanel;
    }

    public void setNextPanel(NodePanel nextPanel) {
        this.nextPanel = nextPanel;
    }

    public boolean submit(){
        return true;
    }
}
