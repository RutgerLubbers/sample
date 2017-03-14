#!/bin/bash

echo "Installing Apache HTTP server"
apt-get -y install apache2 apache2-bin pkg-config apache2-dev
service apache2 stop
mkdir -p /opt/hawaii/apache24
cd /opt/hawaii/apache24
mkdir logs conf
ln -s /usr/lib/apache2/modules modules
cp /installers/apache/mime.types /opt/hawaii/apache24/conf/mime.types
chown -R vagrant:vagrant /opt/hawaii/apache24
cp /installers/apache/apache2.conf /etc/apache2/apache2.conf
chown vagrant:vagrant /etc/apache2/apache2.conf
sed -i -e 's#export APACHE_PID_FILE.*$#export APACHE_PID_FILE=/opt/hawaii/apache24/logs/apache.pid#g' /etc/apache2/envvars
service apache2 start
