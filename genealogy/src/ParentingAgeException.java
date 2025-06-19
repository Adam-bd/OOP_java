public class ParentingAgeException extends Exception{
    public ParentingAgeException(Person parent, Person child) {
        super("Różnica wieku wynosi mniej niż 15 lat między " + parent.toString() + " a " + child.toString());
    }
}
