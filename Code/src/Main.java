
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
		

		dbmanager.ProcessCommand("create S 8 string2 int string4 float string5 int int int");
		dbmanager.ProcessCommand("insertall S S1.csv");
		//dbmanager.ProcessCommand("selectall S");
		dbmanager.ProcessCommand("select S 3 Nati");
	}

}
