# Vagrant image for Jetse Java

## Prerequisites

Add the following entry to your `/etc/hosts` file:

    127.0.0.1 jetse-src jetse-tgt

Make sure this repository (`jetse`) is cloned in your workspace directory (usually `/opt/hawaii/workspace` on Linux/macOS).

Install the **latest** version of Vagrant and VirtualBox. If you're on Windows, you also need to install the winnfsd plugin for Vagrant:
                              
    $ vagrant plugin install vagrant-winnfsd

Note: older versions may not work.

## Installation

Make sure you are connected to the QNH vpn before you run vagrant up.

    $ cd /opt/hawaii/workspace/jetse/vagrant
    
    $ vagrant up


## See that it works 
After install you should be able to visit `http://jetse-src:7777`. 
