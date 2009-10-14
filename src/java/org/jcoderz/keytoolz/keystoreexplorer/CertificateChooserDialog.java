/*
 * $Id: $
 *
 * Copyright 2009, The jCoderZ.org Project. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *    * Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above
 *      copyright notice, this list of conditions and the following
 *      disclaimer in the documentation and/or other materials
 *      provided with the distribution.
 *    * Neither the name of the jCoderZ.org Project nor the names of
 *      its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written
 *      permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.jcoderz.keytoolz.keystoreexplorer;

import java.awt.EventQueue;
import java.awt.Frame;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.jcoderz.keytoolz.keystoreexplorer.util.PropertiesUtil;

/**
 * A dialog, that uses an internal JFileChooser component for exporting
 * certificate files.
 *
 * @author cloroff
 * @created 14. M�rz 2007, 16:32
 */
public class CertificateChooserDialog
    extends JDialog
{
  private static final long serialVersionUID = 1L;
  String mLastSelection;

  // Variables declaration - do not modify
  //GEN-BEGIN:variables
  private JFileChooser mCertFileChooser;
  // End of variables declaration
  //GEN-END:variables


  /**
   * Creates new form CertificateChooserDialog.
   */
  public CertificateChooserDialog(Frame parent, boolean modal)
  {
    super(parent, modal);
    initComponents();
  }


  /**
   * This method is called from within the constructor to initialize the
   * form. WARNING: Do NOT modify this code. The content of this method
   * is always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
  //GEN-BEGIN:initComponents
  private void initComponents()
  {
    mCertFileChooser = new javax.swing.JFileChooser();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    mCertFileChooser.setCurrentDirectory(new File(PropertiesUtil.PROGRAM_SETTINGS.getProperty(
        "start.dir", System.getProperty("user.dir"))));
    mCertFileChooser.setDialogTitle("Export Certificate");
    mCertFileChooser.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
    mCertFileChooser.setFileFilter(new CertificateFileFilter());

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(mCertFileChooser, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE));
    layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(mCertFileChooser, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE));
    pack();
  }


  // </editor-fold>
  //GEN-END:initComponents

  /**
   * Retrieves the selected file from the internalJFileChooser
   * component.
   *
   * @see javax.swing.JFileChooser#getSelectedFile()
   * @return the selected file
   */
  public File getSelectedFile()
  {
    return mCertFileChooser.getSelectedFile();
  }


  /**
   * Activate the "open" version of the file chooser dialog.
   *
   * @see javax.swing.JFileChooser#showOpenDialog(java.awt.Component)
   * @return the return value of the open dialog
   */
  public int showOpenDialog()
  {
    return mCertFileChooser.showOpenDialog(getParent());
  }


  /**
   * Activate the "save" version of the file chooser dialog.
   *
   * @see javax.swing.JFileChooser#showSaveDialog(java.awt.Component)
   * @return the return value of the save dialog
   */
  public int showSaveDialog()
  {
    return mCertFileChooser.showSaveDialog(getParent());
  }


  /**
   * Main method for testing the dialog standalone.
   *
   * @param args the command line arguments
   */
  public static void main(String args[])
  {
    EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        new CertificateChooserDialog(new JFrame(), true).setVisible(true);
      }
    });
  }
}