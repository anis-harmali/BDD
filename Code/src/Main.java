
import java.io.IOException;
import java.lang.ref.Cleaner.Cleanable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		Constants.chemin = args[0];

		DBManager dbmanager = DBManager.getInstance();
		
		dbmanager.Clean();
		
		dbmanager.ProcessCommand("create R 3 int string3 int");
		dbmanager.ProcessCommand("insert R 1 aab 2");
		dbmanager.ProcessCommand("insert R 2 abc 2");
		dbmanager.ProcessCommand("insert R 1 agh 1");
		dbmanager.ProcessCommand("selectall R");
		
		
		dbmanager.ProcessCommand("delete R 3 2");
		dbmanager.ProcessCommand("selectall R");
		
	}

}
