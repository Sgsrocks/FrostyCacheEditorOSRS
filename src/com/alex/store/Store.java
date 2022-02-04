package com.alex.store;

import com.alex.io.OutputStream;
import com.alex.store.Archive;
import com.alex.store.Index;
import com.alex.store.MainFile;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;

public final class Store {
   private String path;
   private boolean newProtocol;
   private RandomAccessFile data;
   private byte[] readCachedBuffer;
   private MainFile index255;
   private Index[] indexes;

   public Store(String path) throws IOException {
      this(path, true);
   }

   public Store(String path, boolean newProtocol) throws IOException {
      this.path = path;
      this.newProtocol = newProtocol;
      this.data = new RandomAccessFile(path + "main_file_cache.dat2", "rw");
      this.readCachedBuffer = new byte[520];
      this.index255 = new MainFile(255, this.data, new RandomAccessFile(path + "main_file_cache.idx255", "rw"), this.readCachedBuffer, newProtocol);
      int idxsCount = this.index255.getArchivesCount();
      this.indexes = new Index[idxsCount];

      for(int id = 0; id < idxsCount; ++id) {
         Index index = new Index(this.index255, new MainFile(id, this.data, new RandomAccessFile(path + "main_file_cache.idx" + id, "rw"), this.readCachedBuffer, newProtocol));
         if(index.getTable() != null) {
            this.indexes[id] = index;
         }
      }

   }

   public byte[] generateIndex255Archive255() {
      OutputStream stream = new OutputStream(this.indexes.length * 8);

      for(int archive = 0; archive < this.indexes.length; ++archive) {
         if(this.indexes[archive] == null) {
            stream.writeInt(0);
            stream.writeInt(0);
         } else {
            stream.writeInt(this.indexes[archive].getCRC());
            stream.writeInt(this.indexes[archive].getTable().getRevision());
         }
      }

      byte[] var3 = new byte[stream.getOffset()];
      stream.setOffset(0);
      stream.getBytes(var3, 0, var3.length);
      return var3;
   }

   public Index[] getIndexes() {
      return this.indexes;
   }

   public MainFile getIndex255() {
      return this.index255;
   }

   public int addIndex(boolean named, boolean usesWhirpool, int tableCompression) throws IOException {
      int id = this.indexes.length;
      OutputStream stream = new OutputStream(4);
      stream.writeByte(5);
      stream.writeByte((named?1:0) | (usesWhirpool?2:0));
      stream.writeShort(0);
      byte[] archiveData = new byte[stream.getOffset()];
      stream.setOffset(0);
      stream.getBytes(archiveData, 0, archiveData.length);
      Archive archive = new Archive(id, tableCompression, -1, archiveData);
      this.index255.putArchiveData(id, archive.compress());
      Index[] newIndexes = (Index[])Arrays.copyOf(this.indexes, this.indexes.length + 1);
      newIndexes[newIndexes.length - 1] = new Index(this.index255, new MainFile(id, this.data, new RandomAccessFile(this.path + "main_file_cache.idx" + id, "rw"), this.readCachedBuffer, this.newProtocol));
      this.indexes = newIndexes;
      return id;
   }
}
