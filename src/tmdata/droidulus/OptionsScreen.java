package tmdata.droidulus;


import tmdata.droidulus.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class OptionsScreen extends Activity {

	public static final String PREFS_NAME = "DroidulusSettings";

	private Button saveSettingsButton;
	private OnClickListener saveSettingsButtonListener; 
	private Spinner operatorSpinner;
	private EditText operator1min;
	private EditText operator1max;
	private EditText operator2min;
	private EditText operator2max;
	private CheckBox showPossibleAnswers;
	private SharedPreferences sharedPreferences;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	setContentView(R.layout.optionsscreen);
    	initilize();
    }

    /** UI initialization code */
    private void initilize()
	{
        sharedPreferences = getSharedPreferences(PREFS_NAME, 0);

        // Operator 1 minimum value
        operator1min = (EditText) findViewById(R.id.operator1min);
        operator1min.setText(Integer.toString(sharedPreferences.getInt("operator1min", 0))); 

        // Operator 1 max value
        operator1max = (EditText) findViewById(R.id.operator1max);
        operator1max.setText(Integer.toString(sharedPreferences.getInt("operator1max", 10))); 
        
        // Operator 2 minimum value
        operator2min = (EditText) findViewById(R.id.operator2min);
        operator2min.setText(Integer.toString(sharedPreferences.getInt("operator2min", 0)));
        
        // Operator 2 max value
        operator2max = (EditText) findViewById(R.id.operator2max);
        operator2max.setText(Integer.toString(sharedPreferences.getInt("operator2max", 10))); 

        showPossibleAnswers = (CheckBox) findViewById(R.id.showPossibleAnswers);
        showPossibleAnswers.setChecked(sharedPreferences.getBoolean("showPossibleAnswers", true)); 
        
        // Operator spinner / drop down
        operatorSpinner = (Spinner) findViewById(R.id.operators);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.operators_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        operatorSpinner.setAdapter(adapter);
        operatorSpinner.setSelection(adapter.getPosition(sharedPreferences.getString("operator", "+")), false);
        
        // Save Settings Button
        saveSettingsButtonListener = new OnClickListener() {
    	    public void onClick(View v) {
    	    	saveAndReturnToCaller();
    	    }
    	};
        saveSettingsButton = (Button)findViewById(R.id.saveOptions);
        saveSettingsButton.setOnClickListener(saveSettingsButtonListener);
	}

    private void saveAndReturnToCaller()
    {
    	saveSettings();
    	setResult(RESULT_OK, null);
        finish();
    }

    private void saveSettings()
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("operator", operatorSpinner.getSelectedItem().toString());
        editor.putInt("operator1min", Integer.parseInt(operator1min.getText().toString()));
        editor.putInt("operator1max", Integer.parseInt(operator1max.getText().toString()));
        editor.putInt("operator2min", Integer.parseInt(operator2min.getText().toString()));
        editor.putInt("operator2max", Integer.parseInt(operator2max.getText().toString()));
        editor.putBoolean("showPossibleAnswers", showPossibleAnswers.isChecked());
        editor.commit();
    }
}