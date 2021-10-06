import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class deals with file management.
 * It provides features to read and write city coordinates from and into the text file
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
        int startIndex = fileContent.indexOf("$", 0) + 1;
        return extractCities(fileContent.substring(startIndex));
    }

    /**
     * This class writes the city coordinates into the text file
     * @param cities list of cities whose coordinates need to be written
     * @return returns a boolean to check if the write operation has been successful
     */
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
     * This method extracts city information from the file string.
     *
     * @param fileContent: a stream of data read from the file
     * @return a list of cities along with the coordinates.
     */
    public List<City> extractCities(String fileContent) {
        List<City> cities = new ArrayList<>();
        fileContent = fileContent.replaceAll("\\$", " ");
        String split[] = fileContent.split(" ");
        for (int i = 0; i < split.length; ) {
            City city = new City(split[i + 1], Integer.parseInt(split[i + 2]), Integer.parseInt(split[i + 3]), 10, 10);
            city.id = Integer.parseInt(split[i + 0]);
            i = i + 4;
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
}
