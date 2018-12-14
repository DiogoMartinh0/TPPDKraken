start cmd /c java -jar KindaDNS.jar
:: dorme 3 segundos, ver https://stackoverflow.com/questions/1672338/how-to-sleep-for-5-seconds-in-windowss-command-prompt-or-dos
ping 127.0.0.1 -n 4 > nul
java -jar Server.jar
:: start cmd /c java -jar Server.jar
pause