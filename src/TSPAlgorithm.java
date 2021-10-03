import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;

/**
 * This class runs the travelling sales person algorithm to find the optimal route between the points.
 * It uses the Simulated Annealing Algorithm.
 *
 * @author : Ishanu Dhar (ID: 1222326326, idhar@asu.edu)
 * @author : Pritam De (ID: 1219491988, pritamde@asu.edu)
 */
public class TSPAlgorithm extends Observable {
    public static final double COOLING_RATE = 0.005;
    public static final double TEMP_MIN = 0.99;
    public TSPRoute tspRoute;
    TSPRoute shortestRoute;

    public void setCities(List<City> cities) {
        if (cities != null) {
            List<TSPCity> cityList = new LinkedList<>();
            cities.forEach(n -> {
                cityList.add(new TSPCity(n.id + "", n.bounds.x, n.bounds.y));
            });
            tspRoute = new TSPRoute();
            tspRoute.cities = new LinkedList<>();
            tspRoute.cities.addAll(cityList);
        }
    }

    public void updateCities(City cityClicked, int x, int y) {
        if (cityClicked != null) {
            tspRoute.cities.stream().map(n -> {
                if (n.name.equals(cityClicked.id + "")) {
                    System.out.println("inside");
                    n.latitude = x * Math.PI / 180D;
                    n.longitude = y * Math.PI / 180D;
                }
                return n;
            }).collect(Collectors.toList());
            System.out.println();
            System.out.println("Printing updated route");
            tspRoute.cities.forEach(n -> {
                        System.out.print(n.name + " ");
                    }
            );
        }
    }


    public void findRoute() {
        System.out.println("Find route called");
        shortestRoute = createTspRoute(tspRoute);
        TSPRoute adjacentRoute;
        int breakEven = 0;
        int initialTemperature = 999;
        while (initialTemperature > TEMP_MIN) {
            TSPRoute route = createTspRoute(tspRoute);
            adjacentRoute = obtainAdjacentRoute(route);
            if (tspRoute.getTotalDistance() < shortestRoute.getTotalDistance()) {
                shortestRoute = createTspRoute(tspRoute);
            }
            if (acceptRoute(tspRoute.getTotalDistance(), adjacentRoute.getTotalDistance(), initialTemperature)) {
                tspRoute = createTspRoute(adjacentRoute);
            }
            initialTemperature *= 1 - COOLING_RATE;
        }

        System.out.println("Printing the route");
        shortestRoute.cities.forEach(n -> {
                    System.out.print(n.name + " ");
                }
        );
        System.out.println("Notified obeservers");
        setChanged();
        notifyObservers();
    }

    private TSPRoute createTspRoute(TSPRoute tspRoute) {
        TSPRoute route = new TSPRoute();
        route.cities = new LinkedList<>();
        route.cities.addAll(tspRoute.cities);
        return route;
    }

    private boolean acceptRoute(double currentDistance, double adjacentDistance, double temperature) {
        double acceptanceProb = 1.0;
        if (adjacentDistance >= currentDistance) {
            acceptanceProb = Math.exp(-(adjacentDistance - currentDistance) / temperature);
        }
        double random = Math.random();
        return acceptanceProb >= random;
    }

    private TSPRoute obtainAdjacentRoute(TSPRoute route) {
        int x1 = 0, x2 = 0;
        while (x1 == x2) {
            x1 = (int) (route.cities.size() * Math.random());
            x2 = (int) (route.cities.size() * Math.random());
        }
        TSPCity city1 = route.cities.get(x1);
        TSPCity city2 = route.cities.get(x2);
        route.cities.set(x2, city1);
        route.cities.set(x1, city2);
        return route;
    }

}