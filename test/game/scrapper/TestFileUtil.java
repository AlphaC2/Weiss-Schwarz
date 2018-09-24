package game.scrapper;

import org.junit.Test;

import game.io.FileUtilities;

public class TestFileUtil {
	@Test
	public void TestReadingTrait(){
		FileUtilities.translateJP("特徴なし");
	}
}
