# BundleBuild

BundleBuild is a Java-based GUI tool designed to convert Android App Bundles (AAB) into APK files. This tool supports both Windows and Linux systems, featuring a beautiful and user-friendly interface created with the FlatLaf module.

## Features

- Convert Android App Bundles (AAB) to APK.
- Cross-platform support (Windows and Linux).
- Intuitive and modern GUI with FlatLaf.
- Easy to use with drag-and-drop functionality.

## Requirements

- Java Development Kit (JDK) 11 or higher (recommended [openjdk21](https://openjdk.org/projects/jdk/21/))
- Android SDK with `bundletool` installed

## Installation

### Prerequisites

Ensure you have the following installed:
1. **Android Keystore**: [Configure your android keystore](https://developer.android.com/privacy-and-security/keystore)

### Clone the Repository

```sh
git clone https://github.com/your-username/BundleBuild.git
cd BundleBuild
```

## Usage
### Windows
  Double-click the BundleBuild.jar file to start the application.
  Use the GUI to navigate and convert your AAB files to APK.

### Linux
  Open a terminal and navigate to the BundleBuild directory.
  Run the following command to start the application:
    
    java -jar BundleBuild.jar

## Configuration
Ensure the bundletool is accessible from your PATH. You can download bundletool from the official GitHub repository.
Example command to add bundletool to PATH (assuming it's downloaded to your home directory):

### Windows
    set PATH=%PATH%;C:\path\to\bundletool

### Linux
    export PATH=$PATH:~/path/to/bundletool

# Contributing

Contributions are welcome! Please submit a pull request or open an issue to discuss any changes.

# Contact

For any questions or suggestions, feel free to open an issue or reach out to me at `trinekaka@gmail.com`
