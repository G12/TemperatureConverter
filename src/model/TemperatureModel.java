/**
 * Contains the corresponding celsius and fahrenheit values of a temperature.
 * When a celsius value is set it's corresponding fahrenheit value will be calculated.
 * When a fahrenheit value is set it's corresponding celsius value will be calculated.
 * 
 * @author Thomas Wiegand (wieg0002)
 * @version 1.0 
 * 
 */

package model;

import java.util.Observable;

public class TemperatureModel extends Observable{

	public static final Float CELCIUS_ROOM_TEMPERATURE;
	public static final Float MAX_CELCIUS;
	public static final Float MIN_CELCIUS;
	public static final Float MAX_FAHRENHEIT;
	public static final Float MIN_FAHRENHEIT;
	
	// Static initializer block. Initialize all class variables here.
	static {
		CELCIUS_ROOM_TEMPERATURE = 23f;
		MAX_CELCIUS = 50f;
		MIN_CELCIUS = -40f;
		MAX_FAHRENHEIT = 122f;
		MIN_FAHRENHEIT = -40f;
	}

	private Float _celsius_value;
	private Float _fahrenheit_value;
	/**
	 * Default Constructor
	 */
	public TemperatureModel(){
		this(CELCIUS_ROOM_TEMPERATURE);
	}
	
	/**
	 * Class constructor.
	 * @param celcius_default_val
	 */
	public TemperatureModel(Float celcius_default_val) 
	{
	    super();
	    _celsius_value = celcius_default_val;
	    _fahrenheit_value = toFahrenheit(_celsius_value);
	}
	
	public Float toFahrenheit(Float celcius_val)
	{
		return celcius_val * 9/5 + 32;
	}
	
	public Float toCelcius(Float fahrenheit_val)
	{
		return (fahrenheit_val - 32) * 5/9;
	}
	
	/**
	 * The model's state has changed!
	 *
	 * Notify all registered observers that this model has changed
	 * state, and the registered observers should change too.
	 */
	private void updateObservers() {		
	    this.setChanged();
	    this.notifyObservers();
	}

	
	@Override
	public String toString() {
		return "Celcius:" + _celsius_value + " Fahrenheit: " + _fahrenheit_value;
	}

	/**
	 * Test cases
	 * @param args
	 */
	public static void main(String[] args) {
		TemperatureModel model = new TemperatureModel();
		//Test Default constructor and toFahrenheit method
		System.out.println(model);
		//more
	}

	public Float get_celsius_value() {
		return _celsius_value;
	}

	/**
	 * Sets the celsius value, calulates and sets corresponding fahrenheit value
	 * calls updateObservers to notify registered observers of the change
	 * @param _celsius_value
	 */
	public void set_celsius_value(Float _celsius_value) {
		this._celsius_value = _celsius_value;
		this._fahrenheit_value = toFahrenheit(_celsius_value);
		updateObservers();
	}

	public Float get_fahrenheit_value() {
		return _fahrenheit_value;
	}

	/**
	 * Sets the fahrenheit value, calulates and sets corresponding celsius value
	 * calls updateObservers to notify registered observers of the change
	 * @param _fahrenheit_value
	 */
	public void set_fahrenheit_value(Float _fahrenheit_value) {
		this._fahrenheit_value = _fahrenheit_value;
		this._celsius_value = toCelcius(_fahrenheit_value);
		updateObservers();
	}

}
