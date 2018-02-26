package druidmetrics;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JavaMetric newJavaMetric = new JavaMetric();
        
        newJavaMetric.calculateNoOfOperators("C:\\Users\\Nico\\Documents\\NetBeansProjects\\DependableSystemsPLC\\DruidMetrics\\Test files\\Test file Halstead.txt");
        newJavaMetric.calculateNoOfOperands("C:\\Users\\Nico\\Documents\\NetBeansProjects\\DependableSystemsPLC\\DruidMetrics\\Test files\\Test file Halstead.txt");
        
        // TODO code application logic here
    }
    
}
