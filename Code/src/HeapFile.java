import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class HeapFile {
	private RelDef reldef;
	DiskManager diskmanager = DiskManager.getInstance();
	BufferManager buffermanager = BufferManager.getInstance();

	public HeapFile(RelDef reldef) {
		this.reldef = reldef;
	}

	public RelDef getReldef() {
		return reldef;
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

	public PageId addDataPage() throws IOException {// ok
		PageId pi = diskmanager.AddPage(reldef.getFileIdx());
		int nbpages = pi.getPageIdx();
		PageId headerpage = new PageId(0, pi.getFileIdx());
		ByteBuffer buf = buffermanager.getPage(headerpage);
		buf.position(0);
		buf.putInt(nbpages);
		buf.position(nbpages * 4);
		buf.putInt(reldef.getSlotCount());
		buffermanager.freePage(headerpage, 1);
		return pi;
	}

	public PageId getFreeDataPageId() throws IOException {// ok
		int idfichier = reldef.getFileIdx();
		PageId pageid = new PageId(idfichier, 0);
		ByteBuffer b = buffermanager.getPage(pageid);
		b.position(0);
		int nbrepages = b.getInt();
		for (int i = 0; i < nbrepages; i++) {
			if (b.getInt() > 0) {

				return new PageId(i+1, this.reldef.getFileIdx());
			}
		}

		return null;
	}

	public Rid writeRecordToDataPage(Record record, PageId pageid) throws IOException {// ok
		ByteBuffer page = buffermanager.getPage(pageid);
		int nbreslots = reldef.getSlotCount();
		boolean isfind = false;
		page.position(0);
		while (nbreslots >= 1 && (isfind == false)) {
			nbreslots--;
			if (page.get() == 0) {
				isfind = true;
			}
		}
		int position = page.position()-1;
		page.put(position , (byte) 1);
		record.WriteToBuffer(page, reldef.getSlotCount() + position * reldef.getRecordSize());
		buffermanager.freePage(pageid, 1);
		return new Rid(pageid, reldef.getSlotCount());
	}

	public ArrayList<Record> getRecordsInDataPage(PageId pageId) throws IOException {
		ArrayList<Record> records = new ArrayList<Record>();
		ByteBuffer buffer = buffermanager.getPage(pageId);
		buffer.position(0);
		for (int i = 0; i < reldef.getSlotCount(); i++) {
			if ( buffer.get(i)== (byte) 1) {
				Record record = new Record();
				record.setReldef(this.reldef);
				record.readFromBuffer(buffer, reldef.getSlotCount() + i * reldef.getRecordSize());
				records.add(record);
			}
		}
		buffermanager.freePage(pageId, 0);
		return records;
	}

	public Rid InsertRecord(Record record) throws IOException {
		record.setReldef(this.reldef);
		Rid rid;
		PageId dpId = getFreeDataPageId();
		if (dpId == null) {
			dpId = addDataPage();

		}
		return rid = writeRecordToDataPage(record, dpId);

	}

	public ArrayList<Record> getAllRecords() throws IOException {
		ArrayList<Record> listederecord = new ArrayList<Record>();
		int fileIdx = reldef.getFileIdx();
		PageId headerPageId = new PageId(0, fileIdx);
		ByteBuffer byteBuffer = buffermanager.getPage(headerPageId);
		int nbrepages = byteBuffer.getInt(0);
		for (int i = 1; i <= nbrepages; i++) {
			PageId pageId = new PageId(i, fileIdx);
			listederecord.addAll(getRecordsInDataPage(pageId));// ajoute tt les record associé a pageId
		}
		return listederecord;
	}

}
