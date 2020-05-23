//Package of the class.
package View;

//Import packages of the application.

import Controller.MaterialControllerDC;
import Controller.VoltageDropControllerDC;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

//Import packages of the JAVA API.

/**
 * Class to create the panel of the material and the voltage drop.
 * This class extends from JPanel.
 * @author : Jaume V.
 * @version : 18/07/2016/A
 */
public class JPanelMaterial_VoltageDropDC extends JPanel
{
    //Frame object.
    private FrameVoltageDrop frame;

    //Init variable. It is used to indicated that the system are starting the panel, this way the controller classes
    //are not call.
    private Boolean init=true;

    //Fields of the class.
    //Material
    private JComboBox<String> materialJComboBox=null;

    //Voltage drop.
    private JTextField voltageDropJTextField;
    private String initialVoltageDrop=null;
    private Double finalVoltageDrop=null;

    //Voltage drop percentage.
    private JTextField voltageDropPercentageJTextField;

    /**
     * Constructor to create the panel and its components. Panel for the material and voltage drop parameters.
     */
    JPanelMaterial_VoltageDropDC(FrameVoltageDrop objectFrame)
    {
        //The constructor received the frame of the application.
        this.frame=objectFrame; //The variable get the frame of the application.

        //Formats for the text fields.
        DecimalFormatSymbols symbol = new DecimalFormatSymbols();
        symbol.setDecimalSeparator('.'); // Decimal separator '.'.
        DecimalFormat VoltageDropFormat=new DecimalFormat("##.#",symbol); //Voltage drop.

        //************************************************************************************
        //2-MATERIAL AND VOLTAGE DROP PANEL.
        //************************************************************************************
        //Setup the panel.
        setLayout(null); //Panel without layout.
        setPreferredSize(new Dimension(325,300)); //Dimensions of the panel.
        //Border line with the tittle.
        Font font=this.getFont(); //Get the default font of the system.
        Border line = BorderFactory.createLineBorder(Color.BLACK);
        setBorder(BorderFactory.createTitledBorder(line, "Wire material:", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, new Font(font.getFontName(),Font.BOLD,13), Color.CYAN));
        setBackground(Color.DARK_GRAY);

        //************************************************************************************
        //ADDING THE MATERIAL TEXT FIELD TO THE VOLTAGE DROP PANEL.
        //************************************************************************************

        //Material label for the combo box.
        JLabel materialJLabel=new JLabel("Wire material:");
        materialJLabel.setForeground(Color.LIGHT_GRAY);
        materialJLabel.setBounds(new Rectangle(50,40,120,20));

        //Combo box for choose the material of the electrical wire.
        materialJComboBox=new JComboBox<>();
        materialJComboBox.setEditable(false);
        materialJComboBox.setBounds(new Rectangle(165,40,105,20));
        materialJComboBox.setToolTipText("Select the material of the wire: Aluminum, cooper or steel.");

        //Adding listeners for the material combo box.

        //Changing the color of the background when the text field is focused. Yellow when is focused and white when
        //loses the focus. Calling to the method materialAction() to know if the value of the field is changed.
        //If the value if changed, then update the frame.
        materialJComboBox.addFocusListener(new FocusListener()
            {
                @Override
                public void focusGained(FocusEvent focusEvent)
                {
                    materialJComboBox.setPopupVisible(true); //Pop up the material combo box.
                }

                @Override
                public void focusLost(FocusEvent focusEvent)
                {
                    materialJComboBox.setPopupVisible(false);
                }
            }
        ); //End of FocusListener() for the material combo box field.

        //When the item is changed the frame is updated with the new value.
        materialJComboBox.addItemListener(itemEvent ->
                {
                    if(!init)
                    {
                        //Updating the frame with the new value of the material combo box.
                        MaterialControllerDC materialControllerDC =new MaterialControllerDC(frame);
                        materialControllerDC.updateFrame();

                        //Transfer the focus to next object.
                        materialJComboBox.transferFocus();
                    }

                }
        );
        //End of the material combo box.

        //************************************************************************************
        //ADDING THE SEPARATOR TO THE VOLTAGE DROP PANEL.
        //************************************************************************************

        //Voltage drop tittle for the separator.
        JLabel VoltageDropTittle=new JLabel("Voltage drop:");
        VoltageDropTittle.setForeground(Color.cyan);
        VoltageDropTittle.setBounds(new Rectangle(10,144,120,20));

        //Separator line to divide the panel.
        JSeparator separator=new JSeparator();
        separator.setForeground(Color.black);
        separator.setBounds(115,155,202,1);
        //End of the separator.

        //************************************************************************************
        //ADDING THE VOLTAGE DROP TO THE VOLTAGE DROP PANEL.
        //************************************************************************************

        //Voltage drop label for the text field.
        JLabel voltageDropJLabel=new JLabel("Voltage drop (DC):");
        voltageDropJLabel.setForeground(Color.LIGHT_GRAY);
        voltageDropJLabel.setBounds(new Rectangle(60,195,160,20));

        //Text field for the voltage drop.
        voltageDropJTextField=new JTextField();
        voltageDropJTextField.setEditable(true);
        voltageDropJTextField.setHorizontalAlignment(JTextField.RIGHT);
        voltageDropJTextField.setColumns(6);
        voltageDropJTextField.setBounds(new Rectangle(200,195,60,20));
        voltageDropJTextField.setToolTipText("Enter the voltage drop, range: > 0 - < Output voltage of power supply.");

        //Add the method to verify the value of the text field before to lost the focus.
        voltageDropJTextField.setInputVerifier(new InputVerifier()
            {
                @Override
                public boolean verify(JComponent jComponent)
                {
                    //Checking the value entered in the text field has the correct format.
                    try
                    {
                        //Parse throws a exception if there is some error to catching the value.
                        finalVoltageDrop=VoltageDropFormat.parse(voltageDropJTextField.getText()).doubleValue();

                        //If the format is correct and the value is different then update the rest of values on the
                        //frame.
                        if (Double.parseDouble(initialVoltageDrop) != finalVoltageDrop)
                        {
                            //If the method return true, then the range is correct, it is possible to update the frame.
                            if(VoltageDropAction(initialVoltageDrop,finalVoltageDrop))
                            {
                                return true; //Return true if the checking is correct, then gives the focus.
                            }
                            else //If it is false re-write the initial voltage and re-assign the focus.
                            {
                                setVoltageDrop(initialVoltageDrop);
                                voltageDropJTextField.requestFocus(); //Re-assign the focus to voltage text field

                                return false; //Return false if the range of the voltage is not correct.
                            }
                        }

                        return true; //Return true if the checking is correct.
                    }
                    catch (ParseException e) //Catch the error and show the message.
                    {
                        showErrorMessages("The voltage drop value entered has not the correct format. The correct " +
                                "format is '##.#', example 1.0.");

                        setVoltageDrop(initialVoltageDrop); //Re-write the initial value.
                        voltageDropJTextField.requestFocus(); //Re-assign the focus ta the text field.
                    }

                    return false; //Return false if the checking is wrong.
                }
                //End verify() method.
            }
            //End InputVerify().
        );
        //End setInputVerify() method.

        //Adding the listeners for voltage drop text field.

        //Changing the color of the background when the text field is focused. Yellow when is focused and white when
        //loses the focus. Calling to the method VoltageDropAction() to know if the value of the field is changed.
        //If the value if changed, then update the frame.
        voltageDropJTextField.addFocusListener(new FocusListener()
            {
                @Override
                public void focusGained(FocusEvent focusEvent)
                {
                    voltageDropJTextField.setBackground(Color.YELLOW);

                    initialVoltageDrop=getVoltageDrop(); // Get the initial value of this field.
                }

                @Override
                public void focusLost(FocusEvent focusEvent)
                {
                    voltageDropJTextField.setText(String.valueOf(finalVoltageDrop));

                    voltageDropJTextField.setBackground(Color.WHITE);
                }
            }
        );
        //End of FocusListener() for voltage drop text field.

        //If the enter key is pressed then update the frame with the new value of the field.
        voltageDropJTextField.addKeyListener(new KeyListener()
            {
                @Override
                public void keyTyped(KeyEvent keyEvent)
                {
                    char character=keyEvent.getKeyChar();

                    if(voltageDropJTextField.getText().length() >= 4)
                    {
                        keyEvent.consume();
                    }
                    else
                    {
                        //Checking if there is written a point in the value.
                        if(points(voltageDropJTextField.getText()) && (character == '.'))
                        {
                            keyEvent.consume(); //Ignore the character entered.
                        }
                        //Checking if the character is: 0123456789.
                        else if(((character < '0') || (character > '9')) && (character != '.'))
                        {
                            keyEvent.consume(); //Ignore the character entered.
                        }
                    }
                }

                @Override
                public void keyPressed(KeyEvent keyEvent)
                {
                    if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) //True if the key pressed is enter.
                    {
                        ((Component) keyEvent.getSource()).transferFocus(); //Change the focus a next one.
                    }
                }

                @Override
                public void keyReleased(KeyEvent keyEvent)
                {
                    //Nothing.
                }
            }
        ); //End KeyListener() for voltage drop text field.
        //End of voltage drop text field.

        //************************************************************************************
        //ADDING THE VOLTAGE DROP AVERAGE TO THE VOLTAGE DROP PANEL.
        //************************************************************************************

        //Voltage drop average label for the text field.
        JLabel VoltageDropPercentageJLabel=new JLabel("Voltage drop percentage (%):");
        VoltageDropPercentageJLabel.setForeground(Color.LIGHT_GRAY);
        VoltageDropPercentageJLabel.setBounds(new Rectangle(30,235,220,20));

        //Text field for the voltage drop average.
        voltageDropPercentageJTextField=new JTextField();
        voltageDropPercentageJTextField.setEnabled(false);
        voltageDropPercentageJTextField.setHorizontalAlignment(JTextField.RIGHT);
        voltageDropPercentageJTextField.setColumns(6);
        voltageDropPercentageJTextField.setBackground(Color.BLUE);
        voltageDropPercentageJTextField.setBounds(new Rectangle(250,235,40,20));
        voltageDropPercentageJTextField.setToolTipText("This field show the percentage of the voltage drop, is not " +
                "editable.");

        //Adding components to the south panel.
        add(materialJLabel);
        add(materialJComboBox);
        add(VoltageDropTittle);
        add(separator);
        add(voltageDropJLabel);
        add(voltageDropJTextField);
        add(VoltageDropPercentageJLabel);
        add(voltageDropPercentageJTextField);
    }
    //End of the constructor JPanelMaterial_VoltageDropDC().

    //****************************************************************************************
    //METHOD: GETTERS AND SETTERS.
    //****************************************************************************************

    /**
     * Getter method to get the material.
     * @return : Return the string with the value of the material.
     */
    public String getMaterial()
    {
        return (String)materialJComboBox.getSelectedItem();
    }

    /**
     * Getter method to get the voltage drop value.
     * @return : Return the string with the value of the voltage drop.
     */
    public String getVoltageDrop()
    {
        return voltageDropJTextField.getText();
    }

     /**
     * Setter method to set the material values in the combo box.
     * @param listOfItems : List of strings with the material values.
     */
    public void setMaterials(String[]listOfItems)
    {
        //Loop to insert all the product of the list.
        for (String item:listOfItems)
        {
            materialJComboBox.addItem(item);
        }
    }

    /**
     * Setter method to select the default item for the combo box of the materials.
     * @param defaultItem : Item string with the default item.
     */
    public void setDefaultItemMaterial(String defaultItem)
    {
        materialJComboBox.setSelectedItem(defaultItem);
    }

    /**
     * Setter method to change the boolean variable init.
     * @param init : Boolean variable with the value for the init variable. Change from the init mode to working mode.
     */
    public void setInit(Boolean init)
    {
        this.init=init;
    }

    /**
     * Setter method to set the voltage drop value.
     * @param VoltageDrop : String with the voltage value.
     */
    public void setVoltageDrop(String VoltageDrop)
    {
        voltageDropJTextField.setText(VoltageDrop);
    }

    /**
     * Setter method to set the voltage drop average value.
     * @param VoltageDropPercentage : String with the voltage value.
     */
    public void setVoltageDropPercentage(String VoltageDropPercentage)
    {
        voltageDropPercentageJTextField.setText(VoltageDropPercentage);
    }
    //End fo the getters and setters methods.

    //****************************************************************************************
    //OTHERS METHODS OF THE CLASS.
    //****************************************************************************************

     /**
     * Method to compare the initial and the final value of the voltage drop field. If the value is different, then
     * updated the frame.
     * @param initialVoltageDrop : String with the initial value of the voltage drop field.
     * @param finalVoltageDrop : String with the final value of the voltage drop field.
     * @return : True if the  range is correct and false if the value is out of the range.
     */
    private Boolean VoltageDropAction(String initialVoltageDrop, Double finalVoltageDrop)
    {
        double voltagePowerSupply= Double.parseDouble(frame.getJPanelPowerSupplyDC().getVoltage());

        if (finalVoltageDrop <= 0 || finalVoltageDrop >= voltagePowerSupply)
        {
            showErrorMessages("The voltage drop value is out of range. The correct value must be" +
                    " between 0 and the voltage of the power supply: "+frame.getJPanelPowerSupplyDC().getVoltage()+
                    "VDC.");
            setVoltageDrop(initialVoltageDrop);

            return false;
        }
        else
        {
            VoltageDropControllerDC voltageDropControllerDC = new VoltageDropControllerDC(frame);
            voltageDropControllerDC.updateFrame();

            return true;
        }
    }
    //End of VoltageDropAction() method.

    /**
     * Method to write message in a dialog panel.
     * @param message : String with the error message.
     */
    private void showErrorMessages (String message)
    {
        JOptionPane.showMessageDialog(this,message);
    }

    /**
     * Method to check out the number of points is more than one point.
     * @param string : String to be verified.
     * @return : Return true if the string has one more than one point.
     */
    private Boolean points(String string)
    {
        int aux=0; //Number of points.
        int i; //Counter number.

        //Loop to know the number of point in the string.
        for (i=0;i<string.length();i++)
        {
            //If there is a point in the actual character add one to the aux variable.
            if (string.charAt(i)=='.')
            {
                aux++; //aux + 1.
            }
        }

        return aux >= 1; //Return true if aux is bigger o equal to 1 and false 0.
    }
    //End of the other methods.
}
//End of the class JPanelMaterial_VoltageDropDC() class.
