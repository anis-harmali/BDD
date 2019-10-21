import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class HeapFile {
	private RelDef reldef;
	DiskManager diskmanager = DiskManager.getInstance();
	BufferManager buffermanager = BufferManager.getInstance();

	public HeapFile(RelDef reldef) {
		this.reldef = reldef;
	}

	public void createNewOnDisk() throws IOException {
		diskmanager.CreateFile(reldef.getFileIdx());
		PageId pid = diskmanager.AddPage(reldef.getFileIdx());
		ByteBuffer buf = buffermanager.getPage(pid);

		for (int i = 0; i < Constants.pageSize; i++) {
			buf.put((byte) 0);
		}

		buffermanager.freePage(pid, 1);
	}

	public PageId addDataPage() throws IOException {
		PageId pi = diskmanager.AddPage(reldef.getFileIdx());
		ByteBuffer buf = buffermanager.getPage(pi);

		RandomAccessFile file = new RandomAccessFile(new File(Constants.chemin + "/Data_" + pi.getFileIdx() + ".rf"),
				"rw");
		int nbpages = ((int) file.length() / Constants.pageSize) - 1;

		buf.putInt(nbpages);
		buf.putInt(reldef.getSlotCount());

		return pi;

	}
}
