package charts;

import CPT.AnimeData;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.chart.PieChart;

/**
 * A class that generates and updates a pie chart
 * @author S. Tuczynski & G. Lui
 * 
 */
public class PieChartGenerator {

    // Instance variables
    private PieChart genrePieChart;
    
    /**
    * Constructor method for the pie chart generator
    *
    */
    public PieChartGenerator() {

        genrePieChart = new PieChart();
        genrePieChart.setTitle("Genre Distribution");
  
    }

    /**
    * Returns the pie chart
    *
    * @return the pie chart
    *
    */
    public PieChart getPieChart() {
        return genrePieChart;
    }
    
    /**
     * Updates the pi chart based on the user list of anime
     * 
     * @param userAnimeList  user list of anime
     * 
     */
    public void updateGenrePieChart(List<AnimeData> userAnimeList) {

        List<PieChart.Data> genreData = new ArrayList<>();
        
        // Iterates through the user list
        for (AnimeData anime : userAnimeList) {

            // Iterates through the genres
            for (String genre : anime.getGenres()) {

                boolean genreExists = false;

                for (PieChart.Data data : genreData) {

                    // Increment genre count
                    if (data.getName().equalsIgnoreCase(genre)) {

                        data.setPieValue(data.getPieValue() + 1);
                        genreExists = true;
                        break;
                    }
                }

                // Start genre count
                if (!genreExists) {

                    genreData.add(new PieChart.Data(genre, 1));
                }
            }
        }
        genrePieChart.setData(FXCollections.observableArrayList(genreData));
    }
}