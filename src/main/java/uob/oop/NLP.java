package uob.oop;

public class NLP {
    /***
     * Clean the given (_content) text by removing all the characters that are not 'a'-'z', '0'-'9' and white space.
     * @param _content Text that need to be cleaned.
     * @return The cleaned text.
     */
    public static String textCleaning(String _content) {
        StringBuilder sbContent = new StringBuilder();
        //TODO Task 2.1 - 3 marks
        char[] contentArray = _content.toLowerCase().toCharArray();
        for (int i=0; i<contentArray.length; i++) {
            if (Character.isDigit(contentArray[i]) || Character.isWhitespace(contentArray[i])
                || (contentArray[i] <= 'z' && contentArray[i] >= 'a')) {
                sbContent.append(contentArray[i]);
            }
        }
        return sbContent.toString().trim();
    }

    /***
     * Text lemmatization. Delete 'ing', 'ed', 'es' and 's' from the end of the word.
     * @param _content Text that need to be lemmatized.
     * @return Lemmatized text.
     */
    public static String textLemmatization(String _content) {
        StringBuilder sbContent = new StringBuilder();
        //TODO Task 2.2 - 3 marks
        String[] splitWords = _content.split(" ");
        for (int i=0; i<splitWords.length; i++) {
            if (splitWords[i].endsWith("ing")) {
                sbContent.append(splitWords[i], 0, splitWords[i].length()-3).append(" ");
            } else if (splitWords[i].endsWith("ed") || splitWords[i].endsWith("es")) {
                sbContent.append(splitWords[i], 0, splitWords[i].length()-2).append(" ");
            } else if (splitWords[i].endsWith("s")) {
                sbContent.append(splitWords[i], 0, splitWords[i].length()-1).append(" ");
            } else {
                sbContent.append(splitWords[i]).append(" ");
            }
        }
        return sbContent.toString().trim();
    }

    /***
     * Remove stop-words from the text.
     * @param _content The original text.
     * @param _stopWords An array that contains stop-words.
     * @return Modified text.
     */
    public static String removeStopWords(String _content, String[] _stopWords) {
        StringBuilder sbConent = new StringBuilder();
        //TODO Task 2.3 - 3 marks
        String[] splitWords = _content.split(" ");
        for (int i=0; i<splitWords.length; i++) {
            for (int j=0; j<_stopWords.length; j++) {
                if (splitWords[i].equals(_stopWords[j])) {
                    splitWords[i] = "stopWord";
                    break;
                }
            }
            if (!splitWords[i].equals("stopWord")) {
                sbConent.append(splitWords[i]).append(" ");
            }
        }
        return sbConent.toString().trim();
    }

}
