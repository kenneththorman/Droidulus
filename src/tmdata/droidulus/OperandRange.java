package tmdata.droidulus;

public class OperandRange {
	// Yes I know bad code missing getter/setter
	public int MinimumValue; // { get; set;} C#
	public int MaximumValue; // { get; set;} C#
	
	public OperandRange(int minValue, int maxValue){
		this.MinimumValue = minValue;
		this.MaximumValue = maxValue;
	}
}
