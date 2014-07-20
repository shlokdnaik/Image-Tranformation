Image-Tranformation
===================
The input to your program will take four parameters where
 The first parameter is the name of the image, which will be provided in an 8 bit per channel RGB format (Total 24 bits per pixel). You may assume that all images will be of the same size for this assignment, more information on the image format will be placed on the DEN class website
 The second parameter will be a scale value. This will control by how much the image has to be scaled. It will be a floating point number, such as 0.5 or 1.2 and so on. Effectively, this will result in re-sampling your image.
 The third parameter will be an angle (given in degrees) that will suggest by how much the image has to be rotated clockwise (about its center). The details for the rotation equations are given below.
 The fourth parameter will be a boolean value (0 or 1) suggesting whether or not you want to deal with aliasing. A 0 signifies do nothing (aliasing will remain in your output) and a 1 signifies that anti-aliasing should be performed.

To invoke your program we will compile it and run it at the command line as

YourProgram.exe C:/myDir/myImage.rgb S R A

where S R A are the parameters as described above. 
