Welcome to Eclipse explorer 4.x!
## Introduction

![screenshot](https://raw.githubusercontent.com/Jamling/eclipse-explorer/master/screenshot.gif)

Eclipse Explorer is an Eclipse plugin helping you to open the folder or select resource in explorer quickly.
Our aim is to make it the most powerfull explorer plugin of Eclipse

Special features

- Support key assitant (default CTRL + `)
- Support Windows/Mac/Linux platform
- Support auto selecting file(Windows only)
- Support exploring file in Text Editor
- Support **`plugin fragment`** for enhance feature

## Plugin fragments
### Eclipse Explorer for Java
  - Author: [Jamling]
  - Short name: explorer4java
  - Suitable: For Java developer IDEs, such as:
    * Eclipse IDE for Java Developers
    * Eclipse IDE for Java EE Developers
    * Eclipse for RCP and RAP Developers
    * And other Eclipse IDE using Java language...
  
### Eclipse Explorer for CDT
 - Author: [Jamling]
 - Short name: explorer4cdt
 - Suitable: for C/C++ developer IDEs, such as:
    * Eclipse IDE for C/C++ Developers
    * Eclipse for Android Developers

Other fragment? just waiting for you!

If no fragment for your development language, you can [develop](http://ieclipse.cn/p/eclipse-explorer/develop.html) it easy or mail [Author](mailto:li.jamling@gmail.com) to develop it.

## Install
### Eclipse Marketplace
1. Click **Help->Eclipse Marketplace...**
2. Input the keywords "**eclipse explorer**" in Find and <kbd>enter</kbd>
3. Choose **Eclipse Explorer** and click "Install" button
4. Trust the unsigned contents or certificate published by Jamling/ieclipse.cn
5. Restart Eclipse

### Local site
1. Download [explorer_4.1.0.zip](https://github.com/Jamling/eclipse-explorer/releases/download/v4.1.0/explorer_4.1.0.zip)
2. Click **Help->Install New Software...** 
3. "Add..." local "Archive..." repository and choose explorer_4.1.0.zip
4. Choose "Eclipse Explorer" feature to install

Marketplcae link: [https://marketplace.eclipse.org/content/eclipse-explorer](https://marketplace.eclipse.org/content/eclipse-explorer)

## Configuration
### Command

Explorer Command: The plugin will auto configure explorer command. And you can customize your explorer command in Window->Preference->General->Eclipse Explorer. 

#### Shortcut Key
The default assistant key is "Ctrl + `", you can change it in Window->Preference->General->Keys.

## Tutorial

- Select an resource item which can be explorerable
- Right click and select "Open in Explorer" or press short key set by you.

## Developer
See [documents](http://ieclipse.cn/en/p/eclipse-explorer/develop.html) on [iecipse.cn](http://ieclipse.cn/en/p/eclipse-explorer) 

[Jamling]: https://github.com/Jamling/
