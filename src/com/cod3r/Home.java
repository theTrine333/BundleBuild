package com.cod3r;

import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.Toolkit;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import com.cod3r.Tools;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
public class Home extends JFrame {

	/**
	 * Create the frame.
	 */
	JPanel panel;
	public static String dashes = "-------------------------------------------------------------------------\n";
	private JTextField aabPath;
	private JTextField outputDir;
	private JTextField devField;
	JRadioButton devRadiobtn;
	public JComboBox devicesCombo;
	public  ButtonGroup radioBtns = new ButtonGroup();
	public static  JTextPane textPane;
	
	public Home() throws IOException {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Tools.startUp();
				try {
					String deviceName = Tools.getDevice();
					devField.setText(deviceName.toUpperCase());
					Tools.setMessage("Adb connected device...\t"+deviceName);
					Tools.setDeviceConnected(true);
				}catch(Exception E) {
					devField.setText("NULL");
					Tools.setMessage("Adb connected device...\tnull");
					Tools.setDeviceConnected(false);
					devRadiobtn.setEnabled(false);
				}
				
				
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Home.class.getResource("/icons/android.png")));
		setResizable(false);
		setSize(447,445);
		setLocationRelativeTo(null);
		setTitle("Android aab to apk");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		JMenuItem mntmSystemsInfo = new JMenuItem("Systems info");
		mnAbout.add(mntmSystemsInfo);
		
		JMenuItem mntmDevelopersInfo = new JMenuItem("Developers info");
		mnAbout.add(mntmDevelopersInfo);
		
		JMenu mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 447, 385);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Convert aab to apk easily");
		lblNewLabel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(26, 95, 180)));
		lblNewLabel.setFont(UIManager.getFont("h4.font"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 6, 445, 36);
		panel.add(lblNewLabel);
		
		getContentPane().add(panel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
		scrollPane.setBounds(1, 157, 445, 222);
		panel.add(scrollPane);
		
		textPane = new JTextPane();
		scrollPane.setViewportView(textPane);
		textPane.setText(dashes+"Logs" + "\n"+dashes);
		textPane.setEditable(false);
		textPane.setForeground(Color.WHITE);
		textPane.setBackground(Color.BLACK);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Bundle(aab) path", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 49, 227, 48);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		aabPath = new JTextField();
		aabPath.setBounds(5, 19, 168, 23);
		panel_1.add(aabPath);
		aabPath.setColumns(10);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setBounds(175, 12, 46, 30);
		panel_1.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("AAB FILES", "aab");
				fileChooser.setFileFilter(filter);
				
				int a = fileChooser.showOpenDialog(null);
				
				if(a == JFileChooser.APPROVE_OPTION) {
					aabPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
					Tools.setAabPath(fileChooser.getSelectedFile().getAbsolutePath().toString());
					Tools.setMessage("aab file..."+Tools.aabPath);
				}
			}
		});
		btnNewButton.setIcon(Tools.resizeIcon("/icons/folder.png",20,20));
		
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setBorder(new TitledBorder(null, "apk output dir", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1_1.setBounds(10, 100, 227, 48);
		panel.add(panel_1_1);
		
		outputDir = new JTextField();
		outputDir.setColumns(10);
		outputDir.setBounds(5, 19, 168, 23);
		panel_1_1.add(outputDir);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				int a = fileChooser.showSaveDialog(null);
				
				if(a == JFileChooser.APPROVE_OPTION) {
					outputDir.setText(fileChooser.getSelectedFile().getAbsolutePath());
					Tools.setOutputDir(fileChooser.getSelectedFile().getAbsolutePath().toString());
				}
			}
		});
		btnNewButton_1.setIcon(Tools.resizeIcon("/icons/folder.png",20,20));
		btnNewButton_1.setBounds(175, 12, 46, 30);
		panel_1_1.add(btnNewButton_1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Device to convert for : ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(240, 42, 200, 76);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		devField = new JTextField();
		devField.setBounds(32, 19, 161, 23);
		panel_2.add(devField);
		devField.setFont(new Font("Liberation Sans", Font.PLAIN, 15));
		devField.setForeground(Color.RED);
		devField.setEditable(false);
		devField.setHorizontalAlignment(SwingConstants.CENTER);
		devField.setColumns(10);
		
		devRadiobtn = new JRadioButton("");
		devRadiobtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (devRadiobtn.isSelected()) {
					Tools.setMessage("Building apks for..."+devField.getText().toString());
				}
			}
		});
		radioBtns.add(devRadiobtn);
		devRadiobtn.setBounds(5, 20, 24, 21);
		panel_2.add(devRadiobtn);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("");
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rdbtnNewRadioButton_1.isSelected()) {
					Tools.setMessage("Building apks for..."+devicesCombo.getSelectedItem().toString());
				}
			}
		});
		radioBtns.add(rdbtnNewRadioButton_1);
		rdbtnNewRadioButton_1.setBounds(5, 49, 24, 21);
		panel_2.add(rdbtnNewRadioButton_1);
		
		devicesCombo = new JComboBox();
		devicesCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rdbtnNewRadioButton_1.doClick();
			}
		});
		devicesCombo.setBounds(32, 48, 161, 23);
		panel_2.add(devicesCombo);
		devicesCombo.setModel(new DefaultComboBoxModel(new String[] {
				"universal",
				"armeabi-v7a",
				"arm64-v8a",
				"x86_64",
				"x86"
				}
			)
		);
		
		JButton btnConvert = new JButton("Convert");
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!aabPath.getText().toString().endsWith(".aab") || aabPath.getText().toString().isBlank()){
					JOptionPane.showMessageDialog(null, "An error occured,\nPlease check the aab file path and try again","Error",JOptionPane.ERROR_MESSAGE);
				}else if(outputDir.getText().toString().isBlank()) {
					JOptionPane.showMessageDialog(null, "An error occured,\nPlease check the output dir and try again","Error",JOptionPane.ERROR_MESSAGE);
//					Tools.setMessage(Tools.outputDir);
				}else {
					Tools.buildApks(Tools.getAabPath(), Tools.getOutputDir());
				}
			}
		});
		btnConvert.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConvert.setFont(new Font("Liberation Sans", Font.BOLD, 13));
		btnConvert.setBackground(new Color(0, 0, 255));
		btnConvert.setForeground(Color.WHITE);
		btnConvert.setBounds(240, 122, 200, 23);
		panel.add(btnConvert);
		
		
		setVisible(true);
	}
}
