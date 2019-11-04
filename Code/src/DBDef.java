import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;




public class DBDef implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static DBDef INSTANCE;
	private List<RelDef> definition;
	private int compteur;
	
	private DBDef() {
		this.definition = new ArrayList<RelDef>();
	}
	
	public static synchronized DBDef getInstance() {
		
		if(INSTANCE == null) {
			INSTANCE =  new DBDef();
			return INSTANCE;
		}
		else 
			return INSTANCE;
	}	
		
	public void init() throws IOException, ClassNotFoundException {
		File saveFile=new File(Constants.chemin+"/Catalog.def");
		if (saveFile.exists()) {
			FileInputStream file=new FileInputStream(saveFile);
			ObjectInputStream in= new ObjectInputStream(file);
			DBDef.INSTANCE=(DBDef) in.readObject();
			in.close();
			file.close();
			}	
		}
		
	public void finish() throws IOException {
		File saveFile=new File(Constants.chemin+"/Catalog.def");
			
		FileOutputStream file= new FileOutputStream(saveFile);
		ObjectOutputStream out= new ObjectOutputStream(file);
		out.writeObject(DBDef.getInstance());
		out.close();
		file.close();
	}
		
	public void addRelation(RelDef a) {
		definition.add(a);
		this.compteur++;
	}
		
	public int getCompteur() {
		return compteur;
	}
	
	public void setCompteur(int compteur) {
		this.compteur = compteur;
	}

	public void setDefinition(List<RelDef> definition) {
		this.definition = definition;
	}
		
	public List<RelDef> getDefinition() {
		return definition;
	}
	
	public void raz() {
		this.definition.clear();
		this.setCompteur(0);
	}	
		
}
