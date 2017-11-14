package controllers;

public interface ControllableScreen {
    public void init();
    public void onShow();
    public void setParentController(ScreenController parent);
}
