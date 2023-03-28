package seedu.internship.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.internship.testutil.Assert.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;
import seedu.internship.logic.parser.ParserUtil;
import seedu.internship.logic.parser.exceptions.ParseException;

public class StringUtilTest {

    //---------------- Tests for nonZeroUnsignedIntegerCheck --------------------------------------

    @Test
    public void nonZeroUnsignedIntegerCheck() {

        // EP: empty strings
        this.nonZeroUnsignedIntegerCheckAssertFail("", ParserUtil.MESSAGE_INVALID_INDEX_FORMAT); // Boundary value
        this.nonZeroUnsignedIntegerCheckAssertFail("  ", ParserUtil.MESSAGE_INVALID_INDEX_FORMAT);
        // EP: not a number
        this.nonZeroUnsignedIntegerCheckAssertFail("a", ParserUtil.MESSAGE_INVALID_INDEX_FORMAT);
        this.nonZeroUnsignedIntegerCheckAssertFail("aaa", ParserUtil.MESSAGE_INVALID_INDEX_FORMAT);

        // EP: zero
        this.nonZeroUnsignedIntegerCheckAssertFail("0", ParserUtil.MESSAGE_INVALID_INDEX);

        // EP: zero as prefix
        this.nonZeroUnsignedIntegerCheckAssertPass("01");

        // EP: signed negative numbers
        this.nonZeroUnsignedIntegerCheckAssertFail("-1", ParserUtil.MESSAGE_INVALID_INDEX);
        this.nonZeroUnsignedIntegerCheckAssertFail("-100", ParserUtil.MESSAGE_INVALID_INDEX);

        // EP: signed positive numbers
        this.nonZeroUnsignedIntegerCheckAssertFail("+1", ParserUtil.MESSAGE_INVALID_POSITIVE_SIGNED_INDEX);
        this.nonZeroUnsignedIntegerCheckAssertFail("+100", ParserUtil.MESSAGE_INVALID_POSITIVE_SIGNED_INDEX);

        // EP: numbers with white space

        // Leading/trailing spaces
        this.nonZeroUnsignedIntegerCheckAssertFail(" 10 ", ParserUtil.MESSAGE_INVALID_INDEX_FORMAT);
        // Spaces in the middle
        this.nonZeroUnsignedIntegerCheckAssertFail("1 0", ParserUtil.MESSAGE_INVALID_INDEX_FORMAT);

        // EP: number larger than Integer.MAX_VALUE
        nonZeroUnsignedIntegerCheckAssertFail(Long.toString(Integer.MAX_VALUE + 1), ParserUtil.MESSAGE_INVALID_INDEX);

        // EP: valid numbers, should return true
        this.nonZeroUnsignedIntegerCheckAssertPass("1"); // Boundary value
        this.nonZeroUnsignedIntegerCheckAssertPass("10");
    }

    private static void nonZeroUnsignedIntegerCheckAssertPass(String input) {
        try {
            StringUtil.nonZeroUnsignedIntegerCheck(input);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    private static void nonZeroUnsignedIntegerCheckAssertFail(String input, String expectedMessage) {
        try {
            StringUtil.nonZeroUnsignedIntegerCheck(input);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(expectedMessage, pe.getMessage());
        }
    }


    //---------------- Tests for containsWordIgnoreCase --------------------------------------

    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullWord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsWordIgnoreCase("typical sentence", null));
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter cannot be empty", ()
            -> StringUtil.containsWordIgnoreCase("typical sentence", "  "));
    }

    @Test
    public void containsWordIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter should be a single word", ()
            -> StringUtil.containsWordIgnoreCase("typical sentence", "aaa BBB"));
    }

    @Test
    public void containsWordIgnoreCase_nullSentence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsWordIgnoreCase(null, "abc"));
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCase_validInputs_correctResult() {

        // Empty sentence
        assertFalse(StringUtil.containsWordIgnoreCase("", "abc")); // Boundary case
        assertFalse(StringUtil.containsWordIgnoreCase("    ", "123"));

        // Matches a partial word only
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bb")); // Sentence word bigger than query word
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bbbb")); // Query word bigger than sentence word

        // Matches word in the sentence, different upper/lower case letters
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc", "Bbb")); // First word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc@1", "CCc@1")); // Last word (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("  AAA   bBb   ccc  ", "aaa")); // Sentence has extra spaces
        assertTrue(StringUtil.containsWordIgnoreCase("Aaa", "aaa")); // Only one word in sentence (boundary case)
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "  ccc  ")); // Leading/trailing spaces

        // Matches multiple words in sentence
        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }

    //---------------- Tests for getDetails --------------------------------------

    /*
     * Equivalence Partitions: null, valid throwable object
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertTrue(StringUtil.getDetails(new FileNotFoundException("file not found"))
            .contains("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.getDetails(null));
    }

}
