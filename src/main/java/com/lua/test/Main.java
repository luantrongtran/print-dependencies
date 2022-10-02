package com.lua.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Read the full filename
        System.out.println("Enter the file name with full directory");
        String filename = scanner.nextLine();
        File f = new File(filename);


        try {
            // init the module graph
            ModuleGraph moduleGraph = new ModuleGraph(f);
            System.out.println("Enter a module to search for its dependencies:");

            // wait for the user to enter the module name to find the dependencies
            var moduleName = scanner.nextLine();
            System.out.println(moduleGraph.getDependencies(moduleName));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
    }


}
