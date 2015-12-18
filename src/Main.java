import analizator.AnalizatorLexical;

public class Main {

    public static void main(String[] args) {
        AnalizatorLexical analizatorLexical = new AnalizatorLexical();
        analizatorLexical.generateTables("./inputs/suma.txt");
        System.out.println("Fisierele TS si FIP generate cu succes!");
    }
}
