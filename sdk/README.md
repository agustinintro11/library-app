### Reservation SDK

Para usarlo se debe importar al archivo index.js, y crear una instancia de objeto Sdk.

```
sdk = new Sdk(nombre_org, email, password)
```

luego inicializar el sdk

```
await sdk.init()
```

y se podra hacer una llamada a al range query de reservations con el siguiente metodo

```
const reservations = await sdk.rangeQuery(isbn, startDate, endDate, page, limit)
```
