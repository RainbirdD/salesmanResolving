package com.salesman.Servises.aco;

import com.salesman.Dto.AntDTO;
import lombok.Data;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
@Data
public class ACORunner {

    public static void main(String[] args) throws IOException {
        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add("C:\\IdeaProjects\\salesman\\src\\main\\java\\com\\salesman\\Servises\\aco\\input.txt");
        for (String filePath : filePaths) {
            Matrix adjMatrix = parseInput(filePath);
            ACO aco = new ACO(adjMatrix);
            aco.runs();
        }
    }

//    private static void runAlgorithm() throws IOException {
//        ArrayList<String> filePaths = new ArrayList<>();
//        filePaths.add("C:\\IdeaProjects\\salesman\\src\\main\\java\\com\\salesman\\Servises\\aco\\input.txt");
//        for (String filePath : filePaths) {
//            Matrix adjMatrix = parseInput(filePath);
//            ACO aco = new ACO(adjMatrix);
//            aco.run();
//        }
//    }


    private AntDTO runAlgorithm() throws IOException {
        AntDTO antDTO = new AntDTO();


        ArrayList<String> filePaths = new ArrayList<>();
        filePaths.add("C:\\IdeaProjects\\salesman\\src\\main\\java\\com\\salesman\\Servises\\aco\\input.txt");
        for (String filePath : filePaths) {
            Matrix adjMatrix = parseInput(filePath);
            ACO aco = new ACO(adjMatrix);
            aco.run();
        }


        return antDTO;
    }

    private static Matrix parseInput(String filePath) throws IOException {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            int N = Integer.parseInt(lines.get(0));
            Matrix adjMatrix = new Matrix(N);
            for (int i = 1; i < lines.size(); i++) {
                String[] neighbors = lines.get(i).split("\\s");
                for (int j = 0; j < neighbors.length; j++) {
                    adjMatrix.set(i - 1, j, Double.parseDouble(neighbors[j]));
                }
            }
            return adjMatrix;
        } catch (IOException e) {
            System.err.println("File read failed");
            throw e;
        }
    }

    private static List<String> scanFolder(String folderPath) throws IOException {
        List<String> filePaths = new ArrayList<>();
        String glob = "glob:**/*.txt";
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(
                glob);

        Files.walkFileTree(Paths.get(folderPath), new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path path,
                                             BasicFileAttributes attrs) throws IOException {
                if (pathMatcher.matches(path)) {
                    filePaths.add(path.toString());
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc)
                    throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
        return filePaths;
    }
    private static boolean isFolderInput() {
        return System.getProperty("folder", "false").equalsIgnoreCase("true");
    }

}
