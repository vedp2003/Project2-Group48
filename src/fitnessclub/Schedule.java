package fitnessclub;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Schedule {
    private static final int INITIAL_CAPACITY = 10;  //GPT did this - idk why u need

    private FitnessClass [] classes;
    private int numClasses;

    public Schedule() {
        classes = new FitnessClass[INITIAL_CAPACITY];
        numClasses = 0;
    }

    public FitnessClass[] getClasses() {
        return classes;
    }

    public int getNumClasses() {
        return numClasses;
    }

    //DO We need this?
    private void grow() {
        FitnessClass[] newClasses = new FitnessClass[classes.length + INITIAL_CAPACITY];
        for (int i = 0; i < numClasses; i++) {
            newClasses[i] = classes[i];
        }
        classes = newClasses;
    }

    public void load(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            FitnessClass newClass = parseFitnessClass(line);
            if (newClass != null) {
                if (numClasses == classes.length) {
                    grow();
                }
                classes[numClasses++] = newClass;
            }
        }
        scanner.close();
    }

    private FitnessClass parseFitnessClass(String line) {
        // This method needs to parse a line of text into a FitnessClass object based on classSchedule.txt file
        return null;
    }

}
