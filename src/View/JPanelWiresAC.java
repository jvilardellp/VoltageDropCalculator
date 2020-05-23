//Package of the class.
package View;

//Import packages of the application.

import Controller.WireControllerAC;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;

//Import packages of the JAVA API.

/**
 * Class to create the panel of the wire.
 * This class extends from JPanel.
 * @author : Jaume V.
 * @version : 18/07/2016/A
 */
public class JPanelWiresAC extends JPanel
{
    //Frame object.
    private FrameVoltageDrop frame;

    //Init variable. It is used to indicated that the system are starting the panel, this way the controller classes
    //are not call.
    private Boolean init=true;

    //Fields of the class.
    private JComboBox<String> sectionJComboBox;
    private JTextField lengthJTextField;

    /**
     * Constructor to create the panel and its components. Panel for the power supply parameters.
     */
    JPanelWiresAC(FrameVoltageDrop objectFrame)
    {
        //The constructor received the frame of the application.
        this.frame=objectFrame; //The variable get the frame of the application.

        //************************************************************************************
        //3-WIRE PANEL.
        //************************************************************************************
        //Setup the panel.
        setLayout(null); //Panel without layout.
        setPreferredSize(new Dimension(650,200)); //Dimension of the panel.
        //Border line with the tittle.
        Font font=this.getFont(); //Get the default font of the system.
        Border line = BorderFactory.createLineBorder(Color.BLACK);
        setBorder(BorderFactory.createTitledBorder(line, "Wire:", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, new Font(font.getFontName(),Font.BOLD,12), Color.CYAN));
        setBackground(Color.DARK_GRAY);

        //************************************************************************************
        //ADDING THE SECTION COMBO BOX TO THE WIRE PANEL.
        //************************************************************************************

        //Section label for the combo box.
        JLabel sectionJLabel=new JLabel("Section (mm2) :");
        sectionJLabel.setForeground(Color.LIGHT_GRAY);
        sectionJLabel.setBounds(new Rectangle(440,95,150,20));

        //Combo box for the section of the wire.
        sectionJComboBox=new JComboBox<>();
        sectionJComboBox.setEditable(false);
        sectionJComboBox.setBounds(new Rectangle(465,125,70,20));
        sectionJComboBox.setToolTipText("Select the section of the wire.");

        //Adding the listeners for the section combo box.

        //Pop up or down when the combo box lose the focus.
        sectionJComboBox.addFocusListener(new FocusListener()
            {
                 @Override
                public void focusGained(FocusEvent focusEvent)
                {
                    sectionJComboBox.setPopupVisible(true);
                }

                @Override
                public void focusLost(FocusEvent focusEvent)
                {
                    sectionJComboBox.setPopupVisible(false);
                }
            }
        ); //End of the FocusListener() for the section combo box.

        //When the item is changed the frame is updated with the new value.
        sectionJComboBox.addItemListener(itemEvent ->
                {
                    if(!init)
                    {
                        //Updating the frame with the new value of the section combo box.
                        WireControllerAC wireControllerAC =new WireControllerAC(frame);
                        wireControllerAC.updateFrame();

                        //Transfer the focus to next object.
                        sectionJComboBox.transferFocus();
                    }

                }
        );
        //End adding section text fields.

        //************************************************************************************
        //ADDING THE LENGTH TEXT FIELD TO THE WIRE PANEL.
        //************************************************************************************

        // Length label for the combo box.
        JLabel lengthJLabel=new JLabel("Length (m):");
        lengthJLabel.setForeground(Color.LIGHT_GRAY);
        lengthJLabel.setBounds(new Rectangle(185,40,120,20));

        //Text field for the length of the wire.
        lengthJTextField=new JTextField();
        lengthJTextField.setEnabled(false);
        lengthJTextField.setHorizontalAlignment(JTextField.RIGHT);
        lengthJTextField.setColumns(6);
        lengthJTextField.setBackground(Color.BLUE);
        lengthJTextField.setBounds(new Rectangle(280,40,80,20));
        lengthJTextField.setToolTipText("Show the length of the wire according to the values selected and entered.");
        //End length text field.

        //Adding components to the south panel.
        add(sectionJLabel);
        add(sectionJComboBox);
        add(lengthJLabel);
        add(lengthJTextField);
    }
    //End of the constructor JPanelWiresAC().

    //****************************************************************************************
    //METHOD: GETTERS AND SETTERS.
    //****************************************************************************************

    /**
     * Getter method to get the section of the wire.
     * @return : Return the string with the section size of the wire.
     */
    public String getSection()
    {
        return (String)sectionJComboBox.getSelectedItem();
    }

    /**
     * Setter method to set the wire section standard in the combo box.
     * @param listOfItems : List of strings with the section values.
     */
    public void setSection(String[]listOfItems)
    {
        //Loop to insert all the product of the list.
        for (String item:listOfItems)
        {
            sectionJComboBox.addItem(item);
        }
    }

    /**
     * Setter method to select the default item for the combo box of the section.
     * @param defaultItem : Item string with the default item.
     */
    public void setDefaultItemSection(String defaultItem)
    {
        sectionJComboBox.setSelectedItem(defaultItem);
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
     * Setter method to set the wire length value.
     * @param length : String with the wire length value.
     */
    public void setLength(String length)
    {
        lengthJTextField.setText(length);
    }
    //End fo the getters and setters methods.

    //****************************************************************************************
    //OTHERS METHODS OF THE CLASS.
    //****************************************************************************************

    /**
     * Class to override the paint method paintComponent. This method print the image indicated in the class over the
     * background of the panel.
     * @param graphics : graphic object.
     */

    @Override
    public void paintComponent(Graphics graphics)
    {
        //Constructor of the JPanel.
        super.paintComponent(graphics);

        //File address to load the image.
        File imageFile=new File("src/Images/WireArrows.gif");

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
        graphics.drawImage(image, 140, 25, 300, 225, null);
    }
    //End of the override method paint component.
    //End of the other methods.
}
//End of the class JPanelWiresAC() class.
