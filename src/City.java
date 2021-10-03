import java.awt.*;

/**
 * THis class holds the properties of a city
 *
 * @author : Ishanu Dhar (ID: 1222326326, idhar@asu.edu)
 * @author : Pritam De (ID: 1219491988, pritamde@asu.edu)
 */
public class City {
    public int id = 0;
    public Rectangle bounds;
    public String label;

    public City(String label, int x, int y, int w, int h) {
        bounds = new Rectangle(x, y, w, h);
        this.label = label;
    }

    public void draw(Graphics g, boolean mousePressed) {
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

    public void move(int x, int y) {
        bounds.x = x;
        bounds.y = y;
    }

    public boolean contains(int x, int y) {
        return (bounds.x - 3 <= x && bounds.x + 3 + bounds.w >= x) &&
                (bounds.y - 3 <= y && bounds.y + 3 + bounds.h >= y);
    }

    private Point center() {
        return new Point(bounds.x + bounds.w / 2, bounds.y + bounds.h / 2);
    }

}