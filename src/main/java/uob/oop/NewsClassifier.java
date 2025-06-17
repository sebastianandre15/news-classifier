package uob.oop;

import java.text.DecimalFormat;

public class NewsClassifier {
    public String[] myHTMLs;
    public String[] myStopWords = new String[127];
    public String[] newsTitles;
    public String[] newsContents;
    public String[] newsCleanedContent;
    public double[][] newsTFIDF;

    private final String TITLE_GROUP1 = "Osiris-Rex's sample from asteroid Bennu will reveal secrets of our solar system";
    private final String TITLE_GROUP2 = "Bitcoin slides to five-month low amid wider sell-off";

    public Toolkit myTK;

    public NewsClassifier() {
        myTK = new Toolkit();
        myHTMLs = myTK.loadHTML();
        myStopWords = myTK.loadStopWords();

        loadData();
    }

    public static void main(String[] args) {
        NewsClassifier myNewsClassifier = new NewsClassifier();

        myNewsClassifier.newsCleanedContent = myNewsClassifier.preProcessing();

        myNewsClassifier.newsTFIDF = myNewsClassifier.calculateTFIDF(myNewsClassifier.newsCleanedContent);

        //Change the _index value to calculate similar based on a different news article.
        double[][] doubSimilarity = myNewsClassifier.newsSimilarity(0);

        System.out.println(myNewsClassifier.resultString(doubSimilarity, 10));

        String strGroupingResults = myNewsClassifier.groupingResults(myNewsClassifier.TITLE_GROUP1, myNewsClassifier.TITLE_GROUP2);
        System.out.println(strGroupingResults);
    }

    public void loadData() {
        //TODO 4.1 - 2 marks
        newsTitles = new String[myHTMLs.length];
        newsContents = new String[myHTMLs.length];
        for (int i=0; i<myHTMLs.length; i++) {
            newsTitles[i] = HtmlParser.getNewsTitle(myHTMLs[i]);
            newsContents[i] = HtmlParser.getNewsContent(myHTMLs[i]);
        }
    }

    public String[] preProcessing() {
        String[] myCleanedContent = null;
        //TODO 4.2 - 5 marks
        myCleanedContent = new String[newsContents.length];
        for (int i=0; i<newsContents.length; i++) {
            myCleanedContent[i] = NLP.textCleaning(newsContents[i]);
            myCleanedContent[i] = NLP.textLemmatization(myCleanedContent[i]);
            myCleanedContent[i] = NLP.removeStopWords(myCleanedContent[i], myStopWords);
        }
        return myCleanedContent;
    }

    public double[][] calculateTFIDF(String[] _cleanedContents) {
        String[] vocabularyList = buildVocabulary(_cleanedContents);
        double[][] myTFIDF = null;

        //TODO 4.3 - 10 marks
        myTFIDF = new double[_cleanedContents.length][vocabularyList.length];
        int[] docsContainingWord = new int[vocabularyList.length];
        double[] TF = new double[_cleanedContents.length];
        double[] IDF = new double[vocabularyList.length];
        for (int i=0; i<vocabularyList.length; i++) {
            for (int j = 0; j < _cleanedContents.length; j++) {
                int wordFrequency = 0;
                String[] eachWord = _cleanedContents[j].split(" ");
                for (int k = 0; k < eachWord.length; k++) {
                    if (eachWord[k].equals(vocabularyList[i])) {
                        wordFrequency += 1;
                    }
                }
                TF[j] = ((double) wordFrequency / eachWord.length);
                if (wordFrequency > 0) {
                    docsContainingWord[i] += 1;
                }
            }
            IDF[i] = Math.log((double) _cleanedContents.length /docsContainingWord[i]) + 1;
            for (int l=0; l< _cleanedContents.length; l++) {
                myTFIDF[l][i] = TF[l]*IDF[i];
            }
        }
        return myTFIDF;
    }

        public String[] buildVocabulary(String[] _cleanedContents) {
        String[] arrayVocabulary = null;

        //TODO 4.4 - 10 marks
        StringBuilder uniqueWords = new StringBuilder(_cleanedContents[0].split(" ")[0]);
        arrayVocabulary = new String[]{_cleanedContents[0].split(" ")[0]};
        for (int i=0; i< _cleanedContents.length; i++) {
            String[] eachWord = _cleanedContents[i].split(" ");
            for (int j=0; j< eachWord.length; j++) {
                int wordCount = 0;
                for (int k=0; k< arrayVocabulary.length; k++) {
                    if (eachWord[j].equals(arrayVocabulary[k])) {
                        wordCount += 1;
                        break;
                    }
                }
                if (wordCount == 0) {
                    uniqueWords.append("/").append(eachWord[j]);
                }
                arrayVocabulary = uniqueWords.toString().split("/");
            }
        }
        return arrayVocabulary;
    }

    public double[][] newsSimilarity(int _newsIndex) {
        double[][] mySimilarity = null;

        //TODO 4.5 - 15 marks
        mySimilarity = new double[myHTMLs.length][2];
        Vector newsIndexTFIDF = new Vector(newsTFIDF[_newsIndex]);
        for (int i=0; i< myHTMLs.length; i++) {
            Vector TFIDF = new Vector(newsTFIDF[i]);
            mySimilarity[i][0] = i;
            mySimilarity[i][1] = TFIDF.cosineSimilarity(newsIndexTFIDF);
        }
        double tempIndex;
        for (int j=1; j<mySimilarity.length; j++) {
            double insertItem = mySimilarity[j][1];
            int k = j-1;
            while (k>=0 && mySimilarity[k][1] < insertItem) {
                mySimilarity[1+k][1] = mySimilarity[k][1];
                tempIndex = mySimilarity[1+k][0];
                mySimilarity[1+k][0] = mySimilarity[k][0];
                mySimilarity[k][0] = tempIndex;
                k -= 1;
            }
            mySimilarity[1+k][1] = insertItem;
        }
        return mySimilarity;
    }

    public String groupingResults(String _firstTitle, String _secondTitle) {
        int[] arrayGroup1 = null, arrayGroup2 = null;

        //TODO 4.6 - 15 marks
        int firstTitleIndex = 0;
        int secondTitleIndex = 0;
        StringBuilder group1 = new StringBuilder();
        StringBuilder group2 = new StringBuilder();
        String[] strArrayGroup1 = null;
        String[] strArrayGroup2 = null;
        double[][] firstTitleSimilarity = null;
        double[][] secondTitleSimilarity = null;
        firstTitleSimilarity = new double[myHTMLs.length][2];
        secondTitleSimilarity = new double[myHTMLs.length][2];

        for (int i=0; i< newsTitles.length; i++) {
            if (newsTitles[i].equals(_firstTitle)) {
                firstTitleIndex = i;
            } else if (newsTitles[i].equals(_secondTitle)) {
                secondTitleIndex = i;
            }
        }
        Vector firstTitleTFIDF = new Vector(newsTFIDF[firstTitleIndex]);
        Vector secondTitleTFIDF = new Vector(newsTFIDF[secondTitleIndex]);
        for (int x=0; x<myHTMLs.length; x++) {
            Vector TFIDF = new Vector(newsTFIDF[x]);
            firstTitleSimilarity[x][0] = x;
            firstTitleSimilarity[x][1] = firstTitleTFIDF.cosineSimilarity(TFIDF);
            secondTitleSimilarity[x][0] = x;
            secondTitleSimilarity[x][1] = secondTitleTFIDF.cosineSimilarity((TFIDF));
        }
        for (int j=0; j<firstTitleSimilarity.length; j++) {
            for (int k=0; k<secondTitleSimilarity.length; k++) {
                if (firstTitleSimilarity[j][0] == secondTitleSimilarity[k][0]) {
                    if (firstTitleSimilarity[j][1] > secondTitleSimilarity[k][1]) {
                        group1.append(" ").append(Math.round(firstTitleSimilarity[j][0]));
                    } else {
                        group2.append(" ").append(Math.round(secondTitleSimilarity[k][0]));
                    }
                    break;
                }
            }
        }
        group1 = new StringBuilder(group1.substring(1));
        group2 = new StringBuilder(group2.substring(1));
        strArrayGroup1 = group1.toString().split(" ");
        strArrayGroup2 = group2.toString().split(" ");
        arrayGroup1 = new int[strArrayGroup1.length];
        arrayGroup2 = new int[strArrayGroup2.length];
        for (int t=0; t< strArrayGroup1.length; t++) {
           arrayGroup1[t] = Integer.parseInt(strArrayGroup1[t]);
        }
        for (int y=0; y< strArrayGroup2.length; y++) {
            arrayGroup2[y] = Integer.parseInt(strArrayGroup2[y]);
        }
        return resultString(arrayGroup1, arrayGroup2);
    }

    public String resultString(double[][] _similarityArray, int _groupNumber) {
        StringBuilder mySB = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        for (int j = 0; j < _groupNumber; j++) {
            for (int k = 0; k < _similarityArray[j].length; k++) {
                if (k == 0) {
                    mySB.append((int) _similarityArray[j][k]).append(" ");
                } else {
                    String formattedCS = decimalFormat.format(_similarityArray[j][k]);
                    mySB.append(formattedCS).append(" ");
                }
            }
            mySB.append(newsTitles[(int) _similarityArray[j][0]]).append("\r\n");
        }
        mySB.delete(mySB.length() - 2, mySB.length());
        return mySB.toString();
    }

    public String resultString(int[] _firstGroup, int[] _secondGroup) {
        StringBuilder mySB = new StringBuilder();
        mySB.append("There are ").append(_firstGroup.length).append(" news in Group 1, and ").append(_secondGroup.length).append(" in Group 2.\r\n").append("=====Group 1=====\r\n");

        for (int i : _firstGroup) {
            mySB.append("[").append(i + 1).append("] - ").append(newsTitles[i]).append("\r\n");
        }
        mySB.append("=====Group 2=====\r\n");
        for (int i : _secondGroup) {
            mySB.append("[").append(i + 1).append("] - ").append(newsTitles[i]).append("\r\n");
        }

        mySB.delete(mySB.length() - 2, mySB.length());
        return mySB.toString();
    }

}
