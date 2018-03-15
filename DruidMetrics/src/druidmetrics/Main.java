package druidmetrics;


public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JavaMetric newJavaMetric = new JavaMetric("C:\\Users\\Nico\\Documents\\NetBeansProjects\\DependableSystemsPLC\\DruidMetrics\\Test files\\Test file Halstead.txt");
        System.out.println(newJavaMetric.calculateCommentPercentage());
    }
    
}
