# Picklist Editor, a tool to easily display and modify pick lists for proteomics

## Introduction to "pick lists"

When you work with 2D gel electrophoresis in proteomics, you finally get a gel with proteins inside. Those proteins are somhow labelled and the gel can be scanned (like when you scan a paper) and you got an image with spots representing those proteins (see figure below). After spot detection (green dots in the figure below), analysis and comparison between experimental conditions, you get a list of interesting spots you would like to identify. These spots are represented by yellow circles in the figure below.

[screenshot]: https://raw.githubusercontent.com/jepoirrier/PickListEditor/master/screenshots/gel-example.jpg "Example of a gel image from a 2D gel electrophoresis experiment. Proteins are separated horizontally following their isoelectric point (electrical charge), between pH 4 and 7. Proteins are separated vertically following their molecular weight (heavier proteins on top, lighter proteins in the bottom).Spots automatically detected by software (DeCyder) are marked by green dots. Interesting spots have a yellow circle. Some of the proteins contained in thoses spots are already identified; in that case, the spot also has a label."

Proteins are separated horizontally following their isoelectric point (electrical charge), between pH 4 and 7. Proteins are separated vertically following their molecular weight (heavier proteins on top, lighter proteins in the bottom). Spots automatically detected by software (DeCyder) are marked by green dots. Interesting spots have a yellow circle. Some of the proteins contained in thoses spots are already identified; in that case, the spot also has a label.

In order to "pick" a plug containing proteins out of each spot, you need to give a robot, called "spot picker", the coordinates of all these interesting spots/proteins. In GE Healthcare systems, these coordinates are computed according to two "picking references". These references are simply small, round stickers we put at a specific location. The analysis software detects these references and then computes the spot coordinates. When we give the gel to the spot picker robot, it detects the picking references and pick plugs of gel according to the given spot coordinates. This is very useful when proteins/spots are labelled with fluorescent dyes or radioactive probes since they are not visible for a human eye or the robot camera (picking references are visible in fluorescence, although not fluorescent by themselves, and for a human eye, the robot camera, etc.).

A "pick list" is a tab-delimited text file containing the coordinates of the picking references and all the interesting spots.

Do you want more information about this, pictures and all? Then have a look at these blog entries:

* [Picklist Editor 0.1](https://jepoirrier.org/2007/07/26/picklist-editor-01/)
* [The hardware side of Picklist Editor 0.1](https://jepoirrier.org/2007/07/26/the-hardware-side-of-picklist-editor-01/)
* [Picklist Editor 0.2](https://jepoirrier.org/2007/07/28/picklist-editor-02/)

## Introduction to Picklist Editor

For a human, a list of coordinates has less meaning than the actual location of these spots on an image. Picklist Editor creates this image for you, based on the pick list you give.

Sometimes (when you didn't pay attention in your analysis!), the picking references are in the wrong order: number 2 before number 1, resulting in a mirror image of spot locations. PickList Editor can automaticallly invert the picking references and modify all the spot coordinates according to the new references. You can of course save this new pick list to feed your robot.

Picklist Editor is now able to display and edit each spot location. This behaviour is not recommended at all. But it can give interesting results, especially when you deal with low molecular weight proteins labelled with Cy and other fluorescent dyes (since the actual spot position reflects the position of the small percentage of labelled proteins and not the bulk of un-labelled proteins which are a bit lighter).

Thus the goal of Picklist Editor is to display and modify pick lists before spot picking.

Features:

* Works with any pick list in the GE Healthcare format (can work with other format provided you send me a sample file)
* Displays the actual location of picking references and proteins of interest on a virtual gel
* Displays a table of the actual location of all spots
* Allows you to edit the spot number and coordinates
* Allows you to invert the picking references and modify all the spot coordinates accordingly
* Allows you to save your pick list (via the File menu or Ctrl + S)
* You can run this software under MS-Windows, MacOS and GNU/Linux, provided you have the Java Runtime Engine, version 6 and above (free download)

## Screenshot

Screenshots can be found in screenshots/

## Software

Download the latest version (0.2) of Picklist Editor in the bin/ directory.

The link above gives you a JAR file (the software you'll use). You can run this software under MS-Windows, MacOS and GNU/Linux, provided you have the Java Runtime Engine, version 6 and above. You can download it for free and install it.

This software is released under the GNU General Public Licence (GPL): Java source code is in the src/ and now includes an example of picklist.

Copyright (C) belongs to Jean-Etienne Poirrier, 2007-2016. You can contact me at jepoirrier at gmail.com. Please report if you have any problem, comment, if you would like new features in this software or simply if you like the software :-).

## Usage

The software is so simple that there should be no problem to use it ... You can launch Picklist Editor by double-clicking on the PicklistEditor.jar file (you can also enter this command in a shell: "java -jar PicklistEditor.jar"). Then you can open your pick list with Ctrl + O or via the File menu. Picking references and spots are displayed. In the control box below, you can check ad hoc checkboxes in order to see (or not) picking references or spot numbers. You can also change the radius of the spots (if you find they are too small or too big). The button on the bottom right allows to invert picking references. The new layout is then displayed (but not saved).

Once a pick list has been opened, it can be saved (even if you didn't modify anything). Use the Save and Save As items in the File menu.

## Improvements

There are some improvements that could be implemented:

* if users or companies provide me with other pick list formats (or example files), I can write functions to open and display those pick lists. Moreover, it could be interesting to write functions to convert coordinates from one format to the other.
* ... (any other suggestions welcome)

If you really want these improvements, please let me know (see the Issues tab above).

## Changelog (history)

### Migration

Migrated to Github in April 2016.

### Version 0.2 (July 28th, 2007)

* Added a table of spots on the right of the window
* Added the opportunity to edit the spot (double click on a cell and apply changes)
* Completely documented code (see JavaDoc)
* Added an example of picklist in the code archive

### Version 0.1 (July 26th, 2007)

* Original release

