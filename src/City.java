import java.awt.*;

/**
 * This class creates the City objects that are displayed in the UI
 * It holds the coordinates and the dimensions of the city objects
 *
 * @author : Ishanu Dhar (ID: 1222326326, idhar@asu.edu)
 * @author : Pritam De (ID: 1219491988, pritamde@asu.edu)
 */
public class City {
    public int id = 0;
    public Rectangle bounds;
    public String label;

    /**
     * This constructor intializes the City with name, coordinats and dimensions
     * @param label name of the city
     * @param x contains the x coordinate
     * @param y contains the y coordinate
     * @param w contains the width of the city
     * @param h contains the height of the city
     */
    public City(String label, int x, int y, int w, int h) {
        bounds = new Rectangle(x, y, w, h);
        this.label = label;
    }

    /**
     * Renders the city in the UI
     * @param g graphics object
     */
    public void draw(Graphics g) {
        int x = bounds.x;
        int y = bounds.y;
        int w = bounds.w;
        int h = bounds.h;
        g.drawRect(x, y, w, h);
        Color c = g.getColor();
        g.setColor(Color.blue);
        g.fillRect(x, y, w, h);
        g.setColor(Color.red);
        g.setFont(new Font("Courier", Font.PLAIN, 10));
        g.drawString(label, x + w, y);
        g.setColor(c);
    }

    /**
     * assigns the new coordinates to the City object
     * @param x contains the x coordinate
     * @param y contains the y coordinate
     */
    public void move(int x, int y) {
        bounds.x = x;
        bounds.y = y;
    }

    /**
     * Checks if the coordinates passed in the parameter is a part of the city
     * @param x
     * @param y
     * @return
     */
    public boolean contains(int x, int y) {
        return (bounds.x - 3 <= x && bounds.x + 3 + bounds.w >= x) &&
                (bounds.y - 3 <= y && bounds.y + 3 + bounds.h >= y);
    }

}