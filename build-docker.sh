#!/bin/sh
#cd /home/www
#pwd
#git pull
docker stop um-java-ws
docker rm um-java-ws
docker rmi um-java-ws:develop
docker build . -t um-java-ws:develop
docker run -itd --name um-java-ws -v /Users/olie/SemicolonProjects/um-java-ws/project/src/main/resources:/root/resources -e "RESOURCES_LOCATION=/root/resources" -e "SPRING_PROFILES_ACTIVE=dev" --restart unless-stopped -p 8080:8080 um-java-ws:develop .
sleep 20s
docker logs um-java-ws
