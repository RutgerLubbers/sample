# Jetse Java


## Project setup
The project currently has the following subdirectories:
- vagrant. The vagrant image that can be used during development. Has software like LDAP, Postgres, Redis, Tomcat and
Apache. Used during development.
- liquibase. Contains the database conversion scripts to automatically upgrade the database to the latest version.
Used during all development stages (dev/tst/stg/prd).
- frontend. The GUI of the application (not present)
- backend. The backend.

The artifactory can be found here:

## Run the stack

1. Edit `/etc/hosts` add:

````
     127.0.0.1 jetse-src jetse-tgt
````

2. VAGRANT 

````
    $ cd /opt/hawaii/workspace/jetse/vagrant
    $ vagrant up
````

3. BACKEND

````
    $ cd /opt/hawaii/workspace/jetse/backend
    $ sh gradlew bootRun
````

4. LIQUIBASE

````
    $ cd /opt/hawaii/workspace/jetse/liquibase
    $ sh gradlew update
````

5. FRONTEND

Not present.

````
    $ cd /opt/hawaii/workspace/jetse/frontend
     
    (do this regularly)
    $ npm prune && npm install
      
    $ npm start
````

## Code Style
For the backend we use the [Hawaii 2.0 code style formatting](https://github.com/hawaiifw/hawaii-framework/blob/master/src/eclipse/hawaii-framework-java-style.xml).
These code style rules can be imported in IntelliJ or Eclipse. IntelliJ cannot import all settings (unfortunately)
 
**Intellij Users:**
1. Import the formatting style: `Settings → Code Style → Java`, click `Manage`, and import the XML file by simply clicking `Import`.
2. Verify that the `Editor → Code Style → Right margin (columns)` is set to `140` and that the class count for regular and static `*` imports in `Editor > Code Style > Java > Imports` is set to `9999`.

## Architecture
The project will use the Hawaii framework (AKA Hawaii 2.0) as a base. All decisions that are taken in this project
regarding frameworks (such as Spring boot), integration testing (Spring mock MVC) are used until the team together
decides that there is a good reason to use something else. (most likely this results in a change in hawaii framework).
