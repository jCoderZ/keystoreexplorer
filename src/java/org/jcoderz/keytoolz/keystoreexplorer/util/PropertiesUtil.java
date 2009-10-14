/*
 * PropertiesUtil.java
 *
 * Created on 7. März 2007, 17:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jcoderz.keytoolz.keystoreexplorer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author cloroff
 */
public class PropertiesUtil
{

  private static final String HOME_DIR = System.getProperty("user.home");
  public static final Properties PROGRAM_SETTINGS = new Properties();


  /** Creates a new instance of PropertiesUtil */
  private PropertiesUtil()
  {
  }


  public static void loadSettings(String propertiesFileName) throws IOException
  {
    File propertiesFile = new File(HOME_DIR + File.separator + propertiesFileName);
    if (propertiesFile.exists())
    {
      PROGRAM_SETTINGS.load(new FileInputStream(propertiesFile));
    }
  }


  public static void storeSettings(String propertiesFileName) throws IOException
  {
    File propertiesFile = new File(HOME_DIR + File.separator + propertiesFileName);
    FileOutputStream fos = new FileOutputStream(propertiesFile);
    PROGRAM_SETTINGS.store(fos, "properties file for the KeyStoreExplorer");
    fos.flush();
    fos.close();
    SemaphoreUtil.notifySingle();
  }
}
