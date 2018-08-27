package scrapper;

import org.junit.Test;

import io.FileUtilities;

public class TestFileUtil {
	@Test
	public void TestReadingTrait(){
		FileUtilities.translateJP("特徴なし");
	}
}
