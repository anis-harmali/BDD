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

	public ArrayList<HeapFile> getHeapFiles() {
		return heapFiles;
	}

	public void raz() throws IOException {
		heapFiles.clear();
	}

	public ArrayList<ArrayList<String>> join(String nomRelation1, String nomRelation2, int indice_colonne1, int indice_colonne2)
			throws IOException {
		ArrayList<PageId> listPage = new ArrayList<PageId>();
		ArrayList<PageId> listPage1 = new ArrayList<PageId>();
		ArrayList listefinale = new ArrayList();
		HeapFile b = null;
		HeapFile c = null;
		ArrayList<Record> listRecords = new ArrayList<Record>();
		for (int i = 0; i < heapFiles.size(); i++) {
			if (heapFiles.get(i).getReldef().getNom().equals(nomRelation1)) {
				b = heapFiles.get(i);
				listPage=heapFiles.get(i).getAllDataPage();
			}
			if (heapFiles.get(i).getReldef().getNom().equals(nomRelation2)) {
				c = heapFiles.get(i);
				listPage1=(heapFiles.get(i).getAllDataPage());
			}
		}
		for (int j = 0; j < listPage.size(); j++) {
			ArrayList<Record> re = b.getRecordsInDataPage(listPage.get(j));
			for (int k = 0; k < listPage1.size(); k++) {
				ArrayList<Record> r = c.getRecordsInDataPage(listPage1.get(k));
				for (int i = 0; i < re.size(); i++) {
					for (int x = 0; x < r.size(); x++) {
						if ((re.get(i).getValues().get(indice_colonne1)).equals(r.get(x).getValues().get(indice_colonne2))) {
							ArrayList liste = new ArrayList();
							liste.addAll(re.get(i).getValues());
							liste.addAll(r.get(x).getValues());
							listefinale.add(liste);
						}
						
					}
				}
			}
		}
		
		return listefinale;

	}
}
