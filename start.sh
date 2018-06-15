#!/usr/bin/env bash

# can point to specific version from docker-compose
MONGO_IMAGE=mongo_3_4

echo " + Start mongoDB "$MONGO_IMAGE

updateMongoStatus() {
while ! $(docker-compose logs $MONGO_IMAGE | grep -q "waiting for connections")
do
    sleep 1
done
START_MONGO=$?
}

rm -rf *.log

echo " + Stopping image "$MONGO_IMAGE
docker-compose stop $MONGO_IMAGE

echo " + Running db images"
docker-compose up -d $MONGO_IMAGE
sleep 1

updateMongoStatus

while test $START_MONGO -ne 0
do
    echo " + Waiting for start MongoDB"
    sleep 1
    updateMongoStatus
done
echo " + MongoDB is running"

#Create mongo users
docker exec -i $MONGO_IMAGE mongo admin << EOF > mongo.log 2>&1
db.createUser({'user': 'userAdmin', 'pwd': 'passAdmin', 'roles': [ { 'role': 'userAdminAnyDatabase', 'db': 'admin' } ] } );
db.auth('userAdmin', 'passAdmin');
use daas;
db.createUser({'user': 'testUser', 'pwd': 'password', 'roles': [ { 'role': 'readWrite', 'db': 'daas' }, { 'role': 'dbAdmin', 'db': 'daas' },  { 'role': 'clusterMonitor', 'db': 'admin' } ] });
db.auth('testUser', 'password');
db.test.insert({'name':'test db'});
EOF

echo " + All done"