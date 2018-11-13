package sth.core;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import sth.core.exception.BadEntryException;

public class Parser {

	private School _school;
	private Person _currPerson;

	Parser(School school) {
		_school = school;
	}

	void parseFile(String fileName) throws IOException, BadEntryException {
		String line;

		try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			while ((line = reader.readLine()) != null)
				parseLine(line);
		}
	}

	private void parseLine(String line) throws BadEntryException {
		if (line.startsWith("#"))
			parseContext(line);
		else
			parseHeaderPerson(line);
	}

	private void parseHeaderPerson(String header) throws BadEntryException {
		String[] components = header.split("\\|");

		if (components.length != 4)
			throw new BadEntryException("Invalid line: " + header);

		try {
			_currPerson = _school.parsePerson(components[0], Integer.parseInt(components[1]), Integer.parseInt(components[2]), components[3]);
		} catch (NumberFormatException nfe) {
			throw new BadEntryException("Invalid line: " + header);
		}
	}

	private void parseContext(String line) throws BadEntryException {
		String lineContext = line.substring(2);

		_currPerson.parseContext(lineContext, _school);
	}
}