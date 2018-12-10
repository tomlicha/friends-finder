package fontys.andr2.friendsfinder.Users;

import java.util.HashMap;

public class UsersAvailable {

    private HashMap<String,User> usersAvailable;
    private RefreshListener refreshListener;

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
                new User("http://ibb.co/K9Df28L", "Baptiste", "b4mamanch@enib.fr", 48,2));
        usersAvailable.put("t4licha@enib.fr",
                new User("http://ibb.co/RTgwp8t", "Tom", "t4licha@enib.fr", 48,4));
        usersAvailable.put("g4gary@enib.fr",
                new User("http://ibb.co/r5Ymq3p", "Gary", "g4gary@enib.fr", 48,3));
        if(refreshListener!=null) refreshListener.onRefresh();

    }

    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public interface RefreshListener{
        void onRefresh();
    }

}
