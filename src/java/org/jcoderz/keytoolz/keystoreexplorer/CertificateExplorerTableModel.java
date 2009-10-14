package org.jcoderz.keytoolz.keystoreexplorer;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class CertificateExplorerTableModel
    extends DefaultTableModel
{
  private static final long serialVersionUID = 1L;


  /**
   * @see javax.swing.table.DefaultTableModel#DefaultTableModel(Vector,
   *      Vector)
   */
  public CertificateExplorerTableModel(Vector<Vector<Object>> data, Vector<String> columnNames)
  {
    super(data, columnNames);
  }


  /**
   * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
   */
  public boolean isCellEditable(int row, int column)
  {
    return false;
  }

}
