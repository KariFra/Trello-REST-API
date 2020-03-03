call runcrud
if "%ERRORLEVEL%" == "0" goto end
echo.
echo RUNCRUD has errors - breaking work
goto fail

:fail
echo.
echo There were errors

:end
start firefox http://localhost:8080/crud/v1/task/getTasks
echo.
echo Work is finished.

