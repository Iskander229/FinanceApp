interface UserObserver {
    void update(User user, String message);
}

class LoggerObserver implements UserObserver {
    @Override
    public void update(User user, String message) {
        System.out.println("[LOG] User: " + user.getUsername() + " - " + message);
    }
}