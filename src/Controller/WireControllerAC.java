//Package of the class.
package Controller;

//Importing classes from the packages View and Model.

import Model.VoltageDropDataDC;
import View.FrameVoltageDrop;

/**
 * Controller class to re-calculate the data of the frame when the material value is changed.
 * @author : Jaume V.
 * @version : 18/07/2016/A
 */
public class WireControllerAC
{
    //Frame of the application and data model variables.
    private FrameVoltageDrop frame;
    private VoltageDropDataDC data;

    /**
     * Constructor to initiating the wire controller.
     * @param objectFrame : Frame object. Frame of the application.
     */
    public WireControllerAC(FrameVoltageDrop objectFrame)
    {
        //The constructor received the frame of the application.
        this.frame=objectFrame; //The variable get the frame of the application.

        //Initiating the data object.
        data=new VoltageDropDataDC();
    }
    //End of the constructor MaterialControllerDC().

    /**
     * Class to update the fields that depends of the new value of the voltage.
     */
    public void updateFrame()
    {
        //****************************************************************************************
        // UPDATING.
        // 1-POWER SUPPLY PANEL.
        //****************************************************************************************
        data.setVoltage(Double.valueOf(frame.getJPanelPowerSupplyAC().getVoltage())); //Voltage.
        data.setCurrent(Double.valueOf(frame.getJPanelPowerSupplyAC().getCurrent())); //Current.
        data.setPower(Double.valueOf(frame.getJPanelPowerSupplyAC().getPower())); //Power

        //****************************************************************************************
        // UPDATING.
        // 2-MATERIAL AND DROP VOLTAGE.
        //****************************************************************************************
        data.setMaterial(frame.getJPanelMaterial_VoltageDropAC().getMaterial()); //Material of the wire.
        data.setVoltageDrop(Double.valueOf(frame.getJPanelMaterial_VoltageDropAC().getVoltageDrop())); //Drop voltage.
        data.setVoltageDropPercentage(data.calculateVoltageDropPercentage()); //Percentage of the drop voltage.

        //****************************************************************************************
        // UPDATING.
        // 3-WIRE.
        //****************************************************************************************
        data.setSection(Double.valueOf(frame.getJPanelWiresAC().getSection())); //Section of the wire.
        frame.getJPanelWiresAC().setLength(String.valueOf(data.calculateLength())); //Length of the wire.

    }
    //End of the method to update the frame updateFrame().
}
//End of the class WireControllerDC().

