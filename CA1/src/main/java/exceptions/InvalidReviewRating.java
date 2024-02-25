package exceptions;

public class InvalidReviewRating extends Exception {
    public InvalidReviewRating(String parameter) {
        super("Review rating parameter <" + parameter + "> out of range.");
    }
}
