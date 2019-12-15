
public class Noeud {
	private Noeud parent;
	protected Arbre monArbre;

	public Noeud(Noeud parent) {
		this.parent = parent;
	}
	
	public void setArbre(Arbre a) {
		this.monArbre = a;
	}
	

	public Noeud getParent() {
		return parent;
	}

	public void setParent(Noeud parent) {
		this.parent = parent;
	}
	
	

}
