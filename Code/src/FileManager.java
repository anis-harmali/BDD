import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
	DBDef dbdef = DBDef.getInstance();
	private ArrayList<HeapFile> heapFiles;
	private static FileManager INSTANCE;

	/**
	 * Constructeur Ã  vide
	 */
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

	/**
	 * 
	 */
	public void init() {
		for (int i = 0; i < dbdef.getDefinition().size(); i++) {
			HeapFile heapfile = new HeapFile(dbdef.getDefinition().get(i));
			heapFiles.add(heapfile);

		}

	}

	/**
	 * Fonction qui crÃ©e un fichier contenant la relation fournit en paramÃ¨tre
	 * 
	 * @param reldef une relation
	 * @throws IOException
	 * 
	 */
	public void CreateRelationFile(RelDef reldef) throws IOException {
		HeapFile heapfile = new HeapFile(reldef);
		heapFiles.add(heapfile);
		heapfile.createNewOnDisk();

	}

	/**
	 * Fonction qui insere un record dans une relation
	 * 
	 * @param record  le record Ã  inserer dans la relation
	 * @param relName nom de la relation
	 * @return un rid
	 * @throws IOException
	 */
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

	/**
	 * Recupere tous les record ayant pour nom de relation celui passÃ© en parametre
	 * 
	 * @param relName nom de la relation
	 * @return la liste des record ayant pour nom de relation "relname"
	 * @throws IOException
	 */
	public ArrayList<Record> SelectAllFromRelation(String relName) throws IOException {
		ArrayList<Record> listederecord = null;
		for (int i = 0; i < heapFiles.size(); i++) {
			if (heapFiles.get(i).getReldef().getNom().equals(relName)) {
				listederecord = heapFiles.get(i).getAllRecords();
			}
		}
		return listederecord;
	}

	/**
	 * Recupere tous les record ayant le meme nom de relation et pour un indice de
	 * colonne donnÃ©e une valeur precise
	 * 
	 * @param relName le nom de la relation
	 * @param idxCol  l'indice de la colonne
	 * @param valeur  la valeur de la colonne
	 * @return tous les records ayant pour nom, valeur et indice de colones les
	 *         valeurs fournies en parametre
	 * @throws IOException
	 */
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

	/**
	 * Recupere l'attribut du filemanager "heapfile"
	 * 
	 * @return liste de heapfiles
	 */
	public ArrayList<HeapFile> getHeapFiles() {
		return heapFiles;
	}

	/**
	 * Supprime toutes les valeurs de l'attribut heapfile pour une remise a zero
	 * 
	 * @throws IOException
	 */
	public void raz() throws IOException {
		heapFiles.clear();
	}

	/**
	 * Donne lâ€™Ã©quijointure de deux relations fournies en parametre par leurs noms
	 * et les indices de leurs colonnes.
	 * 
	 * @param nomRelation1    le nom de la premiere relation
	 * @param nomRelation2    le nom de la deuxieme relation
	 * @param indice_colonne1 l'indice de la 1ere relation
	 * @param indice_colonne2 l'indice de la 2eme relation
	 * @return une liste de liste de l'equijointure des deux relations
	 * @throws IOException
	 */
	public ArrayList<ArrayList<String>> join(String nomRelation1, String nomRelation2, int indice_colonne1,
			int indice_colonne2) throws IOException {
		ArrayList<PageId> listPage = new ArrayList<PageId>();
		ArrayList<PageId> listPage1 = new ArrayList<PageId>();
		ArrayList listefinale = new ArrayList();
		HeapFile b = null;
		HeapFile c = null;
		ArrayList<Record> listRecords = new ArrayList<Record>();
		for (int i = 0; i < heapFiles.size(); i++) {
			if (heapFiles.get(i).getReldef().getNom().equals(nomRelation1)) {
				b = heapFiles.get(i);
				listPage = heapFiles.get(i).getAllDataPage();
			}
			if (heapFiles.get(i).getReldef().getNom().equals(nomRelation2)) {
				c = heapFiles.get(i);
				listPage1 = (heapFiles.get(i).getAllDataPage());
			}
		}
		for (int j = 0; j < listPage.size(); j++) {
			ArrayList<Record> re = b.getRecordsInDataPage(listPage.get(j));
			for (int k = 0; k < listPage1.size(); k++) {
				ArrayList<Record> r = c.getRecordsInDataPage(listPage1.get(k));
				for (int i = 0; i < re.size(); i++) {
					for (int x = 0; x < r.size(); x++) {
						if ((re.get(i).getValues().get(indice_colonne1))
								.equals(r.get(x).getValues().get(indice_colonne2))) {
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
