```
docker-compose exec kafka kafka-topics --bootstrap-server localhost:9092 --topic board-init --create --partitions 5 --replication-factor 1
docker-compose exec kafka kafka-topics --bootstrap-server localhost:9092 --topic column-init --create --partitions 5 --replication-factor 1
docker-compose exec kafka kafka-topics --bootstrap-server localhost:9092 --topic row-init --create --partitions 5 --replication-factor 1
```

```
curl --location --request POST 'localhost:8080/api/boards'
```