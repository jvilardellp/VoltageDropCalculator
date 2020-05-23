//Package of the class.
package Controller;

//Importing classes from the packages View and Model.

import Model.VoltageDropDataAC;
import Model.VoltageDropDataDC;
import View.FrameVoltageDrop;

//Importing classes required from the API of JAVA.
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Controller class to fill the panels fields of the frame with the initial values.
 * @author : Jaume V.
 * @version : 18/07/2016/A
 */
public class LoadData extends WindowAdapter
{
    private FrameVoltageDrop frame;

    /**
     * Constructor to load the data from the voltage drop model to the application frame.
     * @param objectFrame : Frame object.
     */
    public LoadData(FrameVoltageDrop objectFrame)
    {
        //The constructor received the frame of the application.
        this.frame=objectFrame; //The variable get the frame of the application.
    }
    //End of the constructor LoadData().

    /**
     * Method that implements the windowOpened from the class WindowAdapter. This class is executed when the frame is
     * opened. This class fill the panels fields with the initial values from the VoltageDrop class (Model).
     */
    @Override
    public void windowOpened(WindowEvent windowEvent)
    {
        //Data models.
        VoltageDropDataDC VoltageDropObjectDC=new VoltageDropDataDC();
        VoltageDropDataAC VoltageDropObjectAC=new VoltageDropDataAC();

        //DC:
        //****************************************************************************************
        // GETTING VALUES FROM THE DATA MODEL. PANEL DC.
        // 1-POWER SUPPLY.
        //****************************************************************************************
        String voltageDC=String.valueOf(VoltageDropObjectDC.getVoltage());
        String currentDC=String.valueOf(VoltageDropObjectDC.getCurrent());
        String powerDC=String.valueOf(VoltageDropObjectDC.getPower());

        //****************************************************************************************
        // GETTING VALUES FROM THE DATA MODEL. PANEL DC.
        // 2-MATERIAL AND VOLTAGE DROP.
        //****************************************************************************************
        String [] materialListDC=VoltageDropObjectDC.getMaterialList();
        String materialDC=VoltageDropObjectDC.getMaterial();
        String VoltageDropDC=String.valueOf(VoltageDropObjectDC.getVoltageDrop());
        String VoltageDropPercentageDC=String.valueOf(VoltageDropObjectDC.getVoltageDropPercentage());

        //****************************************************************************************
        // GETTING VALUES FROM THE DATA MODEL. PANEL DC.
        // 3-WIRE.
        //****************************************************************************************
        String [] sectionListDC=VoltageDropObjectDC.getSectionList();
        String sectionDC=String.valueOf(VoltageDropObjectDC.getSection());
        String lengthDC=String.valueOf(VoltageDropObjectDC.getLength());
        //End getting methods. PANEL DC

        //AC:
        //****************************************************************************************
        // GETTING VALUES FROM THE DATA MODEL. PANEL AC.
        // 1-POWER SUPPLY.
        //****************************************************************************************
        String voltageAC=String.valueOf(VoltageDropObjectAC.getVoltage());
        String currentAC=String.valueOf(VoltageDropObjectAC.getCurrent());
        String powerAC=String.valueOf(VoltageDropObjectAC.getPower());

        //****************************************************************************************
        // GETTING VALUES FROM THE DATA MODEL. PANEL AC.
        // 2-MATERIAL AND VOLTAGE DROP.
        //****************************************************************************************
        String [] materialListAC=VoltageDropObjectAC.getMaterialList();
        String materialAC=VoltageDropObjectAC.getMaterial();
        String VoltageDropAC=String.valueOf(VoltageDropObjectAC.getVoltageDrop());
        String phiCosAC=String.valueOf(VoltageDropObjectAC.getPhiCos());
        String VoltageDropPercentageAC=String.valueOf(VoltageDropObjectAC.getVoltageDropPercentage());

        //****************************************************************************************
        // GETTING VALUES FROM THE DATA MODEL. PANEL DC.
        // 3-WIRE.
        //****************************************************************************************
        String [] sectionListAC=VoltageDropObjectAC.getSectionList();
        String sectionAC=String.valueOf(VoltageDropObjectAC.getSection());
        String lengthAC=String.valueOf(VoltageDropObjectAC.getLength());
        //End getting methods. PANEL AC.

        //DC:
        //****************************************************************************************
        // SETTING VALUES TO THE FRAME. PANEL DC.
        // 1-POWER SUPPLY.
        //****************************************************************************************
        frame.getJPanelPowerSupplyDC().setVoltage(voltageDC);
        frame.getJPanelPowerSupplyDC().setCurrent(currentDC);
        frame.getJPanelPowerSupplyDC().setPower(powerDC);

        //****************************************************************************************
        // SETTING VALUES TO THE FRAME. PANEL DC.
        // 2-MATERIAL AND VOLTAGE DROP.
        //****************************************************************************************
        frame.getJPanelMaterial_VoltageDropDC().setMaterials(materialListDC);
        frame.getJPanelMaterial_VoltageDropDC().setDefaultItemMaterial(materialDC);
        frame.getJPanelMaterial_VoltageDropDC().setInit(false);
        frame.getJPanelMaterial_VoltageDropDC().setVoltageDrop(VoltageDropDC);
        frame.getJPanelMaterial_VoltageDropDC().setVoltageDropPercentage(VoltageDropPercentageDC);

        //****************************************************************************************
        // SETTING VALUES TO THE FRAME. PANEL DC.
        // 2-MATERIAL AND VOLTAGE DROP.
        //****************************************************************************************
        frame.getJPanelWiresDC().setSection(sectionListDC);
        frame.getJPanelWiresDC().setDefaultItemSection(sectionDC);
        frame.getJPanelWiresDC().setInit(false);
        frame.getJPanelWiresDC().setLength(lengthDC);
        //End setting methods. PANEL DC.

        //AC:
        //****************************************************************************************
        // SETTING VALUES TO THE FRAME. PANEL AC.
        // 1-POWER SUPPLY.
        //****************************************************************************************
        frame.getJPanelPowerSupplyAC().setVoltage(voltageAC);
        frame.getJPanelPowerSupplyAC().setCurrent(currentAC);
        frame.getJPanelPowerSupplyAC().setPower(powerAC);

        //****************************************************************************************
        // SETTING VALUES TO THE FRAME. PANEL AC.
        // 2-MATERIAL AND VOLTAGE DROP.
        //****************************************************************************************
        frame.getJPanelMaterial_VoltageDropAC().setMaterials(materialListAC);
        frame.getJPanelMaterial_VoltageDropAC().setDefaultItemMaterial(materialAC);
        frame.getJPanelMaterial_VoltageDropAC().setInit(false);
        frame.getJPanelMaterial_VoltageDropAC().setVoltageDrop(VoltageDropAC);
        frame.getJPanelMaterial_VoltageDropAC().setPhiCos(phiCosAC);
        frame.getJPanelMaterial_VoltageDropAC().setVoltageDropPercentage(VoltageDropPercentageAC);

        //****************************************************************************************
        // SETTING VALUES TO THE FRAME. PANEL AC.
        // 2-MATERIAL AND VOLTAGE DROP.
        //****************************************************************************************
        frame.getJPanelWiresAC().setSection(sectionListAC);
        frame.getJPanelWiresAC().setDefaultItemSection(sectionAC);
        frame.getJPanelWiresAC().setInit(false);
        frame.getJPanelWiresAC().setLength(lengthAC);
        //End setting methods. PANEL AC
    }
    //End of the override the class windowOpened() class.
}
//End of the LoadData() class.
