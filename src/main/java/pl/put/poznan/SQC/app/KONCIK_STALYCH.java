package pl.put.poznan.SQC.app;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Zawiera stałe wykorzystywane w programie
 */
public class KONCIK_STALYCH {
    // Rozszerzenia bez kropki
    /**
     * Ustawia wspierane rozszerzenia plików
     */
    public static final String[] supportedExtensions = new String[]{"json"};
    /**
     * folder aplikacji
     */
    public static final String mainFolderName = "SQCApplication";
    /**
     * Ustala maksymalny rozmiar scenariusza dla GUI
     */
    public static final int dlugoscElementyWLiscie = 26;
    /**
     * Słowa kluczowe, które mogą być zawarte w scenariuszu.
     */
    public static final String[] keyWords = new String[]{"IF", "ELSE", "FOR EACH", "IF:", "ELSE:", "FOR EACH:", "WHEN", "WHILE"};

    /**
     * Zamienia listę Integer-ów na String
     * @param arrayList Wejściowa lista
     * @return      Wynikowy String
     */
    public static String tablicoNaStringoInator(ArrayList<Integer> arrayList){
        return arrayList.stream().map(Object::toString).collect(Collectors.joining("."));
    }
}
