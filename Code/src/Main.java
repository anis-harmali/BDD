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
		dbmanager.ProcessCommand("create S 2 int int");
		dbmanager.ProcessCommand("insert S 1 2");
		dbmanager.ProcessCommand("join R S 1 1");
		dbmanager.ProcessCommand("join R S 3 2");
		
	}

}
