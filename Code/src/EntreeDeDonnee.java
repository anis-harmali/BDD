import java.util.ArrayList;

public class EntreeDeDonnee {
	private int cle;
	private ArrayList<Rid> listederid;
	
	public EntreeDeDonnee(int cle, ArrayList<Rid> listederid) {
		this.cle = cle;
		this.listederid = listederid;
	}

	public int getCle() {
		return cle;
	}

	public ArrayList<Rid> getListederid() {
		return listederid;
	}
	
}
