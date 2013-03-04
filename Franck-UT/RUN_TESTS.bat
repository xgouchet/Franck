adb uninstall fr.xgouchet.musichelper
adb uninstall fr.xgouchet.musichelper.test 
call ant emma clean debug install 
call ant emma test emma-reports > test.log
findstr "Failure expected" test.log > test-failures.log

@echo off
setlocal
set file="test-failures.log"

FOR /F "usebackq" %%A IN ('%file%') DO set size=%%~zA

IF %size% LEQ 0 goto okay 
	echo msgbox "Some Unit Tests failed",vbOKOnly,"Franck UT" > %tmp%\tmp.vbs
	wscript %tmp%\tmp.vbs
	del %tmp%\tmp.vbs
:okay

rm -f "test.log"
@echo on

