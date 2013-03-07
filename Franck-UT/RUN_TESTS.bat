REM Uninstall existing app on test device
adb uninstall fr.xgouchet.musichelper
adb uninstall fr.xgouchet.musichelper.test 

REM build and install the test app on device
call ant emma clean debug install > build.log

REM launch the test and write the output to test.log
call ant emma test emma-reports > test.log

REM 
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

rm test-failures.log
mv test.log ..\reports\test-output.log
mv build.log ..\reports\build-output.log
@echo on
