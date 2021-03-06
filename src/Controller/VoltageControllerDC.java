//Package of the class.
package Controller;

//Importing classes from the packages View and Model.

import Model.VoltageDropDataDC;
import View.FrameVoltageDrop;

/**
 * Controller class to re-calculate the data of the frame when the voltage value is changed.
 * @author : Jaume V.
 * @version : 18/07/2016/A
 */
public class VoltageControllerDC
{
    //Frame of the application and data model variables.
    private FrameVoltageDrop frame;
    private VoltageDropDataDC data;

    /**
     * Constructor to initiating the voltage controller.
     * @param objectFrame : Frame object. Frame of the application.
     */
    public VoltageControllerDC(FrameVoltageDrop objectFrame)
    {
        //The constructor received the frame of the application.
        this.frame=objectFrame; //The variable get the frame of the application.

        //Initiating the data object.
        data=new VoltageDropDataDC();
    }
    //End of the constructor VoltageControllerDC().

    /**
     * Class to update the fields that depends of the new value of the voltage.
     */
    public void updateFrame()
    {
        //****************************************************************************************
        // UPDATING.
        // 1-POWER SUPPLY PANEL.
        //****************************************************************************************
        data.setVoltage(Double.valueOf(frame.getJPanelPowerSupplyDC().getVoltage())); //Voltage.
        data.setCurrent(Double.valueOf(frame.getJPanelPowerSupplyDC().getCurrent())); //Current.
        frame.getJPanelPowerSupplyDC().setPower(String.valueOf(data.calculatePower())); //Power
        frame.getJPanelPowerSupplyDC().setVoltage(String.valueOf(data.getVoltage())); //Voltage.

        //****************************************************************************************
        // UPDATING.
        // 2-MATERIAL AND DROP VOLTAGE.
        //****************************************************************************************
        data.setMaterial(frame.getJPanelMaterial_VoltageDropDC().getMaterial()); //Material of the wire.
        data.setVoltageDrop(Double.valueOf(frame.getJPanelMaterial_VoltageDropDC().getVoltageDrop())); //Drop voltage.
        frame.getJPanelMaterial_VoltageDropDC().setVoltageDropPercentage(String.valueOf(data.
                calculateVoltageDropPercentage())); //Percentage of the drop voltage.

        //****************************************************************************************
        // UPDATING.
        // 3-WIRE.
        //****************************************************************************************
        data.setSection(Double.valueOf(frame.getJPanelWiresDC().getSection())); //Section of the wire.
        frame.getJPanelWiresDC().setLength(String.valueOf(data.calculateLength())); //Length of the wire.
    }
    //End of the method to update the frame updateFrame().
}
//End of the class VoltageControllerDC().
