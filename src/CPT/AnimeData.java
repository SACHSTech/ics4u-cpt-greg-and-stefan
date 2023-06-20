package CPT;

import java.util.ArrayList;

/**
 * A class models an Anime, which contains a Unique ID, title, summary, genre list, aired date, episode count, members watched count, popularity ranking, score ranking, average score, image link, and anime link.
 * @author S. Tuczynski & G. Lui
 * 
 */
public class AnimeData {
    
    // Instance variables
    private int UID;
    private String title;
    private String synopsis;
    private ArrayList<String> genres;
    private String aired;
    private int episodes;
    private int members; 
    private int popularity;
    private int rank;
    private double score;
    private String imageLink;
    private String animeLink;

    /**
     * Contructor method for AnimeData
     * 
     * @param UID  unique ID
     * @param title  title
     * @param synopsis  summary
     * @param genres  genres list
     * @param aired  aired date
     * @param episodes  episode count
     * @param members  users watched count
     * @param popularity  popularity ranking
     * @param rank  score ranking
     * @param score  average score based on users
     * @param imageLink  image link
     * @param animeLink  anime link to MyAnimeList
     * 
     */
    public AnimeData(int UID, String title, String synopsis, ArrayList<String> genres, String aired, int episodes, int members, int popularity, int rank, double score, String imageLink, String animeLink) {
        this.UID = UID;
        this.title = title;
        this.synopsis = synopsis;
        this.genres = genres;
        this.aired = aired;
        this.episodes = episodes;
        this.members = members;
        this.popularity = popularity;
        this.rank = rank;
        this.score = score;
        this.imageLink = imageLink;
        this.animeLink = animeLink;
    }

    /**
     * Getter method for unique ID
     * 
     * @return the UID
     * 
     */
    public int getUID() {
        return UID;
    }

    /**
     * Setter method for unique ID
     * 
     * @param UID  the UID
     * 
     */
    public void setUID(int UID) {
        this.UID = UID;
    }

    /**
     * Getter method for title
     * 
     * @return the title
     * 
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter method for the title
     * 
     * @param title  the title
     * 
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter method for summary
     * 
     * @return the synopsis
     * 
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Setter method for the summary
     * 
     * @param synopsis  the synopsis
     * 
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * Getter method for genres
     * 
     * @return the list of genres
     * 
     */
    public ArrayList<String> getGenres() {
        return genres;
    } 

    /**
     * Setter method for genre list
     * 
     * @param genres  the genre list
     * 
     */
    public void setGenre(ArrayList<String> genres) {
        this.genres = genres;
    } 

    /**
     * Getter method string of genres
     * 
     * @return the string of genres
     * 
     */
    public String getGenresString() {

        String str = "";
        for (int i = 0; i < this.genres.size(); i++) {

            // End of list, no comma
            if (i == this.genres.size() - 1) {

                str += this.genres.get(i);
            }

            // Continue adding commas
            else {

                str += this.genres.get(i) + ", ";
            }
        }

        return str;
    }

    /**
     * Getter method for aired date
     * 
     * @return the aired date
     * 
     */
    public String getAired() {
        return aired;
    }

    /**
     * Setter method for the aired date
     * 
     * @param aired  the aired date
     * 
     */
    public void setAired(String aired) {
        this.aired = aired;
    }

    /**
     * Getter method for episode count
     * 
     * @return the episode count
     * 
     */
    public int getEpisodes() {
        return episodes;
    }

    /**
     * Setter method for the episode count
     * 
     * @param episodes  the episode count
     * 
     */
    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    /**
     * Getter method for users watched count
     * 
     * @return the users watched count
     * 
     */
    public int getMembers() {
        return members;
    }

    /**
     * Setter method for the user watched count
     * 
     * @param members  the user watched count
     * 
     */
    public void setMembers(int members) {
        this.members = members;
    }

    /**
     * Getter method for popularity ranking
     * 
     * @return the popularity ranking
     * 
     */
    public int getPopularity() {
        return popularity;
    }

    /**
     * Setter method for the popularity ranking
     * 
     * @param popularity  the popularity
     * 
     */
    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    /**
     * Getter method for score ranking
     * 
     * @return the score ranking
     * 
     */
    public int getRank() {
        return rank;
    }

    /**
     * Setter method for the score ranking
     * 
     * @param rank  the score ranking
     * 
     */
    public void setRank(int rank) {
        this.rank = rank;
    }

    /**
     * Getter method for average score
     * 
     * @return the average score
     * 
     */
    public double getScore() {
        return Math.round(score * 100.0) / 100.0;
    }

    /**
     * Setter method for the average score
     * 
     * @param score  the average score
     * 
     */
    public void setScore(double score) {
        this.score = score;
    }

    /**
     * Getter method for image link
     * 
     * @return the image link
     * 
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     * Setter method for the image link
     * 
     * @param imageLink  the image link
     * 
     */
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    /**
     * Getter method for anime link
     * 
     * @return the anime link
     * 
     */
    public String getAnimeLink() {
        return animeLink;
    }

    /**
     * Setter method for anime list
     * 
     * @param animeLink  the anime link
     * 
     */
    public void setAnimeLink(String animeLink) {
        this.animeLink = animeLink;
    }

    /**
     * Title compare method for merge sort operation
     * 
     * @param animeTwo  the anime it is comparing
     * @return  the integer difference between the title it is comparing to
     * 
     */
    public int titleCompareTo(AnimeData animeTwo) {
        return this.getTitle().toLowerCase().compareTo(animeTwo.getTitle().toLowerCase());
    }
    
    /**
     * Score compare method for merge sort operation
     * 
     * @param animeTwo  the anime it is comparing
     * @return  the integer difference between the score it is comparing to
     * 
     */
    public double scoreCompareTo(AnimeData animeTwo) {
        return this.getScore() - animeTwo.getScore();
    }

    /**
     * Popularity ranking compare method for merge sort operation
     * 
     * @param animeTwo  the anime it is comparing
     * @return  the integer difference between the popularity ranking it is comparing to
     * 
     */
    public int popularityCompareTo(AnimeData animeTwo) {
        return this.getPopularity() - animeTwo.getPopularity();
    }

    /**
     * User watched count compare method for merge sort operation
     * 
     * @param animeTwo  the anime it is comparing
     * @return  the integer difference between the User watched count it is comparing to
     * 
     */
    public int membersCompareTo(AnimeData animeTwo) {
        return this.getMembers() - animeTwo.getMembers();
    }

    /**
     * Episode count compare method for merge sort operation
     * 
     * @param animeTwo  the anime it is comparing
     * @return  the integer different between the episode count it is comparing to
     * 
     */
    public int episodesCompareTo(AnimeData animeTwo) {
        return this.getEpisodes() - animeTwo.getEpisodes();
    }

    /**
     * Score ranking compare method for merge sort operation
     * 
     * @param animeTwo  the anime it is comparing
     * @return  the integer different between the score ranking it is comparing to
     * 
     */
    public int rankCompareTo(AnimeData animeTwo) {
        return this.getRank() - animeTwo.getRank();
    }
}
