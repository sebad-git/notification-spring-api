# Despachador de notificaciones (Spring-Boot/Docker)

## Deploy
``` shell
podman compose -p nofitication-system up -d
```

## Curl
###### api key: seba-123
``` shell
postman request POST 'http://localhost:9084/api/notifications/add' \
  --header 'Content-Type: application/json' \
  --header 'x-api-key: seba-123' \
  --body '{
    "recipient":"Test Recipient",
    "channel":"SERVICE",
    "subject":"Test",
    "body":"This is the body",
    "priority":"MEDIUM",
    "metadata":{
        "data":"test"
    }
}' \
  --auth-apikey-key 'x-api-key' \
  --auth-apikey-value 'seba-123'
```

## Precondiciones
###### api key: seba-123
* Instalado podman o docker.
* Opcional para correr el jar desde un ide 
    * Java 22
    * Maven

## Decisiones de diseño
* Considere usar un Thread pool para evitar otro contenedor pero afecta escalabilidad y rendimiento.
* Considere usar Kafka pero por razones de tiempo fui con RabbitMQ que me es familiar.

## Consideración sobre Jakarta
###### Dentro del pom hay comentado el codigo para deployar en wildfly junto con un compose y Dockerfile.
###### el cambio pricipal seria quitar el tomcat integrado y crear un war en vez de jar.



