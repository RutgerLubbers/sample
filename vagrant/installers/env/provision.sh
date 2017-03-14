#!/bin/bash

# echo "Setting up environment variables"

echo "Setting up /etc/hosts file"
cat >> /etc/hosts << EOF
127.0.0.1   jetse-src jetse-tgt
EOF
