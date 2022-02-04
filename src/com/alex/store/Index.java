package com.alex.store;

import com.alex.io.InputStream;
import com.alex.io.OutputStream;
import com.alex.store.Archive;
import com.alex.store.ArchiveReference;
import com.alex.store.FileReference;
import com.alex.store.MainFile;
import com.alex.store.ReferenceTable;
import com.alex.store.Store;
import com.alex.util.crc32.CRC32HGenerator;
import com.alex.util.whirlpool.Whirlpool;
import com.alex.utils.Utils;

public final class Index {
   private MainFile mainFile;
   private MainFile index255;
   private int crc;
   private byte[] whirlpool;
   private ReferenceTable table;
   private byte[][][] cachedFiles;

   protected Index(MainFile index255, MainFile mainFile) {
      this.mainFile = mainFile;
      this.index255 = index255;
      byte[] archiveData = index255.getArchiveData(this.getId());
      if(archiveData != null) {
         this.crc = CRC32HGenerator.getHash(archiveData);
         this.whirlpool = Whirlpool.getHash(archiveData, 0, archiveData.length);
         Archive archive = new Archive(this.getId(), archiveData, (int[])null);
         this.table = new ReferenceTable(archive);
         this.resetCachedFiles();
      }
   }

   public void resetCachedFiles() {
      this.cachedFiles = new byte[this.getLastArchiveId() + 1][][];
   }

   public int getLastFileId(int archiveId) {
      return !this.archiveExists(archiveId)?-1:this.table.getArchives()[archiveId].getFiles().length - 1;
   }

   public int getLastArchiveId() {
      return this.table.getArchives().length - 1;
   }

   public int getValidArchivesCount() {
      return this.table.getValidArchiveIds().length;
   }

   public int getValidFilesCount(int archiveId) {
      return !this.archiveExists(archiveId)?-1:this.table.getArchives()[archiveId].getValidFileIds().length;
   }

   public boolean archiveExists(int archiveId) {
      ArchiveReference[] archives = this.table.getArchives();
      return archives.length > archiveId && archives[archiveId] != null;
   }

   public boolean fileExists(int archiveId, int fileId) {
      if(!this.archiveExists(archiveId)) {
         return false;
      } else {
         FileReference[] files = this.table.getArchives()[archiveId].getFiles();
         return files.length > fileId && files[fileId] != null;
      }
   }

   public int getArchiveId(String name) {
      int nameHash = Utils.getNameHash(name);
      ArchiveReference[] archives = this.table.getArchives();
      int[] validArchiveIds = this.table.getValidArchiveIds();

      for(int index = 0; index < validArchiveIds.length; ++index) {
         int archiveId = validArchiveIds[index];
         if(archives[archiveId].getNameHash() == nameHash) {
            return archiveId;
         }
      }

      return -1;
   }

   public int getFileId(int archiveId, String name) {
      if(!this.archiveExists(archiveId)) {
         return -1;
      } else {
         int nameHash = Utils.getNameHash(name);
         FileReference[] files = this.table.getArchives()[archiveId].getFiles();
         int[] validFileIds = this.table.getArchives()[archiveId].getValidFileIds();

         for(int index = 0; index < validFileIds.length; ++index) {
            int fileId = validFileIds[index];
            if(files[fileId].getNameHash() == nameHash) {
               return fileId;
            }
         }

         return -1;
      }
   }

   public byte[] getFile(int archiveId) {
      return !this.archiveExists(archiveId)?null:this.getFile(archiveId, this.table.getArchives()[archiveId].getValidFileIds()[0]);
   }

   public byte[] getFile(int archiveId, int fileId) {
      return this.getFile(archiveId, fileId, (int[])null);
   }

   public byte[] getFile(int archiveId, int fileId, int[] keys) {
      try {
         if(!this.fileExists(archiveId, fileId)) {
            return null;
         } else {
            if(this.cachedFiles[archiveId] == null || this.cachedFiles[archiveId][fileId] == null) {
               this.cacheArchiveFiles(archiveId, keys);
            }

            byte[] e = this.cachedFiles[archiveId][fileId];
            this.cachedFiles[archiveId][fileId] = null;
            return e;
         }
      } catch (OutOfMemoryError var5) {
         return null;
      }
   }

   public boolean packIndex(Store originalStore) {
      Index originalIndex = originalStore.getIndexes()[this.getId()];
      int[] var6;
      int var5 = (var6 = originalIndex.table.getValidArchiveIds()).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         int archiveId = var6[var4];
         if(!this.putArchive(archiveId, originalStore, false, false)) {
            return false;
         }
      }

      if(!this.rewriteTable()) {
         return false;
      } else {
         this.resetCachedFiles();
         return true;
      }
   }

   public boolean putArchive(int archiveId, Store originalStore) {
      return this.putArchive(archiveId, originalStore, true, true);
   }

   public boolean putArchive(int archiveId, Store originalStore, boolean rewriteTable, boolean resetCache) {
      Index originalIndex = originalStore.getIndexes()[this.getId()];
      byte[] data = originalIndex.getMainFile().getArchiveData(archiveId);
      if(data == null) {
         return false;
      } else {
         if(!this.archiveExists(archiveId)) {
            this.table.addEmptyArchiveReference(archiveId);
         }

         ArchiveReference reference = this.table.getArchives()[archiveId];
         reference.updateRevision();
         ArchiveReference originalReference = originalIndex.table.getArchives()[archiveId];
         reference.copyHeader(originalReference);
         int revision = reference.getRevision();
         data[data.length - 2] = (byte)(revision >> 8);
         data[data.length - 1] = (byte)revision;
         if(!this.mainFile.putArchiveData(archiveId, data)) {
            return false;
         } else if(rewriteTable && !this.rewriteTable()) {
            return false;
         } else {
            if(resetCache) {
               this.resetCachedFiles();
            }

            return true;
         }
      }
   }

   public boolean putFile(int archiveId, int fileId, byte[] data) {
      return this.putFile(archiveId, fileId, 2, data, (int[])null, true, true, -1, -1);
   }

   public boolean removeFile(int archiveId, int fileId) {
      return this.removeFile(archiveId, fileId, 2, (int[])null);
   }

   public boolean removeFile(int archiveId, int fileId, int compression, int[] keys) {
      if(!this.fileExists(archiveId, fileId)) {
         return false;
      } else {
         this.cacheArchiveFiles(archiveId, keys);
         ArchiveReference reference = this.table.getArchives()[archiveId];
         reference.removeFileReference(fileId);
         int filesCount = this.getValidFilesCount(archiveId);
         byte[] archiveData;
         if(filesCount == 1) {
            archiveData = this.getFile(archiveId, reference.getValidFileIds()[0], keys);
         } else {
            int[] archive = new int[filesCount];
            OutputStream closedArchive = new OutputStream();

            int index;
            int offset;
            for(index = 0; index < filesCount; ++index) {
               offset = reference.getValidFileIds()[index];
               byte[] fileData = this.getFile(archiveId, offset, keys);
               archive[index] = fileData.length;
               closedArchive.writeBytes(fileData);
            }

            for(index = 0; index < archive.length; ++index) {
               offset = archive[index];
               if(index != 0) {
                  offset -= archive[index - 1];
               }

               closedArchive.writeInt(offset);
            }

            closedArchive.writeByte(1);
            archiveData = new byte[closedArchive.getOffset()];
            closedArchive.setOffset(0);
            closedArchive.getBytes(archiveData, 0, archiveData.length);
         }

         reference.updateRevision();
         Archive var13 = new Archive(archiveId, compression, reference.getRevision(), archiveData);
         byte[] var14 = var13.compress();
         reference.setCrc(CRC32HGenerator.getHash(var14, 0, var14.length - 2));
         reference.setWhirpool(Whirlpool.getHash(var14, 0, var14.length - 2));
         if(!this.mainFile.putArchiveData(archiveId, var14)) {
            return false;
         } else if(!this.rewriteTable()) {
            return false;
         } else {
            this.resetCachedFiles();
            return true;
         }
      }
   }

   public boolean putFile(int archiveId, int fileId, int compression, byte[] data, int[] keys, boolean rewriteTable, boolean resetCache, int archiveName, int fileName) {
      if(!this.archiveExists(archiveId)) {
         this.table.addEmptyArchiveReference(archiveId);
         this.resetCachedFiles();
      }

      this.cacheArchiveFiles(archiveId, keys);
      ArchiveReference reference = this.table.getArchives()[archiveId];
      if(!this.fileExists(archiveId, fileId)) {
         reference.addEmptyFileReference(fileId);
      }

      reference.sortFiles();
      int filesCount = this.getValidFilesCount(archiveId);
      byte[] archiveData;
      if(filesCount == 1) {
         archiveData = data;
      } else {
         int[] archive = new int[filesCount];
         OutputStream closedArchive = new OutputStream();

         int index;
         int offset;
         for(index = 0; index < filesCount; ++index) {
            offset = reference.getValidFileIds()[index];
            byte[] fileData;
            if(offset == fileId) {
               fileData = data;
            } else {
               fileData = this.getFile(archiveId, offset, keys);
            }

            archive[index] = fileData.length;
            closedArchive.writeBytes(fileData);
         }

         for(index = 0; index < filesCount; ++index) {
            offset = archive[index];
            if(index != 0) {
               offset -= archive[index - 1];
            }

            closedArchive.writeInt(offset);
         }

         closedArchive.writeByte(1);
         archiveData = new byte[closedArchive.getOffset()];
         closedArchive.setOffset(0);
         closedArchive.getBytes(archiveData, 0, archiveData.length);
      }

      reference.updateRevision();
      Archive var18 = new Archive(archiveId, compression, reference.getRevision(), archiveData);
      byte[] var19 = var18.compress();
      reference.setCrc(CRC32HGenerator.getHash(var19, 0, var19.length - 2));
      reference.setWhirpool(Whirlpool.getHash(var19, 0, var19.length - 2));
      if(archiveName != -1) {
         reference.setNameHash(archiveName);
      }

      if(fileName != -1) {
         reference.getFiles()[fileId].setNameHash(fileName);
      }

      if(!this.mainFile.putArchiveData(archiveId, var19)) {
         return false;
      } else if(rewriteTable && !this.rewriteTable()) {
         return false;
      } else {
         if(resetCache) {
            this.resetCachedFiles();
         }

         return true;
      }
   }

   public boolean rewriteTable() {
      this.table.sortTable();
      Object[] hashes = this.table.encodeHeader(this.index255);
      if(hashes == null) {
         return false;
      } else {
         this.crc = ((Integer)hashes[0]).intValue();
         this.whirlpool = (byte[])hashes[1];
         return true;
      }
   }

   private void cacheArchiveFiles(int archiveId, int[] keys) {
      Archive archive = this.getArchive(archiveId, keys);
      int lastFileId = this.getLastFileId(archiveId);
      this.cachedFiles[archiveId] = new byte[lastFileId + 1][];
      if(archive != null) {
         byte[] data = archive.getData();
         if(data != null) {
            int filesCount = this.getValidFilesCount(archiveId);
            if(filesCount == 1) {
               this.cachedFiles[archiveId][lastFileId] = data;
            } else {
               int readPosition = data.length;
               --readPosition;
               int amtOfLoops = data[readPosition] & 255;
               readPosition -= amtOfLoops * filesCount * 4;
               InputStream stream = new InputStream(data);
               stream.setOffset(readPosition);
               int[] filesSize = new int[filesCount];

               int sourceOffset;
               int count;
               for(int filesData = 0; filesData < amtOfLoops; ++filesData) {
                  sourceOffset = 0;

                  for(count = 0; count < filesCount; ++count) {
                     filesSize[count] += sourceOffset += stream.readInt();
                  }
               }

               byte[][] var18 = new byte[filesCount][];

               for(sourceOffset = 0; sourceOffset < filesCount; ++sourceOffset) {
                  var18[sourceOffset] = new byte[filesSize[sourceOffset]];
                  filesSize[sourceOffset] = 0;
               }

               stream.setOffset(readPosition);
               sourceOffset = 0;

               int fileId;
               int i;
               for(count = 0; count < amtOfLoops; ++count) {
                  fileId = 0;

                  for(i = 0; i < filesCount; ++i) {
                     fileId += stream.readInt();
                     System.arraycopy(data, sourceOffset, var18[i], filesSize[i], fileId);
                     sourceOffset += fileId;
                     filesSize[i] += fileId;
                  }
               }

               count = 0;
               int[] var17;
               int var16 = (var17 = this.table.getArchives()[archiveId].getValidFileIds()).length;

               for(i = 0; i < var16; ++i) {
                  fileId = var17[i];
                  this.cachedFiles[archiveId][fileId] = var18[count++];
               }
            }

         }
      }
   }

   public int getId() {
      return this.mainFile.getId();
   }

   public ReferenceTable getTable() {
      return this.table;
   }

   public MainFile getMainFile() {
      return this.mainFile;
   }

   public Archive getArchive(int id) {
      return this.mainFile.getArchive(id, (int[])null);
   }

   public Archive getArchive(int id, int[] keys) {
      return this.mainFile.getArchive(id, keys);
   }

   public int getCRC() {
      return this.crc;
   }

   public byte[] getWhirlpool() {
      return this.whirlpool;
   }
}
