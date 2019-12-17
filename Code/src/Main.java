import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException {
		Constants.chemin = args[0];
		DBManager dbmanager = DBManager.getInstance();
		dbmanager.init();
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
