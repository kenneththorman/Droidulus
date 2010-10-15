package tmdata.droidulus;

import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class AdditionProblemComposer extends ProblemComposer {

	public AdditionProblemComposer(OperandRange operand1Range, OperandRange operand2Range)
	{
		super(operand1Range, operand2Range);
		this.operator = "+";
	}

	@Override
	public void VisualizeProblem(TableLayout visualGrid, Button[] uiViewsToDisableDuringVisualization) {
		enableViews(uiViewsToDisableDuringVisualization, false);
		visualGrid.removeAllViews();
		if (correctAnswer > 100){
			Toast.makeText(visualGrid.getContext(), "Not showing more that 100 items.", Toast.LENGTH_SHORT).show();			
		}
		else{
			visualizeOperand(visualGrid, operand1, R.drawable.apple);
			visualizeOperand(visualGrid, operand2, R.drawable.strawberry);
		}
		enableViews(uiViewsToDisableDuringVisualization, true);
	}
	
	private void visualizeOperand(TableLayout visualGrid, int operand, int imageResId)
	{
        int counter = 0;
        TableRow tr = null;
		while (counter < operand) {

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
            imageView.setImageResource(imageResId);
            tr.addView(imageView);
			counter++;
	    }
	}
}
