#!/bin/bash

echo "Setting system locale"
dos2unix -q /installers/locale/locale.sh
cp /installers/locale/locale.sh /etc/default/locale
locale-gen en_US.UTF-8
timedatectl set-timezone Europe/Amsterdam
