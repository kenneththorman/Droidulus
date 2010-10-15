package tmdata.droidulus;

public class ProblemComposerFactory {
	public static ProblemComposer Create(Options options){
    	if (options.Operator.equals("+"))
    	{
    		return new AdditionProblemComposer(options.Operator1,options.Operator2);
    	}
    	else if (options.Operator.equals("-"))
    	{
    		return new SubtractionProblemComposer(options.Operator1,options.Operator2);
    	}
    	else 
    	{
    		return new MultiplicationProblemComposer(options.Operator1,options.Operator2);
    	}
	}
}
