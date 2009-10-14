package org.jcoderz.keytoolz.keystoreexplorer;

import java.io.File;

public class KeyStoreFileFilter
    extends StrictKeyStoreFileFilter
{

  /**
   * @see java.io.FileFilter#accept(java.io.File)
   */
  public boolean accept(File file)
  {
    boolean result = false;
    if (file.isDirectory())
    {
      result = true;
    }
    else
    {
      result = super.accept(file);
    }
    return result;
  }
}
