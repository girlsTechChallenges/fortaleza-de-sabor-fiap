@echo off
echo ========================================
echo VALIDACAO COMPLETA DOS TESTES DE INTEGRACAO
echo ========================================
echo.

echo [1/6] Testando ResilientBasicIntegrationTest...
call mvn test -Dtest=ResilientBasicIntegrationTest -q
if %errorlevel% neq 0 (echo FALHOU) else (echo SUCESSO)
echo.

echo [2/6] Testando AuthControllerSimplifiedIntegrationTest...
call mvn test -Dtest=AuthControllerSimplifiedIntegrationTest -q  
if %errorlevel% neq 0 (echo FALHOU) else (echo SUCESSO)
echo.

echo [3/6] Testando RestaurantControllerIntegrationTestFixed...
call mvn test -Dtest=RestaurantControllerIntegrationTestFixed -q
if %errorlevel% neq 0 (echo FALHOU) else (echo SUCESSO)
echo.

echo [4/6] Testando BasicEndpointsIntegrationTestFixed...
call mvn test -Dtest=BasicEndpointsIntegrationTestFixed -q
if %errorlevel% neq 0 (echo FALHOU) else (echo SUCESSO)
echo.

echo [5/6] Testando TypeControllerIntegrationTestFixed...
call mvn test -Dtest=TypeControllerIntegrationTestFixed -q
if %errorlevel% neq 0 (echo FALHOU) else (echo SUCESSO)
echo.

echo [6/6] Testando UserControllerIntegrationTestFixed...
call mvn test -Dtest=UserControllerIntegrationTestFixed -q
if %errorlevel% neq 0 (echo FALHOU) else (echo SUCESSO)
echo.

echo ========================================
echo VALIDACAO COMPLETA FINALIZADA
echo ========================================
