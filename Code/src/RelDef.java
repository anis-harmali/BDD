import java.io.Serializable;
import java.util.List;

public class RelDef implements Serializable {
	private String nom;
	private int nbcol;
	private List<String> type;
	private int fileIdx;
	private int recordSize;
	private int slotCount;

	public RelDef(String nom, int nbcol, List<String> type, int fileIdx, int recordSize, int slotCount) {
		this.nom = nom;
		this.nbcol = nbcol;
		this.type = type;
		this.fileIdx = fileIdx;
		this.recordSize = recordSize;
		this.slotCount = slotCount;

	}

	public List<String> getType() {
		return type;
	}

	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}

	public int getFileIdx() {
		return fileIdx;
	}

	public String getNom() {
		return nom;
	}

	public int getNbcol() {
		return nbcol;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public int getSlotCount() {
		return slotCount;
	}
	
	public void setSlotCount(int slotCount) {
		this.slotCount= slotCount;
	}
	

}
