@echo off

echo ==========================================
echo DIMDIM - PROVISIONAMENTO E DEPLOY NO AZURE
echo ==========================================

set RESOURCE_GROUP=rg-dimdim
set LOCATION=brazilsouth
set PLAN_NAME=plan-dimdim
set APP_NAME=dimdim-hpecora-556612
set SQL_SERVER=sql-dimdim-rm556612
set DATABASE_NAME=dimdimdb

echo.
echo 1. Criando/validando Resource Group...
az group create ^
  --name %RESOURCE_GROUP% ^
  --location %LOCATION%

echo.
echo 2. Criando/validando App Service Plan...
az appservice plan create ^
  --name %PLAN_NAME% ^
  --resource-group %RESOURCE_GROUP% ^
  --location %LOCATION% ^
  --sku F1 ^
  --is-linux

echo.
echo 3. Criando/validando Web App...
az webapp create ^
  --resource-group %RESOURCE_GROUP% ^
  --plan %PLAN_NAME% ^
  --name %APP_NAME% ^
  --runtime "JAVA:17-java17"

echo.
echo 4. Configurando Java 17...
az webapp config set ^
  --resource-group %RESOURCE_GROUP% ^
  --name %APP_NAME% ^
  --linux-fx-version "JAVA|17-java17"

echo.
echo 5. Banco de dados utilizado:
echo Servidor: %SQL_SERVER%
echo Banco: %DATABASE_NAME%
echo.
echo O Azure SQL deve ser criado previamente com credenciais seguras.
echo As credenciais reais NAO devem ficar neste script.
echo Configure no App Service as variaveis:
echo DB_USER
echo DB_PASSWORD

echo.
echo 6. Gerando o arquivo JAR...
call mvnw.cmd clean package -DskipTests

echo.
echo 7. Fazendo deploy no Azure...
az webapp deploy ^
  --resource-group %RESOURCE_GROUP% ^
  --name %APP_NAME% ^
  --src-path target\dimdim-0.0.1-SNAPSHOT.jar ^
  --type jar

echo.
echo 8. Reiniciando o Web App...
az webapp restart ^
  --resource-group %RESOURCE_GROUP% ^
  --name %APP_NAME%

echo.
echo ==========================================
echo DEPLOY FINALIZADO
echo ==========================================
echo https://%APP_NAME%.azurewebsites.net