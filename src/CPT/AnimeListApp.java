package CPT;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import charts.BarChartGenerator;
import charts.PieChartGenerator;

/**
 * A class that runs the AnimeList program
 * @author: S. Tuczynski & G. Lui
 * 
 */
public class AnimeListApp extends Application {

    // Instance variables
    private TableView<AnimeData> mainTable;
    private TableView<AnimeData> userTable;
    private List<AnimeData> userAnimeList;
    private ArrayList<AnimeData> currentAnimeList;
    private ArrayList<AnimeData> tempAnimeList;

    /**
     * Main method
     * 
     * @param args  String[] args
     * 
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    /**
     * Method that runs the program
     * 
     * @param primaryStage  primary stage
     * 
     */
    public void start(Stage primaryStage) {

        // Set program title
        primaryStage.setTitle("AnimeList");


        // Processes data from CSV file, File I/O
        ArrayList<AnimeData> animeList = new ArrayList<>(); 

        try (BufferedReader reader = new BufferedReader(new FileReader("src/CPT/animes.csv"))) {

            String line = reader.readLine();

            // Runs while there are still entries
            while((line = reader.readLine()) != null) {

                /*
                 * Note: The processing of this data was very complex, as there are many inconsistencies in the data that make buffered reader processing
                 * much more complicated compared to standard csv file processing. The bulk of this section is a lot of conditional statements and 
                 * substringing to process all this data. 
                 */
                ArrayList<String> genres = new ArrayList<>();

                // A rank of 999999 means no one has scored it and a episode count of 0 means it is still airing
                AnimeData animeData = new AnimeData(0, "", "", genres, "", 0, 0, 0, 999999, 0, "", "");
                animeData.setUID(Integer.parseInt(line.substring(0, line.indexOf(","))));
     
                line = line.substring(line.indexOf(",") + 1);

                // Processes title
                if (line.charAt(0) == '"' && line.charAt(1) != '"') {

                    line = line.substring(1);
                    animeData.setTitle(line.substring(0, line.indexOf('"')));
                    line = line.substring(line.indexOf('"') + 2); 
                }

                else {

                    animeData.setTitle(line.substring(0, line.indexOf(','))); 
                    line = line.substring(line.indexOf(',') + 1); 
                }

                animeData.setTitle(animeData.getTitle().replace("\"", ""));
       
                // Processes Synopsis
                if (line.contains("https") == true) {

                    if (line.contains("['") == true) {

                        line = line.substring(line.indexOf("['"));
                    }

                    else {

                        line = line.substring(line.indexOf("["));
                    }
                } 
            
                else {

                    while (line.contains("https") == false) {

                        animeData.setSynopsis(animeData.getSynopsis() + line);
                        line = reader.readLine();
     
                    }
                    
                    if (line.contains("['") == true) {

                        animeData.setSynopsis(animeData.getSynopsis() + line.substring(0, line.indexOf("['")));
                        line = line.substring(line.indexOf("['"));
                    }

                    else {

                        animeData.setSynopsis(animeData.getSynopsis() + line.substring(0, line.indexOf("[")));
                        line = line.substring(line.indexOf("["));
                    }
  
                }
      
                while (line.length() < 90 && line.contains("https") != true) {

                    line = reader.readLine();
                }
           
                if (animeData.getSynopsis() != "") {

                    if (animeData.getSynopsis().charAt(animeData.getSynopsis().length()-1) == '"') {
          
                        animeData.setSynopsis(animeData.getSynopsis().substring(0, animeData.getSynopsis().length()-2));
                    }

                    else if (animeData.getSynopsis().charAt(animeData.getSynopsis().length()-1) == ',') {
     
                        animeData.setSynopsis(animeData.getSynopsis().substring(0, animeData.getSynopsis().length()-1));
                    }
                }   

                // Processes genre
                String strGenres = line.substring(0, line.indexOf("]"));
                
                line = line.substring(line.indexOf("]") + 1);

                if (line.charAt(0) == '"') {

                    line = line.substring(2);

                    if (line.charAt(0) == '"') {

                        line = line.substring(1);
                    }

                }

                else {

                    line = line.substring(1);
                    if (line.charAt(0) == '"') {

                        line = line.substring(1);
                    }
                }
          
                strGenres= strGenres.replace("[", "");
                strGenres = strGenres.replace("'", "");
                String[] genreList = strGenres.split(", ");
                animeData.setGenre(new ArrayList<>(Arrays.asList(genreList)));

                // Processes Aired Date
                if (line.contains("Not available") == true) {
                    animeData.setAired("Not available");
                    line = line.substring(line.indexOf(',') + 1);
                }

                else if (line.charAt(0) == '1' || line.charAt(0) == '2') {

                    if (line.contains(", ") == true) {

                        animeData.setAired(line.substring(0, line.indexOf('"') + 2));
                        line = line.substring(line.indexOf('"') + 2);
                    }

                    else {

                        animeData.setAired(line.substring(0, line.indexOf(',') + 1));
                        line = line.substring(line.indexOf(',') + 1);
                    }
                }

                else {

                    animeData.setAired(line.substring(0, line.indexOf('"')));
                    line = line.substring(line.indexOf('"') + 2);
                }
    
                if (animeData.getAired() != "") {

                    if (animeData.getAired().charAt(animeData.getAired().length()-1) == ',') {

                        animeData.setAired(animeData.getAired().substring(0, animeData.getAired().indexOf(',')));
                    }
                }
                
                // Processes Episode amount
                if (line.charAt(0) == ',') {

                    line = line.substring(line.indexOf(",") + 1);
                }

                else {

                    animeData.setEpisodes((int) Double.parseDouble(line.substring(0, line.indexOf(","))));
                    line = line.substring(line.indexOf(",") + 1);
                }
       
           
                if (line.charAt(0) == ',') {

                    line = line.substring(line.indexOf(",") + 1);
                }

                else {

                    animeData.setMembers((int) Double.parseDouble(line.substring(0, line.indexOf(","))));
                    line = line.substring(line.indexOf(",") + 1);
                }

       
                // Processes Popularity
                if (line.charAt(0) == ',') {

                    line = line.substring(line.indexOf(",") + 1);
                }
                else {
                    
                    animeData.setPopularity((int) Double.parseDouble(line.substring(0, line.indexOf(","))));
                    line = line.substring(line.indexOf(",") + 1);
                }

                // Processes Rank
                if (line.charAt(0) == ',') {

                    line = line.substring(line.indexOf(",") + 1);
                }

                else {

                    animeData.setRank((int) Double.parseDouble(line.substring(0, line.indexOf(","))));
                    line = line.substring(line.indexOf(",") + 1);
                }
           
                // Processes Score
                if (line.charAt(0) == ',') {

                    line = line.substring(line.indexOf(",") + 1);
                }

                else {

                    animeData.setScore(Double.parseDouble(line.substring(0, line.indexOf(","))));
                    line = line.substring(line.indexOf(",") + 1);
                }
  
                // Processes Image Link
                if (line.charAt(0) == ',') {

                    line = line.substring(line.indexOf(",") + 1);
                }

                else {

                    animeData.setImageLink(line.substring(0, line.indexOf(",")));
                    line = line.substring(line.indexOf(",") + 1);
                }
   

                if (line.charAt(0) != ',') {
                    animeData.setAnimeLink(line);
                }

                // Add anime data to anime list
                animeList.add(animeData);

            }
            
        } 
        
        // If IOException is caught, print stack trace
        catch (IOException e) {
            e.printStackTrace();
        }

        // Setup tables
        mainTable = new TableView<AnimeData>();
        userTable = new TableView<AnimeData>();

        // Create lists
        userAnimeList = new ArrayList<>();
        ArrayList<AnimeData> currentUserAnimeList = new ArrayList<>();
        this.tempAnimeList = new ArrayList<>(animeList);
        this.currentAnimeList = new ArrayList<>(animeList);

        // Create column values to main table
        mainTable.setEditable(true);
        mainTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<AnimeData, String>  animeTitle = new TableColumn<AnimeData, String>("Name");
        animeTitle.setCellValueFactory(new PropertyValueFactory<AnimeData, String>("title"));

        TableColumn<AnimeData, String> animeScore = new TableColumn<AnimeData, String>("Score");
        animeScore.setCellValueFactory(new PropertyValueFactory<AnimeData, String>("score"));

        TableColumn<AnimeData, String> animePopularity = new TableColumn<AnimeData, String>("Popularity");
        animePopularity.setCellValueFactory(new PropertyValueFactory<AnimeData, String>("popularity"));

        TableColumn<AnimeData, String> animeRank = new TableColumn<AnimeData, String>("Rank");
        animeRank.setCellValueFactory(new PropertyValueFactory<AnimeData, String>("rank"));

        TableColumn<AnimeData, String> animeViews = new TableColumn<AnimeData, String>("Views");
        animeViews.setCellValueFactory(new PropertyValueFactory<AnimeData, String>("members"));

        TableColumn<AnimeData, String> animeEpisodes = new TableColumn<AnimeData, String>("Episodes");
        animeEpisodes.setCellValueFactory(new PropertyValueFactory<AnimeData, String>("episodes"));

        // Fills the table with data
        mainTable.setItems(FXCollections.observableArrayList(animeList));

        // Add columns to main table
        mainTable.getColumns().addAll(animeTitle, animeScore, animePopularity, animeRank, animeViews, animeEpisodes);

        //  column values to user table
        userTable.setEditable(true);
        userTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<AnimeData, String> userAnimeTitle = new TableColumn<AnimeData, String>("Name");
        userAnimeTitle.setCellValueFactory(new PropertyValueFactory<AnimeData, String>("title"));

        TableColumn<AnimeData, String> userAnimeScore = new TableColumn<AnimeData, String>("Score");
        userAnimeScore.setCellValueFactory(new PropertyValueFactory<AnimeData, String>("score"));

        TableColumn<AnimeData, String> userAnimePopularity = new TableColumn<AnimeData, String>("Popularity");
        userAnimePopularity.setCellValueFactory(new PropertyValueFactory<AnimeData, String>("popularity"));

        TableColumn<AnimeData, String> userAnimeRank = new TableColumn<AnimeData, String>("Rank");
        userAnimeRank.setCellValueFactory(new PropertyValueFactory<AnimeData, String>("rank"));

        TableColumn<AnimeData, String> userAnimeViews = new TableColumn<AnimeData, String>("Views");
        userAnimeViews.setCellValueFactory(new PropertyValueFactory<AnimeData, String>("members"));

        TableColumn<AnimeData, String> userAnimeEpisodes = new TableColumn<AnimeData, String>("Episodes");
        userAnimeEpisodes.setCellValueFactory(new PropertyValueFactory<AnimeData, String>("episodes"));

        // Add columns to user table
        userTable.getColumns().addAll(userAnimeTitle, userAnimeScore, userAnimePopularity, userAnimeRank, userAnimeViews, userAnimeEpisodes);

        // Show extended info about anime if double clicked on. see ShowAnimeDetails method
        mainTable.setOnMouseClicked(event -> { 
            if (event.getClickCount() == 2) { 
                AnimeData selectedAnime = (AnimeData) mainTable.getSelectionModel().getSelectedItem(); 
                
                if (selectedAnime != null) { showAnimeDetails(selectedAnime);
                }
            }
        });

        userTable.setOnMouseClicked(event -> { 
            if (event.getClickCount() == 2) { 
                AnimeData selectedAnime = (AnimeData) userTable.getSelectionModel().getSelectedItem(); 
                
                if (selectedAnime != null) { showAnimeDetails(selectedAnime); 
                }
            }
        });
        
        // Generate charts
        BarChartGenerator barChart = new BarChartGenerator();
        PieChartGenerator pieChart = new PieChartGenerator();
        
        // Setup interactivity and UI elements
        Text title = new Text(10, 50, "AnimeList.net");
        title.setFont(new Font(20));

        // Chart Text Setup
        Text averageScore = new Text(10, 50, "Average score: " + barChart.getScoreAverage());
        averageScore.setFont(new Font(20));

        Text standardDeviationScore = new Text(10, 50, "Standard Deviation Score: " + barChart.getStandardDeviation());
        standardDeviationScore.setFont(new Font(20));

        Text animeCount = new Text(10, 50, "Anime Count: " + barChart.getAnimeCount());
        animeCount.setFont(new Font(20));

        Text maxScore = new Text(10, 50, "Maximum Score: " + barChart.getScoreMax());
        maxScore.setFont(new Font(20));

        Text minScore = new Text(10, 50, "Minimum Score: " + barChart.getScoreMin());
        minScore.setFont(new Font(20));

        Text medianScore = new Text(10, 50, "Score Median: " + barChart.getScoreMedian());
        medianScore.setFont(new Font(20));

        // Add Watched Button To Main List. See addAnimeToUserList method
        Button addButton = new Button("Watched");
        addButton.setOnAction(e -> addAnimeToUserList(currentUserAnimeList, barChart, pieChart, averageScore, standardDeviationScore, animeCount, maxScore, minScore, medianScore));

        // Add Remove Button To User List. 
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> removeAnimeFromUserList(currentUserAnimeList, barChart, pieChart, averageScore, standardDeviationScore, animeCount, maxScore, minScore, medianScore));

        // Added a choicebox to give user sorting options
        ChoiceBox sortingChoiceBox = new ChoiceBox(FXCollections.observableArrayList("Name", "Score", "Popularity", "Rank", "Views", "Episodes"));

        // sets default sorting option to by name and reorganizes the list
        sortingChoiceBox.setValue("Name");
        animeSorting(sortingChoiceBox);
        
        animeSorting(sortingChoiceBox);
        this.tempAnimeList = this.currentAnimeList;
        this.currentAnimeList = new ArrayList<>(animeList);

        refreshCurrentAnimeList();

        // set action to change sorting option. See AnimeSorting.java and animeSorting method. Uses MergeSort
        sortingChoiceBox.setOnAction(e -> animeSorting(sortingChoiceBox));

        // Adds a checkbox to filter out NSFW anime
        CheckBox nsfwFilterCheckBox = new CheckBox("NSFW Filter");
        CheckBox comedyFilter = new CheckBox("Comedy Only");
        CheckBox actionFilter = new CheckBox("Action Only");
        CheckBox romanceFilter = new CheckBox("Romance Only");

        comedyFilter.setOnAction(event -> genreFilter(animeList, comedyFilter, actionFilter, romanceFilter, "Comedy", "Action", "Romance", sortingChoiceBox));
        actionFilter.setOnAction(event -> genreFilter(animeList, actionFilter, romanceFilter, comedyFilter, "Action", "Romance", "Comedy", sortingChoiceBox));
        romanceFilter.setOnAction(event -> genreFilter(animeList, romanceFilter, comedyFilter, actionFilter, "Romance", "Comedy", "Action", sortingChoiceBox));
        nsfwFilterCheckBox.setOnAction(event -> nsfwFilter(nsfwFilterCheckBox, animeList, sortingChoiceBox, comedyFilter, actionFilter, romanceFilter));


        // Added a textfield to search for anime
        TextField searchField = new TextField();
        searchField.setPromptText("Enter a keyword");

        // set action to search for anime. See animeSearch method. Uses Linear Search
        searchField.setOnAction(e -> animeSearch(animeList, searchField, sortingChoiceBox));

        // Creates a tabpane to add lists and charts to.
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Create Horizontal and Vertical boxes to add elements together

        // Genrefilter HBox
        HBox hboxGenreFilters = new HBox(10);
        hboxGenreFilters.setAlignment(Pos.TOP_CENTER);
        hboxGenreFilters.setPadding(new Insets(10));
        hboxGenreFilters.getChildren().addAll(nsfwFilterCheckBox, comedyFilter, actionFilter, romanceFilter);

        // Search HBox
        HBox hboxAnimeSearch = new HBox(10);
        hboxAnimeSearch.setAlignment(Pos.TOP_CENTER);
        hboxAnimeSearch.setPadding(new Insets(10));
        hboxAnimeSearch.setSpacing(600);
        hboxAnimeSearch.getChildren().addAll(searchField, sortingChoiceBox);

        // Main Anime list VBox
        VBox vboxAnimeList = new VBox(10);
        vboxAnimeList.getChildren().add(hboxAnimeSearch);
        vboxAnimeList.getChildren().add(mainTable);
        vboxAnimeList.getChildren().addAll(tabPane, addButton, hboxGenreFilters);
        vboxAnimeList.setAlignment(Pos.CENTER);
        vboxAnimeList.setPadding(new Insets(10));

        // User Anime list VBox
        VBox vboxUserAnimeList = new VBox(10);
        vboxUserAnimeList.getChildren().add(userTable);
        vboxUserAnimeList.getChildren().add(removeButton);
        vboxUserAnimeList.getChildren().add(tabPane);
        vboxUserAnimeList.setAlignment(Pos.CENTER);
        vboxUserAnimeList.setPadding(new Insets(10));

        // User Chart Hbox
        HBox hboxUserAnimeData1 = new HBox(10);
        hboxUserAnimeData1.getChildren().addAll(averageScore, standardDeviationScore, animeCount);
        hboxUserAnimeData1.setAlignment(Pos.CENTER);
        hboxUserAnimeData1.setPadding(new Insets(10));

        // Second User Chart Hbox
        HBox hboxUserAnimeData2 = new HBox(10);
        hboxUserAnimeData2.getChildren().addAll(maxScore, minScore, medianScore);
        hboxUserAnimeData2.setAlignment(Pos.CENTER);
        hboxUserAnimeData2.setPadding(new Insets(10));

        // Title Hbox
        HBox hboxTitle = new HBox(10);
        hboxTitle.getChildren().add(title);
        hboxTitle.setAlignment(Pos.CENTER);
        hboxTitle.setPadding(new Insets(10));

        // Bar Graph Tab VBox
        VBox vboxBarGraphTab = new VBox(10);
        vboxBarGraphTab.getChildren().add(barChart.getBarChart());
        vboxBarGraphTab.getChildren().add(hboxUserAnimeData1);
        vboxBarGraphTab.getChildren().add(hboxUserAnimeData2);
        vboxBarGraphTab.setAlignment(Pos.CENTER);
        vboxBarGraphTab.setPadding(new Insets(10));

        // Create tabs
        Tab animeListTab = new Tab("Anime List");
        animeListTab.setContent(vboxAnimeList);

        Tab userAnimeListTab = new Tab("My Anime List");
        userAnimeListTab.setContent(vboxUserAnimeList);

        Tab genreTab = new Tab("Genre Distribution");
        genreTab.setContent(pieChart.getPieChart());

        Tab scoreChartTab = new Tab("Score Chart");
        scoreChartTab.setContent(vboxBarGraphTab);

        // Add all tabs to tabPane
        tabPane.getTabs().addAll(animeListTab, userAnimeListTab, genreTab, scoreChartTab);

        // Create border plane
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(hboxTitle);
        borderPane.setCenter(tabPane);

        // Finish scene
        Scene scene = new Scene(borderPane, 850, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Shows the details of anime, pops up new page
     * 
     * @param anime Specfic anime
     * 
     */
    private void showAnimeDetails(AnimeData anime) {

        // Setup alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Anime Details");
        alert.setHeaderText(anime.getTitle());
        
        // Setup gridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setPadding(new Insets(10));

        // Add information
        gridPane.addRow(0, new Label("UID:"), new Label(String.valueOf(anime.getUID())));
        gridPane.addRow(1, new Label("Genre(s):"), new Label(anime.getGenresString()));
        gridPane.addRow(2, new Label("Date Aired:"), new Label(anime.getAired()));
        gridPane.addRow(3, new Label("Episode Count:"), new Label(String.valueOf(anime.getEpisodes())));
        gridPane.addRow(4, new Label("Popularity Rank:"), new Label(String.valueOf(anime.getPopularity())));
        gridPane.addRow(5, new Label("People Watching:"), new Label(String.valueOf(anime.getMembers())));
        gridPane.addRow(6, new Label("Rank:"), new Label(String.valueOf(anime.getRank())));
        gridPane.addRow(7, new Label("Average Score:"), new Label(String.valueOf(anime.getScore())));

        // Setup hyperlink
        Hyperlink hyperLink = new Hyperlink(anime.getAnimeLink());
        hyperLink.setOnAction(e -> { getHostServices().showDocument(anime.getAnimeLink()); });

        // Add summary text area
        TextArea summaryTextArea = new TextArea(anime.getSynopsis());
        summaryTextArea.setEditable(false);
        summaryTextArea.setWrapText(true);
        summaryTextArea.setMaxWidth(Double.MAX_VALUE);
        summaryTextArea.setMaxHeight(Double.MAX_VALUE);
        gridPane.addRow(8, new Label("Summary:"), summaryTextArea);

        // If anime has an image link and isn't NSFW, add image and link to information
        if (anime.getImageLink() != ""  && !anime.getGenres().contains("Hentai") && !anime.getGenres().contains("Harem") && !anime.getGenres().contains("Ecchi")) {
            Group root = new Group();
            Image image = new Image(anime.getImageLink());
            ImageView imageView = new ImageView(image);
            root.getChildren().add(imageView);
            HBox animeInfo = new HBox(); 
            animeInfo.getChildren().add(gridPane);
            animeInfo.getChildren().add(root);
            animeInfo.setAlignment(Pos.CENTER);
            animeInfo.setPadding(new Insets(10));
            VBox animePage = new VBox();
            animePage.getChildren().add(animeInfo);
            animePage.getChildren().add(hyperLink);
            animePage.setAlignment(Pos.CENTER);
            animeInfo.setPadding(new Insets(10));
            alert.getDialogPane().setContent(animePage);
            alert.showAndWait();
        }
        
        else {

            alert.getDialogPane().setContent(gridPane);
            alert.showAndWait();

        }

    }

    /**
     * Helper method that adds anime to the users list based on selection
     * 
     * @param currentUserAnimeList  user anime list used for displaying
     * @param barChart  bar chart
     * @param pieChart  pie chart
     * @param averageScore  average score for user animes
     * @param standardDeviationScore  standard deviation score for user animes
     * @param animeCount  anime count for user
     * @param maxScore  max score entry for user
     * @param minScore  min score entry for user
     * @param medianScore  median score entry for user
     * 
     */
    private void addAnimeToUserList(ArrayList<AnimeData> currentUserAnimeList, BarChartGenerator barChart, PieChartGenerator pieChart, Text averageScore, Text standardDeviationScore, Text animeCount, Text maxScore, Text minScore, Text medianScore) {

        AnimeData selectedAnime = (AnimeData) mainTable.getSelectionModel().getSelectedItem();

        // If an anime is selected and the userlist doesnt have the anime already
        if (selectedAnime != null && !userAnimeList.contains(selectedAnime)) {

            // Add anime
            userAnimeList.add(selectedAnime);
            currentUserAnimeList.add(selectedAnime);
            userTable.setItems(FXCollections.observableArrayList(userAnimeList));

            // Update charts
            pieChart.updateGenrePieChart(userAnimeList);
            barChart.addToBarChart(selectedAnime);

            // Update text
            averageScore.setText("Average score: " + barChart.getScoreAverage());
            standardDeviationScore.setText("Standard Deviation: " + barChart.getStandardDeviation());
            animeCount.setText("Anime Count: " + barChart.getAnimeCount());
            maxScore.setText("Maximum Score: " + barChart.getScoreMax());
            minScore.setText("Minimum Score: " + barChart.getScoreMin());
            medianScore.setText("Median Score: " + barChart.getScoreMedian());
        }
    }

    /**
     * Helper method that removes an anime off the users list based on selection
     * 
     * @param currentUserAnimeList  user anime list used for displaying
     * @param barChart  bar chart
     * @param pieChart  pie chart
     * @param averageScore  average score for user animes
     * @param standardDeviationScore  standard deviation score for user animes
     * @param animeCount  anime count for user
     * @param maxScore  max score entry for user
     * @param minScore  min score entry for user
     * @param medianScore  median score entry for user
     * 
     */
    private void removeAnimeFromUserList(ArrayList<AnimeData> currentUserAnimeList, BarChartGenerator barChart, PieChartGenerator pieChart, Text averageScore, Text standardDeviationScore, Text animeCount, Text maxScore, Text minScore, Text medianScore) {

        AnimeData selectedAnime = (AnimeData) userTable.getSelectionModel().getSelectedItem();

        if (selectedAnime != null && userAnimeList.contains(selectedAnime)) {

            // removes it from lists and graphs.
            userAnimeList.remove(selectedAnime);
            currentUserAnimeList.remove(selectedAnime);
            userTable.setItems(FXCollections.observableArrayList(userAnimeList));
            pieChart.updateGenrePieChart(userAnimeList);
            barChart.removeFromBarChart(selectedAnime);

            // Updates bars and graphs.
            averageScore.setText("Average score: " + barChart.getScoreAverage());
            standardDeviationScore.setText("Standard Deviation: " + barChart.getStandardDeviation());
            animeCount.setText("Anime Count: " + barChart.getAnimeCount());
            maxScore.setText("Maximum Score: " + barChart.getScoreMax());
            minScore.setText("Minimum Score: " + barChart.getScoreMin());
            medianScore.setText("Median Score: " + barChart.getScoreMedian());
        }

    }

    /**
     * Helper method that applies linear search and updates the anime list based on results
     * 
     * @param animeList  the main unmodified
     * @param searchField  text search field
     * @param sortingChoiceBox  sorting choicebox
     * 
     */
    private void animeSearch(ArrayList<AnimeData> animeList, TextField searchField, ChoiceBox sortingChoiceBox) {

        // Setup variables and apply linear search
        String searchText = searchField.getText().toLowerCase();
        ArrayList<AnimeData> searchResults = new ArrayList<>();
        AnimeSorting.linearSearch(animeList, searchField, searchText, searchResults);
        ArrayList<AnimeData> newAnimeList = new ArrayList<>(searchResults);

        // Modify table
        this.currentAnimeList = newAnimeList;
        animeSorting(sortingChoiceBox);
        refreshCurrentAnimeList();
        mainTable.setItems(FXCollections.observableArrayList(newAnimeList));
        
    }

    /**
     * Helper method that applies merge sort and updates the anime list based the sorted list
     * 
     * @param sortingChoiceBox  sorting choice box
     * 
     */
    private void animeSorting(ChoiceBox sortingChoiceBox) {

        // Setup variables and apply merge sort
        int selectedIndex = sortingChoiceBox.getSelectionModel().getSelectedIndex();
        AnimeSorting.mergeSort(this.currentAnimeList, selectedIndex);
        
        // Modify table
        mainTable.setItems(FXCollections.observableArrayList(this.currentAnimeList));
    }

    /**
     * Helper method that filters anime results based on genre
     * 
     * @param animeList  the main unmodified animelist
     * @param filterCheckBox1  the first checkbox
     * @param filterCheckBox2  the second checkbox
     * @param filterCheckBox3  the third checkbox
     * @param genre1  the first genre
     * @param genre2  the second genre
     * @param genre3  the third genre
     * @param sortingChoiceBox  the sortingChoiceBox
     * 
     */
    private void genreFilter(ArrayList<AnimeData> animeList, CheckBox filterCheckBox1, CheckBox filterCheckBox2, CheckBox filterCheckBox3, String genre1, String genre2, String genre3, ChoiceBox sortingChoiceBox){

        // Create a blank filtered list
        ArrayList<AnimeData> filteredAnimeList = new ArrayList<>();

        // is filter checkbox checked
        if (filterCheckBox1.isSelected()){

            // loop through main data and add to filtered list
            for (AnimeData anime : this.currentAnimeList){

                // does anime contain genre? if so add to filtered list
                if (anime.getGenres().contains(genre1)) {

                    filteredAnimeList.add(anime);
                }
            }
            
            // set main table to filtered list
            this.currentAnimeList = filteredAnimeList;
            animeSorting(sortingChoiceBox);
            mainTable.setItems(FXCollections.observableArrayList(this.currentAnimeList));
    
        }
        else {
            // loop through main data and add to filtered list
            for (AnimeData anime : animeList){

                // If the anime doesnt contain the genre and is not in the list
                if (!anime.getGenres().contains(genre1) && !this.currentAnimeList.contains(anime)) {

                    // If both other boxes are checked
                    if (filterCheckBox2.isSelected() && filterCheckBox3.isSelected()) {

                        // Check if anime contains all other 2 genres
                        if (anime.getGenres().contains(genre2) && anime.getGenres().contains(genre3)) {
                            this.currentAnimeList.add(anime);
                        }
                    }

                    // If the first other box is selected
                    else if (filterCheckBox2.isSelected() && !filterCheckBox3.isSelected()) {

                        // Check if anime contains all other 2 genres
                        if (anime.getGenres().contains(genre2) && anime.getGenres().contains(genre3)) {
                            this.currentAnimeList.add(anime);
                        }
                    }  

                    // If the second other box is selected
                    else if (filterCheckBox3.isSelected() && !filterCheckBox2.isSelected()) {

                        // Check if anime contains all other 2 genres
                        if (anime.getGenres().contains(genre3) && anime.getGenres().contains(genre2)) {
                            this.currentAnimeList.add(anime);
                        }
                    }

                    // If neither are selected add the anime
                    else {
                        this.currentAnimeList.add(anime);
                    }
                
                }
            }

            // Sort data, add it to main table and update current anime list
            animeSorting(sortingChoiceBox);
            refreshCurrentAnimeList();
            mainTable.setItems(FXCollections.observableArrayList(this.currentAnimeList));

        }
    }

    /**
     * Helper method that filters anime results based on genre
     * 
     * @param animeList  main anime list
     * @param filterCheckbox  filter checkbox
     * @param genre  genre to filter by
     * @author S. Tuczynski & G. Lui
     */
    private void genreFilter(ArrayList<AnimeData> animeList, CheckBox filterCheckbox, String genre){

        // Create a blank filtered list
        ObservableList<AnimeData> filteredAnimeList = FXCollections.observableArrayList();
        boolean filterEnabled = filterCheckbox.isSelected();

        // is filter checkbox checked
        if (filterEnabled){

            // loop through main data and add to filtered list
            for (AnimeData anime : animeList){

                // does anime contain genre? if so add to filtered list
                if (anime.getGenres().contains(genre)){
                    filteredAnimeList.add(anime);
                }
            }
            
            // set main table to filtered list
            mainTable.setItems(filteredAnimeList);
        }
    }

    /**
     * Helper method that filters anime results to ignore NSFW results
     * 
     * @param nsfwFilterCheckBox  nsfw check box
     * @param animeList  main anime list
     * @param sortingChoiceBox  choice box 
     * @param comedyCheck  the comedy checkbox
     * @param actionCheck  the action checkbox
     * @param romanceCheck  the romance checkbox
     * 
     */
    private void nsfwFilter(CheckBox nsfwFilterCheckBox, ArrayList<AnimeData> animeList, ChoiceBox sortingChoiceBox, CheckBox comedyCheck, CheckBox actionCheck, CheckBox romanceCheck) {

        // Create a blank filtered list
        ArrayList<AnimeData> filteredAnimeList = new ArrayList<>();
        if (nsfwFilterCheckBox.isSelected()) {
        // Iterate through animes in main anime list
            for (AnimeData anime : currentAnimeList) {

                // If anime is not nsfw, add it to new list
                if (!anime.getGenres().contains("Hentai") && !anime.getGenres().contains("Ecchi") && !anime.getGenres().contains("Harem")) {

                    filteredAnimeList.add(anime);
                }
            } 

            // Update main table and current list
            mainTable.setItems(FXCollections.observableArrayList(filteredAnimeList));
            this.currentAnimeList = filteredAnimeList;
        }

        // Otherwise, add all animes to filtered list, as it is not filtered.
        else {

            for (AnimeData anime : animeList){

                // if anime is nsfw and is not in current anime list
                if ((anime.getGenres().contains("Hentai") || anime.getGenres().contains("Ecchi") || anime.getGenres().contains("Harem")) && !currentAnimeList.contains(anime)) {
                    if (!comedyCheck.isSelected() && !actionCheck.isSelected() && !romanceCheck.isSelected()) {
                        this.currentAnimeList.add(anime);
                    }
                }
            }

            // Sort data and add to main table, update current anime list
            animeSorting(sortingChoiceBox);
            refreshCurrentAnimeList();
            mainTable.setItems(FXCollections.observableArrayList(this.currentAnimeList));
            
        }
    }

    /**
     * A helper method that removes duplicate entries from the current anime list
     * 
     */
    private void refreshCurrentAnimeList() {
        for (int i = 0; i < this.tempAnimeList.size()-1; i++) {

            if (this.tempAnimeList.get(i).getTitle().equals(this.tempAnimeList.get(i+1).getTitle())) {
                this.currentAnimeList.remove(this.tempAnimeList.get(i));
            }
        }
    }
}