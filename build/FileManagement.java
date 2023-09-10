import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.*;

public class FileManagement {
    
    final private String FILE_NAME = "config.cfg";
    final private String SECTOR_LIST = "sectors.csv";
    private Boolean copyUSA = false;
    private Boolean zipFiles = false;

    public void readConfig() {
        System.out.println("Reading config.cfg...\n");
        Properties prop = new Properties();
        try {
            FileInputStream inFS = new FileInputStream(FILE_NAME);
            prop.load(inFS); 
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
            System.out.println("Reading sectors.csv...\n");
            while(inFS.hasNextLine()) {
                sectorsToCopy.add(inFS.nextLine());
            }
            inFS.close();

            ArrayList<String> sectors = new ArrayList<String>();
            ArrayList<String> sectorsW = new ArrayList<String>();
            File importDir = new File("import\\map\\usa"); 
            if(!importDir.exists()) { importDir.mkdirs();}
            System.out.println("Reading import\\map\\usa directory...\n");
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
            for (String sector : sectors) { sectorsW.add(sector.split("\\.", 2)[0]); }
            File exportDir = new File("export\\map\\usa"); 
            if(!exportDir.exists()) { exportDir.mkdirs(); }
            System.out.println("Copying sector files...\n");
            for (int i = 0; i < sectors.size();i++ ) {
                for (int j = 0; j < sectorsToCopy.size(); j++) {
                    if (sectorsW.get(i).equals(sectorsToCopy.get(j))) {
                        Files.copy(Paths.get("import\\map\\usa\\" + sectors.get(i)), Paths.get("export\\map\\usa\\" + sectors.get(i)), StandardCopyOption.REPLACE_EXISTING);
                        System.out.println("Copied " + sectors.get(i) + " to export\\map\\usa");
                    }
                }
            }
            if (copyUSA) {
                System.out.println("\nCopying usa.mbd...\n");
                Files.copy(Paths.get("import\\map\\usa.mbd"), Paths.get("export\\map\\usa.mbd"), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Copied usa.mbd to export\\map");
            }
            System.out.println("Done!");
        } catch(FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch(IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void zipFiles() {
        System.out.println("Not finished");
    }
}