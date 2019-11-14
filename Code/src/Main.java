
import java.io.IOException;
import java.lang.ref.Cleaner.Cleanable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub
		Constants.chemin=args[0];

        BufferManager b = BufferManager.getInstance();
        DBManager dbmanager = DBManager.getInstance();
        DiskManager disk = DiskManager.getInstance();
        DBDef dbDef = DBDef.getInstance();
        FileManager fileManager = FileManager.getInstance();
        List<String> liste = new ArrayList<String>();
        dbmanager.Clean();
        liste.add("int");
        liste.add("float");
        liste.add("int");
       
        dbmanager.CreateRelation("R", 3, liste);
       	RelDef relDef = new RelDef("R", 2, liste,1, 20, 5);
        String[] commande = {"insert","R", "1","1.3","2"};
        dbmanager.insert(commande);  
        String[] commande2 = {"insert","R", "2","1.5","2"};
        dbmanager.insert(commande2);
        String[] commande3 = {"selectall","R"};
        dbmanager.selectall(commande3);
		}
	
	}
