package org.jcoderz.keytoolz.keystoreexplorer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class StrictKeyStoreFileFilter
    extends javax.swing.filechooser.FileFilter
    implements java.io.FileFilter
{
  private static final int[] JAVA_KEYSTORE_MAGIC = {0xFE, 0xED, 0xFE, 0xED};


  /**
   * @see java.io.FileFilter#accept(java.io.File)
   */
  public boolean accept(File file)
  {
    boolean result = false;
    try
    {
      if (!file.isDirectory())
      {
        FileInputStream fis = new FileInputStream(file);
        if (fis.available() > JAVA_KEYSTORE_MAGIC.length)
        {
          int[] magic = new int[JAVA_KEYSTORE_MAGIC.length];
          for (int i = 0; i < JAVA_KEYSTORE_MAGIC.length; i++)
          {
            magic[i] = fis.read();
          }
          result = Arrays.equals(magic, JAVA_KEYSTORE_MAGIC);
        }
      }
    }
    catch (IOException ioe)
    {
      result = false;
    }
    return true;//result;
  }


  @Override
  public String getDescription()
  {
    return "keystore files";
  }
}
