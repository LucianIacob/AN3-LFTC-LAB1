package analizator;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with love by Lucian and @Pi on 15.12.2015.
 */
public class AnalizatorLexical {

    // cuvinte rezervate
    private Map<String, Integer> cuvinteRezervate;
    private static final String fisierCuvinteRezervate = "././inputs/cuvinteRezervate.txt";

    // dictionar separatori
    private Map<String, Integer> separatori;
    private static final String fisierSeparatori = "././inputs/separatori.txt";

    // dictionar operatori
    private Map<String, Integer> operatori;
    private static final String fisierOperatori = "././inputs/operatori.txt";

    private Pattern identificator;
    private Pattern constanta;

    // lista tabela de somboluri
    private List<TS> tabelaSimboluri;

    // lista atomi
    private List<String> atomi;

    // indice contor
    int contor;

    // citire atomi
    public AnalizatorLexical() {
        cuvinteRezervate = incarcaCodificari(fisierCuvinteRezervate);
        separatori = incarcaCodificari(fisierSeparatori);
        operatori = incarcaCodificari(fisierOperatori);

        contor = cuvinteRezervate.size() + separatori.size() + operatori.size();

        // sabloane pt identificatori si constante
        identificator = Pattern.compile("[_a-zA-Z][_a-zA-Z0-9]{0,30}");
        constanta = Pattern.compile("(^0|[+|-]?[1-9]|[+|-]?[1-9][0-9]+$)|(^-?0.[0-9]+|[+|-]?[1-9][0-9]+.[0-9]+|[+|-]?[1-9]+.[0-9]+$)|(^'.*'$)");

        tabelaSimboluri = new ArrayList<>();
        atomi = new ArrayList<>();
    }

    private Map<String, Integer> incarcaCodificari(String fisier) {

        Map<String, Integer> list = new HashMap<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fisier));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] items = line.split(" ");
                if (items.length < 2) {
                    System.err.println("Invalid line " + line);
                    continue;
                }
                list.put(items[0], Integer.parseInt(items[1]));
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("File " + fisier + " not found");
        } catch (IOException e) {
            System.err.println("Unable to read from " + fisier);
        }
        return list;
    }

    public void generateTables(String file) {

        parseCodSursa(file);

        for (String atom : atomi) {
            if (!(isCuvantRezervat(atom) || isOperator(atom) || isSeparator(atom)))
                if (isIdentificator(atom)) {
                    if (!TScontainsAtom(atom)) {
                        TS identificator = new TS(contor++, atom);
                        tabelaSimboluri.add(identificator);
                    }
                }
                else if (isConstanta(atom)) {
                    if (!TScontainsAtom(atom)) {
                        TS constanta = new TS(contor++, atom);
                        tabelaSimboluri.add(constanta);
                    }
                }
        }

        writeToFiles("././outputs/TS.txt", "././outputs/FIP.txt", file);
    }

    private void writeToFiles(String ts, String fip, String file) {
        writeTStoFile(ts);
        writeFIPtoFile(fip, file);
    }

    private void writeTStoFile(String TSfile) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(TSfile));
            for (TS ts : tabelaSimboluri) {
                bufferedWriter.write(ts.getSimbol() + " " + ts.getPozitieTS());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }	catch (IOException ex) {
            System.err.println("Unable to write to TS file");
        }
    }

    private void writeFIPtoFile(String FIPfile, String source) {

        try {
            BufferedWriter bufferedWriter = new BufferedWriter (new FileWriter(FIPfile));
            BufferedReader bufferedReader = new BufferedReader (new FileReader(source));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.replaceAll("\t", "");
                String[] splitLine = line.split(" ");
                for (String atom : splitLine) {
                    if (isCuvantRezervat(atom) || isOperator(atom) || isSeparator(atom)) {
                        bufferedWriter.write(getCodAtom(atom) + " ");
                    }
                    else {
                        bufferedWriter.write(getPositionFromTs(atom) + " ");
                    }
                }
                bufferedWriter.newLine();
            }
            bufferedReader.close();
            bufferedWriter.close();
        }	catch (IOException ex) {
            System.err.println("Unable to write to FIP file");
        }

    }

    private int getCodAtom(String numeAtom){
        //System.out.println(numeAtom);
        if (cuvinteRezervate.containsKey(numeAtom))
            return cuvinteRezervate.get(numeAtom);
        else {
            if (separatori.containsKey(numeAtom))
                return separatori.get(numeAtom);
            else
                return operatori.get(numeAtom);
        }
    }

    private int getPositionFromTs(String atom){
        //System.out.println(atom);
        for (TS ts : tabelaSimboluri) {
            if(ts.getSimbol().equals(atom))
                return ts.getPozitieTS();
        }
        return -1;
    }

    private boolean TScontainsAtom(String atom) {

        for (TS ts : tabelaSimboluri) {
            if (ts.getSimbol().equals(atom))
                return true;
        }
        return false;
    }

    private void parseCodSursa(String file) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line;
            int count = 1;

            while ((line = bufferedReader.readLine()) != null) {
                getAtomiFromLine(line, count++);
            }

            bufferedReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("Fisierul nu exista");
        } catch (IOException e) {
            System.err.println("eroare la citire");
        }
    }

    private void getAtomiFromLine(String line, int i) {

        line = line.replaceAll("\t", "");

        String[] splitLine = line.split(" ");
        for (String atom : splitLine) {
            if (atom != "") {
                if (isConstanta(atom) || isCuvantRezervat(atom) || isIdentificator(atom) || isOperator(atom) || isSeparator(atom)) {
                    atomi.add(atom);
                }
                else {
                    System.err.println("error la linia " + i);
                }
            }
        }
    }


    private boolean isIdentificator(String atom) {
        Matcher matcher = identificator.matcher(atom);
        return matcher.matches();
    }
    private boolean isConstanta(String atom) {
        Matcher matcher = constanta.matcher(atom);
        return matcher.matches();
    }
    private boolean isSeparator(String atom) {
        return separatori.containsKey(atom);
    }
    private boolean isOperator(String atom) {
        return operatori.containsKey(atom);
    }
    private boolean isCuvantRezervat(String atom) {
        return cuvinteRezervate.containsKey(atom);
    }
}
