/*
 * CertificateFileFilter.java
 *
 * Created on 14. M�rz 2007, 06:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jcoderz.keytoolz.keystoreexplorer;

import java.io.File;
import java.security.PKCS12Attribute;
import java.util.Arrays;
import java.util.List;

/**
 * @author cloroff
 */
public class StrictCertificateFileFilter
    extends javax.swing.filechooser.FileFilter
    implements java.io.FileFilter
{
  private static final String[] CERTIFICATE_FILE_EXTENSIONS = {"cer", "crt", "der", "p12"};
  private static final List<String> EXTENSION_LIST = Arrays.asList(CERTIFICATE_FILE_EXTENSIONS);


  /**
   * @see java.io.FileFilter#accept(java.io.File)
   */
  public boolean accept(File file)
  {
    boolean result = false;
    if (!file.isDirectory())
    {
      String fileName = file.getName();
      String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
      if (EXTENSION_LIST.contains(extension))
      {
        result = true;
      }
    }
    return result;
  }


  @Override
  public String getDescription()
  {
    return "Certificate files";
  }

}
