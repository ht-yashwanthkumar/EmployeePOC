package com.sapient.poc.utilities;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

/**
 * The {@code ResourceLoader} class serves as utility class to load the data
 * from file and convert to list of pojo provided.
 * 
 */
public class ResourceLoader {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceLoader.class);

	private ResourceLoader() {

	}

	/**
	 * method to load data and to convert to list of pojo
	 * 
	 * @param filename the file to be read
	 * @param type     generic type to map each record
	 * @return Collection of pojos
	 */

	public static <T> List<T> loadEmployeeInfoFromFile(Class<T> type, String fileName) {
		try {
			CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
			CsvMapper mapper = new CsvMapper();
			File file = new ClassPathResource(fileName).getFile();
			@SuppressWarnings("deprecation")
			MappingIterator<T> readValues = mapper.reader(type).with(bootstrapSchema).readValues(file);
			return readValues.readAll();
		} catch (Exception e) {
			LOGGER.error("Error occurred while loading object list from file " + fileName, e);
			return Collections.emptyList();
		}
	}
}
