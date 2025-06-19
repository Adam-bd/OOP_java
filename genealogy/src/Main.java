import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<Person> personList = new ArrayList<>();
//        Person kacper = new Person("Kacper", "Kowalski", LocalDate.of(2000, 11, 27), null);
//        Person adam = new Person("Adam", "Nowak", LocalDate.of(2002, 3, 15), null);
//        Person anna = new Person("Anna", "Dąbrowska", LocalDate.of(2005, 5, 31), null);
//        Person franek = new Person("Franek", "Kowalski", LocalDate.of(2024, 1, 10), null);
//        Person kamil = new Person("Kamil", "Kowalski", LocalDate.of(2025, 3, 20), null);
//        Person wiktor = new Person("Wiktor", "Kowalski", LocalDate.of(1950, 10, 12), LocalDate.of(2025, 03, 28));

//        personList.add(kacper);
//        personList.add(adam);
//        personList.add(anna);
//
//        for(Person person: personList){
//            System.out.println(person);
//        }
//
//        kacper.adopt(kamil);
//        System.out.println("Adopcja: " + kacper.adopt(franek));
//
//        Person youngest = kacper.getYoungestChild();
//        System.out.println("Najmłodsze dziecko Kacpra: " + (youngest != null ? youngest : "Brak dzieci"));
//
//        System.out.println("Dzieci Kacpra posortowane od najstarszego do najmłodszego:");
//        for (Person child : kacper.getChildren()) {
//            System.out.println(child);
//        }
//
//        Family family = new Family();
//        family.add(kacper);
//        family.add(anna);
//        family.add(kacper,franek);
//
//
//        Person jan2 = new Person("Jan", "Kowalski", LocalDate.of(1985, 3, 10), null);
//        family.add(jan2);
//        System.out.println("Wyszukane osoby o imieniu Jan Kowalski:");
//        for (Person p : family.get("Jan Kowalski")) {
//            System.out.println(p);
//        }

        System.out.println(Person.fromCsvLine("Marek Kowalski,15.05.1899,25.06.1957,,"));
        try {
            personList = Person.fromCsv("family.csv");
        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu linii: " + e.getMessage());;
        }
        System.out.println(personList);

        System.out.println("_____________Zapis i odczyt listy obiektów Person - pliki binarne__________________________________________");

        Person.toBinaryFile(personList, "family.bin");
        List<Person> loadPeople = Person.fromBinaryFile("family.bin");
        for(Person person: loadPeople)
            System.out.println(person);
    }
}