package com.cod3r;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class keystoreFrame extends JFrame {
	private JTextField keyStore;
	private JPasswordField passwd;
	private JTextField alias;

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public keystoreFrame() throws IOException {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					String[] configs = Tools.getConfigs();
					keyStore.setText(configs[0]);
					passwd.setText(configs[1]);
					alias.setText(configs[2]);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(keystoreFrame.class.getResource("/icons/android.png")));
		setTitle("Keystore Configurations");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(539, 208);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel lblKeystoreFile = new JLabel("File path");
		lblKeystoreFile.setBounds(12, 15, 99, 35);
		getContentPane().add(lblKeystoreFile);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(12, 53, 99, 35);
		getContentPane().add(lblPassword);
		
		JLabel lblAliasName = new JLabel("Alias Name");
		lblAliasName.setBounds(12, 90, 99, 35);
		getContentPane().add(lblAliasName);
		
		keyStore = new JTextField();
		keyStore.setBounds(100, 20, 378, 27);
		getContentPane().add(keyStore);
		keyStore.setColumns(10);
		
		passwd = new JPasswordField();
		passwd.setBounds(100, 57, 378, 27);
		getContentPane().add(passwd);
		
		alias = new JTextField();
		alias.setColumns(10);
		alias.setBounds(100, 94, 378, 27);
		getContentPane().add(alias);
		
		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = passwd.getText().toString();
				String keyPath = keyStore.getText().toString();
				String aliasText = alias.getText().toString();
				
				if (keyPath.isBlank()) {
					JOptionPane.showMessageDialog(null, "No Keystore selected", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(password.isBlank()) {
					JOptionPane.showMessageDialog(null, "No Password given", "Error", JOptionPane.ERROR_MESSAGE);
				}else if(aliasText.isBlank()) {
					JOptionPane.showMessageDialog(null, "No alias name given", "Error", JOptionPane.ERROR_MESSAGE);
				}else {
					try {
						Tools.setConfigs(keyPath, password, aliasText);
						setVisible(false);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnApply.setBounds(361, 133, 117, 25);
		getContentPane().add(btnApply);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancel.setBounds(232, 133, 117, 25);
		getContentPane().add(btnCancel);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("KEYSTORE FILE", ".jks");
				fileChooser.setFileFilter(filter);
				
				int a = fileChooser.showOpenDialog(null);
				
				if(a == JFileChooser.APPROVE_OPTION) {
					keyStore.setText(fileChooser.getSelectedFile().getAbsolutePath());
					Tools.setAabPath(fileChooser.getSelectedFile().getAbsolutePath().toString());
				}
			}
		});
		button.setBounds(480, 20, 45, 27);
		button.setIcon(Tools.resizeIcon("/icons/folder.png",20,20));
		getContentPane().add(button);
	}
}
