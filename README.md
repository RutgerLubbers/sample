# Jetse Java

## Prerequisites

1. JDK (>= 1.8)
1. Java IDE ([SpringToolSuite](https://spring.io/tools) / [IntelliJ](https://www.jetbrains.com/idea/) / ....)
1. VirtualBox (>= 5.1.10)
1. Vagrant (>= 1.9.1)

## Checkout project
Checkout the project in `/opt/hawaii/workspace`. Do a global search replace to edit paths (most likely only in `vagrant/Vagrantfile`).

## Project setup
The project currently has the following subdirectories:
- vagrant. The vagrant image that can be used during development. Has software like LDAP, Postgres, Redis, Tomcat and
Apache. Used during development.
- liquibase. Contains the database conversion scripts to automatically upgrade the database to the latest version.
Used during all development stages (dev/tst/stg/prd).
- frontend. The GUI of the application (not present)
- backend. The backend.

## Run the stack

#### 1. Edit `/etc/hosts` add:

````
     127.0.0.1 jetse-src jetse-tgt
````

#### 2. VAGRANT 
##### 2.1. Vagrant NFS
###### Windows
For setting up NFS with windows:
   
````
    $ cd /opt/hawaii/workspace/jetse/vagrant
    $ vagrant plugin install vagrant-winnfsd
````

###### Linux / Mac
For disabling the permission prompt for creating NFS mounts, see [NFS](https://www.vagrantup.com/docs/synced-folders/nfs.html). 

Basically:
    
````
    $ sudo mkdir -p /etc/sudoers.d
````
Create file `/etc/sudoers.d/vagrant-syncedfolders` with contents:

````
    Cmnd_Alias VAGRANT_EXPORTS_ADD = /usr/bin/tee -a /etc/exports
    Cmnd_Alias VAGRANT_NFSD = /sbin/nfsd restart
    Cmnd_Alias VAGRANT_EXPORTS_REMOVE = /usr/bin/sed -E -e /*/ d -ibak /etc/exports
    %admin ALL=(root) NOPASSWD: VAGRANT_EXPORTS_ADD, VAGRANT_NFSD, VAGRANT_EXPORTS_REMOVE
````

##### 2.2. Bring up vagrant
    

````
    $ cd /opt/hawaii/workspace/jetse/vagrant
    $ vagrant up
````

#### 3. BACKEND

````
    $ cd /opt/hawaii/workspace/jetse/backend
    $ sh gradlew bootRun
````

#### 4. LIQUIBASE

````
    $ cd /opt/hawaii/workspace/jetse/liquibase
    $ sh gradlew update
````

