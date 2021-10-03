import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class Workspace extends JPanel implements MouseListener, MouseMotionListener, Observer {

    City cityClicked = null;
    TSPAlgorithm tspAlgorithm = new TSPAlgorithm();
    List<City> cities  = new ArrayList<>();
    List<TSPCity> tspCities = null;
    int preX, preY;
    boolean pressOut= false;
    boolean cityDrawMode = false;

    public Workspace() {
        this.setBackground(Color.WHITE);
        addMouseListener(this);
        addMouseMotionListener(this);
        tspAlgorithm.addObserver(this);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (pressOut) {
            g2.setColor(Color.WHITE);
        } else {
            g2.setColor(Color.RED);
        }
        if (cityDrawMode) {
            for (City city : cities) {
                city.draw(g2, pressOut);
            }
            drawRoutes(g);
            cityDrawMode = false;
        }
        //  phoenix.drawConnect(tempe,g2);
        //  tempe.draw(g2, pressOut);
    }
    private void drawRoutes(Graphics graphics) {
        tspCities = tspAlgorithm.tspRoute.cities;
        TSPCity cityA =  tspCities.get(0);
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
        City b =  cities.get(Integer.parseInt(cityB.name));
        g.drawLine( a.bounds.x, a.bounds.y, b.bounds.x, b.bounds.y);
    }
    @Override
    public void mousePressed(MouseEvent e) {
        cityClicked = getCityClicked(e.getX(), e.getY());
        if(cityClicked != null) {
            pressOut = true;
            cityClicked.move(preX+e.getX(), preY+e.getY());

        } else {
            String cityName = JOptionPane.showInputDialog(this,
                    "Enter the city?", null);
            if(cityName != null) {
                City city = new City(cityName, e.getX(), e.getY(), 10, 10);
                city.id = cities.size();
                cities.add(city);
                cityDrawMode = true;
                tspAlgorithm.setCities(cities);
                if(cities.size() > 1) {
                    tspAlgorithm.findRoute();
                } else {
                    repaint();
                }
            }
            pressOut = false;
        }
        /*preX = (tempe.bounds.x - e.getX());
        preY = (tempe.bounds.y - e.getY());
        if(tempe.contains(e.getX(), e.getY())) {
            pressOut = true;
            tempe.move(preX+e.getX(), preY+e.getY());

        } else {
            String cityName = JOptionPane.showInputDialog(this,
                    "Enter the city?", null);
            if(cityName != null) {
                City city = new City(cityName, e.getX(), e.getY(), 10, 10);
                cities.add(city);
                cityDrawMode = true;
                repaint();
            }
            pressOut = false;
        }*/
    }

    private City getCityClicked(int x, int y) {
        for(City city : cities) {
            if(city.contains(x, y)) {
                return city;
            }
        }
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Update called");
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {

      /*  if(pressOut) {
            tempe.move(preX + e.getX(), preY + e.getY());
            repaint();
        }*/
    }

    @Override
    public void mouseReleased(MouseEvent e) {

     /*   if (tempe.contains(e.getX(), e.getY())) {
            tempe.move(e.getY(), e.getY());
            pressOut = false;
            repaint();

        } else {
            pressOut = false;
        }*/
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
