@echo off
color 17

if "%1" == "" (
    for /f "delims=" %%i in ('dir /s /b /a-d /o-s *.jar') do (
        echo 正在反编译 %%~ni...
        title 正在反编译 %%i...
        java -jar G:\cfr-0.145.jar "%%i" --caseinsensitivefs true  --outputdir "%%~di%%~pi%%~ni"
        echo ----%%i已经翻反编译---
    )
    goto :end 
) else (
    title 正在反编译 %1...
    java -jar G:\cfr-0.145.jar %1 --caseinsensitivefs true  --outputdir "%~d1%~p1%~n1"
    echo 反编译完成.
    goto :end
)

echo 反编译完成.
@pause>nul

:end
pause
exit