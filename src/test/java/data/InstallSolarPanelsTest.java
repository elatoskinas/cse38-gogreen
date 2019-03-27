package data;

import org.junit.Test;
import tools.CarbonCalculator;

import static org.junit.Assert.assertEquals;

public class InstallSolarPanelsTest {
    InstallSolarPanels panels = new InstallSolarPanels();
    User user = new User("Vetle", "Hjelmtvedt", 19, "vetle@hjelmtvedt.com","test", "password123");
    @Test
    public void testConstructor() {
        assertEquals("Install Solar Panels", panels.getName());
        assertEquals("Household", panels.getCategory());
        panels.setKwhSavedPerYear(1000);
        assertEquals(1000, panels.getKwhSavedPerYear());
    }

    @Test
    public void testCalculateCarbonSaved() {
        assertEquals((int) (CarbonCalculator.electricityEmissions(1000) / 365.0), (int) panels.calculateCarbonSaved(user));
    }
}
