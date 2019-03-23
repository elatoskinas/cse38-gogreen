package data;

import java.util.Date;


public class UserAchievement {

    public int id;
    boolean completed;
    Date date;

    /**
     * link completed achievement to user
     * the user should have an array list of achievements .
     *
     * @param id        reference to the achievement
     * @param completed flag
     * @param date      date on completion
     */

    public UserAchievement(int id, boolean completed, Date date) {
        this.id = id;
        this.completed = completed;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean equals(Object other) {

        if (other instanceof UserAchievement) {

            UserAchievement that = (UserAchievement) other;

            if(that.id == this.id){
                return true;
            }
        }
         return false;

    }


}
