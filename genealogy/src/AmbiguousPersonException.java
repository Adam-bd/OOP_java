public class AmbiguousPersonException extends Exception{
    public AmbiguousPersonException(Person person){
        super("Znaleziono więcej niż jedną osobę o imieniu i nazwisku: " + person.getName() + " " + person.getSurname());
    }
}
