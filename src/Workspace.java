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

public class Workspace extends JPanel implements MouseListener, MouseMotionListener, Observer {

    City cityClicked = null;
    TSPAlgorithm tspAlgorithm = new TSPAlgorithm();
    List<City> cities = new LinkedList<>();
    List<TSPCity> tspCities = null;
    int preX, preY;
    FileExtractor fileExtractor = null;
    boolean pressOut = false;
    boolean cityDrawMode = false;

    public Workspace() {
        fileExtractor = new FileExtractor();
        this.setLayout(new BorderLayout());
        setMenuBar();
        this.setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
        tspAlgorithm.addObserver(this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (cityDrawMode) {
            for (City city : cities) {
                city.draw(g2, pressOut);
            }
            drawRoutes(g);
            cityDrawMode = false;
        } else {
            if (cityClicked != null) {
                for (City city : cities) {
                    city.draw(g2, pressOut);
                }
                cityClicked.draw(g2, pressOut);
                drawRoutes(g);
            }
        }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (pressOut) {
            cityClicked.move(preX + e.getX(), preY + e.getY());
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        cityClicked = getCityClicked(e.getX(), e.getY());
        if (cityClicked != null) {
            pressOut = true;
            cityClicked.move(preX + e.getX(), preY + e.getY());

        } else {
            String cityName = JOptionPane.showInputDialog(this,
                    "Enter the city?", null);
            if (cityName != null) {
                City city = new City(cityName, e.getX(), e.getY(), 10, 10);
                city.id = cities.size();
                cities.add(city);
                cityDrawMode = true;
                tspAlgorithm.setCities(cities);
                if (cities.size() > 1) {
                    tspAlgorithm.findRoute();
                } else {
                    repaint();
                }
            }
            pressOut = false;
        }
    }

    private void setMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        add(menuBar, BorderLayout.NORTH);
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setMaximumSize(saveMenuItem.getPreferredSize());
        newMenuItem.setMaximumSize(newMenuItem.getPreferredSize());
        openMenuItem.setMaximumSize(openMenuItem.getPreferredSize());
        newMenuItem.addActionListener(this.getProjectNewController());
        openMenuItem.addActionListener(this.getFileOpenController());
        saveMenuItem.addActionListener(this.getFileSaveController());
        menuBar.add(newMenuItem);
        menuBar.add(openMenuItem);
        menuBar.add(saveMenuItem);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Update called");
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (cityClicked != null) {
            tspAlgorithm.updateCities(cityClicked, e.getX(), e.getY());
            tspAlgorithm.findRoute();
            cityDrawMode = true;
        }
        pressOut = false;
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

    private ActionListener getProjectNewController() {
        return e -> {
            this.cities = new LinkedList<>();
            this.cityClicked = null;
            repaint();
        };
    }
    private ActionListener getFileOpenController() {
        return e -> {
            try {
               File file = fileExtractor.openFile();
               cities = fileExtractor.extractFile(file);
               this.cityDrawMode = true;
               tspAlgorithm.setCities(cities);
               tspAlgorithm.findRoute();
            } catch (IOException ex) {
                ex.printStackTrace();

            }
        };
    }
    private ActionListener getFileSaveController() {
        return e -> {
            fileExtractor.fileWrite(this.cities);

        };
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
