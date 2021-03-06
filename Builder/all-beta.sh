#!/usr/bin/env bash

cd ../

./gradlew --no-daemon incVersion
./gradlew --no-daemon clean assembleBetaRelease copyApks -Pbeta

cd Builder
./remove_all.sh
./install_all.sh
./clear-cache.sh