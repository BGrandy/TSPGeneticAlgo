import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//If your chosen input file doesn't have numbering before the distances, comment out line 166. For example the files given to us are formatted like:
//1 38.24 20.42
//2 39.57 26.15
//etc. if your file doesn't have the 1, 2, ..., dst comment out line 166


public class GAHandler {


    public GAHandler() throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter 1 for report output or 2 for a single run on your own file following the file formatted stated in the header");
        String result = scan.nextLine();
        int value = Integer.parseInt(result);

        if (value == 1) {
            double time1 = System.currentTimeMillis(), time2;
            int seed = 0;

            File file1 = new File("./ulysses22.txt");
            Scanner s1 = new Scanner(file1);

            int size1 = s1.nextInt();
            double[][] TSP1 = new double[size1][2];
            for (int i = 0; i < size1; i++) {
                s1.nextInt();
                TSP1[i][0] = s1.nextDouble();
                TSP1[i][1] = s1.nextDouble();
            }
            for (int i = 0; i < 5; i++) {
                GA ga = new GA(250, 250, 4, 100, 1, 0, seed++, 1, TSP1);
                GA ga1 = new GA(250, 250, 4, 100, 1, 0, seed++, 05, TSP1);
                GA ga2 = new GA(250, 250, 4, 100, 1, 0, seed++, 10, TSP1);
                GA ga3 = new GA(250, 250, 4, 100, 2, 0, seed++, 1, TSP1);
                GA ga4 = new GA(250, 250, 4, 100, 2, 0, seed++, 05, TSP1);
                GA ga5 = new GA(250, 250, 4, 100, 2, 0, seed++, 10, TSP1);
            }
            System.out.println("10% done");
            for (int i = 0; i < 5; i++) {
                GA ga = new GA(250, 250, 4, 100, 1, 10, seed++, 1, TSP1);
                GA ga1 = new GA(250, 250, 4, 100, 1, 10, seed++, 05, TSP1);
                GA ga2 = new GA(250, 250, 4, 100, 1, 10, seed++, 10, TSP1);
                GA ga3 = new GA(250, 250, 4, 100, 2, 10, seed++, 1, TSP1);
                GA ga4 = new GA(250, 250, 4, 100, 2, 10, seed++, 05, TSP1);
                GA ga5 = new GA(250, 250, 4, 100, 2, 10, seed++, 10, TSP1);
            }
            System.out.println("20% done");
            for (int i = 0; i < 5; i++) {
                GA ga = new GA(250, 250, 4, 90, 1, 0, seed++, 1, TSP1);
                GA ga1 = new GA(250, 250, 4, 90, 1, 0, seed++, 05, TSP1);
                GA ga2 = new GA(250, 250, 4, 90, 1, 0, seed++, 10, TSP1);
                GA ga3 = new GA(250, 250, 4, 90, 2, 0, seed++, 1, TSP1);
                GA ga4 = new GA(250, 250, 4, 90, 2, 0, seed++, 05, TSP1);
                GA ga5 = new GA(250, 250, 4, 90, 2, 0, seed++, 10, TSP1);
            }
            System.out.println("30% done");
            for (int i = 0; i < 5; i++) {
                GA ga = new GA(250, 250, 4, 90, 1, 10, seed++, 1, TSP1);
                GA ga1 = new GA(250, 250, 4, 90, 1, 10, seed++, 05, TSP1);
                GA ga2 = new GA(250, 250, 4, 90, 1, 10, seed++, 10, TSP1);
                GA ga3 = new GA(250, 250, 4, 90, 2, 10, seed++, 1, TSP1);
                GA ga4 = new GA(250, 250, 4, 90, 2, 10, seed++, 05, TSP1);
                GA ga5 = new GA(250, 250, 4, 90, 2, 10, seed++, 10, TSP1);
            }
            System.out.println("40% done");
            for (int i = 0; i < 5; i++) {
                GA ga = new GA(250, 250, 4, 85, 1, 02, seed++, 1, TSP1);
                GA ga1 = new GA(250, 250, 4, 85, 1, 02, seed++, 05, TSP1);
                GA ga2 = new GA(250, 250, 4, 85, 1, 02, seed++, 10, TSP1);
                GA ga3 = new GA(250, 250, 4, 85, 2, 02, seed++, 1, TSP1);
                GA ga4 = new GA(250, 250, 4, 85, 2, 02, seed++, 05, TSP1);
                GA ga5 = new GA(250, 250, 4, 85, 2, 02, seed++, 10, TSP1);

            }
            System.out.println("50% done");

            File file2 = new File("./eil51.txt");
            Scanner s2 = new Scanner(file2);

            int size2 = s2.nextInt();
            double[][] TSP2 = new double[size2][2];
            for (int i = 0; i < size2; i++) {
                s2.nextInt();
                TSP2[i][0] = s2.nextDouble();
                TSP2[i][1] = s2.nextDouble();
            }
            for (int i = 0; i < 5; i++) {
                GA ga = new GA(250, 250, 4, 100, 1, 0, seed++, 1, TSP2);
                GA ga1 = new GA(250, 250, 4, 100, 1, 0, seed++, 05, TSP2);
                GA ga2 = new GA(250, 250, 4, 100, 1, 0, seed++, 10, TSP2);
                GA ga3 = new GA(250, 250, 4, 100, 2, 0, seed++, 1, TSP2);
                GA ga4 = new GA(250, 250, 4, 100, 2, 0, seed++, 05, TSP2);
                GA ga5 = new GA(250, 250, 4, 100, 2, 0, seed++, 10, TSP2);

            }
            System.out.println("60% done");
            for (int i = 0; i < 5; i++) {
                GA ga = new GA(250, 250, 4, 100, 1, 10, seed++, 1, TSP2);
                GA ga1 = new GA(250, 250, 4, 100, 1, 10, seed++, 05, TSP2);
                GA ga2 = new GA(250, 250, 4, 100, 1, 10, seed++, 10, TSP2);
                GA ga3 = new GA(250, 250, 4, 100, 2, 10, seed++, 1, TSP2);
                GA ga4 = new GA(250, 250, 4, 100, 2, 10, seed++, 05, TSP2);
                GA ga5 = new GA(250, 250, 4, 100, 2, 10, seed++, 10, TSP2);
            }
            System.out.println("70% done");
            for (int i = 0; i < 5; i++) {
                GA ga = new GA(250, 250, 4, 90, 1, 0, seed++, 1, TSP2);
                GA ga1 = new GA(250, 250, 4, 90, 1, 0, seed++, 05, TSP2);
                GA ga2 = new GA(250, 250, 4, 90, 1, 0, seed++, 10, TSP2);
                GA ga3 = new GA(250, 250, 4, 90, 2, 0, seed++, 1, TSP2);
                GA ga4 = new GA(250, 250, 4, 90, 2, 0, seed++, 05, TSP2);
                GA ga5 = new GA(250, 250, 4, 90, 2, 0, seed++, 10, TSP2);
            }
            System.out.println("80% done");
            for (int i = 0; i < 5; i++) {
                GA ga = new GA(250, 250, 4, 90, 1, 10, seed++, 1, TSP2);
                GA ga1 = new GA(250, 250, 4, 90, 1, 10, seed++, 05, TSP2);
                GA ga2 = new GA(250, 250, 4, 90, 1, 10, seed++, 10, TSP2);
                GA ga3 = new GA(250, 250, 4, 90, 2, 10, seed++, 1, TSP2);
                GA ga4 = new GA(250, 250, 4, 90, 2, 10, seed++, 05, TSP2);
                GA ga5 = new GA(250, 250, 4, 90, 2, 10, seed++, 10, TSP2);
            }
            System.out.println("90% done");
            for (int i = 0; i < 5; i++) {
                GA ga = new GA(250, 250, 4, 85, 1, 02, seed++, 1, TSP2);
                GA ga1 = new GA(250, 250, 4, 85, 1, 02, seed++, 05, TSP2);
                GA ga2 = new GA(250, 250, 4, 85, 1, 02, seed++, 10, TSP2);
                GA ga3 = new GA(250, 250, 4, 85, 2, 02, seed++, 1, TSP2);
                GA ga4 = new GA(250, 250, 4, 85, 2, 02, seed++, 05, TSP2);
                GA ga5 = new GA(250, 250, 4, 85, 2, 02, seed++, 10, TSP2);
            }

            System.out.println("100% done");

            time2 = System.currentTimeMillis();
            Double totalTime = time2 - time1;
            System.out.println("Total run time in miliseconds: " + totalTime + " Seconds: " + (totalTime / 1000) + " Minutes: " + (totalTime / 60000));

        } else if (value == 2) {
            JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            jfc.showOpenDialog(null); // get the return value
            File file = jfc.getSelectedFile(); // stores the file path
            try {
                Scanner s = new Scanner(file);
                int POPSize = s.nextInt();
                int generations = s.nextInt();
                int k = s.nextInt();
                int CR = s.nextInt();
                int TCR = s.nextInt();
                int MR = s.nextInt();
                int elitism = s.nextInt();
                int size = s.nextInt();
                if (elitism != 1) {
                    elitism = (POPSize / elitism);
                }
                double[][] TSP = new double[size][2];

                for (int i = 0; i < size; i++) {
                    s.nextInt();
                    TSP[i][0] = s.nextDouble();
                    TSP[i][1] = s.nextDouble();
                }

                GA ga = new GA(POPSize, generations, k, CR, TCR, MR, 1, elitism, TSP);

            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Incorrect input");
            GAHandler GAH = new GAHandler();
        }
    }


    public static void main(String[] args) throws IOException {
        GAHandler ga = new GAHandler();
    }
}
