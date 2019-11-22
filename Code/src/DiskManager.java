import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class DiskManager {
	private static DiskManager INSTANCE;

	private DiskManager() {
	}

	public static synchronized DiskManager getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new DiskManager();
			return INSTANCE;
		} else
			return INSTANCE;
	}

	public void CreateFile(int fileIdx) throws IOException {
		FileWriter file1 = new FileWriter(Constants.chemin + "/Data_" + fileIdx + ".rf");
		file1.close();
	}

	public PageId AddPage(int fileIdx) throws IOException {
		RandomAccessFile fw = new RandomAccessFile(new File(Constants.chemin + "/Data_" + fileIdx + ".rf"), "rw");
		fw.seek(fw.length());
		for(int i=0;i<Constants.pageSize;i++) {
			fw.write((byte) 0);
		}
		PageId pi = new PageId(((int) fw.length() / Constants.pageSize) - 1, fileIdx);
		
		fw.close();

		return pi;
	}

	public void ReadPage(PageId pageId, ByteBuffer buff) throws IOException {
		RandomAccessFile file = new RandomAccessFile(Constants.chemin + "/Data_" + pageId.getFileIdx() + ".rf", "r");
		int pos = pageId.getPageIdx() * Constants.pageSize;
	
			file.seek(pos);
			file.read(buff.array());
			file.close();
	}

	public void Writepage(PageId pageId, ByteBuffer buff) throws IOException {
		RandomAccessFile file = new RandomAccessFile(Constants.chemin + "/Data_" + pageId.getFileIdx() + ".rf", "rw");
		int pos = pageId.getPageIdx() * Constants.pageSize;
		file.seek(pos);
		file.write(buff.array());
		file.close();

	}

}
