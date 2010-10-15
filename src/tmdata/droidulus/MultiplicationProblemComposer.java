package tmdata.droidulus;

import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MultiplicationProblemComposer extends ProblemComposer {

	public MultiplicationProblemComposer(OperandRange operand1Range, OperandRange operand2Range)
	{
		super(operand1Range, operand2Range);
		this.operator = "*";
	}
	
	@Override
	public void VisualizeProblem(TableLayout visualGrid, Button[] uiViewsToDisableDuringVisualization) {
		enableViews(uiViewsToDisableDuringVisualization, false);
		visualGrid.removeAllViews();
		if (correctAnswer > 100){
			Toast.makeText(visualGrid.getContext(), "Not showing more that 100 items.", Toast.LENGTH_SHORT).show();			
		}
		else{
			for (int i=0; i<operand1; i++)
		    {
				// Create a new row to be added. 
		        TableRow tr = new TableRow(visualGrid.getContext());
		        tr.setLayoutParams(new LayoutParams(
		                       LayoutParams.FILL_PARENT,
		                       LayoutParams.WRAP_CONTENT));
				
		        for (int j=0; j<operand2; j++)
			    {
		            // Create the columns in the row
		        	ImageView imageView = new ImageView(visualGrid.getContext());
		            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		            imageView.setImageResource(R.drawable.apple);
		            tr.addView(imageView);
			    }
				
		        // Add the row to table
		        visualGrid.addView(tr, new TableLayout.LayoutParams(
			          LayoutParams.FILL_PARENT,
			          LayoutParams.WRAP_CONTENT));
		    }
		}
		enableViews(uiViewsToDisableDuringVisualization, true);		
	}
}
