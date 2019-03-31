package data;

import org.springframework.web.client.HttpClientErrorException;
import tools.CarbonCalculator;
import tools.Requests;

/**
 * Activity: Install solar panels.
 *
 * @author Kostas Lyrakis
 */
public class InstallSolarPanels extends Activity {
    private int kwhSavedPerYear;
    private double dailyCarbonSaved;
    /**
     * Constructor.
     */
    public InstallSolarPanels() {
        this.setCategory("Household");
        this.setName("Install Solar Panels");
        this.kwhSavedPerYear = 0;
    }

    public int getKwhSavedPerYear() {
        return kwhSavedPerYear;
    }

    public void setKwhSavedPerYear(int kwhSavedPerYear) {
        this.kwhSavedPerYear = kwhSavedPerYear;
    }

    public double getDailyCarbonSaved() {
        return dailyCarbonSaved;
    }

    public void setDailyCarbonSaved(double dailySavedCarbon) {
        this.dailyCarbonSaved = dailySavedCarbon;
    }

    /**
     * Installing solar panels can't be executed more than once per user.
     * Therefore this method calculates a user's daily savings assuming that
     * after the activity is performed, the user will rely on solar panels to cover
     * a percentage of his/her energy needs forever.
     *
     * @param user currently logged in user
     * @return user's daily CO2 savings.
     */
    @Override
    public double calculateCarbonSaved(User user) {
        // TODO
        // update the function so that the amount
        // saved by the solar panels does not exceed the amount
        // of the user's daily consumption in electricity
        if (this.kwhSavedPerYear / 365.0 > user.getElectricityDailyConsumption()) {
            this.kwhSavedPerYear = (int) (user.getElectricityDailyConsumption() * 365);
        }
        return CarbonCalculator.electricityEmissions(this.getKwhSavedPerYear()) / 365.0;
    }

    // TODO
    // override the performActivity method to update the user field hasInstalledSolarPanels

    @Override
    public void performActivity(User user) {
        this.setDailyCarbonSaved(this.calculateCarbonSaved(user));

        // When the solar panels are first installed, no amount of CO2 is saved immediately
        this.setCarbonSaved(0);

        // update logged in user for the gui
        user.addActivity(this);

        // update user in the database
        try {
            user = Requests.addActivityRequest(this, user.getUsername());

            // check if an achievement is completed by this activity
            AchievementsLogic.checkActivity(user , this);

            // adds points to the user
            user.addCO2Points(this.getCarbonSaved());


        } catch (HttpClientErrorException e) {
            System.out.println("Activity was not added to the database");
            System.out.println(e.fillInStackTrace());
        }
    }

}
