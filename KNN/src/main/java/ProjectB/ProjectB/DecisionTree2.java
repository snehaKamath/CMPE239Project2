package ProjectB.ProjectB;
import weka.core.Instances;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import weka.classifiers.Classifier;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
public class DecisionTree2 {
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
	public static void main(String args[]) throws Exception {

	BufferedReader datafile = readDataFile("./census.arff");
	Instances data = new Instances(datafile);
    data.setClassIndex(data.numAttributes() - 1);
    String[] options = new String[2];
    options[0] = "-R";
    options[1] = "1";
    Instances train = new Instances(data)  ;       // from somewhere
    		 Instances test =new Instances(data);
    		 // filter
    		// Remove rm = new Remove();
    		// rm.setAttributeIndices("1");  // remove 1st attribute
    		 // classifier
    		 J48 j48 = new J48();
    		 j48.setUnpruned(true);        // using an unpruned J48
    		 // meta-classifier
    		 FilteredClassifier fc = new FilteredClassifier();
    		// fc.setFilter(rm);
    		 fc.setClassifier(j48);
    		 // train and make predictions
    		 fc.buildClassifier(train);
    		 for (int i = 0; i < test.numInstances(); i++) {
    		   double pred = fc.classifyInstance(test.instance(i));
    		   System.out.print("ID: " + test.instance(i).value(0));
    		   System.out.print(", actual: " + test.classAttribute().value((int) test.instance(i).classValue()));
    		   System.out.println(", predicted: " + test.classAttribute().value((int) pred));
	}
}
}
