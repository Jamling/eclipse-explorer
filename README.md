Welcome to Eclipse explorer!
## Introduction ##

Eclipse Explorer is an Eclipse plugin helping you to open the folder of selected resources 

include follow resources:
Java element, .java, .class, jar file, package, source folder and java project
Common resources, such as .txt, .xml, and other resource
Some special element such as cvs item in Synchronized View

## Requirement ##
  * JDT plugin
  * JDK/JRE 1.5 or higher
  * Eclipse 3.5 or higher(recommend)
  
  *Note:Eclipse 3.4 lower(Un-tested)*
  This plugin are developed in eclipse3.5 + jdk1.5, test under

  * window 32bit+eclipse3.5+jdk1.5
  * linux64bits+eclipse3.6+jdk1.6

  *Note: This plugin requires Java Developement Tool(JDT) plugin. So, this plugin can't be 
installed in non-Java developement eclipse version.*

## Install ##
### Eclipse Marketplace ###
[https://marketplace.eclipse.org/content/eclipse-explorer](https://marketplace.eclipse.org/content/eclipse-explorer)
### Local plugin arachive ###
  * Download org.melord.pde.explorer.jar
  * Copy to %Eclipse_Home%\plugins\
  * Restart Eclipse

### Local Archive ###
 
  * Download update-site archive
  * Click Help->Install New Software... in eclipse to open install page
  * Click "Add..." button open add site dialog
  * Click "Archive..." button to select .zip file.
  * Click "OK" back to install page
  * Select Eclipse Explorer Feature
  * Next step...
## Configuration ##
### Command ###

Explorer Command: The plugin will auto configure explorer command. But you can custom your 

explorer command still in Window->Preference->General->Resource Explorer. 
==== Shortcut Key ====

Key assistant: The default shortcut key is "Ctrl + `", you can custom in Window->Preference

->General->Keys.
## Tutorial ##

  - Select an resource item which can be explorer
  - Right click and select "Open in Explorer" or press short key set by you.

The wiki uses [Markdown](/p/eclipseexplorer/wiki/markdown_syntax/) syntax.

[[project_admins]]
[[download_button]]
[用户指南]
