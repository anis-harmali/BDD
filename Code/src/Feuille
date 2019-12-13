import java.util.ArrayList;

public class Feuille extends Noeud {
	private ArrayList<EntreeDeDonnee> listeentred;
	

	public ArrayList<EntreeDeDonnee> getListeentred() {
		return listeentred;
	}

	public Feuille(Noeud parent) {
		super(parent);
	}

	public Feuille(Noeud parent, ArrayList<EntreeDeDonnee> listeentred) {
		this(parent);
		this.listeentred=listeentred;
	}

	public void rajoutListeentred(EntreeDeDonnee ent) {
		this.listeentred.add(ent);
	}

	public boolean estPleine(int ordre) {
		return listeentred.size() == 2*ordre;
	}
	
	public boolean estVide(int ordre) {
		return listeentred.size()==0;
	}

}
