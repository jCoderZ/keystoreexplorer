package org.jcoderz.keytoolz.keystoreexplorer;

import java.security.Provider;
import java.security.Security;

import javax.swing.UIManager;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jcoderz.keytoolz.keystoreexplorer.util.PropertiesUtil;
import org.jcoderz.keytoolz.keystoreexplorer.util.SemaphoreUtil;


public class KeyStoreExplorer
    extends Thread
{
  private static final String PROPERTIES_FILE_NAME = ".keystore_explorer.properties";


  /**
   * @param args
   */
  public static void main(String[] args) throws Exception
  {
    // Set System L&F
    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    // load the program settings
    PropertiesUtil.loadSettings(PROPERTIES_FILE_NAME);
    // registrate the security provider
    Provider bc = new BouncyCastleProvider();
    Security.addProvider(bc);
    // start the GUI
    ExplorerFrame expFrame = new ExplorerFrame();
    expFrame.setVisible(true);
    // wait for GUI to finish
    SemaphoreUtil.waitSingle();
    // save the program settings
    PropertiesUtil.storeSettings(PROPERTIES_FILE_NAME);
  }
}
