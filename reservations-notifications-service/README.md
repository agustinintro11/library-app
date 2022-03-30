### Reservation Notification Service

Para correr este repositorio necesitamos setear las variables de entorno:
* EMAIL_SERVICE_URL
* ENV
* LOG_IMP
* NEW_RELIC_AGENT_ENABLED
* NEW_RELIC_APP_NAME
* NEW_RELIC_LICENSE_KEY
* NEW_RELIC_NO_CONFIG_FILE
* ORGANIZATION_SERVICE_URL
* PORT
* TIMEOUT
* RESERVATIONS_SERVICE_URL
* USERS_SERVICE_URL


Para correr el repositorio utilizamos el comando
bash
docker compose up


Para subir los cambios usamos:

docker build . -t juanbalian/{nombre-servicio}:{version}


Luego lo subimos con:

docker push juanbalian/{nombre-servicio}:{version}
