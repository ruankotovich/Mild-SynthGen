/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mild.lhi.bean;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import ru.mild.lhi.bean.genetic.GeneticAlgorithm;
import ru.mild.lhi.bean.genetic.Individual;
import ru.mild.lhi.bean.genetic.Population;

/**
 *
 * @author dmitry
 */
public class Spectrum {

    private Graph graph = null;
    private final Map<Vertex, Boolean> verifieds = new HashMap<>();
    private BufferedImage imageSource = null;
    private int matrixX = 0, matrixY = 0;
    private int cellWidth = 0, cellHeight = 0;
    private Vertex start = null, end = null;
    private int pathCount = 0;
    private static final Color DARK_CYAN = new Color(0, 200, 200);

    public void build(BufferedImage image) {
        this.imageSource = image;
        if (image != null) {
            try {
                for (int i = 1; i < image.getWidth(); i++) {
                    if (VertexType.isCloserThat(image.getRGB(i, 1), Color.BLACK.getRGB(), 100)) {
                        matrixX = image.getWidth() / i;
                        cellWidth = i;
                        break;
                    }
                }
                for (int j = 1; j < image.getHeight(); j++) {
                    if (image.getRGB(1, j) == Color.BLACK.getRGB()) {
                        matrixY = image.getHeight() / j;
                        cellHeight = j;
                        break;
                    }
                }
            } catch (Exception ex) {
                throw new Error("Invalid Map Image");
            }
        } else {
            throw new Error("BufferedImage cannot be null in Spectrum(image)");
        }
        System.gc();
    }

    public String printMatrix() {
        String path = "[" + matrixX + ";" + matrixY + "] map\n[" + cellWidth + ";" + cellHeight + "] cells\n\n";
        for (int y = 0; y < matrixY; y++) {
            for (int x = 0; x < matrixX; x++) {
                path += (graph.getVertexes()[x][y]);
            }
            path += "\n";
        }
        return path;
    }

    private void createMatrix() {
        Vertex vertex;
        verifieds.clear();
        int halfCellWidth = cellWidth / 2, halfCellHeight = cellHeight / 2;
        for (int y = 0; y < matrixY; y++) {
            for (int x = 0; x < matrixX; x++) {
                VertexType type = VertexType.detectByRGB(imageSource.getRGB((x * cellWidth) + halfCellWidth, (y * cellHeight) + halfCellHeight));

                Vertex v = new Vertex(type, x, y);

                if (type == VertexType.WAYPOINT) {
                    graph.addCheckpoint(v);
                }

                graph.getVertexes()[x][y] = v;
                if (type == VertexType.OBSTACLE) {
                    verifieds.put(v, Boolean.TRUE);
                } else {
                    verifieds.put(v, Boolean.FALSE);
                }
            }
        }
    }

    public int[][] getAnonymousSpectrum() {
        int[][] anonymousVertexes = new int[matrixX][matrixY];
        int halfCellWidth = cellWidth / 2, halfCellHeight = cellHeight / 2;

        verifieds.clear();

        for (int y = 0; y < matrixY; y++) {
            for (int x = 0; x < matrixX; x++) {
                anonymousVertexes[x][y] = imageSource.getRGB((x * cellWidth) + halfCellHeight, (y * cellHeight) + halfCellHeight);
            }
        }

        return anonymousVertexes;
    }

    public BufferedImage paintedPath(Map<Vertex, Vertex> map, boolean withVerifieds) {
        Vertex v;
        pathCount = 0;
        int matx = (cellWidth / 2) - 1;
        int maty = (cellHeight / 2) - 1;
        if (!map.isEmpty() && map.get(end) != null && map.get(map.get(end)) != null) {
            Vertex child = end;
            while (child != null) {
                graph.getVerifieds().remove(child);
                v = child;
                pathCount++;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        imageSource.setRGB((v.getX() * cellWidth) + matx + i, (v.getY() * cellHeight) + maty + j, Color.RED.getRGB());
                    }
                }
                child = map.get(child);
            }
            if (withVerifieds) {
                for (Vertex ver : graph.getVerifieds()) {
                    for (int i = 0; i < 2; i++) {
                        for (int j = 0; j < 2; j++) {
                            imageSource.setRGB((ver.getX() * cellWidth) + matx + 1 + i, (ver.getY() * cellHeight) + maty + 1 + j, DARK_CYAN.getRGB());
                        }
                    }
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Impossible to find a way!", "Path Error", JOptionPane.ERROR_MESSAGE);
        }
        graph.getVerifieds().clear();
        System.gc();
        return imageSource;
    }

    public static BufferedImage copyImage(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    public void paintVertex(Vertex vertex, JLabel source, boolean walked, int times) {
        Vertex v;
        pathCount = 0;
        int matx = (cellWidth / 2) - 1;
        int maty = (cellHeight / 2) - 1;
        BufferedImage toPaint = imageSource;

        if (walked) {
            if (times == 0) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        toPaint.setRGB((vertex.getX() * cellWidth) + matx + i, (vertex.getY() * cellHeight) + maty + j, Color.BLACK.getRGB());
                    }
                }
            } else {
                toPaint = copyImage(imageSource);
                for (int i = 0; i < 3 + times; i++) {
                    for (int j = 0; j < 3 + times; j++) {
                        toPaint.setRGB((vertex.getX() * cellWidth) + matx + i, (vertex.getY() * cellHeight) + maty + j, Color.BLACK.getRGB());
                    }

                }
            }

        } else {

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    toPaint.setRGB((vertex.getX() * cellWidth) + matx + i, (vertex.getY() * cellHeight) + maty + j, Color.WHITE.getRGB());
                }
            }

        }

        source.setIcon(new ImageIcon(toPaint));
        System.gc();
    }

    public Queue<Vertex> runSilentSearch(Graph.ALGORITHM_TYPE type) throws Exception {

        start = end = null;
        graph = new Graph(matrixX, matrixY);
        createMatrix();
        buildWay();
        System.gc();

        if (start != null && end != null) {

            switch (type) {

                case A_STAR:
                    return graph.progressiveAstar(start, end);
                case BFS:
                    return graph.progressiveBfs(start, end);
                case DFS:
                    return graph.progressiveDfs(start, end);
                case DIJKSTRA:
                    return graph.progressiveDijkstra(start, end);
                case IDDFS:
                    return graph.progressiveIddfs(start, end, 5);

            }

        } else if (start == null) {

            throw new Exception("Start not found (blue square)");

        } else {

            throw new Exception("End not found (green square)");

        }

        return null;

    }

    public Queue<Vertex> runRoam(Graph.ALGORITHM_TYPE type) throws Exception {

        start = end = null;
        graph = new Graph(matrixX, matrixY);
        createMatrix();
        buildWay();

        System.gc();

        if (start != null && end != null) {

            switch (type) {
                case GEN_AGENT: {
                    Map<Vertex, Vertex> mape = graph.astar(start, end);
                    Vertex cur = end;

                    // Create GA object
                    GeneticAlgorithm ga = new GeneticAlgorithm(graph, start, end, 1000, 0.015, 0.95, 100);

                    // Initialize population
                    Population population = ga.initPopulation((graph.getHeight() * graph.getWidth()) * 2);

                    // Evaluate population
                    ga.evalPopulation(population);

                    // Keep track of current generation
                    int generation = 1;

                    /**
                     * Start the evolution loop
                     *
                     * Every genetic algorithm problem has different criteria
                     * for finishing. In this case, we know what a perfect
                     * solution looks like (we don't always!), so our
                     * isTerminationConditionMet method is very straightforward:
                     * if there's a member of the population whose chromosome is
                     * all ones, we're done!
                     */
                    while (ga.isTerminationConditionMet(population, graph.getCheckpointCount(), end) == false) {
                        // Print fittest individual from population
                        Individual fittest = population.getFittest(0);
                        System.out.println("Best solution: fitness " + fittest.getFitness() + " at " + fittest.toString());

                        // Apply crossover
                        population = ga.crossoverPopulation(population);

                        // Apply mutation
                        population = ga.mutatePopulation(population);

                        // Evaluate population
                        ga.evalPopulation(population);

                        // Increment the current generation
                        generation++;
                    }

                    /**
                     * We're out of the loop now, which means we have a perfect
                     * solution on our hands. Let's print it out to confirm that
                     * it is actually all ones, as promised.
                     */
                    System.out.println("Found solution in " + generation + " generations");
                    Individual fittest = population.getFittest(0);
                    System.out.println("Best solution: fitness " + fittest.getFitness() + " at " + fittest.toString());

                    String individualSolution = population.getFittest(0).toString();
                    String lastString = null;

                    Map<Vertex, Vertex> burningMap = new HashMap<>();
                    Queue<Vertex> stackVertex = new ArrayDeque<>();

                    Vertex curVer = start;
                    Vertex curtexVer = null;
                    stackVertex.add(curVer);

                    for (int i = 0; i < individualSolution.length(); i++) {
                        if (i % 2 == 0) {
                            lastString = String.valueOf(individualSolution.charAt(i));
                        } else {
                            lastString = lastString + String.valueOf(individualSolution.charAt(i));
                            int code = Integer.parseInt(lastString);

                            switch (Graph.Direction.fromCode(code)) {
                                case DOWN:
                                    System.out.println("D");
                                    curtexVer = graph.getVertexIfItsFloor(curVer.getX(), curVer.getY() + 1);
                                    break;
                                case LEFT:
                                    System.out.println("L");
                                    curtexVer = graph.getVertexIfItsFloor(curVer.getX() - 1, curVer.getY());
                                    break;
                                case RIGHT:
                                    System.out.println("R");
                                    curtexVer = graph.getVertexIfItsFloor(curVer.getX() + 1, curVer.getY());
                                    break;
                                case UP:
                                    System.out.println("U");
                                    curtexVer = graph.getVertexIfItsFloor(curVer.getX(), curVer.getY() - 1);
                                    break;
                                case INVALID: {

                                }
                            }

                            if (curtexVer != null) {

                                stackVertex.add(curtexVer);
                                curVer = curtexVer;
                                if (curVer == end) {
                                    System.out.println("end");
                                    break;
                                }
                            }
                        }

                    }
                    return stackVertex;
                }

            }
        } else if (start == null) {

            throw new Exception("Start not found (blue square)");

        } else {
            throw new Exception("End not found (green square)");

        }

        return null;

    }

    public Map<Vertex, Vertex> runSearch(Graph.ALGORITHM_TYPE type) throws Exception {

        start = end = null;
        graph = new Graph(matrixX, matrixY);
        createMatrix();
        buildWay();
        System.gc();

        if (start != null && end != null) {

            switch (type) {

                case A_STAR:
                    return graph.astar(start, end);
                case BFS:
                    return graph.bfs(start, end);
                case DFS:
                    return graph.dfs(start, end);
                case DIJKSTRA:
                    return graph.dijkstra(start, end);
                case IDDFS:
                    return graph.iddfs(start, end, 5);

            }

        } else if (start == null) {

            throw new Exception("Start not found (blue square)");

        } else {

            throw new Exception("End not found (green square)");

        }

        return null;

    }

    private void buildWay() {
        Vertex iterator = null;
        Vertex nextLeft = null, nextRight = null, nextUp = null, nextDown = null;
        //int edgeCount;
        for (int y = 0; y < matrixY; y++) {
            for (int x = 0; x < matrixX; x++) {
                if (graph.getVertexes()[x][y].getType() != VertexType.OBSTACLE) {
                    iterator = graph.getVertexes()[x][y];
                    nextLeft = getFloorLeft(x, y);
                    nextRight = getFloorRight(x, y);
                    nextDown = getFloorDown(x, y);
                    nextUp = getFloorUp(x, y);

                    if (nextRight != null) {
                        if (!verifieds.get(nextRight)) {
                            graph.addEdge(iterator, nextRight);
                        }
                    }

                    if (nextLeft != null) {

                        if (!verifieds.get(nextLeft)) {
                            graph.addEdge(iterator, nextLeft);
                        }
                    }

                    if (nextDown != null) {
                        if (!verifieds.get(nextDown)) {
                            graph.addEdge(iterator, nextDown);
                        }
                    }

                    if (nextUp != null) {
                        if (!verifieds.get(nextUp)) {
                            graph.addEdge(iterator, nextUp);
                        }
                    }

                    verifieds.replace(iterator, Boolean.TRUE);

                    if (graph.getVertexes()[x][y].getType() == VertexType.START) {
                        start = iterator;
                    } else if (graph.getVertexes()[x][y].getType() == VertexType.END) {
                        end = iterator;
                    }

                }
            }
        }
    }

    private Vertex getFloorLeft(int x, int y) {
        if (x > 0) {
            if (graph.getVertexes()[x - 1][y].getType() != VertexType.OBSTACLE) {
                return graph.getVertexes()[x - 1][y];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private Vertex getFloorRight(int x, int y) {
        if (x < matrixX - 1) {
            if (graph.getVertexes()[x + 1][y].getType() != VertexType.OBSTACLE) {
                return graph.getVertexes()[x + 1][y];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private Vertex getFloorUp(int x, int y) {
        if (y > 0) {
            if (graph.getVertexes()[x][y - 1].getType() != VertexType.OBSTACLE) {
                return graph.getVertexes()[x][y - 1];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private Vertex getFloorDown(int x, int y) {
        if (y < matrixY - 1) {
            if (graph.getVertexes()[x][y + 1].getType() != VertexType.OBSTACLE) {
                return graph.getVertexes()[x][y + 1];
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    /*
    
    // GETTERS AND SETTERS
    
     */
    public int getMatrixX() {
        return matrixX;
    }

    public int getMatrixY() {
        return matrixY;
    }

    public BufferedImage getImageSource() {
        return imageSource;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    public int getPathCount() {
        return pathCount;
    }

}
