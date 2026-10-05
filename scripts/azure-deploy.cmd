@echo off

echo ================================
echo DIMDIM - DEPLOY AZURE
echo ================================

set RESOURCE_GROUP=rg-dimdim
set APP_NAME=dimdim-hpecora-556612
set PLAN_NAME=plan-dimdim
set LOCATION=brazilsouth

echo.
echo 1. Criando/validando App Service Plan...
az appservice plan create ^
  --name %PLAN_NAME% ^
  --resource-group %RESOURCE_GROUP% ^
  --location %LOCATION% ^
  --sku F1 ^
  --is-linux

echo.
echo 2. Criando/validando Web App...
az webapp create ^
  --resource-group %RESOURCE_GROUP% ^
  --plan %PLAN_NAME% ^
  --name %APP_NAME% ^
  --runtime "JAVA:17-java17"

echo.
echo 3. Configurando Java 17...
az webapp config set ^
  --resource-group %RESOURCE_GROUP% ^
  --name %APP_NAME% ^
  --linux-fx-version "JAVA|17-java17"

echo.
echo 4. Gerando o arquivo JAR...
call mvnw.cmd clean package -DskipTests

echo.
echo 5. Fazendo deploy no Azure...
az webapp deploy ^
  --resource-group %RESOURCE_GROUP% ^
  --name %APP_NAME% ^
  --src-path target\dimdim-0.0.1-SNAPSHOT.jar ^
  --type jar

echo.
echo Deploy concluido.
echo https://%APP_NAME%.azurewebsites.net