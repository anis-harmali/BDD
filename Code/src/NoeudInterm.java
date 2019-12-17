import java.util.ArrayList;

public class NoeudInterm extends Noeud {

	private Noeud pointeur;
	private ArrayList<EntreeDIndex> listeentreeindex;

	public NoeudInterm(Noeud pointeur, ArrayList<EntreeDIndex> listeentreeindex) {
		super(pointeur);
		this.listeentreeindex = listeentreeindex;
	}

	public Noeud getPointeur() {
		return pointeur;
	}

	public void setPointeur(Noeud pointeur) {
		this.pointeur = pointeur;
	}

	public ArrayList<EntreeDIndex> getListeentreeindex() {
		return listeentreeindex;
	}

	public void setListeentreeindex(ArrayList<EntreeDIndex> listeentreeindex) {
		this.listeentreeindex = listeentreeindex;
	}
	
	//private NoeudInterm split() {
		
		
	//}
	
	/*public NoeudInterm rajouterED(EntreeDIndex entreeindex) {
		listeentreeindex.add(entreeindex);
		entreeindex.getFilspointe().setParent(this);
		if(listeentreeindex.size()==2*monArbre.getOrdre()+1)
			return this.split();
		else return this;
	}*/
	
	

	/*public NoeudInterm accrocherfeuille(Feuille feuille) {
		EntreeDIndex ed= new EntreeDIndex(feuille.getListeentred().get(0).getCle(), feuille);
		return rajouterED(ed);
		
		
	}
	*/
	
}
