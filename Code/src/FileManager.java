import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
	DBDef dbdef = DBDef.getInstance();
	private ArrayList<HeapFile> heapFiles;

	private FileManager() {
		heapFiles = new ArrayList<HeapFile>();
	}

	public static final synchronized FileManager getInstance() {
		final FileManager INSTANCE = new FileManager();
		return INSTANCE;
	}

	public void init() {
		for (int i = 0; i < dbdef.getDefinition().size(); i++) {
			HeapFile heapfile = new HeapFile(dbdef.getDefinition().get(i));
			heapFiles.add(heapfile);
		
		}

	}

	public void CreateRelationFile(RelDef reldef) throws IOException {
		HeapFile heapfile = new HeapFile(reldef);
		heapFiles.add(heapfile);
		heapfile.createNewOnDisk();
	}
}
