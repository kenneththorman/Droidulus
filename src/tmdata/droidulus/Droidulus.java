package tmdata.droidulus;


import tmdata.droidulus.R;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Droidulus extends Activity {

	public static final String PREFS_NAME = "DroidulusSettings";
	private final int OPTIONS_ACTIVITY_REQUEST_CODE=0;
	
	private ProblemComposer problemComposer;
	private int points;
	private int answerAttempts;

	private TextView scoreView;
	private TextView problemView;
	private TableLayout visualGrid;
	private ImageButton helpButton;
	private Button answer1Button;
	private Button answer2Button;
	private Button answer3Button;
	private OnClickListener helpButtonListener; 
	private OnClickListener answerButton1Listener; 
	private OnClickListener answerButton2Listener; 
	private OnClickListener answerButton3Listener; 
	private Button[] answerButtons;

	private SharedPreferences sharedPreferences;
	private Options options;
	
    private Menu menu;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    	// Keep this code here so the score is not reset every time you change the settings
        setContentView(R.layout.main);
        scoreView = (TextView)findViewById(R.id.scoreView);
        scoreView.setText(String.format("%s %s",getString(R.string.score), getString(R.string.initialScore)));
        scoreView.setTextColor(Color.GREEN);
        
        initilize();
        presentProblem();
    }
    
    /** Called when clicking the menu button. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        
        // Inflate the currently selected menu XML resource.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.title_only, menu);
        
        return true;
    }

    /** Called when a menu item in the menu is clicked. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuabout:
                Toast.makeText(this, "Droidulus 1.0 by Kenneth Thorman", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.menuoptions:
            	Intent intent = new Intent(Droidulus.this, OptionsScreen.class);
            	startActivityForResult(intent, OPTIONS_ACTIVITY_REQUEST_CODE);
                return true;
                
            // Generic catch all for all the other menu resources
            default:
                if (!item.hasSubMenu()) {
                    return true;
                }
                break;
        }
        
        return false;
    }

    /** Called when a sub/child activity/form/screen is exiting. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        // We have change the saved settings so we need to initialize the application again
        initilize();
        presentProblem();
    }
    
    private void getSavedSettings()
    {
    	sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
    	options.Operator = sharedPreferences.getString("operator", "+");
        options.Operator1 =  new OperandRange(sharedPreferences.getInt("operator1min", 0), sharedPreferences.getInt("operator1max", 10));
        options.Operator2 =  new OperandRange(sharedPreferences.getInt("operator2min", 0), sharedPreferences.getInt("operator2max", 10));
    }

    /** UI initialization code */
    private void initilize()
	{
    	// Initialize from saved settings (set in options screen)
    	options = new Options();
    	getSavedSettings();
    	problemComposer = ProblemComposerFactory.Create(options);
        
        problemView = (TextView)findViewById(R.id.problemView);
        
        // AnswerButtonListener1
        answerButton1Listener = new OnClickListener() {
    	    public void onClick(View v) {
    	      verifyUserAnswer(v);
    	    }
    	};
        answer1Button = (Button)findViewById(R.id.answer1);
        answer1Button.setOnClickListener(answerButton1Listener);

        // AnswerButtonListener2
        answerButton2Listener = new OnClickListener() {
    	    public void onClick(View v) {
    	      verifyUserAnswer(v);
    	    }
    	};
        answer2Button = (Button)findViewById(R.id.answer2);
        answer2Button.setOnClickListener(answerButton2Listener);

        // AnswerButton3Listener
        answerButton3Listener = new OnClickListener() {
    	    public void onClick(View v) {
    	      verifyUserAnswer(v);
    	    }
    	};
        answer3Button = (Button)findViewById(R.id.answer3);
        answer3Button.setOnClickListener(answerButton3Listener);
        
        answerButtons = new Button[] {answer1Button, answer2Button, answer3Button};
        
        // AnswerButton3Listener
        helpButtonListener = new OnClickListener() {
    	    public void onClick(View v) {
    	    	showProblemHelp();
    	    }
    	};
        helpButton = (ImageButton) findViewById(R.id.helpButton);
        helpButton.setOnClickListener(helpButtonListener);

        visualGrid = (TableLayout)findViewById(R.id.myTableLayout);
	}
    
    private void presentProblem()
    {
        problemComposer.GenerateProblem();
		problemView.setText(problemComposer.GetProblemText(false));
		problemComposer.GenerateAnswerPossibilities(answerButtons);
		visualGrid.setVisibility(View.GONE);
		helpButton.setVisibility(View.VISIBLE);
    }
    
    private void showProblemHelp()
    {
    	helpButton.setVisibility(View.GONE);
    	visualGrid.setVisibility(View.VISIBLE);
    	problemComposer.VisualizeProblem(visualGrid, answerButtons);
    	
    }
	private void verifyUserAnswer(final View view){
		answerAttempts++;
		// Disable further user input when the text is green/red otherwise the user can click many times on the buttons
		problemComposer.enableViews(answerButtons, false);
		
		int result = Integer.parseInt(view.getTag().toString());
		if (problemComposer.getCorrectAnswer() == result)
		{
			problemView.setTextColor(Color.GREEN);
			problemView.setText(problemComposer.GetProblemText(true));	// Show full problem + correct result
			points++;								
			
			// Wait 1 second before resetting the text color and showing a new problem text
			new Handler().postDelayed(new Runnable() { 
				public void run() { 
					problemView.setTextColor(Color.WHITE); 
					presentProblem();	// Generate a new problem and update UI
					problemComposer.enableViews(answerButtons, true);
				} 
			}, 1000);
		}
		else{
			problemView.setTextColor(Color.RED);	
			problemComposer.enableViews(answerButtons, true);
		}
		scoreView.setText(String.format("%s %d/%d", getString(R.string.score), points, answerAttempts));
	}
}