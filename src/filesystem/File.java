package filesystem;

public class File extends BaseFile {

    private long size;

    public File(String name, long size) {
        super(name);
        this.size = size;
    }

    public File(Directory parent, String name, long size) {
        super(parent, name);
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
