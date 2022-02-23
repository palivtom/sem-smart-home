# HELP of the smart-home project.

Compile and run the project. When it is running, following commands are available:

## Config commands 
### import [params]
#### Params:
-path	        Path to input file. Loads smart home configuration to run the simulation on.

#### Example:
import -path=export.json

### export [params]
#### Info
Exports the last simulation.

#### Params:
-path	 	    Required. Path of file to export into.

-format         Format of the exported file. Default JSON. Available: JSON, XML (not implemented).

#### One of these params has to be added:
-config	        Exports current house config.

-message        Exports actions of past simulation

-log            Exports logs of past simulation

-consumption	Exports consumption of past simulation

#### Example
export -path=export.json -config

### add \<what> [params]
#### What: Floor
#### Params:
-name           Name of the floor

#### Example
add floor -name=Prizemi

#### What: Room
#### Params
-name           Name of the room

-fId            Required. Id of the floor in which the room is created.

#### Example
add room -name=Kuchyne -fId=0

#### What:Device
#### Params
-rId		    Required. Id of the room in which the device is created.

-type		    Required. Type of the device. Available: DOOR, GARDEN_SPRINKLER, SMART_PHONE, WASHING_MACHINE, WEATHER_STATION, WINDOW, ELECTRIC_TOOTHBRUSH, KETTLE.

-cu             Required. Consumption when device is UP.

-ci             Required. Consumption when device is IDLE.

-cp             Required. Consumption when device is PROCESSING.

#### Example
add device -rId=0 -type=WASHING_MACHINE -ci=5 -cp=5 -cu=6

#### What:LivingBeing
#### Params
-rId		    Required. Id of the room in which the device is created.

-name           Name of the livingBeing.

-type		    Required. Type of the livingBeing. Available: HUMAN, CAT, DOG.

#### Example
add livingBeing -rId=0 -type=DOG -name=Flicek

#### What:Item
#### Params
-rId		    Required. Id of the room in which the device is created.

-type		    Required. Type of the item. Available: SKI, BICYCLE.

#### Example
add item -rId=0 -type=SKI

## Simulation commands
### run [params]

Starts simulation with given params.

#### Params
-n              Number of cycles (default is 100).

## Utility
exit            Exits the program