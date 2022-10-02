package com.lua.test;

import com.lua.test.exceptions.InvalidInputFileFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

class ModuleGraphTest {

    private static final String TEST_DIR = "src/test/resources/";
    ModuleGraph moduleGraph;

    @Test
    void testConstructor_GivenValidFile_ThenExpectAValidGraph() throws IOException {
        // Given
        String firstModule = "A";
        List<String> firstModuleDeps = List.of("B", "C", "D");

        String secondModule = "E";
        List<String> secondModuleDeps = List.of("F", "G");

        // When
        moduleGraph = new ModuleGraph(new File(TEST_DIR + "test_constructor_valid_input.txt"));

        // Then
        Map<String, List<String>> nodesWithDep = moduleGraph.getModulesDependencies();

        nodesWithDep.containsKey(firstModule);
        List<String> actualFirstModuleDeps = nodesWithDep.get(firstModule);
        Assertions.assertTrue(actualFirstModuleDeps.containsAll(firstModuleDeps));

        nodesWithDep.containsKey(secondModule);
        List<String> actualSecondModuleDeps = nodesWithDep.get(secondModule);
        Assertions.assertTrue(actualSecondModuleDeps.containsAll(secondModuleDeps));
    }

    @Test
    void testConstructor_GivenMissingModuleNameInInputFile_ThenExpectInvalidInputFileException() throws IOException {
        // Given

        // When
        Assertions.assertThrows(InvalidInputFileFormatException.class, () -> {
            moduleGraph = new ModuleGraph(new File(TEST_DIR + "test_constructor_invalid_input_file.txt"));
        });

        // Then
    }

    @Test
    void testPrintTree_GivenExampl1_ThenPrintCorrectDependencies() throws IOException {
        // Given
        moduleGraph = new ModuleGraph(new File(TEST_DIR + "/test_get_dependencies_no_circular_ex1.txt"));

        // When
        Assertions.assertEquals("{B,E,F,H,I,G,C,D}", moduleGraph.getDependencies("A"));
        Assertions.assertEquals("{E,F,H,I,G}", moduleGraph.getDependencies("C"));
        Assertions.assertEquals("{H,I}", moduleGraph.getDependencies("G"));
        Assertions.assertEquals("{}", moduleGraph.getDependencies("I"));
    }

    @Test
    void testPrintTree_GivenExample2_ThenPrintCorrectDependencies() throws IOException {
        // Given
        moduleGraph = new ModuleGraph(new File(TEST_DIR + "/test_get_dependencies_no_circular_ex2.txt"));

        // When
        Assertions.assertEquals("{H,D,I}", moduleGraph.getDependencies("G"));
        Assertions.assertEquals("{E,F,H,D,I,G}", moduleGraph.getDependencies("C"));
        Assertions.assertEquals("{B,E,F,H,D,I,G,C}", moduleGraph.getDependencies("A"));
    }

    @Test
    void testPrintTree_GivenExample3_ThenPrintCorrectDependencies() throws IOException {
        // Given
        moduleGraph = new ModuleGraph(new File(TEST_DIR + "/test_get_dependencies_circular_ex3.txt"));

        // When
        Assertions.assertEquals("error", moduleGraph.getDependencies("G"));
        Assertions.assertEquals("error", moduleGraph.getDependencies("C"));
        Assertions.assertEquals("error", moduleGraph.getDependencies("A"));
    }

    @Test
    void testPrintTree_GivenExample4_ThenPrintCorrectDependencies() throws IOException {
        // Given
        moduleGraph = new ModuleGraph(new File(TEST_DIR + "/test_get_dependencies_no_circular_ex4.txt"));

        // When
        Assertions.assertEquals("{B,D,I,C}", moduleGraph.getDependencies("A"));
    }
}