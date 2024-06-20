package filesystem;

public class Main {

    private FileSystem fileSystem = new FileSystem();

    public static void main(String[] args) {

        System.out.printf("Hello and welcome to the file system!");

        Main main = new Main();
        main.fileSystem.addDir("1");
        main.fileSystem.addDir("2");
        main.fileSystem.addDir("1", "11");
        main.fileSystem.addDir("2", "12");
        main.fileSystem.addDir("12", "121");

        main.fileSystem.addFile("1", "1.file", 12);
        main.fileSystem.addFile("11", "11.file", 12);
        main.fileSystem.addFile("12", "12.file", 12);
        main.fileSystem.addFile("121", "121.file", 12);
        main.fileSystem.addFile("121", "122.file", 12);

        main.fileSystem.describeFileSystem();

        main.fileSystem.delete("122.file");
        main.fileSystem.delete("11");

        main.fileSystem.describeFileSystem();
    }
}