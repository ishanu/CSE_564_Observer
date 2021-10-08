import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * This class provides the drawing area for the cities and routes.
 * It observes the TSP algorithm to render the routes among the cities
 */
public class Workspace extends JPanel implements MouseListener, MouseMotionListener, Observer {

    private City cityClicked = null;
    private TSPAlgorithm tspAlgorithm = new TSPAlgorithm();
    private List<City> cities = new LinkedList<>();
    private List<TSPCity> tspCities = null;
    private FileExtractor fileExtractor = null;
    private boolean pressOut = false;
    private boolean cityDrawMode = false;

    /**
     * This constructor initializes the drawing area, sets up the menu
     */
    public Workspace() {
        fileExtractor = new FileExtractor();
        this.setLayout(new BorderLayout());
        setMenuBar();
        this.setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
        tspAlgorithm.addObserver(this);
    }


    /**
     * This is a painting method where cities and routes are drawn.
     * @param g the graphics component
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (cityDrawMode) {
            for (City city : cities) {
                city.draw(g2);
            }
            drawRoutes(g);
            cityDrawMode = false;
        } else {
            if (cityClicked != null) {
                for (City city : cities) {
                    city.draw(g2);
                }
                drawRoutes(g);
            }
        }

    }

    /**
     * This method moves the city whenever drag event is triggered
     * @param e mouse event
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (pressOut) {
            cityClicked.move( e.getX(), e.getY());
            repaint();
        }
    }

    /**
     * This method will either add a new city or picks an existing whenever press event is triggered
     * @param e mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        cityClicked = getCityClicked(e.getX(), e.getY());
        if (cityClicked != null) {
            pressOut = true;
        } else {
            String cityName = JOptionPane.showInputDialog(this,
                    "Enter the city?", null);
            if (cityName != null) {
                City city = new City(cityName, e.getX(), e.getY(), 10, 10);
                city.id = cities.size();
                cities.add(city);
                cityDrawMode = true;
                tspAlgorithm.setCities(cities);
            }
            pressOut = false;
        }
    }

    /**
     * This methods is triggered whenever the observable notifies the observers
     * @param o observed object
     * @param arg arguments from the observed object
     */
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Update called");
        repaint();
    }

    /**
     * This method updates the new route whenever release event is triggered
     * @param e mouse event
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (cityClicked != null) {
            tspAlgorithm.updateCities(cityClicked, e.getX(), e.getY());
            tspAlgorithm.findRoute();
            cityDrawMode = true;
        }
        pressOut = false;
    }

    /**
     * This method handles the event when the new menu option is clicked
     * @return event associated the new option
     */
    public ActionListener getProjectNewController() {
        return e -> {
            this.cities = new LinkedList<>();
            this.cityClicked = null;
            repaint();
        };
    }

    /**
     * This method handles the event when the open file option is clicked
     * @return event associated with the open option
     */
    public ActionListener getFileOpenController() {
        return e -> {
            try {
                File file = fileExtractor.openFile();
                if(file == null) {
                    return;
                }
                cities = fileExtractor.extractFile(file);
                this.cityDrawMode = true;
                tspAlgorithm.setCities(cities);
                tspAlgorithm.findRoute();
            } catch (IOException ex) {
                ex.printStackTrace();

            }
        };
    }

    /**
     * This method handles the event when the save file option is clicked
     * @return event associated with the save option
     */
    public ActionListener getFileSaveController() {
        return e -> {
            fileExtractor.fileWrite(this.cities);

        };
    }

    /**
     * Sets up the menu bar for the workspace
     */
    public void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        add(menuBar, BorderLayout.NORTH);
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        newMenuItem.setMaximumSize(new Dimension(100, 100));
        openMenuItem.setMaximumSize(new Dimension(100, 100));
        saveMenuItem.setMaximumSize(new Dimension(100, 100));
        newMenuItem.addActionListener(this.getProjectNewController());
        openMenuItem.addActionListener(this.getFileOpenController());
        saveMenuItem.addActionListener(this.getFileSaveController());
        menuBar.add(newMenuItem);
        menuBar.add(openMenuItem);
        menuBar.add(saveMenuItem);
    }

    private City getCityClicked(int x, int y) {
        for (City city : cities) {
            if (city.contains(x, y)) {
                return city;
            }
        }
        return null;
    }

    private void drawRoutes(Graphics graphics) {
        if (tspAlgorithm.tspRoute.cities.size() <= 1) {
            tspCities = tspAlgorithm.tspRoute.cities;
        } else {
            tspCities = tspAlgorithm.shortestRoute.cities;
        }
        TSPCity cityA = tspCities.get(0);
        TSPCity tempCity = cityA;
        TSPCity cityB = null;
        for (int i = 1; i < tspCities.size(); i++) {
            graphics.setColor(Color.green);
            cityB = tspCities.get(i);
            drawLine(graphics, cityA, cityB);
            cityA = cityB;
        }
        graphics.setColor(Color.green);
        drawLine(graphics, tempCity, cityA);
    }


    private void drawLine(Graphics g, TSPCity cityA, TSPCity cityB) {
        City a = cities.get(Integer.parseInt(cityA.name));
        City b = cities.get(Integer.parseInt(cityB.name));
        g.drawLine(a.bounds.x, a.bounds.y, b.bounds.x, b.bounds.y);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseMoved(MouseEvent e) {
    }


}
