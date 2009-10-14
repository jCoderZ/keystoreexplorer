/*
 * CertificateHolder.java
 *
 * Created on 28. Februar 2007, 07:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jcoderz.keytoolz.keystoreexplorer;

import java.security.cert.X509Certificate;

import org.jcoderz.keytoolz.keystoreexplorer.util.CertUtil;


/**
 * @author cloroff
 */
public class CertificateHolder
{
  private X509Certificate cert;
  private boolean valid;
  private String status;


  /** Creates a new instance of CertificateHolder */
  public CertificateHolder(X509Certificate cert, boolean valid, String status)
  {
    this.cert = cert;
    this.valid = valid;
    this.status = status;
  }


  public boolean isValid()
  {
    return valid;
  }


  public String getStatus()
  {
    return status;
  }


  public X509Certificate getCertificate()
  {
    return cert;
  }


  public String toString()
  {
    return CertUtil.getSubject(cert);
  }

}
