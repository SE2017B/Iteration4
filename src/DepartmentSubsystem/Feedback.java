/*
* Software Engineering 3733, Worcester Polytechnic Institute
* Team H
* Code produced for Iteration 4
* The following code
*/
package DepartmentSubsystem;

public class Feedback {

    private int feedbackID; //each feedback has specific ID
    private int rating; //rating bar
    private String additionalInfo;  //descriptive info

    //contructor
    public Feedback(int feedbackID, int rating, String additionalInfo) {
        this.feedbackID = feedbackID;
        this.rating = rating;
        this.additionalInfo = additionalInfo;
    }

    public int getFeedbackID() {
        return feedbackID;
    }

    public void setFeedbackID(int feedbackID) {
        this.feedbackID = feedbackID;
    }

    public int incFeedbackID() {
        ++feedbackID;
        return feedbackID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    @Override
    public String toString(){
        String name = " Rating: " + rating + "        Additional Info: " + additionalInfo;
        System.out.println(name);
        return name;
    }
}
