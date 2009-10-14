package org.jcoderz.keytoolz.keystoreexplorer;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class KeyStoreEntryHolder
{
  private String alias;
  private Certificate[] chain;
  private boolean keyEntry;


  public KeyStoreEntryHolder(String alias, Certificate[] chain)
  {
    this.alias = alias;
    this.chain = chain;
    this.keyEntry = true;
  }


  public KeyStoreEntryHolder(String alias, Certificate cert)
  {
    this.alias = alias;
    this.chain = new X509Certificate[1];
    this.chain[0] = cert;
    this.keyEntry = false;
  }


  public String getAlias()
  {
    return alias;
  }


  public Certificate[] getChain()
  {
    return chain;
  }


  public String toString()
  {
    return alias;
  }


  public boolean isKeyEntry()
  {
    return keyEntry;
  }
}
