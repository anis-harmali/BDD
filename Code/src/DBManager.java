import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
	private static DBManager INSTANCE;
	private List<String> types = new ArrayList<String>();
	
	private DBManager() {

	}

	public static synchronized DBManager getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new DBManager();
			return INSTANCE;
		} else
			return INSTANCE;
	}

	public void init() {
		try {
			DBDef.getInstance().init();
			FileManager.getInstance().init();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void finish() {
		try {
			DBDef.getInstance().finish();
		} catch (IOException e) {}
		
	}

	public void ProcessCommand(String chaine) {
		String[] ch = chaine.split(" ");
		types = null;
		for (int i = 3, j = 0; i < ch.length; i++, j++) {
			types.add(j, ch[i]);
		}

		CreateRelation(ch[1], Integer.parseInt(ch[2]), types);

	}

	public static void CreateRelation(String nom, int nbcol, List<String> types) {
		int recordSize = 0;
		for (int i = 0; i < nbcol; i++) {
			if (types.get(i).equals("int")) {
				recordSize += 4;
			} else if (types.get(i).equals("float")) {
				recordSize += 4;
			} else if (types.get(i).substring(0, 5).equals("string")) {
				int valeur = Integer.parseInt(types.get(i).substring(6));
				recordSize += 2 * valeur;
			}
		}
		int slotCount = Constants.pageSize / recordSize;
		int fileIdx = DBDef.getInstance().getCompteur();

		RelDef reldef = new RelDef(nom, nbcol, types, fileIdx, recordSize, slotCount);
		DBDef.getInstance().addRelation(reldef);
		FileManager.getInstance().CreateRelationFile(reldef);
	}
	
	public void Clean() throws IOException {
		BufferManager.getInstance().raz();
		FileManager.getInstance().raz();
		DBDef.getInstance().raz();
	}
	
	public void insert(String[] commande) throws IOException {
		Record record = new Record();
		for (int i = 2; i < commande.length; i++) {
			record.setValues(commande[i]);
		}
		FileManager.getInstance().InsertRecordInRelation(record, commande[1]);

	}
}
