del Stickmen.jar
rd /s /q app

call gradlew lwjgl3:dist

FOR /F "tokens=* USEBACKQ" %%F IN (`git describe --tags`) DO (SET describe=%%F)
FOR /f "tokens=1 delims=-" %%a in ("%describe%") do (SET tag=%%a)
FOR /f "tokens=2 delims=-" %%a in ("%describe%") do (SET build=%%a)

robocopy lwjgl3\build\libs\ . Stickmen-latest.jar
rename Stickmen-latest.jar Stickmen.jar

java -jar /Apps/packr-all-4.0.0.jar packr-config.json

del Stickmen.jar

echo Stickmen %tag%.%build% > "app/Stickmen %tag%.%build%.version

pause
