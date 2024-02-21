package fitnessclub;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Schedule {
    private static final int TOTAL_CLASSES = 15;

    private FitnessClass[] classes;
    private int numClasses;

    public Schedule() {
        classes = new FitnessClass[TOTAL_CLASSES];
        numClasses = 0;
    }

    public FitnessClass[] getClasses() {
        return classes;
    }

    public int getNumClasses() {
        return numClasses;
    }

    public FitnessClass findClass(Offer classInfo, Instructor instructor, Location studio) {
        for (int i = 0; i < numClasses; i++) {
            if (classes[i].getClassInfo().equals(classInfo)
                    && classes[i].getInstructor().equals(instructor) && classes[i].getStudio().equals(studio)) {
                return classes[i];
            }
        }
        return null;
    }

    public void load(File file) throws IOException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\s");
            Offer classType = Offer.valueOf(parts[0].toUpperCase().trim());
            Instructor instructor = Instructor.valueOf(parts[1].toUpperCase());
            Time time = Time.valueOf(parts[2].toUpperCase());
            Location location = Location.valueOf(parts[3].toUpperCase());

            classes[numClasses] = new FitnessClass(classType, instructor, location, time);
            numClasses++;
        }
        scanner.close();
    }
}
