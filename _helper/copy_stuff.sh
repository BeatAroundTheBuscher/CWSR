#!/bin/bash

rm ~/Games/starsector/mods/cwsr -R
cp ~/dev/starsector/mymods/cwsr/out/production/cwsr ~/Games/starsector/mods/cwsr -R
mkdir ~/Games/starsector/mods/cwsr/jars
cp ~/dev/starsector/mymods/cwsr/out/artifacts/cwsr_jar/cwsr.jar ~/Games/starsector/mods/cwsr/jars
