package ui;

public class ScreenMomento {
    private String state;

    public ScreenMomento(String state){
        this.state = state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
