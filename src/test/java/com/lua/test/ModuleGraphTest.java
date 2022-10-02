package com.lua.test;

import com.lua.test.exceptions.InvalidInputFileFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

class ModuleGraphTest {
    ModuleGraph moduleGraph;

    @Test
    void testConstructor_GivenValidFile_ThenExpectAValidGraph() throws IOException {
        // Given
        var firstModule = "A";
        var firstModuleDeps = List.of("B", "C", "D");

        var secondModule = "E";
        var secondModuleDeps = List.of("F", "G");

        // When
        moduleGraph = new ModuleGraph(new File("src/test/resources/test_case_valid_input.txt"));

        // Then
        Map<String, List<String>> nodesWithDep = moduleGraph.getNodesWithDep();

        nodesWithDep.containsKey(firstModule);
        var actualFirstModuleDeps = nodesWithDep.get(firstModule);
        Assertions.assertTrue(actualFirstModuleDeps.containsAll(firstModuleDeps));

        nodesWithDep.containsKey(secondModule);
        var actualSecondModuleDeps = nodesWithDep.get(secondModule);
        Assertions.assertTrue(actualSecondModuleDeps.containsAll(secondModuleDeps));
    }

    @Test
    void testConstructor_GivenMissingModuleNameInInputFile_ThenExpectInvalidInputFileException() throws IOException {
        // Given

        // When
        Assertions.assertThrows(InvalidInputFileFormatException.class, () -> {
            moduleGraph = new ModuleGraph(new File("src/test/resources/test_case_missing_module_name.txt"));
        });

        // Then
    }
}