package com.alex.utils;

import com.alex.io.OutputStream;
import com.alex.store.Store;

public final class Utils {
   public static byte[] getArchivePacketData(int indexId, int archiveId, byte[] archive) {
      OutputStream stream = new OutputStream(archive.length + 4);
      stream.writeByte(indexId);
      stream.writeShort(archiveId);
      stream.writeByte(0);
      stream.writeInt(archive.length);
      int offset = 8;

      for(int packet = 0; packet < archive.length; ++packet) {
         if(offset == 512) {
            stream.writeByte(-1);
            offset = 1;
         }

         stream.writeByte(archive[packet]);
         ++offset;
      }

      byte[] var6 = new byte[stream.getOffset()];
      stream.setOffset(0);
      stream.getBytes(var6, 0, var6.length);
      return var6;
   }

   public static int getNameHash(String name) {
      name = name.toLowerCase();
      int hash = 0;

      for(int index = 0; index < name.length(); ++index) {
         hash = method1258(name.charAt(index)) + ((hash << 5) - hash);
      }

      return hash;
   }

   public static final int getItemDefinitionsSize(Store store) {
      int lastArchiveId = store.getIndexes()[19].getLastArchiveId();
      return lastArchiveId * 256 + store.getIndexes()[19].getValidFilesCount(lastArchiveId);
   }

   private static final byte method1258(char c) {
      byte charByte;
      if((c <= 0 || c >= 128) && (c < 160 || c > 255)) {
         if(c != 8364) {
            if(c != 8218) {
               if(c != 402) {
                  if(c == 8222) {
                     charByte = -124;
                  } else if(c != 8230) {
                     if(c != 8224) {
                        if(c == 8225) {
                           charByte = -121;
                        } else if(c == 710) {
                           charByte = -120;
                        } else if(c == 8240) {
                           charByte = -119;
                        } else if(c == 352) {
                           charByte = -118;
                        } else if(c == 8249) {
                           charByte = -117;
                        } else if(c == 338) {
                           charByte = -116;
                        } else if(c != 381) {
                           if(c == 8216) {
                              charByte = -111;
                           } else if(c != 8217) {
                              if(c != 8220) {
                                 if(c == 8221) {
                                    charByte = -108;
                                 } else if(c != 8226) {
                                    if(c == 8211) {
                                       charByte = -106;
                                    } else if(c == 8212) {
                                       charByte = -105;
                                    } else if(c == 732) {
                                       charByte = -104;
                                    } else if(c == 8482) {
                                       charByte = -103;
                                    } else if(c != 353) {
                                       if(c == 8250) {
                                          charByte = -101;
                                       } else if(c != 339) {
                                          if(c == 382) {
                                             charByte = -98;
                                          } else if(c != 376) {
                                             charByte = 63;
                                          } else {
                                             charByte = -97;
                                          }
                                       } else {
                                          charByte = -100;
                                       }
                                    } else {
                                       charByte = -102;
                                    }
                                 } else {
                                    charByte = -107;
                                 }
                              } else {
                                 charByte = -109;
                              }
                           } else {
                              charByte = -110;
                           }
                        } else {
                           charByte = -114;
                        }
                     } else {
                        charByte = -122;
                     }
                  } else {
                     charByte = -123;
                  }
               } else {
                  charByte = -125;
               }
            } else {
               charByte = -126;
            }
         } else {
            charByte = -128;
         }
      } else {
         charByte = (byte)c;
      }

      return charByte;
   }
}
