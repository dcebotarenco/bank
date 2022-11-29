# bank
CQRS demo bank app

docker run -d -e AXONIQ_AXONSERVER_DEVMODE_ENABLED=true -p 8024:8024 -p 8124:8124 axoniq/axonserver

run query application
run command application
Make a command post in command application
Receive via Axon Server connector the event in query application and update the projection
Query the projection