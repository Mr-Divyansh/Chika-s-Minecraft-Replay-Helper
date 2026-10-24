@echo off
set JAR_PATH="c:\Users\divy1\OneDrive\Desktop\Divyansh all codes\Chika Replay Helper\D-Web-Studio-Chika-Replay-Helper-1.1.0+mc26.1.2.jar"
set JAR_EXE="C:\Program Files\Microsoft\jdk-25.0.4.101-hotspot\bin\jar.exe"
set MOD_JSON="fabric.mod.json"

%JAR_EXE% -uf %JAR_PATH% %MOD_JSON%
if %errorlevel% equ 0 (
    echo JAR updated successfully
) else (
    echo Failed to update JAR
)
pause