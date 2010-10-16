package tmdata.droidulus;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class SubtractionProblemComposer extends ProblemComposer {

	private ArrayList<View> operand2Views = new ArrayList<View>();
	
	public SubtractionProblemComposer(OperandRange operand1Range, OperandRange operand2Range)
	{
		super(operand1Range, operand2Range);
		this.operator = "-";
	}

	@Override
	public void GenerateProblem() {
		operand1 = RandomHelper.GetRandomNumberInRange(operand1Range);
		operand2 = RandomHelper.GetRandomNumberInRange(operand2Range);
		
		// To avoid having to visualize negative results (how do you do that?)
		// We make sure the first operand is always the largest
		if (operand1 < operand2){
			swapOperands();
		}
		
		correctAnswer = operand1 - operand2;
	}

	@Override
	public String GetProblemText(Boolean showCorrectAnswer){
		return String.format("%d %s %d = %s", operand1, operator, operand2, (showCorrectAnswer ? correctAnswer : ""));
	}

	@Override
	public void VisualizeProblem(TableLayout visualGrid, final Button[] uiViewsToDisableDuringVisualization) {
		enableViews(uiViewsToDisableDuringVisualization, false);
		operand2Views.clear();
		visualGrid.removeAllViews();
		int rowsNeededToVisualize = occupiesVisualRows(operand1) + occupiesVisualRows(operand2);
		if (rowsNeededToVisualize > 10){
			Toast.makeText(visualGrid.getContext(), "Not enough room to visualize.", Toast.LENGTH_SHORT).show();			
		}
		else{
	        int counter = 0;
	        TableRow tr = null;
			while (counter < operand1) {
	
				if (counter % 10 == 0){
					// Create a new row to be added. 
			        tr = new TableRow(visualGrid.getContext());
			        tr.setLayoutParams(new LayoutParams(
			                       LayoutParams.FILL_PARENT,
			                       LayoutParams.WRAP_CONTENT));
			        // Add the row to table
			        visualGrid.addView(tr, new TableLayout.LayoutParams(
				          LayoutParams.FILL_PARENT,
				          LayoutParams.WRAP_CONTENT));
				}
	            // Create the columns in the row
	        	ImageView imageView = new ImageView(visualGrid.getContext());
	            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
	            imageView.setImageResource(R.drawable.apple);
	
	            if (counter >= correctAnswer) {
					// Save in arrayList so we can remove later
					operand2Views.add(imageView);
				}
	            tr.addView(imageView);
				counter++;
		    }
	
			// Fade out all the image views that are representing operand2
			for(View view : operand2Views){
				Animation myFadeOutAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.fadeout);
				myFadeOutAnimation.setFillAfter(true);
				view.startAnimation(myFadeOutAnimation); //Set animation to your ImageView
			}
			enableViews(uiViewsToDisableDuringVisualization, true);
		}
	}
}
