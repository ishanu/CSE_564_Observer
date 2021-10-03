import javax.swing.*;
import java.awt.*;


/**
 * This class creates the panel to hold the buttons as well as the drawing area.
 * It extends JFrame and sets the layout of its contents.
 * It also implements ActionListener to handle control events.
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
      //  add(new MenuBar(), BorderLayout.NORTH);
        add(workspace, BorderLayout.CENTER);
        this.setVisible(true);
        workspace.repaint();
    }

   /* WhiteBoard whiteBoard;
    JButton openFileButton;
    JButton drawGraphButton;


    /**
     * This constructor configures the frame with panels and controls.
     * It also sets the size of the frame and the location of the frame and sets the frame behavior.
     * @param name: This parameter sets the title of the frame.

    Frame(String name) {
        super(name);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        whiteBoard = new WhiteBoard();
        add(whiteBoard, BorderLayout.CENTER);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) screenSize.getHeight() - ((int) (0.1 * screenSize.getHeight())), (int) screenSize.getHeight() - ((int) (0.1 * screenSize.getHeight())));
        int x = (int) ((screenSize.getWidth() - this.getWidth()) / 2);
        int y = (int) ((screenSize.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
        configureButtonArea();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * The button area and the buttons are configured here. A Panel is created to hold the button controls
     * and attaches event listens to the button
     */
   /* public void configureButtonArea() {
        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonArea.setBackground(Color.GRAY);
        add(buttonArea, BorderLayout.SOUTH);
        this.openFileButton = new JButton("Open File");
        this.drawGraphButton = new JButton("Draw Graph");
        this.openFileButton.setPreferredSize(new Dimension(120, 50));
        this.drawGraphButton.setPreferredSize(new Dimension(120, 50));
        this.openFileButton.addActionListener(this);
        this.drawGraphButton.addActionListener(this);
        buttonArea.add(this.openFileButton);
        buttonArea.add(this.drawGraphButton);
    }*/

    /**
     * This method handles the button actions.
     * @param e: This parameter indicates the event triggered by the control

    @Override
    public void actionPerformed(ActionEvent e) {
        if (((JButton) e.getSource()).getText().equals("Open File")) {
            drawCities(((JButton) e.getSource()));
        } else {
            drawGraph();
        }
    }



    private void drawGraph() {
        TSPAlgorithm tspAlgorithm = new TSPAlgorithm(whiteBoard.cityCoordinates);
        List<City> routes = tspAlgorithm.findRoute();
        if (!routes.isEmpty()) {
            whiteBoard.routes = routes;
            whiteBoard.repaint();
        }
    }

    private void drawCities(JButton button) {
        FileExtractor fileExtractor;
        button.setEnabled(false);
        button.setText("Loading ..");
        whiteBoard.cityCoordinates = null;
        whiteBoard.routes = null;
        whiteBoard.repaint();
        fileExtractor = new FileExtractor();
        try {
            File file = fileExtractor.openFile();
            if (file != null) {
                List<double[]> cityCoordinates = fileExtractor.fileOperations(file);
                if (!cityCoordinates.isEmpty()) {
                    whiteBoard.cityCoordinates = cityCoordinates;
                    whiteBoard.repaint();
                }
            }
            button.setEnabled(true);
            button.setText("Open File");
            this.drawGraphButton.setEnabled(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }*/


}

