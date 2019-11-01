
import java.io.IOException;
import java.nio.ByteBuffer;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Constants.chemin = args[0];
		BufferManager b = BufferManager.getInstance();
		DBManager inst = DBManager.getInstance();
		ByteBuffer bu1 = null;
		ByteBuffer bu2 = null;
		ByteBuffer bu3 = null;
					
		try {
			inst.init();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DiskManager disk = DiskManager.getInstance();
		
		PageId pi1 = new PageId(1,3);
		PageId pi2 = new PageId(0,15);
		PageId pi3 = new PageId(1,4);
		
		
		bu1 = b.getPage(pi1);
		bu2 = b.getPage(pi2);
		bu3 = b.getPage(pi3);
		
		System.out.print(bu1.getChar());
		System.out.print(bu2.getChar());

			
			
		}
	
	}
