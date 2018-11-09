### Prerequisites
Install Mongo
```
docker pull mongo
docker run -p 27017:27017 --name hello-mongo -d -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=password -e MONGO_INITDB_DATABASE=quote1 mongo
```

### Example of queries

* `POST /quotes/bulk` [hestia](https://github.com/apulbere/hestia)

* fetch all
```
curl -X POST \
  http://localhost:8080/quotes/query \
  -H 'Postman-Token: eeaafb77-e4c4-4cd4-babb-0d9087163a2e' \
  -H 'cache-control: no-cache' \
  -d '{
	quotes {
		id
		source
		author
		text
	}
}'
```
* fetch by id
```
curl -X POST \
  http://localhost:8080/quotes/query \
  -H 'Postman-Token: 589bb6a3-2372-43db-8ca1-cdf25dd7e72a' \
  -H 'cache-control: no-cache' \
  -d '{
	quotes(id: "5bd33ab5f2bd89048a72f845") {
		id
		text
	}
}'
```