package com.wolffr.PDFBlackener;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.wolffr.PDFBlackener.exception.PDFBlackenerException;

public class PageSelectorTest {
 
	@Test
	@DisplayName("Seach page that contains with 1 searchString")
	public void testFindPagesThatContainsSingle() throws IOException, PDFBlackenerException {
		List<Integer> selectedPages = PageSelector.findPagesThatContains(Files.readAllBytes(Paths.get("src/test/resources/gg.pdf")), Arrays.asList("Ausfertigungsdatum: 23.05.1949"));
		assertEquals(Arrays.asList(1),selectedPages);
	}

	@Test
	@DisplayName("Seach page that contains with multiple searchStrings")
	public void testFindPagesThatContainsMultiple() throws IOException, PDFBlackenerException {
		List<Integer> selectedPages = PageSelector.findPagesThatContains(Files.readAllBytes(Paths.get("src/test/resources/gg.pdf")), Arrays.asList("Männer und Frauen sind gleichberechtigt.","Eingriffe und Beschränkungen","Ist in einem Rechtsstreite zweifelhaft"));
		assertEquals(Arrays.asList(2,5,30),selectedPages);
	}
}
