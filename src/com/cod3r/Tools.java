package com.cod3r;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.*; 
import java.io.*;
import com.cod3r.Home;

import net.codejava.swing.download.SwingFileDownloadHTTP;

public class Tools {
	public static String Homedir = System.getProperty("user.home").toString() + "/bundlebuilder/";
	static File homeDir = new File(Homedir);
	static String configFile = Homedir+"build.ini";
	static String bundler = Homedir+"bundletool-all-1.17.0.jar";
	
	static boolean deviceConnected = false;
	
	public static boolean isDeviceConnected() {
		return deviceConnected;
	}

	public static void setDeviceConnected(boolean connection) {
		deviceConnected = connection;
	}
	static ExecutorService executor = Executors.newSingleThreadExecutor();
	public static void startUp() throws IOException {
		if(homeDir.exists()) {
			Tools.setMessage("BundleFolder exists..."+"YES");
		}else {
			Tools.setMessage("BundleFolder exists..."+"NO");
			Tools.setMessage("Creating bundlefolder..."+"YES");
			homeDir.mkdir();
			Tools.setMessage("Created bundlefolder..."+"YES");
		}
		File config = new File(configFile);
		File bundle = new File(bundler);
		if(bundle.exists()) {
			Tools.setMessage("Bundletool-V1.17.0.jar exists..."+"YES");
		}else {
			Tools.setMessage(Home.dashes);
			Tools.setMessage("Downloading bundletool-all-1.17.0.jar from github");
			Tools.setMessage(Home.dashes);
			SwingFileDownloadHTTP downloader = new SwingFileDownloadHTTP();
			downloader.setVisible(true);
			downloader.buttonDownload.doClick();
			
		}
		if(config.exists()) {
			Tools.setMessage("Config file exists..."+"YES");
		}else {
			Tools.setMessage(Home.dashes);
			Tools.setMessage("Creating config file..."+"Ok");
			Tools.setMessage(Home.dashes);
			config.createNewFile();
		}
		
		
		if (config.length() == 0) {
			setConfigs("", "", "");
			new keystoreFrame().setVisible(true);
		}else {
			String[] configs = getConfigs();
			
			if(configs[0].isBlank() || configs[1].isBlank() || configs[2].isBlank()) {
				new keystoreFrame().setVisible(true);
			}
		}
		
	}
	
	public static void setConfigs(String filepath,String password,String aliasName) throws IOException {
	        // create properties object 
	        Properties p = new Properties(); 
	  
	        // Add a wrapper around reader object 
//	        p.load(reader); 
	        
	        p.setProperty("keystore", filepath);
	        p.setProperty("password", password);
	        p.setProperty("alias", aliasName);
	        
	        // store the properties to a file 
	        p.store(new FileWriter(configFile), 
	                "Update Configs files"); 
	}
	
	public static String[] getConfigs() throws IOException {
		 FileReader reader = new FileReader(configFile); 
		  
	        // create properties object 
	        Properties p = new Properties(); 
	  
	        // Add a wrapper around reader object 
	        p.load(reader); 
	        
	        String keystore = p.getProperty("keystore");
	        String paswd = p.getProperty("password");
	        String alias = p.getProperty("alias");
			
	        String[] results = {keystore,paswd,alias};
	        return results;
	        
	        
	}
	
	public static String mode = "universal";
	static String outputCmd = "";
	public static String aabPath = "";
	
	public static String getAabPath() {
		return aabPath;
	}

	public static void setAabPath(String aabPath) {
		Tools.aabPath = aabPath;
	}

	public static String outputDir = "";
//	public static String connectedDevicesCmd = "java -jar "+bundler+" build-apks --bundle="+aabPath+" --output="+outputDir+" --connected-device --ks=~/.gradle/keystore.kst --ks-key-alias=my-key-alias";
	
	public static String getOutputDir() {
		return outputDir;
	}

	public static void setOutputDir(String outputDir) {
		Tools.outputDir = outputDir;
	}

	public static void setMessage(String message) {
		String currentText = Home.textPane.getText().toString();
		Home.textPane.setText(currentText + "\n"+message);
		Home.textPane.validate();
	}
	
    public static ImageIcon resizeIcon(String resourcePath, int width, int height) throws IOException {
        // Load the image from the resource path
        URL url = Home.class.getResource(resourcePath);
        BufferedImage img = ImageIO.read(url);

        // Resize the image
        Image resizedImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // Return the resized image as an ImageIcon
        return new ImageIcon(resizedImage);
    }   
    
    public static String runCmd(String command) {
    	int exitCode = 0;
//    	String connectedDevicesCmd = "java -jar "+bundler+" build-apks --bundle="+aabPath+" --output="+outputDir+"/Output.apks --connected-device --ks=~/.gradle/keystore.kst --ks-key-alias=my-key-alias --ks-pass=pass:Ericko004";
    	
      try {
	      // Command to execute
//	      String command = "cd;pwd;";  // Example command (list files in long format)
	      ProcessBuilder processBuilder = new ProcessBuilder();
	      processBuilder.command("bash", "-c", command);
	      Tools.setMessage("Running..."+command);
	      Process process = processBuilder.start();
	      InputStream inputStream = process.getInputStream();
	      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	      String line;
	      while ((line = reader.readLine()) != null) {
	          outputCmd+=line;
	      }
	      exitCode= process.waitFor();
	      return outputCmd;
      } catch (IOException | InterruptedException e) {
    	  e.printStackTrace();
    	  Tools.setMessage("Error..."+outputCmd);
    	  return "exit code"+exitCode;
      }
    }
    
    public static String buildApks(String aabPath,String outputDir, JFrame frame) {
    	int exitCode = 0;
    	String aab = aabPath.substring(0, aabPath.lastIndexOf('.'));
//    	Tools.setMessage("Apks name.."+aab);
    	String connectedDevicesCmd = "";
    	frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    	
      try {
	      // Command to execute
//	      String command = "cd;pwd;";  // Example command (list files in long format)
    	  
    	  String[] config = getConfigs();
      	  if (isDeviceConnected()) {
      		 connectedDevicesCmd = "java -jar "+bundler+" build-apks --bundle="+aabPath+" --output="+outputDir+"/Output.apks --connected-device --ks="+config[0]+" --ks-key-alias="+config[2]+" --ks-pass=pass:"+config[1]+"";
      	  }else {
      		 connectedDevicesCmd = "java -jar "+bundler+" build-apks --bundle="+aabPath+" --output="+outputDir+"/Output.apks --ks="+config[0]+" --ks-key-alias="+config[2]+" --ks-pass=pass:"+config[1]+"";
      	  }	
	      ProcessBuilder processBuilder = new ProcessBuilder();
	      Tools.setMessage(Home.dashes);
	      Tools.setMessage("Running..."+connectedDevicesCmd);
	      processBuilder.command("bash", "-c", connectedDevicesCmd);
	      Tools.setMessage(Home.dashes);
//	      Tools.setMessage("Running..."+connectedDevicesCmd);
	      Process process = processBuilder.start();
	      InputStream inputStream = process.getInputStream();
	      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	      String line,output="";
	      while ((line = reader.readLine()) != null) {
	          output+=line;
	          Tools.setMessage(line);
	      }
	      exitCode= process.waitFor();
	      frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	      Tools.setMessage("[*] COMPLETED BUILDING APKs");
	      Tools.setMessage(Home.dashes);
	      int a = JOptionPane.showConfirmDialog(null,"The apks have been build succefully,\nDo you want to install them?", "Install apks", JOptionPane.YES_NO_OPTION);
	      
	      if (a == JOptionPane.YES_OPTION) {
	    	  frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	    	  JOptionPane.showMessageDialog(null, "Make sure your adb device is plugged in", "Intalling apks", JOptionPane.INFORMATION_MESSAGE);
	    	  Tools.setMessage("Installing apks to..."+getDevice());
        	  String installApks = "java -jar "+bundler+" install-apks --apks="+outputDir+"/Output.apks";
        	  ProcessBuilder processBuilder1 = new ProcessBuilder();
    	      Tools.setMessage(Home.dashes);
    	      Tools.setMessage("Running..."+installApks);
    	      processBuilder1.command("bash", "-c", installApks);
    	      Tools.setMessage(Home.dashes);
//	        	      Tools.setMessage("Running..."+connectedDevicesCmd);
    	      int exitCode1;
    	      try {
    	    	  Process process1 = processBuilder1.start();
				  InputStream inputStream1 = process1.getInputStream();
				  BufferedReader reader1 = new BufferedReader(new InputStreamReader(inputStream1));
				  String line1,output1="";
				  while ((line1 = reader1.readLine()) != null) {
				      output1+=line1;
				      Tools.setMessage(line1);
				  }
				  exitCode1 = process1.waitFor();
				  frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				  JOptionPane.showMessageDialog(null,"Installation completed successfull","Installed",JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				e.printStackTrace();
			}
	      }
	      return output;
      } catch (IOException | InterruptedException e) {
    	  e.printStackTrace();
    	  return "exit code"+exitCode;
      }
    }
    
    public static String getDevice() {
    	String input = runCmd("adb start-server;adb devices -l");
    	String regex = "model:([^\\s]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            // Group 1 contains the model
            String model = matcher.group(1);
//            System.out.println("Model: " + model);
            return model;
        } else {
//            System.out.println("Model not found.");
        	return null;
        }
    }
}
