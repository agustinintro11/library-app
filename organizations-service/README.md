### Organizations service

Para correr este repositorio necesitamos setear las variables de entorno:
* PORT
* TIMEOUT
* ENV
* LOG_IMP
* AUTH_SECRET
* AUTH_TOKEN_EXPIRATION
* DB_USER
* DB_PASSWORD
* DB_NAME
* DB_HOST
* DB_TEST_USER
* DB_TEST_PASSWORD
* DB_TEST_NAME
* DB_TEST_HOST
* NEW_RELIC_AGENT_ENABLED
* NEW_RELIC_NO_CONFIG_FILE
* NEW_RELIC_APP_NAME
* NEW_RELIC_LICENSE_KEY


Para correr el repositorio utilizamos el comando
bash
docker compose up


Para subir los cambios usamos:

docker build . -t juanbalian/organizations-service:{version}


Luego lo subimos con:

docker push juanbalian/organizations-service:{version}


