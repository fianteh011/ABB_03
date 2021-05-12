package de.hsos.prog3.team5.ab05;

public class App {
    public static void main(String[] args) {
        Ringpuffer<String> flugschreiber = new Ringpuffer<String>(6, 5, false, true);

        flugschreiber.add("New York");
        flugschreiber.add("New Jersey");
        flugschreiber.add("London");
        flugschreiber.add("Paris");
//        flugschreiber.add(null);
        flugschreiber.add("dummy1");
        flugschreiber.add("dummy2");
        flugschreiber.add("dummy3");
        flugschreiber.add("dummy4");
//        flugschreiber.add("dummy6");
//        flugschreiber.add("dummy7");
//        flugschreiber.add("dummy8");
//        flugschreiber.add("dummy9");

        for (int i = 0; i < flugschreiber.size(); i++) {
            System.out.println(flugschreiber.remove());
        }
        System.out.println("----------------------------------------------");
        System.out.println("LIFO Beispiel - variable capacity");
        //LIFO-Beispiel, Kapazitaet variierend
        Ringpuffer<String> buecherstapel = new Ringpuffer<>(5, 3, true, false);

        buecherstapel.push("Harry Potter");
        buecherstapel.push("Herr der Ringe");
        buecherstapel.push("BuchA");
        buecherstapel.push("BuchB");
        buecherstapel.push("BuchC");
//        buecherstapel.push(null);
        System.out.println(buecherstapel.pop());
        System.out.println(buecherstapel.pop());
        System.out.println(buecherstapel.pop());

        System.out.println("----------------------------------------------");
        System.out.println("FIFO Beispiel - fix capacity");
        //FIFO-Beispiel, Kapazitaet fix
        Ringpuffer<String> warteschlangeAnDerKasse = new Ringpuffer<>(5, 3, false, false);

        warteschlangeAnDerKasse.add("KundeA");
        warteschlangeAnDerKasse.add("KundeB");
        warteschlangeAnDerKasse.add("KundeC");
        warteschlangeAnDerKasse.add("KundeD");
//            warteschlangeAnDerKasse.add("KundeX");


        System.out.println(warteschlangeAnDerKasse.remove());
        System.out.println(warteschlangeAnDerKasse.remove());
        System.out.println(warteschlangeAnDerKasse.remove());
        System.out.println(warteschlangeAnDerKasse.remove());
//        System.out.println(warteschlangeAnDerKasse.remove());


    }
}
