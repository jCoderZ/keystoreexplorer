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

import java.awt.Component;
import java.awt.EventQueue;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.jcoderz.commons.util.HexUtil;
import org.jcoderz.keytoolz.keystoreexplorer.util.CertUtil;

import sun.security.util.ObjectIdentifier;
import sun.security.x509.OIDMap;


/**
 * The main panel of the certificate explorer.
 *
 * @author cloroff
 * @created 14. M�rz 2007, 16:32
 */
public class CertificateExplorer
    extends JPanel
{
  private static final ImageIcon INFORMATION_ICON = getIcon("/images/information.gif");

  private static final ImageIcon WARNING_ICON = getIcon("/images/warning.gif");

  private static final ImageIcon OK_ICON = getIcon("/images/ok.gif");

  private static final ImageIcon VALID_CERT_ICON = getIcon("/images/cert1.gif");

  private static final ImageIcon INVALID_CERT_ICON = getIcon("/images/invalidcert.gif");

  private static final ImageIcon PIN_RED_ICON = getIcon("/images/pinred.gif");

  private static final ImageIcon PIN_GREEN_ICON = getIcon("/images/pingreen.gif");

  private static final ImageIcon CERT_ICON = getIcon("/images/cert.gif");

  private static final long serialVersionUID = 1L;

  /** The data container for the general part of the certificate explorer */
  Vector<Vector<Object>> mGeneralData;

  /** The data container for the details part of the certificate explorer */
  Vector<Vector<Object>> mDetailsData;

  /** Array of status flags for the certificate chain */
  boolean[] mCertOk;

  /** Array of status texts for the certificate chain */
  String[] mCertStatus;

  /** The certificate chain */
  private X509Certificate[] mCertChain;

  /**
   * A flag indicating, if a private key is available for the last
   * certificate in the chain
   */
  private boolean mPrivateKey;

  // GEN-BEGIN:variables
  // Variables declaration - do not modify
  private JComboBox jComboBox1;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JLabel jLabel4;
  private JLabel jLabel5;
  private JLabel jLabel6;
  private JLabel jLabel7;
  private JPanel jPanel1;
  private JPanel jPanel2;
  private JPanel jPanel3;
  private JPanel jPanel4;
  private JPanel jPanel5;
  private JScrollPane jScrollPane1;
  private JScrollPane jScrollPane2;
  private JScrollPane jScrollPane3;
  private JScrollPane jScrollPane4;
  private JSeparator jSeparator1;
  private JSeparator jSeparator2;
  private JSplitPane jSplitPane1;
  private JTabbedPane jTabbedPane1;
  private JTable jTable1;
  private JTable jTable2;
  private JTextArea jTextArea1;
  private JTextArea jTextArea2;
  private JTextField jTextField1;
  private JTextField jTextField2;
  private JTextField jTextField3;
  private JTextField jTextField4;
  private JTree jTree1;
  // End of variables declaration
  //GEN-END:variables

  /**
   * Load an image resource from the jar file.
   * @param filename the path/filename of the resource
   * @return the icon object
   */
  private static ImageIcon getIcon(String filename)
  {
    return new ImageIcon(CertificateExplorer.class.getResource(filename));
  }

  /**
   * Creates new CertificateExplorer form.
   */
  public CertificateExplorer ()
  {
    initComponents();
    initDetailsTable();
    ToolTipManager.sharedInstance().registerComponent(jTree1);
  }

  /**
   * This method is called from within the constructor to initialize the
   * form. WARNING: Do NOT modify this code. The content of this method
   * is always regenerated by the Form Editor.
   */
  // GEN-BEGIN:initComponents
  // <editor-fold defaultstate="collapsed" desc="Generated Code">
  private void initComponents()
  {
    jTabbedPane1 = new javax.swing.JTabbedPane();
    jPanel1 = new javax.swing.JPanel();
    jPanel4 = new javax.swing.JPanel();
    jLabel3 = new javax.swing.JLabel();
    jSeparator1 = new javax.swing.JSeparator();
    jTable1 = new javax.swing.JTable();
    jSeparator2 = new javax.swing.JSeparator();
    jPanel5 = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jTextField1 = new javax.swing.JTextField();
    jTextField2 = new javax.swing.JTextField();
    jTextField3 = new javax.swing.JTextField();
    jTextField4 = new javax.swing.JTextField();
    jLabel6 = new javax.swing.JLabel();
    jPanel2 = new javax.swing.JPanel();
    jLabel7 = new javax.swing.JLabel();
    jComboBox1 = new javax.swing.JComboBox();
    jSplitPane1 = new javax.swing.JSplitPane();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTable2 = new javax.swing.JTable();
    jScrollPane2 = new javax.swing.JScrollPane();
    jTextArea1 = new javax.swing.JTextArea();
    jPanel3 = new javax.swing.JPanel();
    jScrollPane3 = new javax.swing.JScrollPane();
    jTree1 = new javax.swing.JTree();
    jScrollPane4 = new javax.swing.JScrollPane();
    jTextArea2 = new javax.swing.JTextArea();

    addPropertyChangeListener(new java.beans.PropertyChangeListener()
    {
      public void propertyChange(java.beans.PropertyChangeEvent evt)
      {
        formPropertyChange(evt);
      }
    });

    jPanel4.setBackground(javax.swing.UIManager.getDefaults().getColor("window"));
    jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(
        javax.swing.border.BevelBorder.LOWERED, javax.swing.UIManager.getDefaults().getColor(
            "TabbedPane.highlight"), javax.swing.UIManager.getDefaults().getColor(
            "TabbedPane.light"), javax.swing.UIManager.getDefaults().getColor(
            "TabbedPane.darkShadow"), javax.swing.UIManager.getDefaults().getColor(
            "TabbedPane.shadow")));

    jLabel3.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel3.setIcon(getHeadlineCertIcon());
    jLabel3.setText("Certificate Information");

    initGeneralTable();
    jTable1.setFont(new java.awt.Font("Dialog", 0, 12));
    jTable1.setFocusable(false);
    jTable1.setRowSelectionAllowed(false);
    jTable1.setShowHorizontalLines(false);
    jTable1.setShowVerticalLines(false);

    jPanel5.setBackground(javax.swing.UIManager.getDefaults().getColor("window"));
    initInfoLabels();

    jLabel1.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel1.setText("Issued by:");

    jLabel4.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel4.setText("Valid from:");

    jLabel2.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel2.setText("Issued to:");

    jLabel5.setFont(new java.awt.Font("Dialog", 1, 12));
    jLabel5.setText("Valid to:");

    jTextField1.setFont(new java.awt.Font("Dialog", 0, 12));
    jTextField1.setBorder(null);

    jTextField2.setFont(new java.awt.Font("Dialog", 0, 12));
    jTextField2.setBorder(null);

    jTextField3.setFont(new java.awt.Font("Dialog", 0, 12));
    jTextField3.setBorder(null);

    jTextField4.setFont(new java.awt.Font("Dialog", 0, 12));
    jTextField4.setBorder(null);

    org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
    jPanel5.setLayout(jPanel5Layout);
    jPanel5Layout.setHorizontalGroup(jPanel5Layout.createParallelGroup(
        org.jdesktop.layout.GroupLayout.LEADING).add(
        jPanel5Layout.createSequentialGroup().addContainerGap().add(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jLabel1)
                .add(jLabel2).add(jLabel4).add(jLabel5)).add(41, 41, 41).add(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                jTextField2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                .add(jTextField1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 364,
                    Short.MAX_VALUE).add(jTextField3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    364, Short.MAX_VALUE).add(jTextField4,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE))
            .addContainerGap()));
    jPanel5Layout.setVerticalGroup(jPanel5Layout.createParallelGroup(
        org.jdesktop.layout.GroupLayout.LEADING).add(
        jPanel5Layout.createSequentialGroup().add(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel1).add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
            org.jdesktop.layout.LayoutStyle.RELATED).add(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel2).add(jTextField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
            org.jdesktop.layout.LayoutStyle.RELATED).add(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel4).add(jTextField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
            org.jdesktop.layout.LayoutStyle.RELATED).add(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel5).add(jTextField4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addContainerGap(58,
            Short.MAX_VALUE)));

    jLabel6.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel6.setIcon(CERT_ICON); // NOI18N
    jLabel6.setText("You have a private key that corresponds to this certificate");
    jLabel6.setVisible(mPrivateKey);

    org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
    jPanel4.setLayout(jPanel4Layout);
    jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(
        org.jdesktop.layout.GroupLayout.LEADING).add(
        jPanel4Layout.createSequentialGroup().add(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                jPanel4Layout.createSequentialGroup().addContainerGap().add(
                    jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING,
                            jPanel4Layout.createSequentialGroup().add(10, 10, 10).add(jLabel6))
                        .add(jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).add(
                            org.jdesktop.layout.GroupLayout.LEADING, jSeparator2,
                            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jSeparator1,
                            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, jLabel3))).add(
                jPanel4Layout.createSequentialGroup().add(20, 20, 20).add(jTable1,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)))
            .addContainerGap()));
    jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(
        org.jdesktop.layout.GroupLayout.LEADING).add(
        jPanel4Layout.createSequentialGroup().addContainerGap().add(jLabel3).addPreferredGap(
            org.jdesktop.layout.LayoutStyle.RELATED).add(jSeparator1,
            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10,
            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(
            org.jdesktop.layout.LayoutStyle.RELATED).add(jTable1,
            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 107,
            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(
            org.jdesktop.layout.LayoutStyle.RELATED).add(jLabel6).add(14, 14, 14).add(jSeparator2,
            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(
            org.jdesktop.layout.LayoutStyle.RELATED).add(jPanel5,
            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));

    org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
        org.jdesktop.layout.GroupLayout.LEADING).add(
        jPanel1Layout.createSequentialGroup().addContainerGap().add(jPanel4,
            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
    jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
        org.jdesktop.layout.GroupLayout.LEADING).add(
        jPanel1Layout.createSequentialGroup().addContainerGap().add(jPanel4,
            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));

    jTabbedPane1.addTab("General", jPanel1);

    jLabel7.setFont(new java.awt.Font("Dialog", 0, 12));
    jLabel7.setLabelFor(jComboBox1);
    jLabel7.setText("Show:");

    jComboBox1.setFont(new java.awt.Font("Dialog", 0, 11));
    jComboBox1.addItem("<All>");
    jComboBox1.addItem("Version 1 Fields Only");
    jComboBox1.addItem("Extensions Only");
    jComboBox1.addItem("Critical Extensions Only");
    jComboBox1.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jComboBox1ActionPerformed(evt);
      }
    });

    jSplitPane1.setBackground(javax.swing.UIManager.getDefaults().getColor("window"));
    jSplitPane1.setDividerLocation(160);
    jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
    jSplitPane1.setResizeWeight(1.0);
    jSplitPane1.setPreferredSize(new java.awt.Dimension(456, 510));

    jScrollPane1.setBackground(javax.swing.UIManager.getDefaults().getColor("window"));
    jScrollPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(
        javax.swing.border.BevelBorder.LOWERED, javax.swing.UIManager.getDefaults().getColor(
            "TabbedPane.highlight"), javax.swing.UIManager.getDefaults().getColor(
            "TabbedPane.light"), javax.swing.UIManager.getDefaults().getColor(
            "TabbedPane.darkShadow"), javax.swing.UIManager.getDefaults().getColor(
            "TabbedPane.shadow")));
    jScrollPane1.setPreferredSize(new java.awt.Dimension(454, 410));

    jTable2.setFont(new java.awt.Font("Dialog", 0, 12));
    jTable2.setShowHorizontalLines(false);
    jTable2.setShowVerticalLines(false);
    initDetailsTable();
    jTable2.addKeyListener(new java.awt.event.KeyAdapter()
    {
      public void keyReleased(java.awt.event.KeyEvent evt)
      {
        jTable2KeyReleased(evt);
      }
    });
    jTable2.addMouseListener(new java.awt.event.MouseAdapter()
    {
      public void mouseReleased(java.awt.event.MouseEvent evt)
      {
        jTable2MouseReleased(evt);
      }
    });
    jScrollPane1.setViewportView(jTable2);

    jSplitPane1.setLeftComponent(jScrollPane1);

    jTextArea1.setColumns(20);
    jTextArea1.setEditable(false);
    jTextArea1.setLineWrap(true);
    jTextArea1.setRows(5);
    jTextArea1.setWrapStyleWord(true);
    jScrollPane2.setViewportView(jTextArea1);

    jSplitPane1.setRightComponent(jScrollPane2);

    org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
        org.jdesktop.layout.GroupLayout.LEADING).add(
        jPanel2Layout.createSequentialGroup().addContainerGap().add(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                org.jdesktop.layout.GroupLayout.TRAILING, jSplitPane1,
                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE).add(
                jPanel2Layout.createSequentialGroup().add(jLabel7).add(25, 25, 25).add(jComboBox1,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))).addContainerGap()));
    jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
        org.jdesktop.layout.GroupLayout.LEADING).add(
        jPanel2Layout.createSequentialGroup().addContainerGap().add(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                .add(jLabel7).add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
            org.jdesktop.layout.LayoutStyle.RELATED).add(jSplitPane1,
            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE).addContainerGap()));

    jTabbedPane1.addTab("Details", jPanel2);

    jTree1.setCellRenderer(new KeyStoreTreeCellRenderer());
    jTree1.setModel(new DefaultTreeModel(null));
    jTree1.setShowsRootHandles(true);
    jTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener()
    {
      public void valueChanged(javax.swing.event.TreeSelectionEvent evt)
      {
        jTree1ValueChanged(evt);
      }
    });
    jScrollPane3.setViewportView(jTree1);

    jTextArea2.setColumns(20);
    jTextArea2.setRows(5);
    jScrollPane4.setViewportView(jTextArea2);

    org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(
        org.jdesktop.layout.GroupLayout.LEADING).add(
        org.jdesktop.layout.GroupLayout.TRAILING,
        jPanel3Layout.createSequentialGroup().addContainerGap().add(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING).add(
                org.jdesktop.layout.GroupLayout.LEADING, jScrollPane4,
                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE).add(
                org.jdesktop.layout.GroupLayout.LEADING, jScrollPane3,
                org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE))
            .addContainerGap()));
    jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(
        org.jdesktop.layout.GroupLayout.LEADING).add(
        jPanel3Layout.createSequentialGroup().addContainerGap().add(jScrollPane3,
            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 219,
            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(
            org.jdesktop.layout.LayoutStyle.RELATED).add(jScrollPane4,
            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 137, Short.MAX_VALUE).addContainerGap()));

    jTabbedPane1.addTab("Chain", jPanel3);

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(org.jdesktop.layout.GroupLayout.TRAILING, jTabbedPane1,
            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE));
    layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE));
  }// </editor-fold>
  // GEN-END:initComponents

  private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt)
  {// GEN-FIRST:event_jTree1ValueChanged
    TreePath treePath = evt.getNewLeadSelectionPath();
    if (treePath != null)
    {
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
      CertificateHolder holder = (CertificateHolder) node.getUserObject();
      this.jTextArea2.setText(holder.getStatus());
    }
  }// GEN-LAST:event_jTree1ValueChanged


  private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt)
  {// GEN-FIRST:event_jComboBox1ActionPerformed
    initDetailsTable();
  }// GEN-LAST:event_jComboBox1ActionPerformed


  private void jTable2MouseReleased(java.awt.event.MouseEvent evt)
  {// GEN-FIRST:event_jTable2MouseReleased
    int row = jTable2.getSelectedRow();
    Vector<Object> details = (Vector<Object>) mDetailsData.get(row);
    jTextArea1.setText(details.get(details.size() - 1).toString());
    jTextArea1.setCaretPosition(0);
  }// GEN-LAST:event_jTable2MouseReleased


  private void jTable2KeyReleased(java.awt.event.KeyEvent evt)
  {// GEN-FIRST:event_jTable2KeyReleased
    int row = jTable2.getSelectedRow();
    Vector<Object> details = (Vector<Object>) mDetailsData.get(row);
    jTextArea1.setText(details.get(details.size() - 1).toString());
    jTextArea1.setCaretPosition(0);
  }// GEN-LAST:event_jTable2KeyReleased


  // GEN-FIRST:event_formPropertyChange
  private void formPropertyChange(java.beans.PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("enabled"))
    {
      Component[] components = this.getComponents();
      for (Component comp : components)
      {
        comp.setEnabled((Boolean) evt.getNewValue());
      }
    }
  }// GEN-LAST:event_formPropertyChange

  /**
   * Determine which icon to show depending on the status of the certificate.
   *
   * @return the icon object
   */
  private ImageIcon getHeadlineCertIcon()
  {
    ImageIcon result = null;
    if (mCertOk != null && mCertOk.length > 0)
    {
      if (mCertOk[0])
      {
        result = VALID_CERT_ICON;
      }
      else
      {
        result = INVALID_CERT_ICON;
      }
    }
    return result;
  }

  /**
   * @see CertificateExplorer#setCertChain(X509Certificate[], boolean)
   */
  public void setCertChain(Certificate[] certChain, boolean privateKey)
  {
    X509Certificate[] x509CertChain = new X509Certificate[certChain.length];
    for (int i = 0; i < certChain.length; i++)
    {
      x509CertChain[i] = (X509Certificate) certChain[i];
    }
    setCertChain(x509CertChain, privateKey);
  }

  /**
   * Set the certificate chain for the certificate explorer.
   * @param certChain  the X509 certificate chain
   * @param privateKey private key flag
   */
  public void setCertChain(X509Certificate[] certChain, boolean privateKey)
  {
    this.mCertChain = certChain;
    this.mPrivateKey = privateKey;
    this.checkChain();
    int selectedTab = this.jTabbedPane1.getSelectedIndex();
    int selectedComboBoxIndex = this.jComboBox1.getSelectedIndex();
    this.removeAll();
    this.initComponents();
    this.jTabbedPane1.setSelectedIndex(selectedTab);
    this.jComboBox1.setSelectedIndex(selectedComboBoxIndex);
    this.generateTree();
    this.updateUI();
  }

  /**
   * Checks the validity of the certificate chain and sets the status texts and flags.
   */
  private void checkChain()
  {
    mGeneralData = new Vector<Vector<Object>>();
    Vector<Object> innerData;
    mCertOk = new boolean[mCertChain.length];
    mCertStatus = new String[mCertChain.length];
    Arrays.fill(mCertOk, true);
    Arrays.fill(mCertStatus, "This Certificate is valid.");

    for (int i = 0; i < mCertChain.length; i++)
    {
      try
      {
        mCertChain[i].checkValidity();
      }
      catch (CertificateExpiredException cee)
      {
        mCertStatus[i] = "This certificate has expired";
        mCertOk[i] = false;
      }
      catch (CertificateNotYetValidException cnyve)
      {
        mCertStatus[i] = "This certificate is not yet valid";
        mCertOk[i] = false;
      }
    }

    if (mCertOk[0])
    {
      innerData = new Vector<Object>();
      innerData.add(OK_ICON);
      innerData.add("This certificate is currently valid");
      mGeneralData.add(innerData);
    }
    else
    {
      innerData = new Vector<Object>();
      innerData.add(WARNING_ICON);
      innerData.add(mCertStatus[0]);
      mGeneralData.add(innerData);
    }

    try
    {
      for (int i = 0; i < mCertChain.length; i++)
      {
        if (i < mCertChain.length - 1)
        {
          mCertChain[i].verify(mCertChain[i + 1].getPublicKey());
        }
        else
        {
          mCertChain[i].verify(mCertChain[i].getPublicKey());
        }
      }
      if (mCertChain.length == 1)
      {
        if (mPrivateKey)
        {
          innerData = new Vector<Object>();
          innerData.add(OK_ICON);
          innerData.add("This is a selfsigned certificate");
          mGeneralData.add(innerData);
        }
        else
        {
          innerData = new Vector<Object>();
          innerData.add(OK_ICON);
          innerData.add("This is a trusted root certificate");
          mGeneralData.add(innerData);
        }
      }
      else
      {
        innerData = new Vector<Object>();
        innerData.add(OK_ICON);
        innerData.add("This certificate has been verified up to a trusted root certificate");
        mGeneralData.add(innerData);
      }
    }
    catch (GeneralSecurityException gse)
    {
      innerData = new Vector<Object>();
      innerData.add(WARNING_ICON);
      innerData.add("This certificate can not be verified up to a trusted root certificate");
      mGeneralData.add(innerData);

      if (mPrivateKey)
      {
        mCertOk[0] = false;
      }
      else
      {
        innerData = new Vector<Object>();
        innerData.add(OK_ICON);
        innerData.add("This is a trusted certificate");
        mGeneralData.add(innerData);
      }
    }

    for (int i = 0; i < mCertChain.length; i++)
    {
      try
      {
        if (i < mCertChain.length - 1)
        {
          mCertChain[i].verify(mCertChain[i + 1].getPublicKey());
        }
        else
        {
          if (mCertChain[i].getIssuerDN().equals(mCertChain[i].getSubjectDN()))
          {
            mCertChain[i].verify(mCertChain[i].getPublicKey());
          }
        }
      }
      catch (InvalidKeyException ike)
      {
        mCertStatus[i] = "There is a problem with the public key used to verify this certificate.";
      }
      catch (SignatureException se)
      {
        mCertStatus[i] = "There is a problem with the signature of this certificate.";
      }
      catch (CertificateException se)
      {
        mCertStatus[i] = "There is a problem with the encoding of this certificate.";
      }
      catch (GeneralSecurityException gse)
      {
        mCertStatus[i] = "This certificate could not be verified because of a technical problem.";
        gse.printStackTrace();
      }
    }
  }

  /**
   * Initialises the table for the 'General' tab.
   */
  private void initGeneralTable()
  {
    Vector<String> columnNames = new Vector<String>();
    columnNames.add("Icon");
    columnNames.add("Text");
    TableModel tableModel = new CertificateExplorerTableModel(mGeneralData, columnNames);

    jTable1.setModel(tableModel);
    jTable1.setDefaultRenderer(Object.class, new CertificateExplorerTableCellRenderer());

    TableColumn column = null;
    column = jTable1.getColumnModel().getColumn(0);
    column.setMinWidth(25);
    column.setMaxWidth(25);
    column = jTable1.getColumnModel().getColumn(1);
    column.setMinWidth(25);
    column.setPreferredWidth(200);
    jTable1.getTableHeader().setReorderingAllowed(false);
  }

  /**
   * Initialise the labels on the 'General' tab.
   */
  private void initInfoLabels()
  {
    if (mCertChain != null && mCertChain.length > 0)
    {
      jTextField1.setText(CertUtil.getIssuer(mCertChain[0]));
      jTextField2.setText(CertUtil.getSubject(mCertChain[0]));
      jTextField3.setText(formatDate(mCertChain[0].getNotBefore()));
      jTextField4.setText(formatDate(mCertChain[0].getNotAfter()));
    }
  }


  /**
   * Initialises the table for the 'Details' tab.
   */
  private void initDetailsTable()
  {
    mDetailsData = new Vector<Vector<Object>>();
    if (mCertChain != null)
    {
      int iSelected = jComboBox1.getSelectedIndex();

      Vector<Object> innerData;
      if (iSelected < 2)
      {
        innerData = new Vector<Object>();
        innerData.add(INFORMATION_ICON);
        innerData.add("Version");
        innerData.add("V" + new Integer(mCertChain[0].getVersion()));
        mDetailsData.add(innerData);
        innerData = new Vector<Object>();
        innerData.add(INFORMATION_ICON);
        innerData.add("Serial Number");
        innerData.add(mCertChain[0].getSerialNumber().toString());
        mDetailsData.add(innerData);
        innerData = new Vector<Object>();
        innerData.add(INFORMATION_ICON);
        innerData.add("Issuer");
        innerData.add(mCertChain[0].getIssuerDN());
        mDetailsData.add(innerData);
        innerData = new Vector<Object>();
        innerData.add(INFORMATION_ICON);
        innerData.add("Subject");
        innerData.add(mCertChain[0].getSubjectDN());
        mDetailsData.add(innerData);
        innerData = new Vector<Object>();
        innerData.add(INFORMATION_ICON);
        innerData.add("Valid From");
        innerData.add(mCertChain[0].getNotBefore());
        mDetailsData.add(innerData);
        innerData = new Vector<Object>();
        innerData.add(INFORMATION_ICON);
        innerData.add("Valid To");
        innerData.add(mCertChain[0].getNotAfter());
        mDetailsData.add(innerData);
        innerData = new Vector<Object>();
        innerData.add(INFORMATION_ICON);
        innerData.add("Signature Algorithm");
        innerData.add(mCertChain[0].getSigAlgName());
        mDetailsData.add(innerData);
        innerData = new Vector<Object>();
        innerData.add(INFORMATION_ICON);
        innerData.add("Public Key");
        int bitLength = 0;
        if (mCertChain[0].getPublicKey() instanceof RSAPublicKey)
        {
          bitLength = ((RSAPublicKey) mCertChain[0].getPublicKey()).getModulus().bitLength();
          innerData.add(spacedHexString(mCertChain[0].getPublicKey().getEncoded()));
          System.out.println(spacedHexString(mCertChain[0].getPublicKey().getEncoded()));
        }
        if (mCertChain[0].getPublicKey() instanceof DSAPublicKey)
        {
          innerData.add(mCertChain[0].getPublicKey().getAlgorithm() + "("
              + ((DSAPublicKey) mCertChain[0].getPublicKey()).getY().bitLength() + " Bits)");
          innerData.add(spacedHexString(mCertChain[0].getPublicKey().getEncoded()));
          System.out.println(spacedHexString(mCertChain[0].getPublicKey().getEncoded()));
        }
        innerData.add(mCertChain[0].getPublicKey().getAlgorithm() + "(" + bitLength + " Bits)");

        mDetailsData.add(innerData);
      }

      if (iSelected < 1 || iSelected == 2)
      {
        addExtensions(false, mDetailsData);
      }

      if (iSelected != 1)
      {
        addExtensions(true, mDetailsData);
      }
    }

    Vector<String> columnNames = new Vector<String>();
    columnNames.add(" ");
    columnNames.add("Field");
    columnNames.add("Value");
    TableModel tableModel = new CertificateExplorerTableModel(mDetailsData, columnNames);

    jTable2.setModel(tableModel);
    jTable2.setDefaultRenderer(Object.class, new CertificateExplorerTableCellRenderer());
    JTableHeader header = new JTableHeader(jTable2.getColumnModel());
    header.setTable(jTable2);

    TableColumn column = null;
    column = jTable2.getColumnModel().getColumn(0);
    column.setMinWidth(25);
    column.setMaxWidth(25);
    column = jTable2.getColumnModel().getColumn(1);
    column.setMinWidth(25);
    column.setPreferredWidth(100);
    column = jTable2.getColumnModel().getColumn(2);
    column.setMinWidth(25);
    column.setPreferredWidth(160);
    jTable2.getTableHeader().setReorderingAllowed(false);
    jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }


  /**
   * Adds the extensions to the data Vector for the 'Details' tab.
   * @param bCritical decides whether the critical or the normal extensions are to be added
   * @param data the vector to add the extensions to
   */
  private void addExtensions(boolean bCritical, Vector<Vector<Object>> data)
  {
    X509Certificate x509Cert = mCertChain[0];
    Iterator<String> oidIterator = null;
    ImageIcon icon = null;
    if (bCritical)
    {
      if (x509Cert.getCriticalExtensionOIDs() != null)
      {
        oidIterator = x509Cert.getCriticalExtensionOIDs().iterator();
        icon = PIN_RED_ICON;
      }
    }
    else
    {
      if (x509Cert.getNonCriticalExtensionOIDs() != null)
      {
        oidIterator = x509Cert.getNonCriticalExtensionOIDs().iterator();
        icon = PIN_GREEN_ICON;
      }
    }
    if (oidIterator != null)
    {
      while (oidIterator.hasNext())
      {
        String sOid = (String) oidIterator.next();
        ObjectIdentifier oid = null;
        try
        {
          oid = ObjectIdentifier.of(sOid);
        }
        catch (IOException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        String oidName = OIDMap.getName(oid);
        if (oidName != null)
        {
          oidName = oidName.substring(oidName.lastIndexOf('.') + 1);
        }
        else
        {
          oidName = sOid;
        }
        Vector<Object> innerData = new Vector<Object>();
        innerData.add(icon);
        innerData.add(oidName);
        String sValue = explicitExtensionHandling(oidName);
        if (sValue == null)
        {
          sValue = spacedHexString(x509Cert.getExtensionValue(sOid));
        }
        innerData.add(sValue);
        data.add(innerData);
      }
    }
  }


  /**
   * Special handling of certificate extensions.
   * Currently only the KeyUsage extension is implemented.
   * @param extension the extension name
   * @return the extension value or null, if the extension is not known or handled explicitly
   */
  private String explicitExtensionHandling(String extension)
  {
    if (extension.equalsIgnoreCase("KeyUsage"))
    {
      String[] keyUsages =
          {"digitalSignature", "nonReputation", "keyEncipherment", "dataEncipherment",
              "keyAgreement", "keyCertSign", "crlSign", "encipherOnly", "decipherOnly"};
      boolean[] keyUsageArray = mCertChain[0].getKeyUsage();
      String keyUsage = "";
      if (null != keyUsageArray)
      {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 9; i++)
        {
          if (keyUsageArray[i])
          {
            sb.append(keyUsages[i]);
            sb.append(", ");
          }
        }
        if (sb.length() > 1)
        {
          sb.delete(sb.length() - 2, sb.length() - 1);
        }
        keyUsage = sb.toString();
      }
      if (keyUsage.length() > 0)
      {
        return keyUsage;
      }
    }
    return null;
  }


  /**
   * Create a space separated hex string with block of four characters.
   * @param data the binary data to be formatted
   * @return the spaced hex string
   */
  private String spacedHexString(byte[] data)
  {
    String hex = HexUtil.bytesToHex(data);
    StringBuilder spaced = new StringBuilder();
    int i;
    for (i = 0; i < hex.length() - 4; i += 4)
    {
      spaced.append(hex.substring(i, i + 4));
      spaced.append(" ");
    }
    spaced.append(hex.substring(i, hex.length()));
    return spaced.toString();
  }


  /**
   * Formats the given date with the default formatter for the locale.
   * @param date the date to be formatted
   * @return the formatted date
   */
  private String formatDate(Date date)
  {
    String strDate = null;
    if (null != date)
    {
      DateFormat formatter = DateFormat.getDateInstance();
      strDate = formatter.format(date);
    }
    return strDate;
  }


  /**
   * Generate the tree structure from the certificate chain.
   */
  private void generateTree()
  {
    DefaultMutableTreeNode root = null;
    DefaultMutableTreeNode previousNode = null;
    for (int i = mCertChain.length - 1; i >= 0; i--)
    {
      DefaultMutableTreeNode node = new DefaultMutableTreeNode(
          new CertificateHolder(mCertChain[i], mCertOk[i], mCertStatus[i]));
      if (previousNode != null)
      {
        previousNode.add(node);
      }
      else
      {
        root = node;
      }
      previousNode = node;
    }
    DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
    model.setRoot(root);
    jTree1.updateUI();
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
        JFrame testFrame = new JFrame();
        JPanel certExp = new CertificateExplorer();
        certExp.setEnabled(true);
        certExp.setVisible(true);
        testFrame.getContentPane().add(certExp);
        testFrame.setVisible(true);
        testFrame.pack();
      }
    });
  }
}
