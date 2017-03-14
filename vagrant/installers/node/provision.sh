#!/bin/bash

echo "Installing Node.js"

disable_apt_proxy

cat > /etc/apt/sources.list.d/nodesource.list << EOF
deb https://deb.nodesource.com/node_6.x xenial main
deb-src https://deb.nodesource.com/node_6.x xenial main
EOF
curl -s https://deb.nodesource.com/gpgkey/nodesource.gpg.key | apt-key add -
apt-get update
apt-get -y install nodejs
npm install -g npm-check-updates
npm install -g grunt-cli
npm install -g angular-cli@1.0.0-beta.26

enable_apt_proxy
