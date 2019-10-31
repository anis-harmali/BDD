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

	public PageId getFreeDataPageId() throws IOException {
		int idfichier = reldef.getFileIdx();
		PageId pageid = new PageId(idfichier, 0);
		ByteBuffer b = buffermanager.getPage(pageid);
		int nbrepages = b.getInt(0);
		for (int i = 0; i < nbrepages; i++) {
			if (b.getInt(i) > 0) {

				return new PageId(this.reldef.getFileIdx(), i);
			}
		}

		return null;
	}

	public Rid writeRecordToDataPage(Record record, PageId pageid) throws IOException {
		ByteBuffer page = buffermanager.getPage(pageid);
		int nbreslots = reldef.getSlotCount();
		int i = 0;
		boolean isfind = false;
		while (nbreslots >= 1 && isfind) {
			if (page.getInt(i) == 1) {
				isfind = true;
			} else
				i++;
		}
		record.WriteToBuffer(page, nbreslots + i * reldef.getRecordSize());
		reldef.setSlotCount(nbreslots - 1);
		return new Rid(pageid, reldef.getSlotCount());
	}

	 public ArrayList<Record> getRecordsInDataPage(PageId pageId) throws IOException{
			
			ArrayList<Record> records = new ArrayList<Record>();
			
			ByteBuffer buffer = buffermanager.getPage(pageId);
			
			 for (int i = 0; i < reldef.getSlotCount(); i++) {
				
				 	
				 if(buffer.getInt(i)==(byte)1) {
					
					 Record record = new Record();
					 //record = readRecordFromBuffer(buffer,i );
				
					 records.add(record);
					
					 
		   	 }
			 }
			 bufferManager.freePage(pageId, 0);
			 return records;
		}
		
	public Rid InsertRecord(Record record) throws IOException {
		 Rid rid = new Rid();
		 
		 
		 
		 return rid;
	 }
	 
	 
	 public ArrayList<Record> GetAllRecords() throws IOException {
		 ArrayList<Record> listederecord = new ArrayList<Record>();
		 
		 
		 
		 return listederecord;
	 }


}
