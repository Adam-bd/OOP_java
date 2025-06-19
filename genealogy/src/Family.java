import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Family {
    HashMap<String, List<Person>> familyMembers;

    public Family() {
        this.familyMembers = new HashMap<>();
    }

//    Zad.7
//    public void add(Person... person){
//        for(Person p: person){
//            familyMembers.put(p.getName() + ' ' + p.getSurname(), p);
//        }
//        familyMembers.put(person.getName() + " " + person.getSurname(), person);
//    }
//    public Person get(String string){
//        return familyMembers.get(string);
//    }
//}

    public void add(Person... people){
        for(Person person: people){
            String key = person.toString().split(",")[0];
            familyMembers.putIfAbsent(key, new ArrayList<>());
            familyMembers.get(key).add(person);
        }
    }

    public Person[] get(String key){
        List<Person> persons = familyMembers.getOrDefault(key, new ArrayList<>());
        persons.sort(Comparator.naturalOrder());
        return persons.toArray(new Person[1]);
    }
}
