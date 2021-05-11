package de.hsos.prog3.team5.ab05;

public class App {
    public static void main(String[] args) {
        Ringpuffer <String> flugschreiber = new Ringpuffer<String>(5, false, true);
        flugschreiber.add("New York");
        flugschreiber.add("New Jersey");
        flugschreiber.add("London");
        flugschreiber.add("Paris");
        flugschreiber.add("Berlin");

        while (flugschreiber.peek() != null){
            System.out.println(flugschreiber.remove());
        }

        // System.out.println(list);
    }
}
