package uob.oop;

public class HtmlParser {
    /***
     * Extract the title of the news from the _htmlCode.
     * @param _htmlCode Contains the full HTML string from a specific news. E.g. 01.htm.
     * @return Return the title if it's been found. Otherwise, return "Title not found!".
     */
    public static String getNewsTitle(String _htmlCode) {
        //TODO Task 1.1 - 5 marks
        int startIndexTitle = _htmlCode.indexOf("<title>");
        int endIndexTitle = _htmlCode.indexOf(" | ");
        if (startIndexTitle < 0 || endIndexTitle < 0) {
            return "Title not found!";
        } else {
            return _htmlCode.substring(startIndexTitle+7, endIndexTitle);
        }
    }

    /***
     * Extract the content of the news from the _htmlCode.
     * @param _htmlCode Contains the full HTML string from a specific news. E.g. 01.htm.
     * @return Return the content if it's been found. Otherwise, return "Content not found!".
     */
    public static String getNewsContent(String _htmlCode) {
        //TODO Task 1.2 - 5 marks
        int startIndexContents = _htmlCode.indexOf("articleBody");
        int endIndexContents = _htmlCode.indexOf(("mainEntityOfPage"));
        if (startIndexContents < 0 || endIndexContents < 0) {
            return "Content not found";
        } else {
            return _htmlCode.substring(startIndexContents+15, endIndexContents-4).toLowerCase();
        }
    }
}
