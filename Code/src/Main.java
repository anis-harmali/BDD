import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IllegalArgumentException, IOException {
		// TODO Auto-generated method stub
		Constants.chemin = args[0];
		ArrayList<Integer> list = new ArrayList<>();
		DBManager dbmanager = DBManager.getInstance();
		dbManager.Init();
		Scanner s = new Scanner(System.in);
		String commande = "";
		boolean b = true;
		
			do {
				System.out.println("Entrez votre commande");
				commande = s.nextLine();
				String[] comm = commande.split(" ");
				if (comm[0].equals("exit")) {
					dbmanager.finish();
					break;
				} else {
					try {
					dbmanager.ProcessCommand(commande);
					} catch (IllegalArgumentException i) {
						System.out.println("Mauvaise saisie");
					}
				}
			} while (b == true);
		
	}

}
