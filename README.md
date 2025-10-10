# KinoXP

Biograf administrationssystem bygget med Spring Boot og MySQL.

## Kom i gang

Start applikationen med Docker:

```bash
docker compose up --build -d
```

Hvis du får permission error, prøv:

```bash
sudo docker compose up --build
```

Applikationen kører på http://localhost:8081 når den er startet.

## Test applikationen

Tjek om den kører:
```bash
curl http://localhost:8081
```


Se filmvisninger:
```bash
curl http://localhost:8081/api/screenings
```

Dagens visninger:
```bash
curl http://localhost:8081/api/scheduling/today
```

Ugens visninger:
```bash
curl http://localhost:8081/api/scheduling/week
```

Se sale:
```bash
curl http://localhost:8081/api/scheduling/theatres
```

Ledige pladser for en bestemt visning:
```bash
curl http://localhost:8081/api/screenings/1/available-seats
```

## Stop applikationen

```bash
docker compose down
```

## Fejlfinding

Hvis Docker brokker sig over permissions:
```bash
sudo usermod -aG docker $USER
```
Derefter log ud og ind igen.

Hvis port 8081 er optaget:
```bash
sudo lsof -i :8081
```

Se logs hvis noget går galt:
```bash
docker compose logs app
docker compose logs mysql
```

## DB - SQL

Database DDL ligger i resorces mappen.

## Projektstruktur

- `src/main/java/com/example/kinoxp/controller/` - Web controllers
- `src/main/java/com/example/kinoxp/RestController/` - REST API
- `src/main/java/com/example/kinoxp/model/` - Database modeller
- `docker-compose.yml` - Docker konfiguration
- `.env` - Environment variabler
