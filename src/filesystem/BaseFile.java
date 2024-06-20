package filesystem;

import java.time.LocalDateTime;

abstract public class BaseFile {

    protected BaseFile parent;

    protected String name;
    protected LocalDateTime createdDate = LocalDateTime.now();

    public BaseFile(String name) {
        this.parent = null;
        this.name = name;
    }

    public BaseFile(BaseFile parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public BaseFile getParent() {
        return parent;
    }

    public void setParent(BaseFile parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
