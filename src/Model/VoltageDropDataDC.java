//Package of the class.
package Model;

/**
 * Data model for the MVC application to calculate the voltage drop.

 * @author : Jaume V.
 * @version : 18/07/2016/A
 */
public class VoltageDropDataDC
{
    //Fields of the class.
    //1-Power supply.
    private Double voltage;
    private Double current;
    private Double power;

    //2-Material and voltage drop.
    private String[] materialList={"Aluminum","Cooper","Steel"};
    private String material;
    private Double voltageDrop;
    private Double voltageDropPercentage;

    //3-Wire.
    private String[] sectionList={"0.2","0.25","0.3","0,35","0.5","0.75","1","1.5","2.5","4","6","10","16","25"};
    private Double section;
    private Double length;

    /**Constructor of the class to create the data model of the voltage drop application.
    */
    public VoltageDropDataDC()
    {
        initVariables();
    }
    //End of the constructor of the class voltageDropData().

    //****************************************************************************************
    // METHOD: GETTERS AND SETTERS.
    // 1-POWER SUPPLY.
    //****************************************************************************************

    /**
     * Getter method to get the voltage value.
     * @return : Return to double number with the voltage value.
     */
    public Double getVoltage()
    {
        return voltage;
    }

    /**
     * Getter method to get the current value.
     * @return : Return to double number with the current value.
     */
    public Double getCurrent()
    {
        return current;
    }

    /**
     * Getter method to get the power value.
     * @return : Return to double number with the power value.
     */
    public Double getPower()
    {
        return power;
    }

    /**
     * Setter to add the voltage value to this class.
     * @param voltage : Double voltage value.
     */
    public void setVoltage(Double voltage)
    {
        this.voltage=voltage;
    }

    /**
     * Setter to add the voltage value to this class.
     * @param current : Double voltage value.
     */
    public void setCurrent(Double current)
    {
        this.current=current;
    }

    /**
     * Setter to add the voltage value to this class.
     * @param power : Double voltage value.
     */
    public void setPower(Double power)
    {
        this.power=power;
    }
    //End of the getters and setters methods for the power supply.

    //****************************************************************************************
    // METHOD: GETTERS AND SETTERS.
    // 2-MATERIAL AND VOLTAGE DROP.
    //****************************************************************************************

    /**
     * Getter method to get the list of materials.
     * @return : Return the string list with the material names.
     */
    public String[] getMaterialList()
    {
        return materialList;
    }

    /**
     * Getter method to get the material.
     * @return : Return the string with the material.
     */
    public String getMaterial()
    {
        return material;
    }

    /**
     * Getter method to get the voltage drop value.
     * @return : Return to double number with the voltage drop value.
     */
    public Double getVoltageDrop()
    {
        return voltageDrop;
    }

    /**
     * Getter method to get the voltage drop percentage value.
     * @return : Return to double number with the  voltage drop percentage value.
     */
    public Double getVoltageDropPercentage()
    {
        return voltageDropPercentage;
    }

    /**
     * Setter to add the material to this class.
     * @param material : String with the material.
     */
    public void setMaterial(String material)
    {
        this.material=material;
    }

    /**
     * Setter to add the voltage drop value to this class.
     * @param voltageDrop : Double voltage drop value.
     */
    public void setVoltageDrop(Double voltageDrop)
    {
        this.voltageDrop=voltageDrop;
    }

    /**
     * Setter to add the voltage drop percentage value to this class.
     * @param voltageDropPercentage : Double voltage drop percentage value.
     */
    public void setVoltageDropPercentage(Double voltageDropPercentage)
    {
        this.voltageDropPercentage=voltageDropPercentage;
    }

    //****************************************************************************************
    // METHOD: GETTERS AND SETTERS.
    // 3-WIRE.
    //****************************************************************************************

    /**
     * Getter method to get the list of sections.
     * @return : Return the string list with the sections.
     */
    public String[] getSectionList()
    {
        return sectionList;
    }

    /**
     * Getter method to get the section.
     * @return : Return to double value with the section size.
     */
    public Double getSection()
    {
        return section;
    }

    /**
     * Getter method to get the length.
     * @return : Return to double value with the length of the wire.
     */
    public Double getLength()
    {
        return length;
    }

    /**
     * Setter to add the section value to this class.
     * @param section : Double voltage drop value.
     */
    public void setSection(Double section)
    {
        this.section=section;
    }

    //****************************************************************************************
    // OTHERS METHODS. CALCULATING METHODS.
    // 1-POWER SUPPLY.
    //****************************************************************************************

    /**
     * Method to get the power (Power=Voltage x Current).
     * @return : Return to double value with the power.
     */
    public Double calculatePower()
    {
        Double power=voltage*current;

        return Math.ceil(power*Math.pow(10,2))/Math.pow(10,2);
    }

    /**
     * Method to get the current (Current=Power/Voltage).
     * @return : Return to double value with the current.
     */
    public Double calculateCurrent()
    {
        Double current=power/voltage;

        return Math.ceil(current*Math.pow(10,3))/Math.pow(10,3);
    }

    //****************************************************************************************
    // OTHERS METHODS. CALCULATING METHODS.
    // 2-MATERIAL AND VOLTAGE DROP.
    //****************************************************************************************

    /**
     * Method to get the voltage drop percentage (voltage drop percentage=(voltage drop/voltage)x100).
     * @return : Return to double value with the voltage drop percentage.
     */
    public Double calculateVoltageDropPercentage()
    {
        Double result=(voltageDrop/voltage)*100;

        return Math.round(result*Math.pow(10,1))/Math.pow(10,1);
    }

    //****************************************************************************************
    // OTHERS METHODS. CALCULATING METHODS.
    // 3-WIRE.
    //****************************************************************************************

    /**
     * Method to get the length of the wire. Formula -> L = Au x S / 2 x I x p.
     * L = Length of the wire (m).
     * Au = voltage drop (VDC).
     * S = Section (mm2).
     * I = Current (A).
     * p = Resistivity of the material (Ohms mm2/m).
     * @return : Return to double value with the length in meters.
     */
    public Double calculateLength()
    {
        Double p; //Resistivity material.

        switch(material)
        {
            case "Aluminum":
                p=0.026; //Assigning the resistivity of the aluminum.
                break;
            case "Cooper":
                p=0.0176; //Assigning the resistivity of the cooper.
                break;
            case "Steel":
                p=0.035; //Assigning the resistivity of the steel.
                break;
            default:
                p=0.0176; //Assigning the resistivity of the cooper as default value.
                break;
        }
        //End switch/case.

        Double result=(voltageDrop*section)/(2*current*p);

        return Math.round(result*Math.pow(10,2))/Math.pow(10,2);
    }

    //****************************************************************************************
    // OTHERS METHODS.
    //****************************************************************************************

    /**
     * Method to init the variables of the voltageDropData() class.
     */
    private void initVariables()
    {
        //Add values to the fields of the class.
        //1-Power supply.p=0.0; //Initiating resistivity variable.
        voltage=24.0;
        current=1.00;
        power=calculatePower();

        //2-Material and voltage drop.
        material="Cooper";
        voltageDrop=0.7;
        voltageDropPercentage=calculateVoltageDropPercentage();

        //3-Wire.
        section=1.5;
        length=calculateLength();
    }
    //End initVariables() method.
}
//End of the class voltageDropData().
