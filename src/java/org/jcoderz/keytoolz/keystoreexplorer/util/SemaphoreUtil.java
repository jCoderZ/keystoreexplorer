/*
 * Semaphore.java
 *
 * Created on 22. Februar 2007, 06:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jcoderz.keytoolz.keystoreexplorer.util;

/**
 * @author cloroff
 */
public class SemaphoreUtil
{
  private static final Object SINGLE_SEMAPHORE = new Object();


  private SemaphoreUtil()
  {
  }


  public static void waitSingle()
  {
    synchronized (SINGLE_SEMAPHORE)
    {
      try
      {
        SINGLE_SEMAPHORE.wait();
      }
      catch (InterruptedException iex)
      {
        iex.printStackTrace();
      }
    }
  }


  public static void notifySingle()
  {
    synchronized (SINGLE_SEMAPHORE)
    {
      SINGLE_SEMAPHORE.notify();
    }
  }
}
