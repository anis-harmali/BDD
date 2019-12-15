
public class EntreeDIndex {
	private int cle;
	private Noeud filspointe;

	public EntreeDIndex(int cle, Noeud filspointe) {
		this.cle = cle;
		this.filspointe = filspointe;
	}

	public int getCle() {
		return cle;
	}

	public void setCle(int cle) {
		this.cle = cle;
	}

	public Noeud getFilspointe() {
		return filspointe;
	}

	public void setFilspointe(Noeud filspointe) {
		this.filspointe = filspointe;
	}
	
}
