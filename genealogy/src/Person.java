import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Person implements Comparable<Person>{
    private String name;
    private String surname;
    private LocalDate birthDate;
    private LocalDate deathDate;
    private HashSet<Person> children;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }
    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    public Person(String name, String surname, LocalDate birthDate, LocalDate deathDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
        children = new HashSet<>();
    }

    @Override
    public String toString() {
        return name + " " + surname + ", urodzony: " + birthDate + ", zgon: " + deathDate + " , dzieci: " + children;
    }

    public boolean adopt(Person child){
        return children.add(child);
    }

    public Person getYoungestChild(){
        Person youngest = null;
        for(Person child: children){
            if(youngest == null || child.compareTo(youngest) > 0/*child.birthDate.isAfter(youngest.birthDate)*/){
                youngest = child;
            }
        }
        return youngest;
    }

    public List<Person> getChildren(){
        List<Person> getChildren = new ArrayList<>(children);
        Collections.sort(getChildren);
        return getChildren;
    }

    @Override
    public int compareTo(Person o) {
        return this.birthDate.compareTo(o.birthDate);
    }

    public static Person fromCsvLine(String line){
        String[] dane = line.split(",");
        String[] nameAndSurname = dane[0].split(" ");
        DateTimeFormatter formatted = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate birthDate = LocalDate.parse(dane[1], formatted);
        LocalDate deathDate = null;
        if(!dane[2].isEmpty()) {
            deathDate = LocalDate.parse(dane[2], formatted);
        }
        return new Person(nameAndSurname[0], nameAndSurname[1], birthDate, deathDate);
    }

    private void checkLifespanException() throws NegativeLifespanException {
        if(deathDate != null && (birthDate.isAfter(deathDate))){
            throw new NegativeLifespanException(this);
        }
    }

//    public static List<Person> fromCsv(String path) throws IOException {
//        List<Person> result = new ArrayList<>();
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(path));
//            Person person;
//            String line;
//            br.readLine();
//            String[] parentsName;
//            while ((line = br.readLine()) != null) {
//                person = Person.fromCsvLine(line);
//                person.checkLifespanException();
//                person.checkAmbiguousPersonException(result);
//                result.add(person);
//                parentsName = line.split(",");
//                if(parentsName.length > 3 && parentsName[3] != ""){
//                    for(Person per: result){
//                        if((per.getName() + " " + per.getSurname()).equals(parentsName[3])){
//                            try {
//                                per.checkParentingAgeException(person);
//                            } catch (ParentingAgeException e) {
//                                System.out.println(e.getMessage());
//                                System.out.println("Czy dodać dziecko mimo tego [Y/N]?");
//                                Scanner y = new Scanner(System.in);
//                                String x = y.nextLine();
//                                if(x.equals("Y")){
//                                    per.adopt(person);
//                                }
//                            }
//
//                        }
//                    }
//                }
//                if(parentsName.length > 4){
//                    for(Person per: result){
//                        if((per.getName() + " " + per.getSurname()).equals(parentsName[4])){
//                            try {
//                                per.checkParentingAgeException(person);
//                            } catch (ParentingAgeException e) {
//                                System.out.println(e.getMessage());
//                                System.out.println("Czy dodać dziecko mimo tego [Y/N]?");
//                                Scanner y = new Scanner(System.in);
//                                String x = y.nextLine();
//                                if(x.equals("Y")){
//                                    per.adopt(person);
//                                }
//                            }
//                        }
//                    }
//                }
//                //br.close();
//            }
//        } catch (FileNotFoundException e) {
//            System.err.println("Plik " + path + " nie istnieje.");
//        } catch (NegativeLifespanException | AmbiguousPersonException e) {
//            System.err.println(e.getMessage());
//        }
//        return result;
//    }

    public static List<Person> fromCsv(String path) throws IOException {
        List<Person> result = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Person person;
            String line;
            br.readLine();
            String[] parentsName;
            while ((line = br.readLine()) != null) {
                person = Person.fromCsvLine(line);
                person.checkLifespanException();
                person.checkAmbiguousPersonException(result);
                result.add(person);
                //br.close();
                //dodanie dziecka do rodziców
                parentsName = line.split(","); //skladowe 3 i 4
                if (parentsName.length > 3) {
                    if (parentsName[3] != "") {
                        for (Person p : result) {
                            if (parentsName.length > 3 && (p.name + " " + p.surname).equals(parentsName[3])) {
                                p.adopt(person);
                                p.checkParentingAgeException();
                            }
                        }
                    }
                    if (parentsName.length == 5) {
                        for (Person p : result) {
                            if ((p.name + " " + p.surname).equals(parentsName[4])) {
                                p.adopt(person);
                                p.checkParentingAgeException();
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Plik " + path + " nie istnieje.");
        } catch (NegativeLifespanException | AmbiguousPersonException | ParentingAgeException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    private void checkAmbiguousPersonException(List<Person> personList) throws AmbiguousPersonException {
        for(Person person: personList){
            if(this.getName().equals(person.getName()) && this.getSurname().equals(person.getSurname())){
                throw new AmbiguousPersonException(this);
            }
        }
    }

    private void checkParentingAgeException() throws ParentingAgeException{
        for(Person child: children) {
            if (this.deathDate != null && (this.deathDate.isBefore(child.birthDate) || (this.birthDate.isAfter(child.birthDate.minusYears(15))))) {
                throw new ParentingAgeException(this, child);
            }
        }
    }

    public static void toBinaryFile(List<Person> people, String filename) throws IOException {
        try (//try-with-resources" - konstrukcja, która automatycznie zamyka strumienie po zakończeniu bloku try,
             // strumienie muszą implementować interfejs AutoCloseable
             FileOutputStream fos = new FileOutputStream(filename);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(people);
        }
    }

    public static List<Person> fromBinaryFile(String filename) throws IOException, ClassNotFoundException {
        try (//try-with-resources" - konstrukcja, która automatycznie zamyka strumienie po zakończeniu bloku try,
             // strumienie muszą implementować interfejs AutoCloseable
             FileInputStream fis = new FileInputStream(filename);
             ObjectInputStream ois = new ObjectInputStream(fis)
        ) {
            return (List<Person>) ois.readObject();
        }
    }

}
