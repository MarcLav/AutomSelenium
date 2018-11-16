package library;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import modeles.ObjTest;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class ScriptsCSV {

	public static Iterator<ObjTest> ReadDataCSV(String FileData)
			throws IOException {
		Iterator<ObjTest> ObjTestIterator = null;

		Reader reader = Files.newBufferedReader(Paths.get(FileData));
		ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
		strategy.setType(ObjTest.class);
		String[] memberFieldsToBindTo = { "TexteLog", "ActionType", "ByType", "Texte" };
		strategy.setColumnMapping(memberFieldsToBindTo);

		CsvToBean<ObjTest> csvToBean = new CsvToBeanBuilder(reader)
				.withMappingStrategy(strategy).withSkipLines(1)
				.withSeparator(';').withIgnoreLeadingWhiteSpace(true).build();

		ObjTestIterator = csvToBean.iterator();

		return ObjTestIterator;

	}

}
