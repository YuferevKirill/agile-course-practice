package ru.unn.agile.numberstowords.model;

public final class NumbersToWordsConverter {
    private static final int TEN = 10;
    private static final int TWENTY = 20;
    private static final int HUNDRED = 100;
    private static final int THOUSAND = 1000;

    private NumbersToWordsConverter() { }

    private static String getSpaser(final String firstWord, final String lastWord,
                             final String middleWord) {
        if ((!firstWord.equals("")) && (!lastWord.equals(""))) {
            return firstWord + middleWord + lastWord;
        } else {
            return firstWord + lastWord;
        }
    }


    private static String getOnes(final int number) {
        final String[] ones = {"", "one", "two", "three", "four", "five",
                               "six", "seven", "eight", "nine"};

        return ones[number % TEN];
    }

    private static String getTens(final int number) {
        final String[] tens = {"", "", "twenty", "thirty", "forty", "fifty",
                               "sixty", "seventy", "eighty", "ninety"};

        return tens[number / TEN];
    }

    private static String getTeens(final int number) {
        final String[] teens = {"ten", "eleven", "twelve", "thirteen", "fourteen",
                                "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};

        return teens[number % TEN];
    }

    private static String getHighOrder(final int number) {
        String[] highOrder = {"", "thousand", "million", "billion"};

        return highOrder[number];
    }


    private static String getFirstPart(final int number) {
        int numberForAnalysis = number / HUNDRED;
        String numberOfHighOrder = getOnes(numberForAnalysis);

        if (!numberOfHighOrder.equals("")) {
            return getOnes(numberForAnalysis) + " hundred";
        } else {
            return "";
        }
    }

    private static String getSecondPart(final int number) {
        int numberForAnalysis = number % HUNDRED;

        if ((numberForAnalysis >= TEN) && (numberForAnalysis < TWENTY)) {
            return getTeens(numberForAnalysis);
        } else {
            String tens = getTens(numberForAnalysis);
            String ones = getOnes(numberForAnalysis);

            return getSpaser(tens, ones, " ");
        }
    }


    public static String toWord(final int number) {

        if (number < 0) {
            throw new IllegalArgumentException("The value can not be negative.");
        } else {
            String words = "";

            int iter = 0;
            int numberForAnalysis = number;
            while (numberForAnalysis > 0) {
                int numberForAnalysisLow = numberForAnalysis % THOUSAND;
                String firstPartLowOrder = getFirstPart(numberForAnalysisLow);
                String secondPartLowOrder = getSecondPart(numberForAnalysisLow);
                String partLowOrder = getSpaser(firstPartLowOrder, secondPartLowOrder, " and ");

                int numOfHighOrder = partLowOrder.equals("") ? 0 : iter;
                String partHighOrder = getSpaser(partLowOrder, getHighOrder(numOfHighOrder), " ");

                words = getSpaser(partHighOrder, words, ", ");

                numberForAnalysis = numberForAnalysis / THOUSAND;
                iter = iter + 1;
            }

            return words;
        }
    }
}
