
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Record {

	private RelDef reldef;
	private List<String> values;
	//Ajout d'un constructeur a vide
	public Record() {
		values = new ArrayList<String>();
	}
	
	public Record(RelDef reldef) {
		this.reldef = reldef;
		values = new ArrayList<String>();
	}
	
	public List<String> getValues() {
		return values;
	}
	
	public void setValues(String values) {
		this.values.add(values);
	}

	public void WriteToBuffer(ByteBuffer buff, int position) {
		buff.position(position);
		for (int i = 0; i < values.size(); i++) {
			if (reldef.getType().get(i).equals("int")) {
				buff.putInt(Integer.parseInt(values.get(i)));
			} else if (reldef.getType().get(i).equals("float")) {
				buff.putFloat(Float.parseFloat(values.get(i)));
			} else if (reldef.getType().get(i).substring(0, 5).equals("string")) {
				int valeur = Integer.parseInt(reldef.getType().get(i).substring(6));
				for (int j = 0; j < values.get(i).length(); j++) {
					buff.putChar(values.get(i).charAt(j));
				}
			}

		}
	}
	
	public void readFromBuffer(ByteBuffer buff,int position) {
		buff.position(position);
		for (int i = 0; i < reldef.getType().size(); i++) {
			if (reldef.getType().get(i).equals("int")) {
				values.add(String.valueOf(buff.getInt()));
			} else if (reldef.getType().get(i).equals("float")) {
				values.add(String.valueOf(buff.getFloat()));
			} else if (reldef.getType().get(i).substring(0, 5).equals("string")) {
				int valeur = Integer.parseInt(reldef.getType().get(i).substring(6));
				for (int j = 0; j <valeur; j++) {
					values.add(String.valueOf(buff.getChar(j)));
				}
					
				}
			}

		}
	}

