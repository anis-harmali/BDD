import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class Arbre {
	private Noeud racine;
	private int ordre;

	public Arbre(Noeud racine, int ordre) {
		this.racine = racine;
		this.ordre = ordre;
	}

	@SuppressWarnings("null")
	public ArrayList<Feuille> recupFeuilles(TreeMap<Integer, ArrayList<Rid>> map) {
		ArrayList<Feuille> listedefeuilles = null;
		Feuille feuillecourante = new Feuille(null);
		feuillecourante.setArbre(this);
		for (int cle : map.keySet()) {
			ArrayList<Rid> lst = map.get(cle);
			EntreeDeDonnee ent = new EntreeDeDonnee(cle, lst);
			feuillecourante.rajoutListeentred(ent);
			if (feuillecourante.estPleine(this.ordre)) {
				listedefeuilles.add(feuillecourante);
				feuillecourante = new Feuille(null);
				feuillecourante.setArbre(this);
			}
		}
		if (!feuillecourante.estVide(this.ordre)) {
			listedefeuilles.add(feuillecourante);
		}
		return listedefeuilles;
	}

	/*public void ConstruireArbre(ArrayList<Feuille> listedefeuilles) {
		ArrayList<EntreeDeDonnee> listeed = new ArrayList<>();
		listeed.add(new)
		NoeudInterm ni = new NoeudInterm(listedefeuilles.get(0),new);
		ni.setArbre(this);
	}*/

	public int getOrdre() {
		return ordre;
	}

}
