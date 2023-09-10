import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManagement {
    
    final private String FILE_NAME = "config.cfg";
    final private String SECTOR_LIST = "sectors.csv";
    private String outputDir = "";
    private Boolean copyUSA = false;
    private Boolean zipFiles = false;

    public void readConfig() {
        Properties prop = new Properties();
        try {
            FileInputStream inFS = new FileInputStream(FILE_NAME);
            prop.load(inFS); 
            outputDir = prop.getProperty("P_OUTPUT_DIR");
            copyUSA = Boolean.parseBoolean(prop.getProperty("P_COPY_USA"));
            zipFiles = Boolean.parseBoolean(prop.getProperty("P_ZIP_FILES"));
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void copyFiles() {
        try {
            FileInputStream fileIn = new FileInputStream(SECTOR_LIST);
            Scanner inFS = new Scanner(fileIn);
            ArrayList<String> sectorsToCopy = new ArrayList<String>();
            while(inFS.hasNextLine()) {
                sectorsToCopy.add(inFS.nextLine());
            }
            inFS.close();

            ArrayList<String> sectors = new ArrayList<String>();
            ArrayList<String> sectorsW = new ArrayList<String>();
            File importDir = new File("import\\map\\usa"); 
            if (importDir.exists() && importDir.isDirectory()) {
                File[] files = importDir.listFiles();
                if (files != null) {
                    for (File file : files) {
                        sectors.add(file.getName());
                    }
                }
            } else {
                System.out.println("The specified directory does not exist or is not a directory.");
            }
        for (String sector : sectors) { sectorsW.add(sector.split("\\.", 2)[0]); /*System.out.println(sector.split("\\.", 2)[0]);*/ }
            File exportDir = new File("export\\map\\usa"); 
            if(!exportDir.exists()) {
                exportDir.mkdirs();
            }
            for (int i = 0; i < sectors.size();i++ ) {
                for (int j = 0; j < sectorsToCopy.size(); j++) {
                    if (sectorsW.get(i).equals(sectorsToCopy.get(j))) {
                        Files.copy(Paths.get("import\\map\\usa\\" + sectors.get(i)), Paths.get("export\\map\\usa\\" + sectors.get(i)), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Copied " + sectors.get(i) + " to export\\map\\usa");
                    }
                }
            }

        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch(IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

}