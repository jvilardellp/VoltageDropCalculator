//Package of the class.
package View;

//Importing classes from the packages View and Model.

import Controller.CurrentControllerAC;
import Controller.PowerControllerAC;
import Controller.VoltageControllerAC;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

//Import packages of the JAVA API.


/**
 * Class to create the panel of the power supply.
 * This class extends from JPanel.
 * @author : Jaume V.
 * @version : 18/07/2016/A
 */
public class JPanelPowerSupplyAC extends JPanel
{
    //Frame object.
    private FrameVoltageDrop frame;

    //Fields of the class.
    //Voltage.
    private JTextField voltageJTextField;
    private String initialVoltage=null;
    private Double finalVoltage=null;

    //Current.
    private JTextField currentJTextField;
    private String initialCurrent=null;
    private Double finalCurrent=null;

    //Power.
    private JTextField powerJTextField;
    private String initialPower=null;
    private Double finalPower=null;

    /**
     * Constructor to create the panel and its components. Panel for the power supply parameters.
     */
    JPanelPowerSupplyAC(FrameVoltageDrop objectFrame)
    {
        //The constructor received the frame of the application.
        this.frame=objectFrame; //The variable get the frame of the application.

        //Formats for the text fields.
        DecimalFormatSymbols symbol = new DecimalFormatSymbols();
        symbol.setDecimalSeparator('.'); // Decimal separator '.'.
        DecimalFormat voltageFormat=new DecimalFormat("##.#",symbol); //Voltage.
        DecimalFormat currentFormat=new DecimalFormat("##.###",symbol); //Current.
        DecimalFormat powerFormat=new DecimalFormat("####.##",symbol); //Power.

        //************************************************************************************
        //1-POWER SUPPLY PANEL. PLACE IN THE LEFT AND TOP SIDE.
        //************************************************************************************
        //Setup the panel.
        setLayout(null); //Panel without layout.
        setPreferredSize(new Dimension(325,300)); //Dimension of the panel.
        //Border line with the tittle.
        Font font=this.getFont(); //Get the default font of the system.
        Border line = BorderFactory.createLineBorder(Color.BLACK);
        setBorder(BorderFactory.createTitledBorder(line, "Power supply:",TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,new Font(font.getFontName(),Font.BOLD,13), Color.CYAN));
        setBackground(Color.DARK_GRAY);

        //************************************************************************************
        //ADDING THE VOLTAGE TEXT FIELD TO THE POWER SUPPLY PANEL.
        //************************************************************************************

        //Voltage label for the text field.
        JLabel voltageJLabel=new JLabel("Voltage (AC):");
        voltageJLabel.setForeground(Color.LIGHT_GRAY);
        voltageJLabel.setBounds(new Rectangle(35,170,125,20));

        //Creating and setting up the text field for the voltage.
        voltageJTextField=new JTextField();
        voltageJTextField.setEditable(true);
        voltageJTextField.setHorizontalAlignment(JTextField.RIGHT);
        voltageJTextField.setBounds(new Rectangle(60,196,45,20));
        voltageJTextField.setToolTipText("Enter the output voltage of the power supply, range: 1 - 54VDC");

        //Add the method to verify the value of the text field before to lost the focus.
        voltageJTextField.setInputVerifier(new InputVerifier()
            {
                @Override
                public boolean verify(JComponent jComponent)
                {
                    //Checking the value entered in the text field has the correct format.
                    try
                    {
                        //Parse throws a exception if there is some error to catching the value.
                        finalVoltage=voltageFormat.parse(voltageJTextField.getText()).doubleValue();

                        //If the format is correct and the value is different then update the rest of values on the
                        //frame.
                        if (Double.parseDouble(initialVoltage) != finalVoltage)
                        {
                            //If the method return true, then the range is correct, it is possible to update the frame.
                            if(voltageAction(initialVoltage,finalVoltage))
                            {
                                return true; //Return true if the checking is correct, then gives the focus.
                            }
                            else //If it is false re-write the initial voltage and re-assign the focus.
                            {
                                setVoltage(initialVoltage);
                                voltageJTextField.requestFocus(); //Re-assign the focus to voltage text field

                                return false; //Return false if the range of the voltage is not correct.
                            }
                        }
                        else //If the initial and final values of voltage are equal gives the focus and it does not do
                        // anything, only gives the focus.
                        {
                            return true;
                        }

                    }
                    catch (ParseException e) //Catch the error and show the message.
                    {
                        showErrorMessages("The voltage value entered has not the correct format.\nThe correct " +
                                "format is '##.#', example 24.5.");

                        setVoltage(initialVoltage); //Re-write the initial value.
                        voltageJTextField.requestFocus(); //Re-assign the focus ta the text field.
                    }
                    return false; //Return false if the checking is wrong.
                }
                //End override verify method.
            }
            //End InputVerify() method.
        );
        //End setInputVerify() method.

        //Adding the listeners for the voltage text field.

        //Changing the color of the background when the text field is focused. Yellow when is focused and white
        // when loses the focus. Calling to the method voltageAction() to know if the value of the field is changed.
        // If the value if changed, then update the frame.
        voltageJTextField.addFocusListener(new FocusListener()
            {
                @Override
                public void focusGained(FocusEvent focusEvent) //Focus of the field gained.
                {
                    initialVoltage=getVoltage(); //Get the initial value of this field.

                    voltageJTextField.setBackground(Color.YELLOW);
                }

                @Override
                public void focusLost(FocusEvent focusEvent) //Focus of the field lost.
                {
                    voltageJTextField.setText(String.valueOf(finalVoltage));

                    voltageJTextField.setBackground(Color.WHITE);
                }
            }
        );
        //End of FocusListener() for voltage text field.

        //If the enter key is pressed then change the focus to next one.
        voltageJTextField.addKeyListener(new KeyListener()
            {
                @Override
                public void keyTyped(KeyEvent keyEvent)
                {
                    char character=keyEvent.getKeyChar();

                    if(voltageJTextField.getText().length() >= 4)
                    {
                        keyEvent.consume();
                    }
                    else
                    {
                        //Checking if there is written a point in the value.
                        if(points(voltageJTextField.getText()) && (character == '.'))
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
                    if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) //True if the key pressed is enter.
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
        ); //End of KeyListener() for voltage text field.
        //End of the voltage text field.

        //************************************************************************************
        //ADDING THE CURRENT TEXT FIELD TO THE POWER SUPPLY PANEL.
        //************************************************************************************

        //Current label for the text field.
        JLabel currentJLabel=new JLabel("Current (A):");
        currentJLabel.setForeground(Color.LIGHT_GRAY);
        currentJLabel.setBounds(new Rectangle(210,170,125,20));

        //Text field for the current.
        currentJTextField=new JTextField();
        currentJTextField.setEditable(true);
        currentJTextField.setHorizontalAlignment(JTextField.RIGHT);
        currentJTextField.setBounds(new Rectangle(210,196,80,20));
        currentJTextField.setToolTipText("Enter the output current of the power supply, range: > 0 - 30A");

        //Add the method to verify the value of the text field before to lost the focus.
        currentJTextField.setInputVerifier(new InputVerifier()
            {
                @Override
                public boolean verify(JComponent jComponent)
                {
                    //Checking the value entered in the text field has the correct format.
                    try
                    {
                        //Parse throws a exception if there is some error to catching the value.
                        finalCurrent=currentFormat.parse(currentJTextField.getText()).doubleValue();

                        //If the format is correct and the value is different then update the rest of values on the
                        //frame.
                        if (Double.parseDouble(initialCurrent) != finalCurrent)
                        {
                            //If the method return true, then the range is correct, it is possible to update the frame.
                            if(currentAction(initialCurrent,finalCurrent))
                            {
                                return true; //Return true if the checking is correct, then gives the focus.
                            }
                            else //If it is false re-write the initial voltage and re-assign the focus.
                            {
                                setCurrent(initialCurrent);
                                currentJTextField.requestFocus(); //Re-assign the focus to voltage text field

                                return false; //Return false if the range of the voltage is not correct.
                            }
                        }

                        return true; //Return true if the checking is correct.
                    }
                    catch (ParseException e) //Catch the error and show the message.
                    {
                        showErrorMessages("The current value entered has not the correct format.\nThe correct " +
                                "format is '##.###', example 2.500.");

                        setCurrent(initialCurrent); //Re-write the initial value.
                        currentJTextField.requestFocus(); //Re-assign the focus ta the text field.
                    }
                    return false; //Return false if the checking is wrong.
                }
                //End override verify method.
            }
            //End InputVerify() method.
        );
        //End setInputVerify() method.

        //Adding the listeners for the current text field.

        //Changing the color of the background when the text field is focused. Yellow when is focused and white when
        // when loses the focus. Calling to the method currentAction() to know if the value of the field is changed.
        // If the value if changed, then update the frame.
        currentJTextField.addFocusListener(new FocusListener()
            {
                @Override
                public void focusGained(FocusEvent focusEvent) //Focus of the field gained.
                {
                    initialCurrent=getCurrent(); //Get the initial value of this field.

                    currentJTextField.setBackground(Color.YELLOW);
                }

                @Override
                public void focusLost(FocusEvent focusEvent) //Focus of the field lost.
                {
                    currentJTextField.setText(String.valueOf(finalCurrent));

                    currentJTextField.setBackground(Color.WHITE);
                }
            }
        );
        //End of FocusListener() for the current text field.

        //If the enter key is pressed then update the frame with the new value of the field.
        currentJTextField.addKeyListener(new KeyListener()
            {
                @Override
                public void keyTyped(KeyEvent keyEvent)
                {
                    char character=keyEvent.getKeyChar();

                    if(currentJTextField.getText().length() >= 6)
                    {
                        keyEvent.consume();
                    }
                    else
                    {
                        //Checking if there is written a point in the value.
                        if(points(currentJTextField.getText()) && (character == '.'))
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
        );
        //End KeyListener() for current text field.
        //End of current text field.

        //************************************************************************************
        //ADDING THE POWER TEXT FIELD TO THE POWER SUPPLY PANEL.
        //************************************************************************************

        //Power label for the text field.
        JLabel powerJLabel=new JLabel("Power (W):");
        powerJLabel.setForeground(Color.LIGHT_GRAY);
        powerJLabel.setBounds(new Rectangle(125,205,125,20));

        //Text field for the power.
        powerJTextField=new JTextField();
        powerJTextField.setEditable(true);
        powerJTextField.setHorizontalAlignment(JTextField.RIGHT);
        powerJTextField.setBounds(new Rectangle(122,231,80,20));
        powerJTextField.setToolTipText("Enter the power to be delivered for the power supply, range: > 0 - 1620W");

        //Add the method to verify the value of the text field before to lost the focus.
        powerJTextField.setInputVerifier(new InputVerifier()
            {
                @Override
                public boolean verify(JComponent jComponent)
                {
                    //Checking the value entered in the text field has the correct format.
                    try
                    {
                        //Parse throws a exception if there is some error to catching the value.
                        finalPower=powerFormat.parse(powerJTextField.getText()).doubleValue();

                        //If the format is correct and the value is different then update the rest of values on the
                        //frame.
                        if (Double.parseDouble(initialPower) != finalPower)
                        {
                            //If the method return true, then the range is correct, it is possible to update the frame.
                            if(powerAction(initialPower,finalPower))
                            {
                                return true; //Return true if the checking is correct, then gives the focus.
                            }
                            else //If it is false re-write the initial voltage and re-assign the focus.
                            {
                                setPower(initialPower);
                                powerJTextField.requestFocus(); //Re-assign the focus to voltage text field

                                return false; //Return false if the range of the voltage is not correct.
                            }
                        }

                        return true; //Return true if the checking is correct.
                    }
                    catch (ParseException e) //Catch the error and show the message.
                    {
                        showErrorMessages("The power value entered has not the correct format.\nThe correct " +
                                "format is '####.##', example 100.50.");

                        setPower(initialPower); //Re-write the initial value.
                        powerJTextField.requestFocus(); //Re-assign the focus ta the text field.
                    }
                    return false; //Return false if the checking is wrong.
                }
                //End override verify method.
            }
            //End InputVerify() method.
        );
        //End setInputVerify() method.

        //Adding the listeners for the power text field.

        //Changing the color of the background when the text field is focused. Yellow when is focused and white when
        //loses the focus. Calling to the method powerAction() to know if the value of the field is changed.
        //If the value if changed, then update the frame.
        powerJTextField.addFocusListener(new FocusListener()
            {
                @Override
                public void focusGained(FocusEvent focusEvent) //Focus of the field gained.
                {
                    initialPower=getPower(); //Get the initial value of this field.

                    powerJTextField.setBackground(Color.YELLOW);
                }

                @Override
                public void focusLost(FocusEvent focusEvent) //Focus of the field lost.
                {
                    powerJTextField.setText(String.valueOf(finalPower));

                    powerJTextField.setBackground(Color.WHITE);
                }
            }
        ); //End of FocusListener() for the power text field.

        //If the enter key is pressed then update the frame with the new value of the field.
        powerJTextField.addKeyListener(new KeyListener()
            {
               @Override
               public void keyTyped(KeyEvent keyEvent)
               {
                   char character=keyEvent.getKeyChar();

                   if(powerJTextField.getText().length() >= 7)
                   {
                       keyEvent.consume();
                   }
                   else
                   {
                       //Checking if there is written a point in the value.
                       if(points(powerJTextField.getText()) && (character == '.'))
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
        );
        //End KeyListener() for current text field.
        //End of current text field.

        //Adding components to the south panel.
        add(voltageJLabel);
        add(voltageJTextField);
        add(currentJLabel);
        add(currentJTextField);
        add(powerJLabel);
        add(powerJTextField);
    }
    //End of the constructor JPanelPowerSupplyAC().

    //****************************************************************************************
    //METHOD: GETTERS AND SETTERS.
    //****************************************************************************************

    /**
     * Getter method to get the voltage value.
     * @return : Return the string with the value of the voltage.
     */
    public String getVoltage()
    {
        return voltageJTextField.getText();
    }

    /**
     * Getter method to get the current value.
     * @return : Return the string with the value of the current.
     */
    public String getCurrent()
    {
        return currentJTextField.getText();
    }

    /**
     * Getter method to get the power value.
     * @return : Return the string with the value of the power.
     */
    public String getPower()
    {
        return powerJTextField.getText();
    }

    /**
     * Setter method to set the voltage value.
     * @param voltage : String with the voltage value.
     */
    public void setVoltage(String voltage)
    {
        voltageJTextField.setText(voltage);
    }

    /**
     * Setter method to set the current value.
     * @param current : String with the current value.
     */
    public void setCurrent(String current)
    {
        currentJTextField.setText(current);
    }

    /**
     * Setter method to set the power value.
     * @param power : String with the power value.
     */
    public void setPower(String power)
    {
        powerJTextField.setText(power);
    }
    //End fo the getters and setters methods.

    //****************************************************************************************
    //OTHERS METHODS OF THE CLASS.
    //****************************************************************************************

    /**
     * Method to override the paint method paintComponent. This method print the image indicated in the class over the
     * background of the panel.
     * @param graphics : graphic object.
     */

    @Override
    public void paintComponent(Graphics graphics)
    {
        //Constructor of the JPanel.
        super.paintComponent(graphics);

        //File address to load the image.
        File imageFile=new File("src/Images/AC.gif");

        //Image object.
        Image image=null;

        //Trying to load the image.
        try
        {
            image= ImageIO.read(imageFile);
        }
        catch(IOException e) //It is not possible to read the image.
        {
            e.printStackTrace();
            System.out.println("The power supply image does not exist.");
        }

        //Painting the image over the panel.
        graphics.drawImage(image, 60, 35, 220, 130, null);
    }
    //End of the override method paint component.

    /**
     * Method to compare the range of voltage, if the value is correct, then update the frame. If the value is wrong,
     * then show the message and restore the initial value in the text field and the focus.
     * @param initialVoltage : String with the initial value of the voltage field.
     * @param finalVoltage : Double with the final value of the voltage field.
     * @return : True if the  range is correct and false if the value is out of the range.
     */
    private Boolean voltageAction(String initialVoltage, Double finalVoltage)
    {
        if (finalVoltage < 1 || finalVoltage > 54)
        {
            showErrorMessages("The voltage value is out of range. The correct value must be" +
                    " between 1 and 54VDC.");
            setVoltage(initialVoltage);

            return false;
        }
        else
        {
            VoltageControllerAC voltageControllerAC = new VoltageControllerAC(frame);
            voltageControllerAC.updateFrame();

            return true;
        }
    }
    //End of voltageAction() method.

    /**
     * Method to compare the range of current, if the value is correct, then update the frame. If the value is wrong,
     * then show the message and restore the initial value in the text field and the focus.
     * @param initialCurrent : String with the initial value of the current field.
     * @param finalCurrent: Double with the final value of the current field.
     * @return : True if the  range is correct and false if the value is out of the range.
    */
    private Boolean currentAction(String initialCurrent, Double finalCurrent)
    {
        if (finalCurrent <= 0 || finalCurrent > 30)
        {
            showErrorMessages("The current value is out of range. The correct value must be" +
                    " between 0 and 30A");
            setCurrent(initialCurrent);

            return false;
        }
        else
        {
            CurrentControllerAC currentControllerAC = new CurrentControllerAC(frame);
            currentControllerAC.updateFrame();

            return true;
        }
    }
    //End of currentAction() method.

    /**
     * Method to compare the initial and the final value of the power field. If the value is different, then updated
     * the frame. Also this method verify the input value is between the correct range. If the value is not correct,
     * then show the dialog message.
     * @param initialPower : String with the initial value of the power field.
     * @param finalPower: String with the final value of the power field.
     * @return : True if the  range is correct and false if the value is out of the range.
     */
    private Boolean powerAction(String initialPower, Double finalPower)
    {
        if (finalPower <= 0 || finalPower > 1620)
        {
            showErrorMessages("The power value is out of range. The correct value must be" +
                    " between 0 and 1620W.");
            setPower(initialPower);

            return false;
        }
        else
        {
            PowerControllerAC powerControllerAC = new PowerControllerAC(frame);
            powerControllerAC.updateFrame();

            return true;
        }
    }
    //End of PowerAction() method.

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
//End the JPanelPowerSupplyAC() class.
