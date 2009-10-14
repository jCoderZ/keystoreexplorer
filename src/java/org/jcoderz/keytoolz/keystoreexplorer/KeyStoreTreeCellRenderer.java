package org.jcoderz.keytoolz.keystoreexplorer;

import java.awt.Component;
import java.io.File;
import java.security.KeyStore;
import java.security.KeyStoreException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class KeyStoreTreeCellRenderer
    extends DefaultTreeCellRenderer
{
  private static final long serialVersionUID = 1L;
  Icon keyStoreIcon;
  Icon keyIcon;
  Icon trustedCertIcon;
  Icon validCertIcon;
  Icon invalidCertIcon;


  public KeyStoreTreeCellRenderer()
  {
    keyStoreIcon = new ImageIcon(getClass().getResource("/images/keystore.gif"));
    keyIcon = new ImageIcon(getClass().getResource("/images/keyandcert.gif"));
    trustedCertIcon = new ImageIcon(getClass().getResource("/images/trustedcert.gif"));
    validCertIcon = new ImageIcon(getClass().getResource("/images/cert1.gif"));
    invalidCertIcon = new ImageIcon(getClass().getResource("/images/invalidcert.gif"));
  }


  public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
      boolean expanded, boolean leaf, int row, boolean hasFocus)
  {

    super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
    if (leaf && node.getUserObject() instanceof KeyStoreEntryHolder)
    {
      if (((KeyStoreEntryHolder) node.getUserObject()).isKeyEntry())
      {
        setToolTipText("Private Key with Certificate");
        setIcon(keyIcon);
      }
      else
      {
        setToolTipText("Trusted Certificate");
        setIcon(trustedCertIcon);
      }
    }
    else if (node.getUserObject() instanceof KeyStoreHolder)
    {
      setIcon(keyStoreIcon);
      setToolTipText(generateKeyStoreToolTip((KeyStoreHolder) node.getUserObject()));
    }
    else if (node.getUserObject() instanceof CertificateHolder)
    {
      CertificateHolder certHolder = (CertificateHolder) node.getUserObject();
      if (certHolder.isValid())
      {
        setIcon(validCertIcon);
      }
      else
      {
        setIcon(invalidCertIcon);
      }
      setToolTipText(certHolder.getStatus());
    }
    return this;
  }


  private String generateKeyStoreToolTip(KeyStoreHolder holder)
  {
    KeyStore ks = holder.getKeyStore();
    File ksFile = holder.getKsFile();
    StringBuilder sb = new StringBuilder();
    sb.append("<html><b>Keystore summary</b><br>");
    try
    {
      if (ks != null)
      {
        sb.append(ks.getType());
        sb.append(" type<br>");
        sb.append(ks.getProvider().getName());
        sb.append(" provider <br>");
        sb.append(ks.size());
        sb.append(" entries<br>");
      }
    }
    catch (KeyStoreException e)
    {
      // ignore
      e.printStackTrace();
    }
    sb.append(ksFile.length());
    sb.append(" bytes");
    sb.append("</html>");
    return sb.toString();
  }
}
