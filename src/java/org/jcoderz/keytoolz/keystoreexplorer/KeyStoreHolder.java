package org.jcoderz.keytoolz.keystoreexplorer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.KeyStore;
import java.security.PKCS12Attribute;

public class KeyStoreHolder
{
  private static final String STORE_TYPE = "PKCS12"; //"P12" "JKS"
  private File ksFile;
  private KeyStore keyStore;
  private char[] storePass;


  public KeyStoreHolder(File ksFile)
  {
    this.ksFile = ksFile;
  }


  public String toString()
  {
    String result = "";
    if (ksFile != null)
    {
      result = ksFile.getName();
    }
    return result;
  }


  public char[] getStorePass()
  {
    return storePass;
  }


  public void setStorePass(char[] storePass)
  {
    this.storePass = storePass;
  }


  public KeyStore getKeyStore()
  {
    return keyStore;
  }


  public void loadKeyStore() throws Exception
  {
    try
    {
      this.keyStore = KeyStore.getInstance(STORE_TYPE);
      this.keyStore.load(new FileInputStream(ksFile), storePass);
    }
    catch (Exception e)
    {
      this.keyStore = null;
      throw e;
    }
  }


  public void saveKeyStore() throws Exception
  {
    try
    {
      this.keyStore.store(new FileOutputStream(ksFile), storePass);
    }
    catch (Exception e)
    {
      this.keyStore = null;
      throw e;
    }
  }


  public File getKsFile()
  {
    return ksFile;
  }
}
