@echo off
color 17

if "%1" == "" (
    for /f "delims=" %%i in ('dir /s /b /a-d /o-s *.jar') do (
        echo ���ڷ����� %%~ni...
        title ���ڷ����� %%i...
        java -jar E:\renfei\WorkSpace\IDEA\RenPeoject\����\����������\cfr-0.145.jar "%%i" --caseinsensitivefs true  --outputdir "%%~di%%~pi%%~ni"
        echo ----%%i�Ѿ���������---
    )
    goto :end 
) else (
    title ���ڷ����� %1...
    java -jar G:\cfr-0.145.jar %1 --caseinsensitivefs true  --outputdir "%~d1%~p1%~n1"
    echo ���������.
    goto :end
)

echo ���������.
@pause>nul

:end
pause
exit