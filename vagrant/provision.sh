#!/bin/bash

function warn {
  echo "$@" 1>&2
}

function do_install {
	if [ -n "$1" ]; then
		dos2unix -q /installers/${1}/provision.sh
    source /installers/${1}/provision.sh
	else
		warn do_install WHAT
		exit 1;
	fi
}

APT_CONF="/etc/apt/apt.conf.d"
CFG="01proxy"
function disable_apt_proxy {
	if [ -e "${APT_CONF}/${CFG}" ]; then
	  warn "Disabling apt proxy..."
	  mv ${APT_CONF}/${CFG} ~
	fi
}

function enable_apt_proxy {
	if [ -e ~/${CFG} ]; then
	  warn "Enabling apt proxy..."
	  mv ~/${CFG} ${APT_CONF}/${CFG}
	fi
}

#section "checking for apt-cacher proxy"
DEFAULT_GW=`netstat -rn | grep '^0.0.0.0' | awk '{print $2}'`

if [[ ${DEFAULT_GW} =~ ^[0-9]+\.[0-9]+\.[0-9]+\.[0-9]+$ ]]
then
  warn "Found default gateway: ${DEFAULT_GW}"

	if nc -z "${DEFAULT_GW}" 3142 -w 5; then
		warn "Configuring APT to user apt-cacher proxy"
		warn "  APT proxy configuration stored in file /etc/apt/apt.conf.d/01proxy"
		echo "Acquire::http::proxy \"http://${DEFAULT_GW}:3142\";" > /etc/apt/apt.conf.d/01proxy
	else
		warn "Could not find a local apt-get proxy."
	fi
fi
echo ""

echo "Starting provisioning script..."
sed -i 's/^mesg n/tty -s \&\& mesg n/g' /root/.profile
. /root/.profile
apt-get -y update
apt-get -y install dos2unix

echo "Adjusting permissions and ownerships..."
chown vagrant:vagrant /opt
chown vagrant:vagrant /opt/hawaii

do_install locale
do_install dependencies
do_install env
do_install apache
#do_install ldap
do_install postgresql
#do_install redis
#do_install java
#do_install node
do_install rinetd
#do_install ciam
do_install inbucket
