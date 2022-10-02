package com.lua.test;

import com.lua.test.exceptions.InvalidInputFileFormatException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ModuleGraph {

    private static final String MODULE_NAME_SEPARATOR = ":";
    private static final String MODULE_DEPENDENCIES_SEPARATOR = ",";

    /**
     * a Map object in which the key is the node name, and the value is the list of its dependencies
     */
    private Map<String, List<String>> modulesDependencies = new LinkedHashMap<>();

    public ModuleGraph(File file) throws IOException {
        parseFile(file);
    }

    public String getDependencies(String moduleName) {
        StringBuilder sb = new StringBuilder();

        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        queue.add(moduleName);

        goDeep(moduleName, sb, visited);

        return "{" + sb.substring(0, sb.length() - 2)
                // trim the last comma
                .replace(",", " ").trim()
                .replace(" ", ",")
                + "}";
    }

    private void goDeep(String moduleName, StringBuilder sb, Set<String> visited) {
        if (!modulesDependencies.containsKey(moduleName)) {
            if (!visited.contains(moduleName)) {
                sb.append(moduleName).append(",");
                visited.add(moduleName);
            }
            return;
        }

        modulesDependencies.get(moduleName).forEach(dep -> goDeep(dep, sb, visited));
        if (!visited.contains(moduleName)) {
            sb.append(moduleName).append(",");
            visited.add(moduleName);
        }
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

        var dependencies = Arrays.asList(array[1].split(MODULE_DEPENDENCIES_SEPARATOR)).stream()
                .map(String::trim).filter(str -> !str.isBlank()).collect(Collectors.toList());

        modulesDependencies.put(moduleName, dependencies);
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

    public Map<String, List<String>> getModulesDependencies() {
        return modulesDependencies;
    }
}
