# Proyecto PRG2022

Configuraciones de /src/main/resources/application.properties:

# Opciones a modificar DB
spring.datasource.url=jdbc:h2:file:./db21
spring.datasource.username=admin
spring.datasource.password=password

# Opciones a modificar App Encriptacion
eeae.app.jwtCookieName= eeae
eeae.app.jwtSecret= eeaeSecretKey
eeae.app.cryptos.password = ElpasswordDebeSerComplicadillo.!
eeae.app.cryptos.salt = EchaleSaltALasCastanias

# opciones que no necesitan ser modificadas pero si agregadas
eeae.app.jwtExpirationMs= 86400000
spring.jpa.properties.hibernate.show_sql=false
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto= update

spring.sql.init.mode=always
createDatabaseIfNotExist=true

spring.mvc.format.date=dd-MM-yyyy
spring.mvc.format.date-time=dd-MM-yyyy HH:mm:ss
spring.mvc.format.time=HH:mm:ss


# Opciones extra:
#logging.level.root=WARN
#logging.level.org.springframework.web=DEBUG
#logging.level.org.hibernate=ERROR
#logging.level.root=error
#spring.jpa.show_sql = false
#spring.jpa.show-sql=false 

#spring.h2.console.enabled=true
#spring.h2.console.path=/h2-ui

#server.ssl.key-store-type=PKCS12
#server.ssl.key-store=classpath:keystore/XXX.p12
#server.ssl.key-store-password=xxx
#server.ssl.key-alias=prg22

#trust.store=classpath:keystore/XXXXX.p12
#trust.store.password=xxx



