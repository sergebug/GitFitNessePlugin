FitNesse plug-in based on the https://github.com/timander/fitnesse-git-plugin.
Was intended to be used only on Windows. 
It is intended to work with FitNesse's built-in Source Control mechanism.

Please see http://fitnesse.org/FitNesse.UserGuide.SourceCodeControl

Given the following folder structure
 
  FitNesse
  |---FitNesseRoot
  |---fitnesse-standalone.jar
  FitNesse-git
  |---FitNesseRoot
  |---GitCmSystemCmd.jar
  |---plugins.properties
  |---fitnesse-git.bat

Run in FitNesse-git folder
  
  ---------------------------------------------
  |git init
  ---------------------------------------------

Add git.path=/path/to/git.exe to plugins.properties

Example of plugins.properties
  ---------------------------------------------
  |git.path=/git/bin/git.exe
  |git.add=add
  |git.delete=rm --cached
  ---------------------------------------------

Example of fitnesse-git.bat (Windows)
  ---------------------------------------------
  |java.exe -cp GitCmSystemCmd.jar;..\FitNesse\fitnesse-standalone.jar fitnesseMain.FitNesseMain -p 8888 -e 0 -o
  ---------------------------------------------

Inside any WIKI page you want to be source controlled define CM_SYSTEM mark-up variable
  ---------------------------------------------
  |!define CM_SYSTEM {fitnesse.wiki.cmSystems.GitCmSystemCmd} 
  ---------------------------------------------

To test it, try making changes to a FitNesse page and check git log

Feedback is appreciated!
