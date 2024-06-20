package filesystem;

import java.util.HashSet;
import java.util.Set;

public class Directory extends BaseFile {

    private Set<BaseFile> subEntities = new HashSet<>();   // points to all sub folders belong to this particular directory

    public Directory(String name) {
        super(name);
    }

    public Directory(Directory parent, String name) {
        super(parent, name);
    }

    public Set<BaseFile> getSubEntities() {
        return subEntities;
    }

    public void deleteSubEntity(BaseFile baseFile) {
        subEntities.remove(baseFile);
    }
}
