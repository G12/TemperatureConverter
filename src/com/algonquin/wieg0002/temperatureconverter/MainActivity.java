/**
 * Main Activity controls interactions between user interface and the Temperature Model
 * @author Thomas Wiegand (wieg0002)
 * @version 1.0 
 * 
 */

package com.algonquin.wieg0002.temperatureconverter;

import java.util.Observable;
import java.util.Observer;

import com.tinycoolthings.double_seekbar.DoubleSeekBar;

import model.TemperatureModel;
import android.app.Activity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;


public class MainActivity extends Activity implements Observer{

	private DoubleSeekBar seekCelsius;
	private DoubleSeekBar seekFahrenheit;
	private EditText celsius;
	private EditText fahrenheit;
	private TemperatureModel model;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        model = new TemperatureModel();
        model.addObserver(this);
        
        celsius = (EditText) findViewById(R.id.editTextCentigrade);
        celsius.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (KeyEvent.KEYCODE_ENTER == keyCode)
				{
					String str = ((EditText)v).getText().toString();
					try
					{
						Float test = Float.valueOf(str);
						if(test > TemperatureModel.MAX_CELCIUS)
						{
							makeToast(R.string.celsius, "<=", TemperatureModel.MAX_CELCIUS); 
						}
						else if(test < TemperatureModel.MIN_CELCIUS)
						{
							makeToast(R.string.celsius, ">=", TemperatureModel.MIN_CELCIUS); 
						}
						else
						{
							model.set_celsius_value(test);
						}
						return true; // indicate that we handled event, won't propagate it
					}
					catch(NumberFormatException ex){}
				}
		        return false; // when we don't handle other keys, propagate event further
			}
		});
        
        
        seekCelsius = (DoubleSeekBar) findViewById(R.id.doubleSeekBar1);
        seekCelsius.setMinValue(TemperatureModel.MIN_CELCIUS.intValue());
        seekCelsius.setMaxValue(TemperatureModel.MAX_CELCIUS.intValue());
        seekCelsius.setHasMax(false);
        seekCelsius.setUnits(getString(R.string.degrees));
        seekCelsius.setMinTitle(getString(R.string.celsius));
        seekCelsius.setTextSize((float) 30, TypedValue.COMPLEX_UNIT_PX);
        seekCelsius.registerOnChangeListenerMinSB(new OnSeekBarChangeListener()
        {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
			{
				//Integer current = seekCelsius.getCurrentMinValue();
				//Integer last = model.get_celsius_value().intValue();
				if(fromUser)
				{
					model.set_celsius_value(seekCelsius.getCurrentMinValue().floatValue());
				}
			}
		});
        
        fahrenheit = (EditText) findViewById(R.id.editTextFahrenheit);
        fahrenheit.setOnKeyListener(new OnKeyListener() {
 			@Override
 			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (KeyEvent.KEYCODE_ENTER == keyCode)
				{
					String str = ((EditText)v).getText().toString();
					try
					{
						Float test = Float.valueOf(str);
						if(test > TemperatureModel.MAX_FAHRENHEIT)
						{
							makeToast(R.string.celsius, "<=", TemperatureModel.MAX_FAHRENHEIT);
						}
						else if(test < TemperatureModel.MIN_FAHRENHEIT)
						{
							makeToast(R.string.celsius, ">=", TemperatureModel.MIN_FAHRENHEIT);
						}
						else
						{
							model.set_fahrenheit_value(test);
						}
						return true; // indicate that we handled event, won't propagate it
					}
					catch(NumberFormatException ex){}
		        }
		        return false; // when we don't handle other keys, propagate event further
 			}
 		});

        seekFahrenheit = (DoubleSeekBar) findViewById(R.id.doubleSeekBar2);
        seekFahrenheit.setMinValue(TemperatureModel.MIN_FAHRENHEIT.intValue());
        seekFahrenheit.setMaxValue(TemperatureModel.MAX_FAHRENHEIT.intValue());
        seekFahrenheit.setHasMax(false);
        seekFahrenheit.setUnits(getString(R.string.degrees));
        seekFahrenheit.setMinTitle(getString(R.string.fahrenheit));
        seekFahrenheit.setTextSize((float) 30, TypedValue.COMPLEX_UNIT_PX);
        
        seekFahrenheit.registerOnChangeListenerMinSB(new OnSeekBarChangeListener()
        {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar){}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser)
			{
				//Integer current = seekFahrenheit.getCurrentMinValue();
				//Integer last = model.get_fahrenheit_value().intValue();
				if(fromUser)
				{
					model.set_fahrenheit_value(seekFahrenheit.getCurrentMinValue().floatValue());
				}
			}
		});
        //Change the view to show Room Temperature (set by default constructor)
        changeView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.mainLayout) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void update(Observable observable, Object data) {
		changeView();
	}
	
	/**
	 * Updates all view objects with their corresponding model values
	 */
	private void changeView()
	{
		celsius.setText(model.get_celsius_value().toString());
		fahrenheit.setText(model.get_fahrenheit_value().toString());
		seekCelsius.setCurrentMinValue(model.get_celsius_value().intValue());
		seekFahrenheit.setCurrentMinValue(model.get_fahrenheit_value().intValue());
	}
	
	/**
	 * Display a Toast
	 * @param scaleId - The resource id of the scale string value: ie "Fahrenheit"
	 * @param operator - The string representing the logical operator to display: ie ">="
	 * @param val - The maximum or minimum value: ie TemperatureModel.MIN_FAHRENHEIT
	 */
	private void makeToast(int scaleId, String operator, Float val)
	{
		String strScale = getString(scaleId);
		String formatMsg = getString(R.string.msg);
		String msg = String.format(formatMsg, strScale, operator, val); 
		Toast.makeText(MainActivity.this, msg,Toast.LENGTH_SHORT).show();
	}

}
