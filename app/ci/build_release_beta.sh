#!/bin/bash

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd ${CURRENT_DIR}
source ./android_home.sh

cd ../../
./gradlew -PtestfairyChangelog="$1" testfairyDebug
