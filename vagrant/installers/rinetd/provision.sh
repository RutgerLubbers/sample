#!/bin/bash

echo "Installing rinetd"
# settings
placeholder="DEFAULT_GW_IP"

echo "Trying to determine the default gateway..."
defaultgw=`netstat -rn | grep '^0.0.0.0' | awk '{print $2}'`

if [[ $defaultgw =~ ^[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+$ ]]
then
  echo "Found default gateway: "$defaultgw
  echo "Installing rinetd..."
  apt-get install -y rinetd
  service rinetd stop

  echo "Updating rinetd.conf file with the template file in /installers/rinetd"
  dos2unix -q /installers/rinetd/rinetd.conf
  sed -e "s/DEFAULT_GW_IP/${defaultgw}/g" /installers/rinetd/rinetd.conf > /etc/rinetd.conf

  echo "Restarting rinetd..."
  service rinetd start
else
  echo "ERROR: Could not determine the default gateway (i.e. IP of your host machine)." >&2
fi
