@echo OFF
%~dp0"KMSAuto_Net.exe" /off=act
cd %~dp0
attrib -R -A -S -H *.*
RMDIR /S /Q "%WINDIR%\Setup\Scripts"
exit