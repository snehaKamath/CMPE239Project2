/*package ProjectB.ProjectB;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import weka.classifiers.Evaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

public class KMeans {
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
		System.out.println("Started");

		SimpleKMeans kmeans = new SimpleKMeans();
 
		kmeans.setSeed(10);
 
		//important parameter to set: preserver order, number of cluster.
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(3);
 
		BufferedReader datafile = readDataFile("./census.arff"); 
		BufferedReader testFile = readDataFile("./census_test_updated.arff");

		Instances testData = new Instances(testFile);
		testData.setClassIndex(data.numAttributes() - 1);

		System.out.println("Building");

		kmeans.buildClusterer(data);
 
		System.out.println("Built");
		
		// Test the model
		Evaluation eTest = new Evaluation(data);
		eTest.evaluateModel(kmeans, testData); // Will evaluate on a multiple instance..
		
		// Print the result à la Weka explorer:
		String strSummary = eTest.toSummaryString();
		System.out.println(strSummary);

		// This array returns the cluster number (starting with 0) for each instance
		// The array has as many elements as the number of instances
		int[] assignments = kmeans.getAssignments();
 
		int i=0;
		for(int clusterNum : assignments) {
		    System.out.printf("Instance %d -> Cluster %d \n", i, clusterNum);
		    i++;
		}
	}
}
*/