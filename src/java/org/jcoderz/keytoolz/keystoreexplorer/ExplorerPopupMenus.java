package org.jcoderz.keytoolz.keystoreexplorer;

import java.awt.Component;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;

import javax.security.auth.x500.X500Principal;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.jce.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

public class ExplorerPopupMenus
    extends MouseAdapter
    implements ActionListener
{
  // Label should be unique, because they are used as labels _and_
  // action command strings
  private static final String RELOAD_KEYSTORES = "Reload Keystores";

  private static final String NEW_KEYSTORE = "Create new Keystore";

  private static final String DELETE_KEYSTORE = "Delete Keystore";

  private static final String CHANGE_STORE_PASS = "Change Keystore Password";

  private static final String NEW_KEY = "Create new Key";

  private static final String IMPORT_TRUSTED = "Import trusted Certificate";

  private static final String PKCS10_REQUEST = "Create PKCS10 Request";

  private static final String CREATE_CERT = "Create Certificate from Root";

  private static final String MARK_AS_ROOT = "Mark as Root Certificate";

  private static final String IMPORT_CERT = "Import Certificate";

  private static final String EXPORT_CERT = "Export Certificate";

  private static final String CHANGE_KEY_PASS = "Change Key Password";

  private static final String CLONE_KEY = "Clone Key";

  private static final String DELETE_KEY = "Delete Key";

  private static final String EXPORT_TRUSTED = "Export trusted Certificate";

  private static final String DELETE_TRUSTED = "Delete trusted Certificate";

  private static final String RENAME_TRUSTED = "Rename trusted Certificate";

  private static final String RENAME_KEY = "Rename Key";

  JPopupMenu popupDirectory;

  JPopupMenu popupKeyStoreClosed;

  JPopupMenu popupKeyStoreOpen;

  JPopupMenu popupKeysAndCertificates;

  JPopupMenu popupTrustedCertificates;

  JPopupMenu popupKeyEntries;

  JPopupMenu popupTrustedEntries;

  JPopupMenu currentPopup;

  JTree parent;

  JFrame parentFrame;

  TreePath treePath; // current selected treepath

  private CsrChooserDialog csrDiag = new CsrChooserDialog(parentFrame, true);

  private CertificateChooserDialog certDiag = new CertificateChooserDialog(parentFrame, true);

  private KeyStoreChooserDialog keyStoreDiag = new KeyStoreChooserDialog(parentFrame, true);

  private ChangePasswordDialog changePassDiag = new ChangePasswordDialog(parentFrame, true);

  private PasswordDialog passDiag = new PasswordDialog(parentFrame, "Key", true);

  private PasswordDialog storePassDiag = new PasswordDialog(parentFrame, "KeyStore", true);

  private PrivateKey caKey;

  private X509Certificate[] caChain;


  public ExplorerPopupMenus(JTree parent, JFrame parentFrame)
  {
    super();
    this.parent = parent;
    this.parentFrame = parentFrame;
    createPopupMenus();
  }


  private void createPopupMenus()
  {
    popupDirectory = new JPopupMenu(); // level 1
    popupDirectory.setLabel("Keystore Directory");
    addItemToPopupMenu(popupDirectory, NEW_KEYSTORE, "newkeystore.gif");
    addItemToPopupMenu(popupDirectory, RELOAD_KEYSTORES, "reload.gif");

    popupKeyStoreClosed = new JPopupMenu(); // level 2
    popupKeyStoreClosed.setLabel("Keystore");
    addItemToPopupMenu(popupKeyStoreClosed, DELETE_KEYSTORE, "deletekeystore.gif");
    addItemToPopupMenu(popupKeyStoreClosed, CHANGE_STORE_PASS, "empty.gif");

    popupKeyStoreOpen = new JPopupMenu(); // level 2
    popupKeyStoreOpen.setLabel("Keystore");
    addItemToPopupMenu(popupKeyStoreOpen, DELETE_KEYSTORE, "deletekeystore.gif");
    addItemToPopupMenu(popupKeyStoreOpen, CHANGE_STORE_PASS, "empty.gif");
    popupKeyStoreOpen.addSeparator();
    addItemToPopupMenu(popupKeyStoreOpen, IMPORT_TRUSTED, "importcert.gif");
    popupKeyStoreOpen.addSeparator();
    addItemToPopupMenu(popupKeyStoreOpen, NEW_KEY, "newkey.gif");

    popupKeyEntries = new JPopupMenu();
    popupKeyEntries.setLabel("Key with Certificate");
    addItemToPopupMenu(popupKeyEntries, PKCS10_REQUEST, "newcert.gif");
    addItemToPopupMenu(popupKeyEntries, CREATE_CERT, "newcert.gif");
    addItemToPopupMenu(popupKeyEntries, MARK_AS_ROOT, "empty.gif");
    popupKeyEntries.addSeparator();
    addItemToPopupMenu(popupKeyEntries, IMPORT_CERT, "importcert.gif");
    addItemToPopupMenu(popupKeyEntries, EXPORT_CERT, "exportcert.gif");
    popupKeyEntries.addSeparator();
    addItemToPopupMenu(popupKeyEntries, CLONE_KEY, "keyclone.gif"); //
    addItemToPopupMenu(popupKeyEntries, DELETE_KEY, "deletekey.gif");
    addItemToPopupMenu(popupKeyEntries, CHANGE_KEY_PASS, "empty.gif");
    addItemToPopupMenu(popupKeyEntries, RENAME_KEY, "empty.gif");

    popupTrustedEntries = new JPopupMenu();
    popupTrustedEntries.setLabel("Trusted Certificate");
    addItemToPopupMenu(popupTrustedEntries, EXPORT_TRUSTED, "exportcert.gif");
    addItemToPopupMenu(popupTrustedEntries, DELETE_TRUSTED, "deletecert.gif");
    addItemToPopupMenu(popupTrustedEntries, RENAME_TRUSTED, "empty.gif");
  }


  private void addItemToPopupMenu(JPopupMenu popup, String s, String icon)
  {
    JMenuItem item = null;
    if (icon != null)
      item = new JMenuItem(s, new ImageIcon(getClass().getResource("/images/" + icon)));
    else
      item = new JMenuItem(s);

    item.setActionCommand(s);
    item.addActionListener(this);
    popup.add(item);
  }


  public void mousePressed(MouseEvent e)
  {
    showPopup(e);
  }


  public void mouseReleased(MouseEvent e)
  {
    showPopup(e);
  }


  private void showPopup(MouseEvent e)
  {
    if (e.isPopupTrigger())
    {
      Component comp = e.getComponent();
      if (comp instanceof JTree)
      {
        JTree tree = (JTree) comp;
        treePath = tree.getPathForLocation(e.getX(), e.getY());
        parent.setSelectionPath(treePath);
      }

      JPopupMenu popup = getPopupMenu();
      if (popup == null) // outside scope
        return;
      popup.show(e.getComponent(), e.getX(), e.getY());
    }
  }


  private JPopupMenu getPopupMenu()
  {
    if (treePath == null)
    {
      currentPopup = popupDirectory;
    }
    else
    {
      int level = treePath.getPathCount() - 1;
      switch (level)
      {
        case 1:
          DefaultMutableTreeNode node =
              (DefaultMutableTreeNode) parent.getSelectionPath().getLastPathComponent();
          KeyStoreHolder ksHolder = (KeyStoreHolder) node.getUserObject();
          if (ksHolder.getKeyStore() == null)
          {
            currentPopup = popupKeyStoreClosed;
          }
          else
          {
            currentPopup = popupKeyStoreOpen;
          }
          break;
        case 2:
          if (getKeyStoreEntry(2).isKeyEntry())
          {
            currentPopup = popupKeyEntries;
          }
          else
          {
            currentPopup = popupTrustedEntries;
          }
          break;
        default:
          currentPopup = new JPopupMenu();
          break;
      }
    }
    return currentPopup;
  }


  /** Common Event handler for _all_ Popup events */
  public void actionPerformed(ActionEvent e)
  {
    System.out.println("Action: " + e);
    String cmd = e.getActionCommand();

    if (NEW_KEYSTORE.equals(cmd))
    {
      newKeyStore();
    }
    else if (DELETE_KEYSTORE.equals(cmd))
    {
      deleteKeyStore();
    }
    else if (CHANGE_STORE_PASS.equals(cmd))
    {
      changeStorePass();
    }
    else if (NEW_KEY.equals(cmd))
    {
      newKey();
    }
    else if (IMPORT_TRUSTED.equals(cmd))
    {
      importTrustedCertificate();
    }
    else if (IMPORT_CERT.equals(cmd))
    {
      importCertificate();
    }
    else if (EXPORT_CERT.equals(cmd) || EXPORT_TRUSTED.equals(cmd))
    {
      exportCertificate();
    }
    else if (CHANGE_KEY_PASS.equals(cmd))
    {
      changeKeyPass();
    }
    else if (CLONE_KEY.equals(cmd))
    {
      cloneKey();
    }
    else if (DELETE_KEY.equals(cmd) || DELETE_TRUSTED.equals(cmd))
    {
      deleteAlias();
    }
    else if (RENAME_KEY.equals(cmd) || RENAME_TRUSTED.equals(cmd))
    {
      renameAlias();
    }
    else if (PKCS10_REQUEST.equals(cmd))
    {
      pkcs10Request();
    }
    else if (CREATE_CERT.equals(cmd))
    {
      createCert();
    }
    else if (MARK_AS_ROOT.equals(cmd))
    {
      markAsRoot();
    }
    else if (RELOAD_KEYSTORES.equals(cmd))
    {
      reloadTree();
    }
  }


  private void createCert()
  {
    DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) parent.getSelectionPath().getLastPathComponent();
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
    KeyStoreEntryHolder entryHolder = (KeyStoreEntryHolder) node.getUserObject();
    String alias = entryHolder.getAlias();
    KeyStoreHolder ksHolder = (KeyStoreHolder) parentNode.getUserObject();
    KeyStore ks = ksHolder.getKeyStore();
    if (caChain != null && caChain.length > 0 && caChain[0] != null && caKey != null)
    {
      try
      {
        PublicKey pubKey = ks.getCertificate(alias).getPublicKey();
        passDiag.setVisible(true);
        PrivateKey privKey = (PrivateKey) ks.getKey(alias, passDiag.getPassword());

        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
        String subjectDn =
            JOptionPane.showInputDialog(currentPopup,
                "Please enter the Subject Distinguished Name for the certificate (CN=...).",
                "Enter SubjectDN", JOptionPane.PLAIN_MESSAGE);
        X509Name subjectName = new X509Name(subjectDn);
        BigInteger serialNumber =
            new BigInteger(JOptionPane.showInputDialog(currentPopup,
                "Please enter the Serial Number for the certificate ([0-9]+).",
                "Enter Serial Number", JOptionPane.PLAIN_MESSAGE));
        certGen.setSerialNumber(serialNumber);
        certGen.setIssuerDN(new X509Name(caChain[0].getSubjectX500Principal().getName()));
        Calendar cal = Calendar.getInstance();
        certGen.setNotBefore(cal.getTime());
        int validity =
            Integer.parseInt(JOptionPane.showInputDialog(currentPopup,
                "Please enter the validity period in years ([1-9]).", "Enter Validity",
                JOptionPane.PLAIN_MESSAGE));
        cal.add(Calendar.YEAR, validity);
        certGen.setNotAfter(cal.getTime());
        certGen.setSubjectDN(subjectName);
        certGen.setPublicKey(pubKey);
        certGen.setSignatureAlgorithm("SHA1withRSA");

        certGen.addExtension(X509Extensions.AuthorityKeyIdentifier, false,
            new AuthorityKeyIdentifierStructure(caChain[0]));
        certGen.addExtension(X509Extensions.SubjectKeyIdentifier, false,
            new SubjectKeyIdentifierStructure(pubKey));

        X509Certificate cert = certGen.generateX509Certificate(caKey); // note:
                                                                        // private
                                                                        // key
                                                                        // of
                                                                        // CA
        Certificate[] chain = new Certificate[caChain.length + 1];
        chain[0] = cert;
        System.arraycopy(caChain, 0, chain, 1, caChain.length);
        ks.setKeyEntry(alias, privKey, passDiag.getPassword(), chain);
        ksHolder.saveKeyStore();
      }
      catch (CertificateException ex)
      {
        ex.printStackTrace();
      }
      catch (FileNotFoundException ex)
      {
        ex.printStackTrace();
      }
      catch (KeyStoreException ex)
      {
        ex.printStackTrace();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
    reloadTree();
  }


  private void markAsRoot()
  {
    DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) parent.getSelectionPath().getLastPathComponent();
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
    KeyStoreEntryHolder entryHolder = (KeyStoreEntryHolder) node.getUserObject();
    String alias = entryHolder.getAlias();
    KeyStoreHolder ksHolder = (KeyStoreHolder) parentNode.getUserObject();
    KeyStore ks = ksHolder.getKeyStore();
    try
    {
      caChain = new X509Certificate[entryHolder.getChain().length];
      System.arraycopy(entryHolder.getChain(), 0, caChain, 0, caChain.length);
      passDiag.setVisible(true);
      caKey = (PrivateKey) ks.getKey(alias, passDiag.getPassword());

      JOptionPane.showMessageDialog(currentPopup, "The certificate '"
          + caChain[0].getSubjectDN().getName() + "' will be used as root certificate.",
          "Root Certificate", JOptionPane.INFORMATION_MESSAGE);
    }
    catch (KeyStoreException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (NoSuchAlgorithmException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (UnrecoverableKeyException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }


  private void importCertificate()
  {
    DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) parent.getSelectionPath().getLastPathComponent();
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
    KeyStoreEntryHolder entryHolder = (KeyStoreEntryHolder) node.getUserObject();
    String alias = entryHolder.getAlias();
    KeyStoreHolder ksHolder = (KeyStoreHolder) parentNode.getUserObject();
    KeyStore ks = ksHolder.getKeyStore();
    passDiag.setVisible(true);
    try
    {
      PrivateKey privKey = (PrivateKey) ks.getKey(alias, passDiag.getPassword());
      int diagResult = certDiag.showOpenDialog();
      File certFile = certDiag.getSelectedFile();
      if (diagResult == JFileChooser.APPROVE_OPTION && certFile != null && certFile.canRead())
      {
        FileInputStream fis = new FileInputStream(certFile);
        CertificateFactory factory = CertificateFactory.getInstance("X.509");
        Certificate cert = factory.generateCertificate(fis);
        Certificate[] chain = new Certificate[2];
        chain[0] = cert;
        ks.setKeyEntry(alias, privKey, passDiag.getPassword(), chain);
        ksHolder.saveKeyStore();
      }
    }
    catch (KeyStoreException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (NoSuchAlgorithmException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (UnrecoverableKeyException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (FileNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (CertificateException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (Exception e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    reloadTree();
  }


  private void importTrustedCertificate()
  {
    DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) parent.getSelectionPath().getLastPathComponent();
    KeyStoreHolder holder = (KeyStoreHolder) node.getUserObject();
    KeyStore ks = holder.getKeyStore();
    int diagResult = certDiag.showOpenDialog();
    File certFile = certDiag.getSelectedFile();
    if (diagResult == JFileChooser.APPROVE_OPTION && certFile != null && certFile.canRead())
    {
      String alias =
          JOptionPane.showInputDialog(currentPopup,
              "Please enter the alias for the trusted certificate to import.",
              "Enter trusted certificate alias", JOptionPane.PLAIN_MESSAGE);
      try
      {
        boolean importCert = true;
        if (ks.containsAlias(alias))
        {
          int option =
              JOptionPane.showConfirmDialog(currentPopup, "The alias '" + alias
                  + "' already exists. " + "Do you realy want to overwrite the key store entry.",
                  "Overwrite entry?", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
          if (option != JOptionPane.YES_OPTION)
          {
            importCert = false;
          }
        }
        if (importCert)
        {
          FileInputStream fis = new FileInputStream(certFile);
          CertificateFactory factory = CertificateFactory.getInstance("X.509");
          Certificate cert = factory.generateCertificate(fis);
          ks.setCertificateEntry(alias, cert);
          holder.saveKeyStore();
        }
      }
      catch (CertificateException ex)
      {
        ex.printStackTrace();
      }
      catch (FileNotFoundException ex)
      {
        ex.printStackTrace();
      }
      catch (KeyStoreException ex)
      {
        ex.printStackTrace();
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }
    }
    reloadTree();
  }


  private void deleteAlias()
  {
    DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) parent.getSelectionPath().getLastPathComponent();
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
    KeyStoreEntryHolder entryHolder = (KeyStoreEntryHolder) node.getUserObject();
    String alias = entryHolder.getAlias();
    KeyStoreHolder ksHolder = (KeyStoreHolder) parentNode.getUserObject();
    KeyStore ks = ksHolder.getKeyStore();
    boolean delete = true;
    try
    {
      if (ks.isKeyEntry(alias))
      {
        int option =
            JOptionPane.showConfirmDialog(currentPopup,
                "Do you realy want to delete the key entry with alias '" + alias + "'?\n"
                    + "The coresponding private key will also be deleted.", "Delete '" + alias
                    + "'?", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (option != JOptionPane.YES_OPTION)
        {
          delete = false;
        }
      }
      if (delete)
      {
        ks.deleteEntry(alias);
        ksHolder.saveKeyStore();
        reloadTree();
      }
    }
    catch (HeadlessException ex)
    {
      ex.printStackTrace();
    }
    catch (KeyStoreException ex)
    {
      ex.printStackTrace();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  private void renameAlias()
  {
    DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) parent.getSelectionPath().getLastPathComponent();
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
    KeyStoreEntryHolder entryHolder = (KeyStoreEntryHolder) node.getUserObject();
    final String alias = entryHolder.getAlias();
    KeyStore.ProtectionParameter protParam = null;
    if (entryHolder.isKeyEntry())
    {
      passDiag.setVisible(true);
      protParam = new KeyStore.PasswordProtection(passDiag.getPassword());
    }
    KeyStoreHolder ksHolder = (KeyStoreHolder) parentNode.getUserObject();
    KeyStore ks = ksHolder.getKeyStore();
    try
    {
      String newAlias =
        JOptionPane.showInputDialog(currentPopup, "Please enter the new alias for the entry.",
            "Enter alias", JOptionPane.PLAIN_MESSAGE);
      if (!newAlias.equals(alias))
      {
        ks.setEntry(newAlias, ks.getEntry(alias, protParam), protParam);
        ks.deleteEntry(alias);
        ksHolder.saveKeyStore();
        reloadTree();
      }
    }
    catch (HeadlessException ex)
    {
      ex.printStackTrace();
    }
    catch (KeyStoreException ex)
    {
      ex.printStackTrace();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }


  private void pkcs10Request()
  {
    DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) parent.getSelectionPath().getLastPathComponent();
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
    KeyStoreEntryHolder entryHolder = (KeyStoreEntryHolder) node.getUserObject();
    String alias = entryHolder.getAlias();
    KeyStoreHolder ksHolder = (KeyStoreHolder) parentNode.getUserObject();
    KeyStore ks = ksHolder.getKeyStore();
    Certificate cert = (Certificate) entryHolder.getChain()[0];
    passDiag.setVisible(true);
    X500Principal subjectName = new X500Principal("CN=Test V3 Certificate");
    try
    {
      PKCS10CertificationRequest kpGen =
          new PKCS10CertificationRequest("SHA1withRSA", subjectName, cert.getPublicKey(), null,
              (PrivateKey) ks.getKey(alias, passDiag.getPassword()));
      byte[] certRequest = kpGen.getEncoded();
      int diagResult = csrDiag.showSaveDialog();
      File saveFile = csrDiag.getSelectedFile();
      if (diagResult == JFileChooser.APPROVE_OPTION && saveFile != null)
      {
        boolean save = true;
        if (saveFile.exists())
        {
          int option =
              JOptionPane.showConfirmDialog(currentPopup, "The file '" + saveFile.getName()
                  + "' already exists. " + "Do you realy want to overwrite the file.",
                  "Overwrite file?", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
          if (option != JOptionPane.YES_OPTION)
          {
            save = false;
          }
        }
        if (save)
        {
          try
          {
            FileOutputStream fos = new FileOutputStream(saveFile);
            fos.write(certRequest);
            fos.flush();
            fos.close();
          }
          catch (IOException ex)
          {
            ex.printStackTrace();
          }
        }

      }
    }
    catch (InvalidKeyException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (NoSuchAlgorithmException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (NoSuchProviderException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (SignatureException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (KeyStoreException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (UnrecoverableKeyException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }


  private void exportCertificate()
  {
    DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) parent.getSelectionPath().getLastPathComponent();
    KeyStoreEntryHolder holder = (KeyStoreEntryHolder) node.getUserObject();
    Certificate cert = (Certificate) holder.getChain()[0];
    byte[] encodedCert = null;
    try
    {
      encodedCert = cert.getEncoded();
    }
    catch (CertificateEncodingException ex)
    {
      ex.printStackTrace();
    }
    int diagResult = certDiag.showSaveDialog();
    File saveFile = certDiag.getSelectedFile();
    if (diagResult == JFileChooser.APPROVE_OPTION && saveFile != null)
    {
      boolean save = true;
      if (saveFile.exists())
      {
        int option =
            JOptionPane.showConfirmDialog(currentPopup, "The file '" + saveFile.getName()
                + "' already exists. " + "Do you realy want to overwrite the file.",
                "Overwrite file?", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (option != JOptionPane.YES_OPTION)
        {
          save = false;
        }
      }
      if (save)
      {
        try
        {
          FileOutputStream fos = new FileOutputStream(saveFile);
          fos.write(encodedCert);
          fos.flush();
          fos.close();
        }
        catch (IOException ex)
        {
          ex.printStackTrace();
        }
      }

    }
  }


  private void changeStorePass()
  {
    DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) parent.getSelectionPath().getLastPathComponent();
    KeyStoreHolder ksHolder = (KeyStoreHolder) node.getUserObject();
    changePassDiag.setVisible(true);
    try
    {
      ksHolder.setStorePass(changePassDiag.getOldPassword());
      ksHolder.loadKeyStore();
      ksHolder.setStorePass(changePassDiag.getNewPassword());
      ksHolder.saveKeyStore();
    }
    catch (KeyStoreException ex)
    {
      ex.printStackTrace();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }


  private void changeKeyPass()
  {
    DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) parent.getSelectionPath().getLastPathComponent();
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
    KeyStoreEntryHolder entryHolder = (KeyStoreEntryHolder) node.getUserObject();
    String alias = entryHolder.getAlias();
    KeyStoreHolder ksHolder = (KeyStoreHolder) parentNode.getUserObject();
    KeyStore ks = ksHolder.getKeyStore();
    changePassDiag.setVisible(true);
    try
    {
      ks.setKeyEntry(alias, ks.getKey(alias, changePassDiag.getOldPassword()), changePassDiag
          .getNewPassword(), ks.getCertificateChain(alias));
      ksHolder.saveKeyStore();
    }
    catch (UnrecoverableKeyException ex)
    {
      ex.printStackTrace();
    }
    catch (KeyStoreException ex)
    {
      ex.printStackTrace();
    }
    catch (NoSuchAlgorithmException ex)
    {
      ex.printStackTrace();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }


  private void cloneKey()
  {
    DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) parent.getSelectionPath().getLastPathComponent();
    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) node.getParent();
    KeyStoreEntryHolder entryHolder = (KeyStoreEntryHolder) node.getUserObject();
    String alias = entryHolder.getAlias();
    KeyStoreHolder ksHolder = (KeyStoreHolder) parentNode.getUserObject();
    KeyStore ks = ksHolder.getKeyStore();
    passDiag.setVisible(true);
    String newAlias =
        JOptionPane.showInputDialog(currentPopup,
            "Please enter the new alias for the copy of the key entry.", "Enter key alias",
            JOptionPane.PLAIN_MESSAGE);
    try
    {
      boolean cloneKey = true;
      if (ks.containsAlias(newAlias))
      {
        int option =
            JOptionPane.showConfirmDialog(currentPopup, "The alias '" + alias
                + "' already exists. " + "Do you realy want to overwrite the key store entry.",
                "Overwrite entry?", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (option != JOptionPane.YES_OPTION)
        {
          cloneKey = false;
        }
      }
      if (cloneKey)
      {
        ks.setKeyEntry(newAlias, ks.getKey(alias, passDiag.getPassword()), passDiag.getPassword(),
            ks.getCertificateChain(alias));
        ksHolder.saveKeyStore();
        reloadTree();
      }
    }
    catch (UnrecoverableKeyException ex)
    {
      ex.printStackTrace();
    }
    catch (KeyStoreException ex)
    {
      ex.printStackTrace();
    }
    catch (NoSuchAlgorithmException ex)
    {
      ex.printStackTrace();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }


  private void deleteKeyStore() throws HeadlessException
  {
    int option =
        JOptionPane.showConfirmDialog(currentPopup,
            "Do you realy want to delete the complete keystore?\n"
                + "All certificates and keys will be lost!", "Delete KeyStore?",
            JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
    if (option == JOptionPane.YES_OPTION)
    {
      DefaultMutableTreeNode node =
          (DefaultMutableTreeNode) parent.getSelectionPath().getLastPathComponent();
      KeyStoreHolder holder = (KeyStoreHolder) node.getUserObject();
      File ksFile = holder.getKsFile();
      ksFile.delete();
      reloadTree();
    }
  }


  private void newKeyStore()
  {
    int diagResult = keyStoreDiag.showSaveDialog();
    File saveFile = keyStoreDiag.getSelectedFile();
    if (diagResult == JFileChooser.APPROVE_OPTION && saveFile != null)
    {
      boolean save = true;
      if (saveFile.exists())
      {
        int option =
            JOptionPane.showConfirmDialog(currentPopup, "The file '" + saveFile.getName()
                + "' already exists. " + "Do you realy want to overwrite the file.",
                "Overwrite file?", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
        if (option != JOptionPane.YES_OPTION)
        {
          save = false;
        }
      }
      if (save)
      {
        storePassDiag.setVisible(true);

        try
        {
          KeyStore ks = KeyStore.getInstance("JKS");
          ks.load(null, storePassDiag.getPassword());
          ks.store(new FileOutputStream(saveFile), storePassDiag.getPassword());
          reloadTree();
        }
        catch (NoSuchAlgorithmException ex)
        {
          ex.printStackTrace();
        }
        catch (CertificateException ex)
        {
          ex.printStackTrace();
        }
        catch (FileNotFoundException ex)
        {
          ex.printStackTrace();
        }
        catch (KeyStoreException ex)
        {
          ex.printStackTrace();
        }
        catch (IOException ex)
        {
          ex.printStackTrace();
        }
      }
    }

  }


  private void newKey()
  {
    DefaultMutableTreeNode node =
        (DefaultMutableTreeNode) parent.getSelectionPath().getLastPathComponent();
    KeyStoreHolder ksHolder = (KeyStoreHolder) node.getUserObject();
    KeyStore ks = ksHolder.getKeyStore();
    String alias =
        JOptionPane.showInputDialog(currentPopup, "Please enter the alias for the new key.",
            "Enter key alias", JOptionPane.PLAIN_MESSAGE);
    passDiag.setVisible(true);
    try
    {
      KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
      int keySize =
          Integer.parseInt(JOptionPane.showInputDialog(currentPopup,
              "Please enter the key size (512, 1024, 2048 or 4096 bit).", "Enter Key Size",
              JOptionPane.PLAIN_MESSAGE));

      generator.initialize(keySize);
      KeyPair pair = generator.genKeyPair();
      X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
      certGen.setPublicKey(pair.getPublic());
      certGen.setSignatureAlgorithm("SHA1withRSA");
      X509Name issuer =
          new X509Name("CN=Achievo Development Root CA, O=Achievo Deutschland AG, "
              + "OU=Professional Services, OU=Development");
      certGen.setIssuerDN(issuer);
      certGen.setSubjectDN(issuer);
      certGen.setSerialNumber(BigInteger.ONE);
      Calendar cal = Calendar.getInstance();
      certGen.setNotBefore(cal.getTime());
      cal.add(Calendar.YEAR, 10);
      certGen.setNotAfter(cal.getTime());
      X509Certificate cert = certGen.generateX509Certificate(pair.getPrivate());
      ks.setKeyEntry(alias, pair.getPrivate(), passDiag.getPassword(), new Certificate[] {cert});
      ksHolder.saveKeyStore();
      reloadTree();
    }
    catch (KeyStoreException ex)
    {
      ex.printStackTrace();
    }
    catch (InvalidKeyException ex)
    {
      ex.printStackTrace();
    }
    catch (NoSuchProviderException ex)
    {
      ex.printStackTrace();
    }
    catch (SignatureException ex)
    {
      ex.printStackTrace();
    }
    catch (SecurityException ex)
    {
      ex.printStackTrace();
    }
    catch (NoSuchAlgorithmException ex)
    {
      ex.printStackTrace();
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }


  private void reloadTree()
  {
    Container cont = parent;
    while (!(cont instanceof ExplorerFrame))
    {
      cont = cont.getParent();
    }
    ((ExplorerFrame) cont).reloadTree();
  }


  private KeyStoreEntryHolder getKeyStoreEntry(int index)
  {
    DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getPathComponent(index);
    return (KeyStoreEntryHolder) node.getUserObject();
  }
}
