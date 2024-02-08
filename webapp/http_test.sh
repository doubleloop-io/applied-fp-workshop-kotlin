#!/bin/bash

echo "Ping server"
curl -i http://localhost:9000/hello/ping
echo ""
echo ""

echo "Execute mission: FF"
curl -i -H "Content-Type: application/json" -X POST -d '{"commands":"FF"}' http://localhost:9000/rover/mission
echo ""
echo ""

echo "Execute mission (hit obstacle): RFF"
curl -i -H "Content-Type: application/json" -X POST -d '{"commands":"RFF"}' http://localhost:9000/rover/mission
echo ""
echo ""

echo "Execute mission (invalid command): RXFF"
curl -i -H "Content-Type: application/json" -X POST -d '{"commands":"RXFF"}' http://localhost:9000/rover/mission
