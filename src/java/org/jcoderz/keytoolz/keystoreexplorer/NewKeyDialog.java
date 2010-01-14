/*
 * ChangePasswordDialog.java
 *
 * Created on 16. März 2007, 16:35
 */

package org.jcoderz.keytoolz.keystoreexplorer;

import java.awt.Frame;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * @author cloroff
 */
public class NewKeyDialog extends JDialog
{
  private static final long serialVersionUID = 1L;

  private static final Integer[] KEY_SIZES
    = { Integer.valueOf(512), Integer.valueOf(512), Integer.valueOf(512), Integer.valueOf(512) };
  private static final String[] ALGORITHMS = { "SHA1withRSA" };
  /** Creates new form ChangePasswordDialog */
  public NewKeyDialog(Frame parent, boolean modal)
  {
    super(parent, modal);
    initComponents();
  }


  /**
   * This method is called from within the constructor to initialize the form.
   */
  private void initComponents()
  {
    mDistinguishedNameField = new JTextField("CN=");
    mKeySizeField = new JComboBox(KEY_SIZES);
    mAlgorithmField = new JComboBox(ALGORITHMS);
    mDistinguishedNameLabel = new JLabel();
    mKeySizeLabel = new JLabel();
    mAlgorithmLabel = new JLabel();
    jButton1 = new JButton();
    jButton2 = new JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("Change password");

    mDistinguishedNameLabel.setFont(new java.awt.Font("Dialog", 0, 12));
    mDistinguishedNameLabel.setText("current password:");

    mKeySizeLabel.setFont(new java.awt.Font("Dialog", 0, 12));
    mKeySizeLabel.setText("new password:");

    mAlgorithmLabel.setFont(new java.awt.Font("Dialog", 0, 12));
    mAlgorithmLabel.setText("retype new password:");

    jButton1.setText("OK");
    jButton1.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton1ActionPerformed(evt);
      }
    });

    jButton2.setText("Cancel");
    jButton2.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        jButton2ActionPerformed(evt);
      }
    });

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(
            layout.createSequentialGroup().addContainerGap().add(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                    layout.createSequentialGroup().add(mDistinguishedNameLabel).add(22, 22, 22)).add(mKeySizeLabel).add(
                    mAlgorithmLabel)).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false).add(
                    mKeySizeField).add(mDistinguishedNameField).add(mAlgorithmField,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .add(
            layout.createSequentialGroup().add(37, 37, 37).add(jButton1).addPreferredGap(
                org.jdesktop.layout.LayoutStyle.RELATED, 78, Short.MAX_VALUE).add(jButton2).add(40,
                40, 40)));
    layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(
            layout.createSequentialGroup().addContainerGap().add(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(mDistinguishedNameLabel)
                    .add(mDistinguishedNameField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                org.jdesktop.layout.LayoutStyle.RELATED).add(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(
                    mKeySizeField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(mKeySizeLabel)).addPreferredGap(
                org.jdesktop.layout.LayoutStyle.RELATED).add(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(mAlgorithmLabel)
                    .add(mAlgorithmField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).add(14, 14, 14).add(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(jButton1)
                    .add(jButton2)).addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                Short.MAX_VALUE)));
    pack();
  }


  private void jButton2ActionPerformed(java.awt.event.ActionEvent evt)
  {// GEN-FIRST:event_jButton2ActionPerformed
    setVisible(false);
  }// GEN-LAST:event_jButton2ActionPerformed


  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt)
  {// GEN-FIRST:event_jButton1ActionPerformed
//    if (Arrays.equals(mKeySizeField.getPassword(), mAlgorithmField.getPassword()))
//    {
//      setVisible(false);
//    }
//    else
//    {
//      JOptionPane.showMessageDialog(this, "The new passwords are different.", "Password error",
//          JOptionPane.ERROR_MESSAGE);
//      mKeySizeField.setText("");
//      mAlgorithmField.setText("");
//    }
  }// GEN-LAST:event_jButton1ActionPerformed



  /**
   * @param args the command line arguments
   */
  public static void main(String args[])
  {
    java.awt.EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        new NewKeyDialog(new javax.swing.JFrame(), true).setVisible(true);
      }
    });
  }

  private javax.swing.JButton jButton1;
  private javax.swing.JButton jButton2;
  private JLabel mDistinguishedNameLabel;
  private JLabel mKeySizeLabel;
  private JLabel mAlgorithmLabel;
  private JTextField mDistinguishedNameField;
  private JComboBox mKeySizeField;
  private JComboBox mAlgorithmField;
}
