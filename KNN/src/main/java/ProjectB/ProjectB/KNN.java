
package ProjectB.ProjectB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.unsupervised.attribute.Remove;
 
public class KNN {
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
		return inputReader;
	}
 
	public static void main(String[] args) throws Exception {

		// load CSV
		System.out.println("Reading Data Set File.....");

		BufferedReader datafile = readDataFile("./finalcensus.arff");
		BufferedReader testFile = readDataFile("./finalcensus_test.arff");
		
		Instances data = new Instances(datafile);
		System.out.println(data.numAttributes());		

		data.setClassIndex(data.numAttributes() - 3);

		Instances testData = new Instances(testFile);
		System.out.println(testData.numAttributes());		

		testData.setClassIndex(testData.numAttributes() - 3);
	
		IBk ibk = new IBk(1);
		ibk.setDebug(true);
				
		FilteredClassifier fc = new FilteredClassifier();		
		fc.setClassifier(ibk);

		// train and make predictions
		fc.buildClassifier(data);
		 
		System.out.println("After building");		

		// Train and Test the model
		Evaluation corssEval = new Evaluation(data);
		corssEval.crossValidateModel(fc, data, 10, new Random(1));
		
		String strSummary = corssEval.toSummaryString();
		System.out.println(strSummary);
		
		//From here I start to take User Input...
		Scanner sc = new Scanner(System.in);
		
		HashMap<String, Integer> states = new HashMap<String, Integer>();
        states.put("alabama", 1);
        states.put("alaska", 2);
        states.put("arizona", 3);
        states.put("arkansas", 4);
        states.put("california", 5);
        states.put("colorado", 6);
        states.put("connecticut", 7);
        states.put("delaware", 8);
        states.put("district of Columbia", 9);
        states.put("florida", 10);
        states.put("georgia", 11);
        states.put("hawaii", 12);
        states.put("idaho", 13);
        states.put("illinois", 14);
        states.put("indiana", 15);
        states.put("iowa", 16);
        states.put("kansas", 17);
        states.put("kentucky", 18);
        states.put("louisiana", 19);
        states.put("maine", 20);
        states.put("maryland", 21);
        states.put("massachusetts", 22);
        states.put("michigan", 23);
        states.put("minnesota", 24);
        states.put("mississippi", 25);
        states.put("missouri", 26);
        states.put("montana", 27);
        states.put("nebraska", 28);                                 
        states.put("new hampshire", 29);
        states.put("new jersey", 30);
        states.put("new mexico", 31);
        states.put("new york", 32);
        states.put("north carolina", 33);
        states.put("north dakota", 34);
        states.put("ohio", 35);
        states.put("oklahoma", 36);
        states.put("oregon", 37);
        states.put("pennsylvania", 38);
        states.put("rhode island", 39);                                 
        states.put("south carolina", 40);
        states.put("south dakota", 41);
        states.put("tennessee", 42);
        states.put("texas", 43);
        states.put("utah", 44);
        states.put("vermont", 45);
        states.put("virginia", 46);
        states.put("washington", 47);
        states.put("wisconsin", 48);                                 
        states.put("wyoming", 49);
        states.put("puerto rico", 50);                                 
		
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
		int elecMonthlyCost = getElecMonthlyCost(sc.nextInt());
		
		System.out.println("Gas Monthly Cost: ");			
		int gasMonthlyCost = getGasMonthlyCost(sc.nextInt());
		
		System.out.println("Your Financial Income: ");		
		int finIncome = getFinancialIncome(sc.nextInt());

		System.out.println("State(Lower Case): ");		
		String state = sc.next();		
		
		if(!states.containsKey(state))	{
			System.out.println("No such state in US");
			return;
		}
		
		int stateInt = states.get(state);

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
		 
		Attribute attrFincp = new Attribute("FINCP", 7);
		Attribute attrST = new Attribute("ST", 8);
		
		Instance instance = new Instance(9);
		instance.setValue(attrDivision, division);
		instance.setValue(attrAccess, access);
		instance.setValue(attrAcr, acre);
		instance.setValue(attrBDSP, numberOfBedrooms);
		instance.setValue(attrELEP, elecMonthlyCost);
		instance.setValue(attrGASP, gasMonthlyCost);
		instance.setValue(attrRNTPRange, "low");
		instance.setValue(attrFincp, finIncome);
		instance.setValue(attrST, stateInt);
		
		instance.setDataset(data);
		
		double cls = fc.classifyInstance(instance);
		if(Double.compare(cls, 0.0) == 0)	{
			System.out.println("Rent is low");			
		}
		
		if(Double.compare(cls, 1.0) == 0)	{
			System.out.println("Rent is Medium");
		}
		
		if(Double.compare(cls, 2.0) == 0)	{
			System.out.println("Rent is High");
		}	
	}

	private static int getFinancialIncome(int cost) {
		if(cost > -92000 && cost <= 0)	{
			return 0;			
		}
		
		if(cost > 0 && cost < 50000)	{
			return 1;
		}
		
		if(cost > 50000 && cost < 500000)	{
			return 2;
		}
		
		if(cost > 500000 && cost < 1000000)	{
			return 3;
		}
		
		return 3;
	}

	private static int getGasMonthlyCost(int cost) {
		if(cost < 180)	{
			return 0;			
		}
		if(cost >= 180 && cost < 360)	{
			return 1;
		}
		return 2;
	}

	private static int getElecMonthlyCost(int cost) {
		if(cost < 200)	{
			return 0;
		}
		if(cost >= 200 && cost < 400){
			return 1;
		}
		return 2;
	}
}