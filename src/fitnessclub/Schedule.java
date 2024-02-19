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


    public FitnessClass[] getClasses() {
        return classes;
    }

    public int getNumClasses() {
        return numClasses;
    }


    //DO We need this?
    private void grow() {
        FitnessClass[] newClasses = new FitnessClass[classes.length + INITIAL_CAPACITY];
        for (int i = 0; i < classes.length; i++) {
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


    public void add(FitnessClass fitnessClass) {
        if (numClasses == classes.length) grow();
        classes[numClasses++] = fitnessClass;
        /*
        if (contains(fitnessClass)) { //CHANGED from contains()
            return false;
        }
        if (numClasses >= classes.length) { //intially had this as ==
            grow();
        }
        classes[numClasses] = fitnessClass;
        numClasses++;
        return true;

         */
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
    public FitnessClass findClass(Offer classInfo, Instructor instructor, Location studio) {
        for (int i = 0; i < numClasses; i++) {
            FitnessClass fClass = classes[i];
            if (fClass.getClassInfo().equals(classInfo) && fClass.getInstructor().equals(instructor) && fClass.getStudio().equals(studio)) {
                return fClass;
            }
        }
        return null;
    }

    public void load(File file) throws IOException {

        //classes = new FitnessClass[INITIAL_CAPACITY];
        //FitnessClass[] classesList = new FitnessClass[INITIAL_CAPACITY];

        Scanner scanner = new Scanner(file);
        System.out.println("-Fitness classes loaded-");
        //int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.isEmpty()){
                continue;
            }
            String[] parts = line.split("\\s");
            Offer classType = Offer.valueOf(parts[0].toUpperCase().trim());
            Instructor instructor = Instructor.valueOf(parts[1].toUpperCase());
            Time time = Time.valueOf(parts[2].toUpperCase());
            Location location = Location.valueOf(parts[3].toUpperCase());

            //System.out.println(classType + " - " + instructor + ", " + time + ", " + location.getName());

            add(new FitnessClass(classType, instructor, location, time));

//            FitnessClass fitnessClass = new FitnessClass(classType, instructor, location, time);
//            classes[i++] = fitnessClass;
//            numClasses++;
        }
        scanner.close();
        printClasses();

        System.out.println("-end of class list.");
    }
    public void printClasses() {

        for (int i = 0; i < classes.length; i++) {
            System.out.println(classes[i].getClassInfo() + " - " + classes[i].getInstructor() + ", " + classes[i].getTime() + ", " + classes[i].getStudio().getName());
        }
    }

    public int findClass(FitnessClass fitnessClass) {
        for (int index = 0; index < classes.length; index++) {

            if (classes[index].getClassInfo().equals(fitnessClass.getClassInfo())
                    && classes[index].getInstructor().equals(fitnessClass.getInstructor())
                    && classes[index].getStudio().getName().equals(fitnessClass.getStudio().getName())) {

                return index;
            }
        }
        return -1;
    }

    public boolean containsClass(FitnessClass fitnessClass) {

        return findClass(fitnessClass) != -1;
    }

}
