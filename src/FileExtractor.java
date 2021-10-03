import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class deals with file management. It opens and reads the TSP files.
 * It provides methods to extract the TSP and ATSP data for further operations.
 *
 * @author : Ishanu Dhar (ID: 1222326326, idhar@asu.edu)
 * @author : Pritam De (ID: 1219491988, pritamde@asu.edu)
 */
public class FileExtractor {

    /**
     * This method opens a file from the system and returns it.
     *
     * @return a file from the system
     * @throws IOException
     */
    public File openFile() throws IOException {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setMultiSelectionEnabled(false);
        jFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = jFileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = jFileChooser.getSelectedFile();
            return file;
        } else {
            return null;
        }
    }

    /**
     * This method extracts the file data which has been entered from the system.
     *
     * @param file: file from the system
     * @return a list of locations along with the coordinates.
     * @throws IOException
     */
    public List<City> extractFile(File file) throws IOException {
        String fileContent = readFile(file.getAbsolutePath());
            int startIndex = fileContent.indexOf("NODE_COORD_SECTION");
            startIndex = fileContent.indexOf("$", 0) + 1;
            return extractCities(fileContent.substring(startIndex));
    }


    public boolean fileWrite(List<City> cities) {
        String content = "id city latitude longitude\n";
        for (City city : cities) {
            content += "" + city.id + " " + city.label + " " + city.bounds.x + " " + city.bounds.y + "\n";
        }
        final JFileChooser SaveAs = new JFileChooser();
        SaveAs.setApproveButtonText("Save");
        int actionDialog = SaveAs.showOpenDialog(null);
        if (actionDialog != JFileChooser.APPROVE_OPTION) {
            return false;
        }
        File fileName = new File(SaveAs.getSelectedFile() + ".txt");
        BufferedWriter outFile = null;
        try {
            outFile = new BufferedWriter(new FileWriter(fileName));
            outFile.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (outFile != null) {
                try {
                    outFile.close();
                } catch (IOException e) {
                }
            }
        }
        return true;
    }

    /**
     * This method extracts TSP data from the file string.
     *
     * @param fileContent: a stream of data read from the file
     * @return a list of locations along with the coordinates.
     */
    public List<City> extractCities(String fileContent) {
        List<City> cities = new ArrayList<>();
        fileContent = fileContent.replaceAll("\\$", " ");
        String split[] = fileContent.split(" ");
        for (int i = 0; i < split.length; ) {
            City city = new City(split[i+1],Integer.parseInt(split[i+2]),Integer.parseInt(split[i+3]),10,10);
            city.id = Integer.parseInt(split[i+0]);
            i=i+4;
            cities.add(city);
        }
        return cities;
    }

    private String readFile(String path) throws IOException {
        File file = new File(path);
        BufferedReader br = null;
        String fileContent = "";
        try {
            br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null) {
                fileContent = fileContent + str + "$";
            }
        } finally {
            br.close();
            return fileContent;
        }
    }

    private List<double[]> getAtspCoordinates(int[][] distanceMatrix, int numColumns, int maxValue) {
        List<double[]> cityCoordinates = new ArrayList<>();
        double[] cityLocation = new double[3];
        Random rand = new Random();
        int upperBound = numColumns - 1;
        int k = rand.nextInt(upperBound);
        cityLocation[0] = k + 1;
        cityLocation[1] = 1;
        cityLocation[2] = 1;
        cityCoordinates.add(cityLocation);
        for (int i = 0; i <= numColumns - 1; i++) {
            cityLocation = new double[3];
            if (i != k) {
                cityLocation[0] = i + 1;
                cityLocation[1] = distanceMatrix[k][i];
                cityLocation[2] = rand.nextInt(maxValue - 1);
                cityCoordinates.add(cityLocation);
            }
        }
        return cityCoordinates;
    }

}
