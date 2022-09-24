# Fetch Rewards Take Home Project

## To run the project:
```
1. Run mvn clean install
2. Run src/main/java/com/fetch/rewards/RewardsApplication.java
```

## Dependencies:

1. Java 16
2. Maven

## API usage examples

### Add a transaction

`POST http://localhost:8080/transaction/add`

Request body:
```
{
    "payer": "DANNON",
    "points": 200,
    "timestamp": "2020-10-31T15:00:00Z"
}
```


### Spend points

`POST http://localhost:8080/points/spend`

Request body:
```
{
    "points": 5000
}
```

Response body:
```
[
    {
        "payer": "UNILEVER",
        "points": -200
    },
    {
        "payer": "MILLER COORS",
        "points": -4700
    },
    {
        "payer": "DANNON",
        "points": -100
    }
]
```

### Check points balance

`GET http://localhost:8080/points/balance`

Response body:

```
{
    "UNILEVER": 0,
    "MILLER COORS": 5300,
    "DANNON": 1000
}
```
