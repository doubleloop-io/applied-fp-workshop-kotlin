#!/bin/bash

echo "Ping server"
curl http://localhost:9000/hello/ping
echo ""
echo ""

echo "Execute mission"
curl http://localhost:9000/rover/FF -X POST
echo ""
echo ""

echo "Execute mission (hit obstacle)"
curl http://localhost:9000/rover/RFF -X POST
