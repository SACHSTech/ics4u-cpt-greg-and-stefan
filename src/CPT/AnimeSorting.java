package CPT;

import java.util.ArrayList;
import javafx.scene.control.TextField;

/**
 * A class which contains methods that is used for sorting and reflects what we've learned in the algorithms unit.
 * @author S. Tuczynski & G. Lui
 * 
 */
public class AnimeSorting {

    /**
     * Merge sort method, recursive split portion
     * 
     * @param list  Anime data list displayed on main page
     * @param comparatorType  Comparator value
     * 
     */
    public static void mergeSort(ArrayList<AnimeData> list, int comparatorType) {

        if (list == null || list.size() <= 1) {
            
            // Base case: has one item or nothing
            return; 
        }

        // Split list into two halves
        int mid = list.size() / 2;
        ArrayList<AnimeData> left = new ArrayList<>(list.subList(0, mid));
        ArrayList<AnimeData> right = new ArrayList<>(list.subList(mid, list.size()));

        // Recursively sort the two halves
        mergeSort(left, comparatorType);
        mergeSort(right, comparatorType);

        // Merge the sorted halves
        merge(list, left, right, comparatorType);
    }

    /**
     * Merge sort method, merge portion. 
     * 
     * @param list  Anime data list 
     * @param left  Left side 
     * @param right  Right side
     * @param comparatorType  Comparator value
     */
    private static void merge(ArrayList<AnimeData> list, ArrayList<AnimeData> left, ArrayList<AnimeData> right, int comparatorType) {
        int leftIndex = 0;
        int rightIndex = 0;
        int listIndex = 0;

        // Compare first data types of objects from left and right lists
        while (leftIndex < left.size() && rightIndex < right.size()) {

            AnimeData leftAnime = left.get(leftIndex);
            AnimeData rightAnime = right.get(rightIndex);

            switch (comparatorType){

                // Title
                case 0:
                    if (leftAnime.titleCompareTo(rightAnime) <= 0) {

                        list.set(listIndex, leftAnime);
                        leftIndex++;
                    } 
                    
                    else {
                        list.set(listIndex, rightAnime);

                        rightIndex++;
                    }

                    break;

                // Score
                case 1:
                    if (leftAnime.scoreCompareTo(rightAnime) > 0) {
                            list.set(listIndex, leftAnime);
                            leftIndex++;
                        } 
                    
                    else {
                        list.set(listIndex, rightAnime);
                        rightIndex++;
                    }

                    break;
                
                // Popularity ranking
                case 2:
                    if (leftAnime.popularityCompareTo(rightAnime) <= 0) {
                            list.set(listIndex, leftAnime);
                            leftIndex++;
                        } 
                    
                    else {
                        list.set(listIndex, rightAnime);
                        rightIndex++;
                    }

                    break;

                // Score ranking
                case 3:
                    if (leftAnime.rankCompareTo(rightAnime) < 0) {
                            list.set(listIndex, leftAnime);
                            leftIndex++;
                        } 
                    
                    else {
                        list.set(listIndex, rightAnime);
                        rightIndex++;
                    }

                    break;
                
                // User watched count 
                case 4:
                    if (leftAnime.membersCompareTo(rightAnime) >= 0) {
                            list.set(listIndex, leftAnime);
                            leftIndex++;
                        } 
                    
                    else {
                        list.set(listIndex, rightAnime);
                        rightIndex++;
                    }

                    break;

                // Episode count
                case 5:
                    if (leftAnime.episodesCompareTo(rightAnime) >= 0) {
                            list.set(listIndex, leftAnime);
                            leftIndex++;
                        } 
                    
                    else {
                        list.set(listIndex, rightAnime);
                        rightIndex++;
                    }

                    break;
            }
 
            listIndex++;
        }

        // Copy remaining objects from left list
        while (leftIndex < left.size()) {
            list.set(listIndex, left.get(leftIndex));
            leftIndex++;
            listIndex++;
        }

        // Copy remaining objects from right list
        while (rightIndex < right.size()) {
            list.set(listIndex, right.get(rightIndex));
            rightIndex++;
            listIndex++;
        }
    }

    /**
     * Linear search algorithm method to use in search bar on main anime list
     * 
     * @param animeList  main anime list
     * @param searchField  search field
     * @param searchText  the inputted user text
     * @param searchResults  the list of animeresults
     * 
     */
    public static void linearSearch(ArrayList<AnimeData> animeList, TextField searchField, String searchText, ArrayList<AnimeData> searchResults) {
        
        //Iterate through animeList
        for (AnimeData anime : animeList) {

            // If search is nothing
            if (searchText.length() == 0){
                searchResults.add(anime);
            }

            // If search is numbers
            else if (isDigit(searchField)){

                if (anime.getRank() == Double.parseDouble(searchText)) {

                    searchResults.add(anime);
                }

                else if (anime.getPopularity() == Double.parseDouble(searchText)) {

                    searchResults.add(anime);
                }

                else if (anime.getMembers() == Double.parseDouble(searchText)) {

                    searchResults.add(anime);
                }

                else if (anime.getEpisodes() == Double.parseDouble(searchText)) {

                    searchResults.add(anime);
                }

                if (anime.getScore() == Double.parseDouble(searchText)) {

                    searchResults.add(anime);
                }
                
            }

            // If search is strings
            else {

                if (anime.getTitle().toLowerCase().contains(searchText)) {

                    searchResults.add(anime);
                }

                else if (anime.getGenresString().toLowerCase().contains(searchText)) {

                    searchResults.add(anime);
                }

                else if (anime.getAired().toLowerCase().contains(searchText)) {

                    searchResults.add(anime);
                }

            }
        }
    }

    /**
     * Helper method to determine if search is a number or string
     * 
     * @param searchField  the search field
     * @return  whether search is number or string
     * 
     */
    private static boolean isDigit(TextField searchField){
        try {
            Double.parseDouble(searchField.getText());
            return true;
        } 
        catch (NumberFormatException e) {
            return false;
        }
    }
    
}
