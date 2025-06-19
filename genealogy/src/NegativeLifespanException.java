public class NegativeLifespanException extends Exception{
    public NegativeLifespanException(Person person) {
        super(person.getName() + " " + person.getSurname() + " urodzony w " + person.getBirthDate()
                + " i zmarł w " + person.getDeathDate() + ". " + "Konflikt daty urodzenia i zgonu.");
    }
}
