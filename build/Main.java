public class Main {
    static FileManagement file = new FileManagement();
    public static void main(String[] args) {
        file.readConfig();
        file.copyFiles();
    }
}