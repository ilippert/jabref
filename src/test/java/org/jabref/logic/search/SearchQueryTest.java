package org.jabref.logic.search;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.EnumSet;

import org.jabref.logic.pdf.search.LuceneIndexer;
import org.jabref.logic.pdf.search.LuceneSearcher;
import org.jabref.model.database.BibDatabase;
import org.jabref.model.database.BibDatabaseContext;
import org.jabref.model.search.rules.SearchRules;
import org.jabref.preferences.FilePreferences;
import org.jabref.preferences.PreferencesService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchQueryTest {

    private LuceneSearcher searcher;
    private PreferencesService preferencesService;
    private BibDatabase bibDatabase;
    private BibDatabaseContext bibDatabaseContext;

    @BeforeEach
    public void setUp(@TempDir Path indexDir) throws IOException {
        preferencesService = mock(PreferencesService.class);
        when(preferencesService.getFilePreferences()).thenReturn(mock(FilePreferences.class));

        bibDatabase = new BibDatabase();
        bibDatabaseContext = mock(BibDatabaseContext.class);
        when(bibDatabaseContext.getFileDirectories(Mockito.any())).thenReturn(Collections.singletonList(Path.of("src/test/resources/pdfs")));
        when(bibDatabaseContext.getFulltextIndexPath()).thenReturn(indexDir);
        when(bibDatabaseContext.getDatabase()).thenReturn(bibDatabase);
        when(bibDatabaseContext.getEntries()).thenReturn(bibDatabase.getEntries());

        LuceneIndexer indexer = LuceneIndexer.of(bibDatabaseContext, preferencesService);
        indexer.createIndex();

        searcher = LuceneSearcher.of(bibDatabaseContext);
    }

    @Test
    public void nullWhenQueryBlank() {
        assertNull(new SearchQuery("", EnumSet.noneOf(SearchRules.SearchFlags.class)).getQuery());
    }

//    @Test
//    public void testToString() {
//        assertEquals("\"asdf\" (case sensitive, regular expression) [CASE_SENSITIVE, REGULAR_EXPRESSION]", new SearchQuery("asdf", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE, SearchRules.SearchFlags.REGULAR_EXPRESSION)).toString());
//        assertEquals("\"asdf\" (case insensitive, plain text) []", new SearchQuery("asdf", EnumSet.noneOf(SearchFlags.class)).toString());
//    }
//
//    @Test
//    public void isContainsBasedSearch() {
//        assertTrue(new SearchQuery("asdf", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE)).isContainsBasedSearch());
//        assertTrue(new SearchQuery("asdf", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE, SearchRules.SearchFlags.REGULAR_EXPRESSION)).isContainsBasedSearch());
//        assertFalse(new SearchQuery("author=asdf", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE)).isContainsBasedSearch());
//    }
//
//    @Test
//    public void isGrammarBasedSearch() {
//        assertFalse(new SearchQuery("asdf", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE)).isGrammarBasedSearch());
//        assertFalse(new SearchQuery("asdf", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE, SearchRules.SearchFlags.REGULAR_EXPRESSION)).isGrammarBasedSearch());
//        assertTrue(new SearchQuery("author=asdf", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE)).isGrammarBasedSearch());
//    }
//
//    @Test
//    public void grammarSearch() {
//        BibEntry entry = new BibEntry();
//        entry.addKeyword("one two", ',');
//        SearchQuery searchQuery = new SearchQuery("keywords=\"one two\"", EnumSet.noneOf(SearchFlags.class));
//        assertTrue(searchQuery.isMatch(entry));
//    }
//
//    @Test
//    public void grammarSearchFullEntryLastCharMissing() {
//        BibEntry entry = new BibEntry();
//        entry.setField(StandardField.TITLE, "systematic revie");
//        SearchQuery searchQuery = new SearchQuery("title=\"systematic review\"", EnumSet.noneOf(SearchFlags.class));
//        assertFalse(searchQuery.isMatch(entry));
//    }
//
//    @Test
//    public void grammarSearchFullEntry() {
//        BibEntry entry = new BibEntry();
//        entry.setField(StandardField.TITLE, "systematic review");
//        SearchQuery searchQuery = new SearchQuery("title=\"systematic review\"", EnumSet.noneOf(SearchFlags.class));
//        assertTrue(searchQuery.isMatch(entry));
//    }
//
//    @Test
//    public void searchingForOpenBraketInBooktitle() {
//        BibEntry e = new BibEntry(StandardEntryType.InProceedings);
//        e.setField(StandardField.BOOKTITLE, "Super Conference (SC)");
//
//        SearchQuery searchQuery = new SearchQuery("booktitle=\"(\"", EnumSet.noneOf(SearchFlags.class));
//        assertTrue(searchQuery.isMatch(e));
//    }
//
//    @Test
//    public void searchMatchesSingleKeywordNotPart() {
//        BibEntry e = new BibEntry(StandardEntryType.InProceedings);
//        e.setField(StandardField.KEYWORDS, "banana, pineapple, orange");
//
//        SearchQuery searchQuery = new SearchQuery("anykeyword==apple", EnumSet.noneOf(SearchFlags.class));
//        assertFalse(searchQuery.isMatch(e));
//    }
//
//    @Test
//    public void searchMatchesSingleKeyword() {
//        BibEntry e = new BibEntry(StandardEntryType.InProceedings);
//        e.setField(StandardField.KEYWORDS, "banana, pineapple, orange");
//
//        SearchQuery searchQuery = new SearchQuery("anykeyword==pineapple", EnumSet.noneOf(SearchFlags.class));
//        assertTrue(searchQuery.isMatch(e));
//    }
//
//    @Test
//    public void searchAllFields() {
//        BibEntry e = new BibEntry(StandardEntryType.InProceedings);
//        e.setField(StandardField.TITLE, "Fruity features");
//        e.setField(StandardField.KEYWORDS, "banana, pineapple, orange");
//
//        SearchQuery searchQuery = new SearchQuery("anyfield==\"fruity features\"", EnumSet.noneOf(SearchFlags.class));
//        assertTrue(searchQuery.isMatch(e));
//    }
//
//    @Test
//    public void searchAllFieldsNotForSpecificField() {
//        BibEntry e = new BibEntry(StandardEntryType.InProceedings);
//        e.setField(StandardField.TITLE, "Fruity features");
//        e.setField(StandardField.KEYWORDS, "banana, pineapple, orange");
//
//        SearchQuery searchQuery = new SearchQuery("anyfield=fruit and keywords!=banana", EnumSet.noneOf(SearchFlags.class));
//        assertFalse(searchQuery.isMatch(e));
//    }
//
//    @Test
//    public void searchAllFieldsAndSpecificField() {
//        BibEntry e = new BibEntry(StandardEntryType.InProceedings);
//        e.setField(StandardField.TITLE, "Fruity features");
//        e.setField(StandardField.KEYWORDS, "banana, pineapple, orange");
//
//        SearchQuery searchQuery = new SearchQuery("anyfield=fruit and keywords=apple", EnumSet.noneOf(SearchFlags.class));
//        assertTrue(searchQuery.isMatch(e));
//    }
//
//    @Test
//    public void isMatch() {
//        BibEntry entry = new BibEntry();
//        entry.setType(StandardEntryType.Article);
//        entry.setField(StandardField.AUTHOR, "asdf");
//
//        assertFalse(new SearchQuery("BiblatexEntryType", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE, SearchRules.SearchFlags.REGULAR_EXPRESSION)).isMatch(entry));
//        assertTrue(new SearchQuery("asdf", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE, SearchRules.SearchFlags.REGULAR_EXPRESSION)).isMatch(entry));
//        assertTrue(new SearchQuery("author=asdf", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE, SearchRules.SearchFlags.REGULAR_EXPRESSION)).isMatch(entry));
//    }
//
//    @Test
//    public void isValidQueryNotAsRegEx() {
//        assertTrue(new SearchQuery("asdf", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE)).isValid());
//    }
//
//    @Test
//    public void isValidQueryContainsBracketNotAsRegEx() {
//        assertTrue(new SearchQuery("asdf[", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE)).isValid());
//    }
//
//    @Test
//    public void isNotValidQueryContainsBracketNotAsRegEx() {
//        assertTrue(new SearchQuery("asdf[", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE, SearchRules.SearchFlags.REGULAR_EXPRESSION)).isValid());
//    }
//
//    @Test
//    public void isValidQueryAsRegEx() {
//        assertTrue(new SearchQuery("asdf", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE, SearchRules.SearchFlags.REGULAR_EXPRESSION)).isValid());
//    }
//
//    @Test
//    public void isValidQueryWithNumbersAsRegEx() {
//        assertTrue(new SearchQuery("123", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE, SearchRules.SearchFlags.REGULAR_EXPRESSION)).isValid());
//    }
//
//    @Test
//    public void isValidQueryContainsBracketAsRegEx() {
//        assertTrue(new SearchQuery("asdf[", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE, SearchRules.SearchFlags.REGULAR_EXPRESSION)).isValid());
//    }
//
//    @Test
//    public void isValidQueryWithEqualSignAsRegEx() {
//        assertTrue(new SearchQuery("author=asdf", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE, SearchRules.SearchFlags.REGULAR_EXPRESSION)).isValid());
//    }
//
//    @Test
//    public void isValidQueryWithNumbersAndEqualSignAsRegEx() {
//        assertTrue(new SearchQuery("author=123", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE, SearchRules.SearchFlags.REGULAR_EXPRESSION)).isValid());
//    }
//
//    @Test
//    public void isValidQueryWithEqualSignNotAsRegEx() {
//        assertTrue(new SearchQuery("author=asdf", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE)).isValid());
//    }
//
//    @Test
//    public void isValidQueryWithNumbersAndEqualSignNotAsRegEx() {
//        assertTrue(new SearchQuery("author=123", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE)).isValid());
//    }
//
//    @Test
//    public void isMatchedForNormalAndFieldBasedSearchMixed() {
//        BibEntry entry = new BibEntry();
//        entry.setType(StandardEntryType.Article);
//        entry.setField(StandardField.AUTHOR, "asdf");
//        entry.setField(StandardField.ABSTRACT, "text");
//
//        assertTrue(new SearchQuery("text AND author=asdf", EnumSet.of(SearchRules.SearchFlags.CASE_SENSITIVE, SearchRules.SearchFlags.REGULAR_EXPRESSION)).isMatch(entry));
//    }
//
//    @Test
//    public void simpleTerm() {
//        String query = "progress";
//
//        SearchQuery result = new SearchQuery(query, EnumSet.noneOf(SearchFlags.class));
//        assertFalse(result.isGrammarBasedSearch());
//    }
//
//    @Test
//    public void getPattern() {
//        String query = "progress";
//        SearchQuery result = new SearchQuery(query, EnumSet.noneOf(SearchFlags.class));
//        Pattern pattern = Pattern.compile("(\\Qprogress\\E)");
//        // We can't directly compare the pattern objects
//        assertEquals(Optional.of(pattern.toString()), result.getPatternForWords().map(Pattern::toString));
//    }
//
//    @Test
//    public void getRegexpPattern() {
//        String queryText = "[a-c]\\d* \\d*";
//        SearchQuery regexQuery = new SearchQuery(queryText, EnumSet.of(SearchRules.SearchFlags.REGULAR_EXPRESSION));
//        Pattern pattern = Pattern.compile("([a-c]\\d* \\d*)");
//        assertEquals(Optional.of(pattern.toString()), regexQuery.getPatternForWords().map(Pattern::toString));
//    }
//
//    @Test
//    public void getRegexpJavascriptPattern() {
//        String queryText = "[a-c]\\d* \\d*";
//        SearchQuery regexQuery = new SearchQuery(queryText, EnumSet.of(SearchRules.SearchFlags.REGULAR_EXPRESSION));
//        Pattern pattern = Pattern.compile("([a-c]\\d* \\d*)");
//        assertEquals(Optional.of(pattern.toString()), regexQuery.getJavaScriptPatternForWords().map(Pattern::toString));
//    }
//
//    @Test
//    public void escapingInPattern() {
//        // first word contain all java special regex characters
//        String queryText = "<([{\\\\^-=$!|]})?*+.> word1 word2.";
//        SearchQuery textQueryWithSpecialChars = new SearchQuery(queryText, EnumSet.noneOf(SearchFlags.class));
//        String pattern = "(\\Q<([{\\^-=$!|]})?*+.>\\E)|(\\Qword1\\E)|(\\Qword2.\\E)";
//        assertEquals(Optional.of(pattern), textQueryWithSpecialChars.getPatternForWords().map(Pattern::toString));
//    }
//
//    @Test
//    public void escapingInJavascriptPattern() {
//        // first word contain all javascript special regex characters that should be escaped individually in text based search
//        String queryText = "([{\\\\^$|]})?*+./ word1 word2.";
//        SearchQuery textQueryWithSpecialChars = new SearchQuery(queryText, EnumSet.noneOf(SearchFlags.class));
//        String pattern = "(\\(\\[\\{\\\\\\^\\$\\|\\]\\}\\)\\?\\*\\+\\.\\/)|(word1)|(word2\\.)";
//        assertEquals(Optional.of(pattern), textQueryWithSpecialChars.getJavaScriptPatternForWords().map(Pattern::toString));
//    }
}
