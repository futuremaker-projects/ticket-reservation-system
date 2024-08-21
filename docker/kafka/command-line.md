도커에 접속하지 않고 생성하기

```vi
docker exec -it kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic sampleTopic

docker exec -it kafka kafka-console-producer.sh --topic sampleTopic --broker-list 0.0.0.0:9092
```

카프카 도커에 접속하여 생성하기

```vi
# Kafka 접속
docker exec -it kafka bash

# kafka 버전 확인
kafka-topics.sh --version
```

### 토픽
 
```vi
# 토픽 생성
kafka-topics.sh --create --topic <topic 명> --bootstrap-server localhost:9092



# 토픽 목록 조회
kafka-topics.sh --list --bootstrap-server localhost:9092

# 
```



```vi
# Producer
kafka-console-producer.sh --broker-list localhost:9092 --topic test_topic

# Consumer
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test_topic

# Consumer 초기화
```
