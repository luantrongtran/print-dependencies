package com.lua.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the file name with full directory");
        String filename = scanner.nextLine();
        File f = new File(filename);

        try {
            ModuleGraph moduleGraph = new ModuleGraph(f);
            System.out.println("Enter a module to search for its dependencies:");
            var moduleName = scanner.nextLine();
            System.out.println(moduleGraph.getDependencies(moduleName));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }
    }


}
