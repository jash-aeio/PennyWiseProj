
import java.io.Serializable;
import java.util.HashMap;

public class UserDataMap implements Serializable {

    private static final long serialVersionUID = 1L;
    private HashMap<String, UserData> userDataMap;

    public UserDataMap() {
        userDataMap = new HashMap<>();
    }

    public UserData getUserData(String username) {
        return userDataMap.get(username);
    }

    public void saveUserData(String username, UserData userData) {
        userDataMap.put(username, userData);
    }
}
