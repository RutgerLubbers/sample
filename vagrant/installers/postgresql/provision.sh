#!/bin/bash

echo "Installing PostgreSQL"
apt-get -y install postgresql-9.5 postgresql-client-9.5 postgresql-contrib-9.5
sed -i -e "s/#listen_addresses = 'localhost'/listen_addresses = '*'/g" /etc/postgresql/9.5/main/postgresql.conf
sed -i -e 's#127.0.0.1/32#0.0.0.0/0#g' /etc/postgresql/9.5/main/pg_hba.conf
service postgresql restart
sudo -u postgres psql -U postgres -d postgres -c "alter user postgres with password 'postgres';"

# setup partner in balans database and user
sudo -u postgres createuser --login jetse
sudo -u postgres psql -c "ALTER USER jetse WITH PASSWORD 'jetse';"
sudo -u postgres createdb -E UTF-8 -O jetse jetse
