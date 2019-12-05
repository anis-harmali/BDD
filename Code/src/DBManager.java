import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
	private static DBManager INSTANCE;
	private List<String> types = new ArrayList<String>();
	FileManager filemanager = FileManager.getInstance();
	BufferManager buffermanager = BufferManager.getInstance();

	// ff
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

	public void ProcessCommand(String commande) throws IOException {
		String[] tab = commande.split(" ");
		List<String> listetypeStrings = new ArrayList<String>();
		for (int i = 3; i < tab.length; i++) {
			listetypeStrings.add(tab[i]);
		}
		switch (tab[0]) {
		case "create":
			CreateRelation(tab[1], Integer.parseInt(tab[2]), listetypeStrings);
			break;
		case "insert":
			insert(tab);
			break;
		case "clean":
			Clean();
			break;
		case "selectall":
			selectall(tab[1]);
			break;
		case "select":
			select(tab);
			break;
		case "delete":
			delete(tab);
			break;
		case "insertall":
			insertAll(tab);
			break;
		case "join":
			join(tab);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + tab[0]);
		}

	}

	public void CreateRelation(String nom, int nbcol, List<String> types) throws IOException {
		int recordSize = 0;
		for (int i = 0; i < nbcol; i++) {
			if (types.get(i).equals("int")) {
				recordSize += 4;
			} else if (types.get(i).equals("float")) {
				recordSize += 4;
			} else if (types.get(i).substring(0, 6).equals("string")) {
				int valeur = Integer.parseInt(types.get(i).substring(6));
				recordSize += 2 * valeur;
			}
		}
		int slotCount = Constants.pageSize / (recordSize + 1);
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
		String nomRelation = commande[1];
		String csv = commande[1];
		File fichierCsv = new File(Constants.chemin + "/../" + commande[2]);

		List<String> lignes = new ArrayList<String>();
		FileReader fr = new FileReader(fichierCsv);
		BufferedReader buffer = new BufferedReader(fr);

		String ligne;

		while ((ligne = buffer.readLine()) != null) {
			Record record = new Record();
			String[] tmp = ligne.split(",");
			for (String j : tmp) {
				record.setValues(j);
			}

			filemanager.InsertRecordInRelation(record, nomRelation);
		}

		buffer.close();
		fr.close();

	}

	public void selectall(String nomcommande) throws IOException {
		ArrayList<Record> listederecord = new ArrayList<Record>();
		listederecord = filemanager.SelectAllFromRelation(nomcommande);
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
		int compt = 0;
		int file = filemanager.SelectAllFromRelation(nomRel).get(0).getReldef().getFileIdx();
		PageId headerpage = new PageId(0, file);
		for (int j = 0; j < filemanager.getHeapFiles().size(); j++) {
			if (filemanager.getHeapFiles().get(j).getReldef().getNom().equals(nomRel)) {
				compt = filemanager.getHeapFiles().get(j).deleterecords(indiceCol, valeur);
			}
		}
		buffermanager.freePage(headerpage, 1);
		System.out.println("Total records effacÃ©s : " + compt);
	}

	public void join(String[] commande) throws IOException {
		String nomRelation1 = commande[1];
		String nomRelation2 = commande[2];
		int indice_colonne1 = Integer.valueOf(commande[3]) - 1;
		int indice_colonne2 = Integer.valueOf(commande[4]) - 1;
		ArrayList<ArrayList<String>> array = new ArrayList<>();
		array = filemanager.join(nomRelation1, nomRelation2, indice_colonne1, indice_colonne2);
		System.out.println("nombre de tuples : " + array.size());
		for(int i=0;i<array.size();i++) {
			System.out.println(array.get(i));
		}
		System.out.println();

	}
}
