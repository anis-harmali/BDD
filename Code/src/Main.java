
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Constants.chemin=args[0];
		ByteBuffer buff = ByteBuffer.allocate(10) ;
        BufferManager b = BufferManager.getInstance();
        DBManager dbmanager = DBManager.getInstance();
        DiskManager disk = DiskManager.getInstance();
        DBDef dbDef = DBDef.getInstance();
        FileManager fileManager = FileManager.getInstance();
        List<String> liste = new ArrayList<String>();
        liste.add("int");
        liste.add("int");
        disk.CreateFile(12);
        RelDef relDef = new RelDef("R", 2, liste,0, 20, 100);
        Record record = new Record(relDef);
        fileManager.CreateRelationFile(relDef);
        fileManager.InsertRecordInRelation(record, "Rel");
        record.readFromBuffer(buff, 0);
        System.out.print(record.getValues());
		
		}
	
	}
