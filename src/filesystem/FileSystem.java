package filesystem;

import java.util.HashMap;
import java.util.Map;

public class FileSystem {

    private static final String ROOT_FOLDER_NAME = "ROOT";
    private final Directory ROOT;

    private Map<String, BaseFile> fileSystemMap = new HashMap<>();  // used to hold an entire picture of the file system

    public FileSystem() {
        ROOT = new Directory(ROOT_FOLDER_NAME);
        fileSystemMap.put(ROOT_FOLDER_NAME, ROOT);
    }

    public void addFile(String name, long size) {
        this.addFile(ROOT_FOLDER_NAME, name, size);
    }

    public void addFile(String parentDirName, String name, long size) {
        Directory parentDir = (Directory) fileSystemMap.get(parentDirName);
        if (parentDir != null) {
            this.addEntity(parentDir, new File(name, size));
            System.out.println("File " + name + " added under parent directory " + parentDirName);
        } else {
            System.out.println("Parent directory " + parentDirName + " does not exist.");
        }
    }

    public void addDir(String name) {
        this.addDir(ROOT_FOLDER_NAME, name);
    }

    public void addDir(String parentDirName, String name) {
        Directory parentDir = (Directory) fileSystemMap.get(parentDirName);
        if (parentDir != null) {
            this.addEntity(parentDir, new Directory(name));
            System.out.println("Directory " + name + " added under parent directory " + parentDirName);
        } else {
            System.out.println("Parent directory " + parentDirName + " does not exist.");
        }
    }

    private void addEntity(Directory parentDirectory, BaseFile baseFile) {

        parentDirectory.getSubEntities().add(baseFile);
        baseFile.setParent(parentDirectory);

        fileSystemMap.put(baseFile.getName(), baseFile);
    }

    public void describeFileSystem() {
        printNode(ROOT, " ");
    }

    private void printNode(BaseFile baseFile, String indent) {
        System.out.println(indent + baseFile.getName());
        if (baseFile instanceof Directory) {
            Directory directory = (Directory) baseFile;
            for (BaseFile subEntity : directory.getSubEntities()) {
                printNode(subEntity, indent + "  ");
            }
        }
    }

    public void delete(String name) {
        BaseFile baseFile = fileSystemMap.get(name);
        if (baseFile != null) {
            fileSystemMap.remove(baseFile.getName());
            BaseFile parentBaseFile = baseFile.getParent();
            String deleteMsg = "The file " + baseFile.getName() + " deleted";
            if (parentBaseFile != null) {
                ((Directory) parentBaseFile).deleteSubEntity(baseFile);
                deleteMsg += " from the folder " + parentBaseFile.getName();
            }
            System.out.println(deleteMsg);
        } else {
            System.out.println("File or directory " + name + " does not exist.");
        }
    }
}
