/*
 * CertUtil.java
 *
 * Created on 5. März 2007, 05:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jcoderz.keytoolz.keystoreexplorer.util;

import java.security.cert.X509Certificate;

/**
 * @author cloroff
 */
public class CertUtil
{

  /** Creates a new instance of CertUtil */
  private CertUtil()
  {
  }


  public static String getSubject(X509Certificate cert)
  {
    String result = parseDn(cert.getSubjectX500Principal().getName(), "CN=", ",");
    // if no common name is present, use the first organizational unit
    if (result == null)
    {
      result = parseDn(cert.getSubjectX500Principal().getName(), "OU=", ",");
    }
    return result;
  }


  public static String getIssuer(X509Certificate cert)
  {
    String result = parseDn(cert.getIssuerX500Principal().getName(), "CN=", ",");
    // if no common name is present, use the first organizational unit
    if (result == null)
    {
      result = parseDn(cert.getIssuerX500Principal().getName(), "OU=", ",");
    }
    return result;
  }


  private static String parseDn(String source, String target, String delimiter)
  {
    String substring = null;
    if (source != null && target != null && source.indexOf(target) > -1)
    {
      substring = source.substring(source.indexOf(target) + target.length(), source.length());
      if (delimiter != null && substring.indexOf(delimiter) > -1)
      {
        substring = substring.substring(0, substring.indexOf(delimiter));
      }
    }
    return substring;
  }
}
