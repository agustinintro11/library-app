### Review Service

Para correr este repositorio necesitamos setear las variables de entorno:
* AUTH_SECRET
* AUTH_TOKEN_EXPIRATION
* env
* ORGANIZATION_SERVICE_URL
* USERS_SERVICE_URL
* CONNECTION_STRING
* BOOK_SERVICE_URL


Para correr el repositorio utilizamos el comando
bash
docker compose up


Para subir los cambios usamos:

docker build . -t juanbalian/reviews-service:{version}


Luego lo subimos con:

docker push juanbalian/reviews-service:{version}
