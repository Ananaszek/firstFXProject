package pl.agataanaszewicz.models;

public class UserSession {
        private static UserSession ourInstance = new UserSession();

        public static UserSession getInstance() {
            return ourInstance;
        }

        private UserSession() {
        }

        private int id;
        private String username;
        private boolean isLogedIn;



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public boolean isLogedIn() {
            return isLogedIn;
        }

        public void setLogedIn(boolean logedIn) {
            isLogedIn = logedIn;
        }
}
