//Package of the class.
package Main;

//Importing  the class of the frame.
import View.*;

//Import the API class of JAVA.
import javax.swing.*;

/**
 * Main class to run the drop voltage application.
 * @author : Jaume V.
 * @version : 01/02/2017
 */
public class Main
{
        /**
         * Main class to start the application.
         */
        public static void main(String[] args)
        {
            //Creating the frame of the application
            FrameVoltageDrop frame=new FrameVoltageDrop();
            frame.setVisible(true);

            //Closing the frame.
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        //End of the main class.
}
//End of the Main().
