import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = null;
        int count = 0;
        
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            count++;
            if (count == 1 || StdRandom.bernoulli(1.0 / count)) {
                champion = word;
            }
        }
        
        if (count > 0) {
            System.out.println(champion);
        }
    }
}