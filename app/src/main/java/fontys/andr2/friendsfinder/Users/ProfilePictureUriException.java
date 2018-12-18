package fontys.andr2.friendsfinder.Users;

public class ProfilePictureUriException extends Exception{
    private final static String ERR_MESSAGE = "The URI is not in http format";
        public ProfilePictureUriException() {
        }

        public ProfilePictureUriException(String message) {
            super(ERR_MESSAGE);
        }

        public ProfilePictureUriException(String message, Throwable cause) {
            super(ERR_MESSAGE, cause);
        }

        public ProfilePictureUriException(Throwable cause) {
            super(cause);
        }

        public ProfilePictureUriException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
            super(ERR_MESSAGE, cause, enableSuppression, writableStackTrace);
        }
}
