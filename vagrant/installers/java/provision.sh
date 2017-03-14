#!/bin/bash

echo "Installing Java 8"
add-apt-repository -y ppa:webupd8team/java
apt-get update
echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections


echo " Installing dependencies."
apt-get -y install $(apt-cache depends oracle-java8-installer | grep -E "Depends|Recommends" | grep -v '<' | sed "s/.*ends:\ //" | tr '\n' ' ')
echo " Done installing dependencies."

disable_apt_proxy

apt-get -y install oracle-java8-installer

enable_apt_proxy
