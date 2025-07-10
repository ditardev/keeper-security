#!/bin/bash
readonly container_name=keeper_security_c
readonly image_name=keeper_security_img

echo $container_name
echo $image_name

docker --version

if docker ps -a | grep -- $container_name > /dev/null
then
    docker stop $container_name
    docker rm $container_name
    docker rmi $image_name
else
    echo no such container
fi