# 3D-Scanner

In this project I built a 3D-Scanner by using an Arduino Mega 2560, a Lidar Lite v1 distance sensor and Fischertechnik pieces (similar to LEGO Technic) for the frame. From the center of a room, the scanner can create a pointcloud consisting of a couple of 100.000 points. The pointcloud can then be improved by running the measurment error correction program and converted into a .obj file. This .obj file can be opened by most 3D modeling softwares.

Explanation of different the different folders:

- /arduino: Contains the code for the Arduino Mega 2560 (Connected to a PC)
- /documentation: Contains the documentation of the project (in German)
- /geogebra: Contains GeoGebra files used in the documentation
- /images: Contains images of the scanner and of the scanned room
- /java: 
  - /3d_viewer: Contains self-written code for a 3D-Viewer that can display the raw pointclouds (.txt)
  - /converting_pointcloud_depthimage: Contanis self-written code to convert a raw pointcloud (.txt) to a depthimage (.png)
  - /converting_pointcloud_obj: Contains self-written code to convert a raw pointcloud (.txt) to a .obj file
  - /correction_of_measurment_errors: Contains self-written code to remove measurment errors from a raw pointcloud (.txt)
  - /scanner_control: Contains self-written code to control the 3D-Scanner (connected via USB)
-  /scanresults: Contains an example of a scan

The project took place from 2015 to 2016 and started as a school project (W-Seminar).
