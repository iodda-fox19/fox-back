# Fox back service
## Локальный запуск
### Необходимо:
* Запущенный Postgres на порту 4532
### Запуск
* Запускать с профилем local
* При необходимости оверрайднуть порт на отличный от 8080
## Деплой 
* Обновить версию (пока в ручную)
* выполнить gradle таску jib
``./gradlew jib``
* зайти на хост через ssh </p>
``ssh root@hostname``
 конфиги для хоста (https://my.firstvds.ru/)
* выкачать имедж из докера
``docker pull mghost/fox:${Version}``
* запустить контейнер
``docker run -d -p 8081:8081 ${ImageId}``
## SWAGGER
http://213.159.209.245:8081/swagger-ui.html