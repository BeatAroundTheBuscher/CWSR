#!/bin/bash

MYPWD=(pdw)

rm -R ~/Games/starsector/mods/CWSR
cp -R ../cwsr ~/Games/starsector/mods/CWSR
cd ~/Games/starsector
./starsector.sh

cd $MYPWD
