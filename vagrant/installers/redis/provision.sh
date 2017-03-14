#!/bin/bash

echo "Installing Redis Cluster"
apt-get -y install redis-server redis-tools redis-sentinel
service redis-server stop
service redis-sentinel stop
rm -rf /lib/systemd/system/redis-server.service /lib/systemd/system/redis-sentinel.service /etc/init.d/redis-server /etc/init.d/redis-sentinel
mkdir -p /var/lib/redis/server1 /var/lib/redis/server2
chown -R redis:redis /var/lib/redis
rm -rf /etc/redis/redis.conf /etc/redis/sentinel.conf
cp /installers/redis/redis-server*.conf /installers/redis/redis-sentinel*.conf /etc/redis
chown -R redis:redis /etc/redis/redis-*conf
dos2unix -q /etc/redis/redis-*conf
cp /installers/redis/redis-*.service /lib/systemd/system
dos2unix -q /lib/systemd/system/redis-*.service
systemctl daemon-reload
echo "vm.overcommit_memory = 1" > /etc/sysctl.d/61-redis.conf
sysctl -p /etc/sysctl.d/61-redis.conf
sed -i -e 's/exit 0//g' /etc/rc.local
echo "echo never > /sys/kernel/mm/transparent_hugepage/enabled" >> /etc/rc.local
echo "exit 0" >> /etc/rc.local
chmod +x /etc/rc.local
/etc/rc.local
systemctl enable redis-server1
systemctl enable redis-server2
systemctl enable redis-sentinel1
systemctl enable redis-sentinel2
systemctl enable redis-sentinel3
systemctl start redis-server1
systemctl start redis-server2
systemctl start redis-sentinel1
systemctl start redis-sentinel2
systemctl start redis-sentinel3
