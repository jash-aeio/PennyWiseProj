import java.io.*;

public class PennyWiseManager {
    UserDataMap userDataMap;
    private static final String DATA_FILE = "userdata.ser";

    public PennyWiseManager() {
        userDataMap = new UserDataMap();
        loadUserData();
    }

    public UserData getUserData(String username) {
        return userDataMap.getUserData(username);
    }

    public void saveUserData(String username, UserData userData) {
        userDataMap.saveUserData(username, userData);
        saveToFile();
    }

    private void loadUserData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            userDataMap = (UserDataMap) ois.readObject();
        } catch (FileNotFoundException e) {
            // File not found, initialize an empty map
            userDataMap = new UserDataMap();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(userDataMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}