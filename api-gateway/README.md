### API Gateway

Para correr este repositorio necesitamos setear las variables de entorno:
* AUTH_SECRET
* AUTH_TOKEN_EXPIRATION
* BOOKS_SERVICE_URL
* EMAIL_SERVICE_URL
* LOG_IMP
* NEW_RELIC_AGENT_ENABLED
* NEW_RELIC_APP_NAME
* NEW_RELIC_LICENSE_KEY
* NEW_RELIC_NO_CONFIG_FILE
* ORGANIZATIONS_SERVICE_URL
* PORT
* RESERVATIONS_SERVICE_URL
* REVIEWS_SERVICE_URL
* TIMEOUT
* USERS_SERVICE_URL


Para correr el repositorio utilizamos el comando
bash
docker compose up


Para subir los cambios usamos:

docker build . -t juanbalian/api-gateway-service:{version}


Luego lo subimos con:

docker push juanbalian/api-gateway-service:{version}
