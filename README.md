# Appium Framework with Java Client

## Overview
This is a robust Appium-based automation framework written in **Java**, designed to test mobile applications on Android and iOS platforms. It uses **TestNG** for test management, **Maven** for build automation, and **Allure** for reporting.

---

## Features
- Cross-platform support: Android and iOS.
- Modular design for reusable components.
- TestNG integration for parallel execution and test management.
- Allure Reports for detailed test results.
- Maven for dependency management and build execution.

---

## Prerequisites
1. **Java Development Kit (JDK):**
    - Download and install [JDK 8+](https://www.oracle.com/java/technologies/javase-downloads.html).
    - Ensure `JAVA_HOME` is set up correctly.
2. **Maven:**
    - Download and install [Maven](https://maven.apache.org/download.cgi).
    - Ensure Maven is added to the system's `PATH`.
3. **Node.js and Appium:**
    - Install [Node.js](https://nodejs.org/).
    - Install Appium globally:
      ```bash
      npm install -g appium
      ```
    - Start Appium server:
      ```bash
      appium
      ```
4. **Android/iOS setup:**
    - For Android, set up Android SDK and ensure `adb` is available in the `PATH`.
    - For iOS, set up Xcode and enable developer mode.

---

## Installation
1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd <repository-directory>

## Generating Reports

### Generate Allure Reports

After running the tests, the results will be stored in the `target/allure-results` directory.

1. **Generate the Allure Report:**
   Run the following command to generate the report:
   ```bash
   allure generate target/allure-results --clean
2. **View Report:**
   To view the generated report in your default browser, run::
   ```bash
   allure open target/allure-report


# Setting Up Apache Maven for Your Project

Follow the steps below to install and configure Apache Maven on your machine for building and managing Java-based projects.

---

## Step 1: Install Apache Maven

### 1.1 Download Maven
- Go to the [official Maven download page](https://maven.apache.org/download.cgi).
- Download the latest binary `.zip` file.

### 1.2 Extract Maven
- Extract the `.zip` file to a directory (e.g., `C:\Program Files\Apache Maven`).

### 1.3 Verify Maven Directory
- Ensure the extracted folder contains subfolders like `bin`, `conf`, etc.

---

## Step 2: Configure Environment Variables

### 2.1 Open Environment Variables
- Press `Win + R`, type `sysdm.cpl`, and press Enter.
- Go to the **Advanced** tab and click on **Environment Variables**.

### 2.2 Add `MAVEN_HOME`
- Under **System variables**, click **New**.
- Enter `MAVEN_HOME` as the variable name and the path to the Maven folder (e.g., `C:\Program Files\Apache Maven`) as the value.

### 2.3 Update `PATH` Variable
- Find the `Path` variable under **System variables** and click **Edit**.
- Add the `bin` directory of Maven to the `PATH` (e.g., `C:\Program Files\Apache Maven\bin`).

### 2.4 Save Changes
- Click **OK** to save and close all dialogs.

---

## Step 3: Verify Installation

### 3.1 Open Command Prompt
- Open a new command prompt or terminal.

### 3.2 Run the Following Command
```bash
mvn -v

Step 1: Install Allure CLI
1.1 Install via Scoop (Recommended for Windows)
Open PowerShell as Administrator.
Install Scoop if you don't have it:
powershell
Copy code
Set-ExecutionPolicy RemoteSigned -Scope CurrentUser
iwr -useb get.scoop.sh | iex
Use Scoop to install Allure:
powershell
Copy code
scoop install allure
1.2 Install Manually
Download Allure CLI from the official GitHub releases page.
Extract the downloaded ZIP file to a directory (e.g., C:\Program Files\Allure).
Step 2: Add Allure to PATH
Open Environment Variables:

Press Win + R, type sysdm.cpl, and press Enter.
Go to the Advanced tab and click on Environment Variables.
Update the PATH Variable:

Locate the Path variable under System variables and click Edit.
Add the path to the Allure bin directory (e.g., C:\Program Files\Allure\bin).
Save Changes:

Click OK to save and close all dialogs.
Step 3: Verify Allure Installation
Open a new command prompt or PowerShell.
Type the following command:
bash
Copy code
allure --version
You should see the Allure version number. If not, recheck the PATH configuration.
