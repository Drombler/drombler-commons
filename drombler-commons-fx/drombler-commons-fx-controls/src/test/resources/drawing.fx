/*###################################################################
### This JavaFX document was generated by Inkscape
### http://www.inkscape.org
### Created: Sun Nov 19 22:56:15 2017
### Version: 0.91 r13725
#####################################################################
### NOTES:
### ============
### JavaFX information can be found at
### http://www.javafx.com/
###
### If you have any problems with this output, please see the
### Inkscape project at http://www.inkscape.org, or visit
### the #inkscape channel on irc.freenode.net . 
###
###################################################################*/


/*###################################################################
##   Exports in this file
##==========================
##    Shapes   : 2
##    Nodes    : 6
###################################################################*/


import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.scene.paint.*;



public class drawing extends CustomNode {
    /** path path3376 */
    function path3376() : Path {
        Path {
            id: "path3376"
            opacity: 1.00000000
            stroke: Color.rgb(0x00, 0x00, 0x00, 1.00000000)
            strokeWidth: 1.00000000
            strokeLineCap: StrokeLineCap.BUTT
            strokeLineJoin: StrokeLineJoin.MITER
            strokeMiterLimit: 4.00000000
            elements: [
                MoveTo {
                    x: 1.00000000
                    y: -15.00000000
                },
                LineTo {
                    x: 15.00000000
                    y: -1.00000000
                },
                LineTo {
                    x: 1.00000000
                    y: -15.00000000
                },
            ] // elements
        }; // Path
    } // end path path3376

    /** path path3378 */
    function path3378() : Path {
        Path {
            id: "path3378"
            opacity: 1.00000000
            stroke: Color.rgb(0x00, 0x00, 0x00, 1.00000000)
            strokeWidth: 1.00000000
            strokeLineCap: StrokeLineCap.BUTT
            strokeLineJoin: StrokeLineJoin.MITER
            strokeMiterLimit: 4.00000000
            elements: [
                MoveTo {
                    x: 15.00000000
                    y: -15.00000000
                },
                LineTo {
                    x: 1.00000000
                    y: -1.00000000
                },
                LineTo {
                    x: 15.00000000
                    y: -15.00000000
                },
            ] // elements
        }; // Path
    } // end path path3378

   override function create(): Node {
       Group {
           content: [
               path3376(),
               path3378(),
           ] // content
           transforms: Translate { x : 24.00000000, y : 40.00000000 }
       } // Group
   } // function create()
} // class drawing


/*###################################################################
### E N D   C L A S S    drawing
###################################################################*/


