how to run the project - (05/19/2019)

publisher - 

step 1 - goto ~/IdeaProjects/devo-mqtt-publisher in git bash
step 2 - run ./mvnw compile exec:exec
this should start server on port 3001

broker - 

step 3 - goto C:\Program Files\mosquitto> in windows powershell
step 4 - run the command- mosquitto -v
this should start mosquitto broker on port 1883 for ipv4 and ipv6 sockets

step 5 - goto chromer and open the address - http://localhost:3001/publish
this should start the system and mqtt messages should start publishing.

note - if publisher complains that 3001 is being used, open task manager and delete java.exe processes