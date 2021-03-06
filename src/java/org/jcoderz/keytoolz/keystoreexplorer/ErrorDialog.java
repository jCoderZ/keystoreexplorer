/*
 * MessageDialog.java
 *
 * Created on __DATE__, __TIME__
 */

package org.jcoderz.keytoolz.keystoreexplorer;

/**
 * @author __USER__
 */
public class ErrorDialog
    extends javax.swing.JDialog
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  /** Creates new form MessageDialog */
  public ErrorDialog(java.awt.Frame parent, boolean modal)
  {
    super(parent, modal);
    initComponents();
  }


  /**
   * This method is called from within the constructor to initialize the
   * form. WARNING: Do NOT modify this code. The content of this method
   * is always regenerated by the Form Editor.
   */
  // GEN-BEGIN:initComponents
  // <editor-fold defaultstate="collapsed" desc=" Generated Code ">
  private void initComponents()
  {
    jButton1 = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTextArea1 = new javax.swing.JTextArea();

    setTitle("Error");
    setLocationByPlatform(true);
    jButton1.setText("OK");
    jButton1.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        btnOkAction(evt);
      }
    });

    jTextArea1.setBackground(new java.awt.Color(136, 192, 184));
    jTextArea1.setColumns(20);
    jTextArea1.setEditable(false);
    jTextArea1.setFont(new java.awt.Font("Courier", 1, 13));
    jTextArea1.setForeground(new java.awt.Color(255, 0, 0));
    jTextArea1.setLineWrap(true);
    jTextArea1.setRows(5);
    jTextArea1.setWrapStyleWord(true);
    jScrollPane1.setViewportView(jTextArea1);

    org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(
            layout.createSequentialGroup().add(
                layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                    layout.createSequentialGroup().addContainerGap().add(jScrollPane1,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 241,
                        org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).add(
                    layout.createSequentialGroup().add(101, 101, 101).add(jButton1)))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
    layout.setVerticalGroup(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
        .add(
            layout.createSequentialGroup().addContainerGap().add(jScrollPane1).addPreferredGap(
                org.jdesktop.layout.LayoutStyle.RELATED).add(jButton1).addContainerGap()));
    pack();
  }// </editor-fold>//GEN-END:initComponents


  // GEN-FIRST:event_btnOkAction
  private void btnOkAction(java.awt.event.ActionEvent evt)
  {
    this.setVisible(false);
  }// GEN-LAST:event_btnOkAction


  /**
   * @param args the command line arguments
   */
  public static void main(String args[])
  {
    java.awt.EventQueue.invokeLater(new Runnable()
    {
      public void run()
      {
        new ErrorDialog(new javax.swing.JFrame(), true).setVisible(true);
      }
    });
  }


  public void setMessage(String message)
  {
    jTextArea1.setText(message);
  }

  // GEN-BEGIN:variables
  // Variables declaration - do not modify
  private javax.swing.JButton jButton1;

  private javax.swing.JScrollPane jScrollPane1;

  private javax.swing.JTextArea jTextArea1;
  // End of variables declaration//GEN-END:variables

}
