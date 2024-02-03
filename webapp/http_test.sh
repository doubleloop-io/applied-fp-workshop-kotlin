#!/bin/bash

echo "Ping server"
curl http://localhost:9000/hello/ping
echo ""
echo ""

echo "Execute mission: FF"
curl -H "Content-Type: application/json" -X POST -d '{"commands":"FF"}' http://localhost:9000/rover/mission
echo ""
echo ""

echo "Execute mission (hit obstacle): RFF"
curl -H "Content-Type: application/json" -X POST -d '{"commands":"RFF"}' http://localhost:9000/rover/mission
echo ""
echo ""

echo "Execute mission (invalid command): RXFF"
curl -H "Content-Type: application/json" -X POST -d '{"commands":"RXFF"}' http://localhost:9000/rover/mission
