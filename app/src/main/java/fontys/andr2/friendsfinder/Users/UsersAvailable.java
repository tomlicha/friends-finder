package fontys.andr2.friendsfinder.Users;

import java.util.HashMap;

public class UsersAvailable {

    private HashMap<String,User> usersAvailable;

    public UsersAvailable() {
        this.usersAvailable = new HashMap<>();
    }

    public User getUser(String email){
        return usersAvailable.get(email);
    }

    public HashMap<String, User> getAvailables(){
        return usersAvailable ;
    }

    public void refreshAvailable (){
        usersAvailable.clear();
        usersAvailable.put("b4mamanch@enib.fr",
                new User("http://ibb.co/K9Df28L".getBytes(), "Baptiste", "b4mamanch@enib.fr"));
        usersAvailable.put("t4licha@enib.fr",
                new User("http://ibb.co/RTgwp8t".getBytes(), "Tom", "t4licha@enib.fr"));
        usersAvailable.put("g4gary@enib.fr",
                new User("http://ibb.co/r5Ymq3p".getBytes(), "Gary", "g4gary@enib.fr"));

    }
    

}
