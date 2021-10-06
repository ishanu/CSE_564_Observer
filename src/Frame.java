import javax.swing.*;
import java.awt.*;


/**
 * This class creates the panel to the main drawing area.
 * It extends JFrame and sets the layout of its contents.
 * @author : Ishanu Dhar (ID: 1222326326, idhar@asu.edu)
 * @author : Pritam De (ID: 1219491988, pritamde@asu.edu)
 */
public class Frame extends JFrame  {

    public Frame() {
        super("Assignment 3");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) screenSize.getHeight() - ((int) (0.1 * screenSize.getHeight())), (int) screenSize.getHeight() - ((int) (0.1 * screenSize.getHeight())));
        int x = (int) ((screenSize.getWidth() - this.getWidth()) / 2);
        int y = (int) ((screenSize.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        Workspace workspace = new Workspace();
        workspace.setSize(500, 500);
        add(workspace, BorderLayout.CENTER);
        this.setVisible(true);
        workspace.repaint();
    }

}

