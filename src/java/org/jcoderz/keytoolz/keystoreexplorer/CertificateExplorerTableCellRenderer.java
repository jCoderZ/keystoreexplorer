package org.jcoderz.keytoolz.keystoreexplorer;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CertificateExplorerTableCellRenderer
    extends DefaultTableCellRenderer
{

  private static final long serialVersionUID = 1L;


  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
      boolean hasFocus, int row, int column)
  {
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    if (column == 0)
    {
      setText(null);
      setIcon((Icon) value);
      this.setHorizontalAlignment(CENTER);
    }
    else
    {
      setText(value.toString());
      setIcon(null);
      setHorizontalAlignment(LEFT);
    }
    return this;
  }
}
