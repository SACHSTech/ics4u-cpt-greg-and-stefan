package charts;

import CPT.AnimeData;
import java.util.ArrayList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

/**
 * A class that generates and updates a Bar Chart
 * @author S. Tuczynski & G. Lui
 * 
 */
public class BarChartGenerator {
    
    // Instance Variables
    private BarChart<String, Number> barChart;
    private ArrayList<Double> scores;


    /**
    * Constructor method for bar chart
    * 
    */
    public BarChartGenerator() {

        // Setup bar chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setCategoryGap(50);
        barChart.setTitle("Average Score Distribution for your Animes");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        scores = new ArrayList<Double>();

        // Create the X Axis and fill it with values that will be added to
        series.setName("Values");
        series.getData().add(new XYChart.Data<>("0", 0));
        series.getData().add(new XYChart.Data<>("1", 0));
        series.getData().add(new XYChart.Data<>("2", 0));
        series.getData().add(new XYChart.Data<>("3", 0));
        series.getData().add(new XYChart.Data<>("4", 0));
        series.getData().add(new XYChart.Data<>("5", 0));
        series.getData().add(new XYChart.Data<>("6", 0));
        series.getData().add(new XYChart.Data<>("7", 0));
        series.getData().add(new XYChart.Data<>("8", 0));
        series.getData().add(new XYChart.Data<>("9", 0));
        barChart.getData().add(series);

    }

    /**
    * Gets the bar chart
    * 
    * @return the bar chart
    *
    */
    public BarChart<String, Number> getBarChart() {

        return barChart;

    }

    /**
     * Increments a specific bar chart based on the integer value of the score of the selected anime
     * 
     * @param userSelectedAnime  the Anime the user added
     * 
     */
    public void addToBarChart(AnimeData userSelectedAnime) {

        int roundedScore = (int) Math.round(userSelectedAnime.getScore());
        XYChart.Series<String, Number> series = barChart.getData().get(0);

        // Iterate through series numbers
        for (XYChart.Data<String, Number> data : series.getData()) {

            // Increment when score matches a value
            if (data.getXValue().equals(Integer.toString(roundedScore))) {

                scores.add(userSelectedAnime.getScore());
                int newValue = data.getYValue().intValue() + 1;
                data.setYValue(newValue);
                break;
            }
        }
    }

    /**
     * Decrements a specific bar chart based on the integer value of the score of the selected anime
     * 
     * @param userSelectedAnime  the Anime the user added
     * 
     */
    public void removeFromBarChart(AnimeData userSelectedAnime) {

        int roundedScore = (int) Math.round(userSelectedAnime.getScore());
        XYChart.Series<String, Number> series = barChart.getData().get(0);

        // Iterate through series numbers
        for (XYChart.Data<String, Number> data : series.getData()) {

            if (data.getXValue().equals(Integer.toString(roundedScore))) {

                // Decrement when score matches a value
                scores.remove(userSelectedAnime.getScore());
                int newValue = data.getYValue().intValue() - 1;
                data.setYValue(newValue);
                break;
            }
        }
    }

    /**
     * Calculates the average score of selected anime and returns it
     * 
     * @return the average score based on the users selected anime
     * 
     */
    public double getScoreAverage() {

        double average = 0;

        if (scores.isEmpty() == true) {

            return 0;
        }

        for (double num : scores) {

            average += num;
        }

        return Math.round(average/scores.size() * 100.0) / 100.0;
    }

    /**
     * Calculates the standard deviation score of selected anime and returns it
     * 
     * @return the standard deviation score based on the users selected anime
     * 
     */
    public double getStandardDeviation() {
        double sum = 0.0;

        if (scores.isEmpty() == true) {

            return 0;
        }

        for (double i : scores) {

            sum += i;
        }

        int length = scores.size();
        double mean = sum / length;
        double standardDeviation = 0.0;

        for (double num : scores) {

            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.round(Math.sqrt(standardDeviation / length) * 100.0) / 100.0;

    }

    /**
     * Calculates the anime count of users selected anime
     * 
     * @return the anime count based on the users selected anime
     * 
     */
    public int getAnimeCount() {

        return scores.size();
    }

    /**
     * Calculates the maximum score of selected anime and returns it
     * 
     * @return the maximum score based on the users selected anime
     * 
     */
    public double getScoreMax() {

        double max = 0;
        
        if (scores.isEmpty() == true) {
            return 0;
        }
        for (double i : scores) {

            if (i > max){
                
                max = i;
            }
        }

        return Math.round(max * 100.0) / 100.0;
            
    }

    /**
     * Calculates the minimum score of selected anime and returns it
     * 
     * @return the minimum score based on the users selected anime
     * 
     */
    public double getScoreMin() {
        double min = 10;
        
        if (scores.isEmpty() == true) {

            return 0;
        }

        for (double i : scores) {
        
            if (i < min) {

                min = i;
            }
        }

        return Math.round(min * 100.0) / 100.0;
    }

    /**
     * Calculates the median score of selected anime and returns it
     * 
     * @return the median score based on the users selected anime
     * 
     */
    public double getScoreMedian() {

        if (scores.isEmpty() == true) {

            return 0;
        }

        scores.sort(null);
        double median = 0;

        for (int i = 0; i <  scores.size(); i++) {

            for (int j =  scores.size() - 1; j > i; j--) {
                if (scores.get(i) > scores.get(j)) {

                    Double tmp = scores.get(i);
                    scores.set(i, scores.get(j)) ;
                    scores.set(j,tmp);
                }
            }
        }

        if (scores.size() % 2 == 0) {

            median = ((double)scores.get(scores.size()/2) + (double) scores.get(scores.size()/2 - 1))/2;
        }
        else {

            median = (double) scores.get(scores.size()/2);
        }
        return Math.round(median * 100.0) / 100.0;
    }
}
