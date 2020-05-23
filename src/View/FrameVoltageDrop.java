//Package of the class.
package View;

//Importing classes from the package controller.
import Controller.LoadData;

//Importing classes required from the API of JAVA.
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

//Importing classes required form the application VoltageDropCalculator.
import CustomClasses.CustomTabbedPaneUI;//Tabbed Pane customized.

/**
 * Main class to run the drop voltage application.
 * @author : Jaume V.
 * @version : 01/02/2017
 */
public class FrameVoltageDrop extends JFrame implements Serializable
{
    //Version number.
    private static final long serialVersionUID = 1L;

    //Panels of the tabs.
    private JPanel DCPanel;
    private JPanel ACPanel;

    //Panels.
    //DC:
    private JPanelPowerSupplyDC panelPowerSupplyDC;
    private JPanelMaterial_VoltageDropDC panelMaterial_VoltageDropDC;
    private JPanelWiresDC panelWiresDC;

    //AC:
    private JPanelPowerSupplyAC panelPowerSupplyAC;
    private JPanelMaterial_VoltageDropAC panelMaterial_VoltageDropAC;
    private JPanelWiresAC panelWiresAC;

    //Constructor of the class.
    public FrameVoltageDrop()
    {
        InitFrame();//Method to init all the components of the frame;
        FrameIcon();//Assigning icon to the frame.
    }

    //Initiating the frame.
    private void InitFrame()
    {
        //************************************************************************************
        //SETUP OF THE FRAME.
        //************************************************************************************
        setBounds(300, 150, 650, 530);//Size of the frame.
        setTitle("VOLTAGE DROP");//Title of the frame.
        setResizable(false);//The frame is not resizable.

        //**********************************************************************************************
        //METHOD TO ADD PANELS TO THE FRAME (DC, AC & Technical information of the wires);
        //**********************************************************************************************
        AddTabbedPane();

        //************************************************************************************
        //ADDING OPEN WINDOW LISTENER.
        //************************************************************************************
        addWindowListener(new LoadData(this));
    }

    //Creating the frame icon for the application.
    private void FrameIcon()
    {
        //File address to load the image.
        File imageFile=new File("src/Images/Icon.png");

        //Image object.
        Image image=null;

        //Trying to load the image.
        try
        {
            image=ImageIO.read(imageFile);
        }
        catch(IOException e) //It is not possible to read the image.
        {
            e.printStackTrace();
            System.out.println("The power supply image does not exist.");
        }

        setIconImage(image);
    }
    //End of the FrameIcon().

    //Creating the frame tabs.
    private void AddTabbedPane()
    {
        InitDCPanel();
        InitACPanel();

        setLayout(new BorderLayout());//Layout of the frame.
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        JTabbedPane tb = new JTabbedPane();
        tb.setUI(new CustomTabbedPaneUI());
        tb.add("DC: Direct current", DCPanel);
        tb.add("AC: Altern current", ACPanel);
        // tb.add("Wires: Technical information", new JTextArea(""));
        jp.add(tb, BorderLayout.CENTER);
        jp.setBackground(Color.DARK_GRAY);
        add(jp, BorderLayout.CENTER);
    }
    //End of the AddTabbedPane().

    private void InitDCPanel()
    {
        DCPanel=new JPanel();
        DCPanel.setLayout(new BorderLayout());
        DCPanel.setBackground(Color.DARK_GRAY);

        //************************************************************************************
        //CREATING THE PANELS -> POWER SUPPLY, MATERIAL_VOLTAGE DROP AND WIRES PANELS.
        //************************************************************************************
        panelPowerSupplyDC =new JPanelPowerSupplyDC(this);
        panelMaterial_VoltageDropDC =new JPanelMaterial_VoltageDropDC(this);
        panelWiresDC =new JPanelWiresDC(this);

        //************************************************************************************
        //ADDING PANELS TO THE DCPanel
        //************************************************************************************
        DCPanel.add(panelPowerSupplyDC, BorderLayout.WEST);
        DCPanel.add(panelMaterial_VoltageDropDC, BorderLayout.CENTER);
        DCPanel.add(panelWiresDC, BorderLayout.SOUTH);
    }

    private void InitACPanel()
    {
        ACPanel=new JPanel();
        ACPanel.setLayout(new BorderLayout());
        ACPanel.setBackground(Color.DARK_GRAY);

        //************************************************************************************
        //CREATING THE PANELS -> POWER SUPPLY, MATERIAL_VOLTAGE DROP AND WIRES PANELS.
        //************************************************************************************
        panelPowerSupplyAC=new JPanelPowerSupplyAC(this);
        panelMaterial_VoltageDropAC=new JPanelMaterial_VoltageDropAC(this);
        panelWiresAC=new JPanelWiresAC(this);

        //************************************************************************************
        //ADDING PANELS TO THE DCPanel
        //************************************************************************************
        ACPanel.add(panelPowerSupplyAC, BorderLayout.WEST);
        ACPanel.add(panelMaterial_VoltageDropAC, BorderLayout.CENTER);
        ACPanel.add(panelWiresAC, BorderLayout.SOUTH);
    }

    //****************************************************************************************
    // METHOD: GETTERS.
    //****************************************************************************************

    //DC:

    /**
     * Getter method to get the power supply panel.
     * @return : Return to JPanelPowerSupplyDC.
     */
    public JPanelPowerSupplyDC getJPanelPowerSupplyDC()
    {
        return panelPowerSupplyDC;
    }

    /**
     * Getter method to get the DC wire panel .
     * @return : Return to JPanelWiresDC.
     */
    public JPanelWiresDC getJPanelWiresDC()
    {
        return panelWiresDC;
    }

    /**
     * Getter method to get the DC material and voltage drop panel.
     * @return : Return to JPanelMaterial_VoltageDropDC.
     */
    public JPanelMaterial_VoltageDropDC getJPanelMaterial_VoltageDropDC()
    {
        return panelMaterial_VoltageDropDC;
    }

    //AC:

    /**
     * Getter method to get the power supply panel.
     * @return : Return to JPanelPowerSupplyAC.
     */
    public JPanelPowerSupplyAC getJPanelPowerSupplyAC()
    {
        return panelPowerSupplyAC;
    }

    /**
     * Getter method to get the AC wire panel .
     * @return : Return to JPanelWiresAC.
     */
    public JPanelWiresAC getJPanelWiresAC()
    {
        return panelWiresAC;
    }

    /**
     * Getter method to get the DC material and voltage drop panel.
     * @return : Return to JPanelMaterial_VoltageDropDC.
     */
    public JPanelMaterial_VoltageDropAC getJPanelMaterial_VoltageDropAC()
    {
        return panelMaterial_VoltageDropAC;
    }
 }
//End of the class.
