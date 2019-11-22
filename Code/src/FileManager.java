import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
	DBDef dbdef = DBDef.getInstance();
	private ArrayList<HeapFile> heapFiles;
	private static FileManager INSTANCE;

	private FileManager() {
		heapFiles = new ArrayList<HeapFile>();
	}

	public static synchronized FileManager getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new FileManager();
			return INSTANCE;
		} else
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

	public Rid InsertRecordInRelation(Record record, String relName) throws IOException {
		Rid rid = null;
		for (int i = 0; i < heapFiles.size(); i++) {

			if (heapFiles.get(i).getReldef().getNom().equals(relName)) {
				rid = heapFiles.get(i).InsertRecord(record);
				return rid;

			}
		}

		return rid;
	}

	public ArrayList<Record> SelectAllFromRelation(String relName) throws IOException {
		ArrayList<Record> listederecord = null;
		for (int i = 0; i < heapFiles.size(); i++) {
			if (heapFiles.get(i).getReldef().getNom().equals(relName)) {
				listederecord = heapFiles.get(i).getAllRecords();
			}
		}
		return listederecord;
	}

	public ArrayList<Record> SelectFromRelation(String relName, int idxCol, String valeur) throws IOException {
		ArrayList<Record> listederecord = new ArrayList<Record>();
		ArrayList<Record> liste = new ArrayList<Record>();
		liste = SelectAllFromRelation(relName);
		for (int i = 0; i < liste.size(); i++) {
			if (liste.get(i).getValues().get(idxCol).equals(valeur)) {
				listederecord.add(liste.get(i));
			}
		}

		return listederecord;
	}

	public void raz() throws IOException {
		heapFiles.clear();
	}

}
