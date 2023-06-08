package nl.autogarage.finalassignmentbackendmain.exceptions;

public class DuplicateErrorException extends RuntimeException {

  public DuplicateErrorException(){
      super();

  }

    public DuplicateErrorException(String message){
        super(message);

    }

}
