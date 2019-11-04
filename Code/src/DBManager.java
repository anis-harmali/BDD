import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
	private static DBManager INSTANCE;
	private List<String> types = new ArrayList<String>();
	FileManager filemanager = FileManager.getInstance();
	//ff
	private DBManager() {

	}

	public static synchronized DBManager getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new DBManager();
			return INSTANCE;
		} else
			return INSTANCE;
	}

	public void init() throws IOException, ClassNotFoundException {
		DBDef.getInstance().init();
		filemanager.init();
	}

	public void finish() throws IOException {
		DBDef.getInstance().finish();
	}

	public void ProcessCommand(String chaine) throws IOException {
		String[] ch = chaine.split(" ");
		types = null;
		for (int i = 3, j = 0; i < ch.length; i++, j++) {
			types.add(j, ch[i]);
		}

		CreateRelation(ch[1], Integer.parseInt(ch[2]), types);

	}

	public void CreateRelation(String nom, int nbcol, List<String> types) throws IOException {
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
		int slotCount = Constants.pageSize / (recordSize+1);
		int fileIdx = DBDef.getInstance().getCompteur();

		RelDef reldef = new RelDef(nom, nbcol, types, fileIdx, recordSize, slotCount);
		DBDef.getInstance().addRelation(reldef);
		filemanager.CreateRelationFile(reldef);
	}

	public void Clean() throws IOException {
		File repertoire = new File(Constants.chemin);
		File[] fichiers = repertoire.listFiles();
		for (int i = 0; i < fichiers.length; i++) {
			fichiers[i].delete();
		}
		BufferManager.getInstance().raz();
		filemanager.raz();
		DBDef.getInstance().raz();
	}

	public void insert(String[] commande) throws IOException {
		Record record = new Record();
		for (int i = 2; i < commande.length; i++) {
			record.setValues(commande[i]);
		}
		filemanager.InsertRecordInRelation(record, commande[1]);

	}

	public void insertAll(String[] commande) throws IOException {
		String nomRelation = commande[0];
		String csv = commande[1];
		File fichierCsv = new File(Constants.chemin + "\\" + csv);

		List<String> lignes = new ArrayList<String>();
		FileReader fr = new FileReader(fichierCsv);
		BufferedReader buffer = new BufferedReader(fr);

		String ligne;

		while ((ligne = buffer.readLine()) != null) {
			lignes.add(ligne);
		}

		ArrayList<Record> tabRecords = new ArrayList<Record>();
		// on met tout les record du fichier dans un tableau de record
		for (String i : lignes) {
			Record record = new Record();
			String[] tmp = i.split(",");

			for (String j : tmp) {
				record.setValues(j);
			}
			tabRecords.add(record);
		}

		for (Record record : tabRecords) {
			filemanager.InsertRecordInRelation(record, nomRelation);
		}

		buffer.close();
		fr.close();

	}

	public void selectall(String[] commande) throws IOException {
		ArrayList<Record> listederecord = new ArrayList<Record>();
		listederecord.addAll(filemanager.SelectAllFromRelation(commande[1]));
		for (int i = 0; i < listederecord.size(); i++) {
			for (int j = 0; j < listederecord.get(i).getValues().size(); j++) {
				System.out.print(listederecord.get(i).getValues().get(j).toString() + " ; ");
			}
			System.out.println();
		}
		System.out.println("Total records : " + listederecord.size());
	}

	public void select(String[] commande) throws IOException {
		String nomRel = commande[1];
		int indiceCol = Integer.valueOf(commande[2]) - 1;
		String valeur = commande[3];
		ArrayList<Record> listederecord = new ArrayList<Record>();
		listederecord.addAll(filemanager.SelectFromRelation(nomRel, indiceCol, valeur));
		for (int i = 0; i < listederecord.size(); i++) {

			System.out.print(listederecord.get(i).getValues().toString() + " ; ");

			System.out.println();
		}
		System.out.println("Total records : " + listederecord.size());
	}
	
	public void delete(String[] commande) throws IOException {
		String nomRel = commande[1];
		int indiceCol = Integer.valueOf(commande[2]);
		String valeur = commande[3];
		ArrayList<Record> listederecord = new ArrayList<Record>();
		listederecord.addAll(filemanager.SelectFromRelation(nomRel, indiceCol, valeur));
		int x = listederecord.size();
		for(int i=0;i<listederecord.size();i++) {
			listederecord.remove(i);
			
		}
		System.out.println("Total records effacés : " + x);
	}
}
