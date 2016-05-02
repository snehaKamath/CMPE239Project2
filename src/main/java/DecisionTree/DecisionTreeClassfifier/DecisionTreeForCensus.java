package DecisionTree.DecisionTreeClassfifier;

import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;
import java.util.Scanner;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;


public class DecisionTreeForCensus {
	// Function to read the dataset file.
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
        // Read the census data in arff format. 
		// Original format was in csv. 
		// It was converted to arff using a command line API previously to avoid conversion overhead in code.
		BufferedReader datafile = readDataFile("./fcensus.arff");
		
		// Map the dataset instances to the instances object.
		Instances traindata = new Instances(datafile);
		
		// Set the column number of the class that has to be predicted as output.
		traindata.setClassIndex(6);
		String[] options = new String[2];
		options[0] = "-R";
		options[1] = "1";
		
		// Build the training instances
		Instances train = new Instances(traindata);
       
		// Build the test set.
		BufferedReader testfile = readDataFile("./fcensus_test.arff");
		Instances testdata = new Instances(testfile);
		testdata.setClassIndex(6);
		Instances test = new Instances(testdata);
        
		// Remove class removes the attribute column which we do not need.
		Remove rm = new Remove();
		
		// Remove the 8th attribute from the dataset. 
		rm.setAttributeIndices("8"); 
	
		// Build the j48 decision tree classifier.
		J48 j48 = new J48();
		
		// Set the pruning option to true or false
		j48.setUnpruned(false); 
		
		// meta-classifier. Filter classifer to filter and remove the attributes from the previous step.
		FilteredClassifier fc = new FilteredClassifier();
		fc.setFilter(rm);
		fc.setClassifier(j48);
		
		// train and make predictions
		fc.buildClassifier(train);
		
		// Evaluate the classifier.
		
		  Evaluation eval=new Evaluation(train);
		
		// Use n fold cross validation method with 'n' value specified as a parameter.
		eval.crossValidateModel(fc, train, 10, new Random(1));
		
		  // Print the evaluation results.
		System.out.println(eval.toSummaryString());
		
		// Evaluate results for test set. 
		for (int i = 0; i < test.numInstances(); i++) {
			double pred = fc.classifyInstance(test.instance(i));
			System.out.print("ID: " + test.instance(i).value(0));
			System.out.print(", actual: " + test.classAttribute().value((int) test.instance(i).classValue()));
			System.out.println(", predicted: " + test.classAttribute().value((int) pred));
		}
		
		
		
        // Take user input and predict the rent range.
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Division: ");	
		System.out.println("Enter the Division: ");
		System.out.println("0 .Puerto Rico");
        System.out.println("1 .New England (Northeast region)");
        System.out.println("2 .Middle Atlantic (Northeast region)");
        System.out.println("3 .East North Central (Midwest region)");
        System.out.println("4 .West North Central (Midwest region)");
        System.out.println("5 .South Atlantic (South region)");       
        System.out.println("6 .East South Central (South region)");
        System.out.println("7 .West South Central (South Region)");
        System.out.println("8 .Mountain (West region)");
        System.out.println("9 .Pacific (West region)");
		int division = sc.nextInt();
		
		System.out.println("Do you need Broadband Access(1 for yes OR 2 or NO): ");		
		int access = sc.nextInt();
		
		System.out.println("Size of resident plot:(1 for < one acre) OR 2 for 1 < acre < 10 OR 3 for > 10 acres");		
		int acre = sc.nextInt();
		
		System.out.println("Number of Bedrooms: ");		
		int numberOfBedrooms = sc.nextInt();
		
		System.out.println("Electricity Monthly Cost: ");		
		int elecMonthlyCost = sc.nextInt();
		
		System.out.println("Gas Monthly Cost: ");			
		int gasMonthlyCost = sc.nextInt();
		
		System.out.println("Your Financial Income: ");		
		int finIncome = sc.nextInt();
		Attribute attrDivision = new Attribute("Division", 0);
		Attribute attrAccess = new Attribute("ACCESS", 1);
		Attribute attrAcr = new Attribute("ACR", 2);
		Attribute attrBDSP = new Attribute("BDSP", 3);
		Attribute attrELEP = new Attribute("ELEP", 4);
		Attribute attrGASP = new Attribute("GASP", 5);
		 
		// Declare a nominal attribute along with its values
		FastVector fvNominalVal = new FastVector(3);
		fvNominalVal.addElement("low");
		fvNominalVal.addElement("medium");
		fvNominalVal.addElement("high");
		Attribute attrRNTPRange = new Attribute("RNTPRange", fvNominalVal, 6);
		 Attribute attrFincp = new Attribute("FINCP", 8);
		Instance instance = new Instance(9);
		instance.setValue(attrDivision, division);
		instance.setValue(attrAccess, access);
		instance.setValue(attrAcr, acre);
		instance.setValue(attrBDSP, numberOfBedrooms);
		instance.setValue(attrELEP, elecMonthlyCost);
		instance.setValue(attrGASP, gasMonthlyCost);
		instance.setValue(attrRNTPRange, "low");
		instance.setValue(attrFincp, finIncome);
		instance.setDataset(traindata);
		
		double cls=fc.classifyInstance(instance);
		if (Double.compare(cls, 0.0)==0) {
			System.out.println("Rent is low");
		}
		if (Double.compare(cls, 1.0)==0) {
			System.out.println("Rent is medium");
		}
		if (Double.compare(cls, 2.0)==0) {
			System.out.println("Rent is high");
		}
		
		
		
		
	}
}
