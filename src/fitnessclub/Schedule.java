package fitnessclub;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Schedule {
    private static final int INITIAL_CAPACITY = 15;

    private FitnessClass [] classes;
    private int numClasses;

    public Schedule() {
        classes = new FitnessClass[INITIAL_CAPACITY];
        numClasses = 0;
    }

    public Schedule(FitnessClass[] classes, int numClasses) {
        this.classes = classes;
        this.numClasses = numClasses;
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


    private int find(FitnessClass fitnessClass) {
        for (int index = 0; index < numClasses; index++) {
            if (classes[index].equals(fitnessClass)) {
                return index;
            }
        }
        return -1;
    }


    public boolean contains(FitnessClass fitnessClass) {
        return find(fitnessClass) != -1;
    }

    public boolean add(FitnessClass fitnessClass) {
//        if (contains(fitnessClass)) {
//            return false;
//        }
        if (numClasses >= classes.length) { //intially had this as ==
            grow();
        }
        classes[numClasses] = fitnessClass;
        numClasses++;
        return true;
    }

    public boolean remove(FitnessClass fitnessClass) {
        int memberIndex = find(fitnessClass);
        if (memberIndex == -1) {
            return false;
        }
        classes[memberIndex] = null;
        for (int i = memberIndex; i < numClasses - 1; i++) {
            classes[i] = classes[i + 1];
        }
        numClasses--;
        return true;
    }

    public void load(File file) throws IOException {

        classes = new FitnessClass[INITIAL_CAPACITY];
        FitnessClass[] classesList = new FitnessClass[INITIAL_CAPACITY];

        Scanner scanner = new Scanner(file);
        System.out.println("-Fitness classes loaded-");
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.isEmpty()){
                line = scanner.nextLine();
            }
            String[] parts = line.split("\\s");
            Offer classType = Offer.valueOf(parts[0].toUpperCase().trim());
            Instructor instructor = Instructor.valueOf(parts[1].toUpperCase());
            Time time = Time.valueOf(parts[2].toUpperCase());
            Location location = Location.valueOf(parts[3].toUpperCase());

            //System.out.println(classType + " - " + instructor + ", " + time + ", " + location.getName());

            FitnessClass fitnessClass = new FitnessClass(classType, instructor, location, time);
            classes[i++] = fitnessClass;
        }
        printClasses();

        System.out.println("-end of class list-");
    }
    private void printClasses() {

        for (int i = 0; i < classes.length; i++) {
            System.out.println(classes[i].getClassInfo() + " - " + classes[i].getInstructor() + ", " + classes[i].getTime() + ", " + classes[i].getStudio().getName());
        }
    }
}
