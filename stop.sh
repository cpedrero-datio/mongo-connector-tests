#!/bin/bash
#abort on errors
set -e
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo " + Stopping all services"
docker-compose down

for z in ${SCRIPT_DIR}/*.log; do
        rm -f "$z" || continue
        echo "Removing logs: $z "
done