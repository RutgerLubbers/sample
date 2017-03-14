#!/bin/bash

VERSION="1.2.0-rc1"
echo "Installing InBukcet ${VERSION}"


# Download
cd /tmp
wget --quiet --output-document inbucket_${VERSION}_linux_amd64.tar.gz https://dl.bintray.com/content/jhillyerd/golang/inbucket_${VERSION}_linux_amd64.tar.gz?direct

# Unzip
cd /opt
tar xzf /tmp/inbucket_${VERSION}_linux_amd64.tar.gz
ln -s inbucket_${VERSION}_linux_amd64/ inbucket

# Allow inbucket ports << 1024
apt-get -y install libcap2-bin
setcap 'cap_net_bind_service=+ep' /opt/inbucket/inbucket

# Configuration & create user
cd /opt/inbucket/etc/ubuntu
useradd -r -m inbucket
install -o inbucket -g inbucket -m 775 -d /var/opt/inbucket
touch /var/log/inbucket.log
chown inbucket: /var/log/inbucket.log
install -o root -g root -m 644 inbucket.logrotate /etc/logrotate.d/inbucket
install -o root -g root -m 644 inbucket.service /lib/systemd/system/inbucket.service
install -o root -g root -m 644 ../unix-sample.conf /etc/opt/inbucket.conf.original

dos2unix -q /installers/inbucket/inbucket.conf
install -o root -g root -m 644 /installers/inbucket/inbucket.conf /etc/opt/inbucket.conf


# Start services (upon boot too)
systemctl enable inbucket.service
systemctl start inbucket

rm /tmp/inbucket_${VERSION}_linux_amd64.tar.gz
