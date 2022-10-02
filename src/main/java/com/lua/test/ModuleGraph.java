package com.lua.test;

import com.lua.test.exceptions.InvalidInputFileFormatException;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModuleGraph {

    private static final String MODULE_NAME_SEPARATOR = ":";
    private static final String MODULE_DEPENDENCIES_SEPARATOR = ",";

    /**
     * a Map object in which the key is the node name, and the value is the list of its dependencies
     */
    private Map<String, List<String>> nodesWithDep = new HashMap<>();

    public ModuleGraph(File file) throws IOException {
        parseFile(file);
    }

    private void parseFile(File file) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                parseLine(line);
            }
        }
    }

    private void parseLine(String line) {
        validateLine(line);

        var array = line.split(MODULE_NAME_SEPARATOR);
        var moduleName = array[0];
        var moduleDeps = Arrays.asList(array[1].split(MODULE_DEPENDENCIES_SEPARATOR)).stream()
                .map(String::trim).filter(str -> !str.isBlank()).collect(Collectors.toList());

        nodesWithDep.put(moduleName, moduleDeps);
    }

    private void validateLine(String line) {
        var array = line.split(MODULE_NAME_SEPARATOR);

        if (array.length < 2) {
            throw new InvalidInputFileFormatException();
        }

        if (array[0].isBlank()) {
            throw new InvalidInputFileFormatException();
        }
    }

    public Map<String, List<String>> getNodesWithDep() {
        return nodesWithDep;
    }
}
