package DepartmentSubsystem;

public class Feedback {

    private int feedbackID;
    private int rating;
    private String additionalInfo;


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
}
