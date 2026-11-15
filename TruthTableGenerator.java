public class TruthTableGenerator {

    public static void main(String[] args) {

        boolean[] values = {true, false};

        System.out.println("P\tQ\tAND\tOR\tNOT P");

        for (boolean p : values) {
            for (boolean q : values) {

                boolean and = p && q;
                boolean or = p || q;
                boolean not = !p;

                System.out.println(p + "\t" + q + "\t"
                        + and + "\t" + or + "\t" + not);
            }
        }
    }
}
