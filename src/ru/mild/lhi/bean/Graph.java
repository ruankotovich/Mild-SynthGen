/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.mild.lhi.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author dmitry
 */
public class Graph {

    private Vertex vertexes[][] = null;
    private final Set<Vertex> verifieds;

    public static enum ALGORITHM_TYPE {

        DIJKSTRA("Dijkstra", true), BFS("Breadth-First Search", true), DFS("Depth-First Search", true), A_STAR("A Star", true), IDDFS("ID Depth-First Search", true), GEN_AGENT("Hatrix GENALG", false);

        private final String name;
        private final boolean singleRaster;

        ALGORITHM_TYPE(String name, boolean sr) {
            this.name = name;
            this.singleRaster = sr;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public String getName() {
            return name;
        }

        public boolean isSingleRaster() {
            return singleRaster;
        }

        public static List<ALGORITHM_TYPE> getTypes() {
            return Arrays.asList(ALGORITHM_TYPE.values());
        }

    };

    public Vertex[][] getVertexes() {
        return vertexes;
    }

    public Set<Vertex> getVerifieds() {
        return verifieds;
    }

    public void setVertexes(Vertex[][] vertexes) {
        this.vertexes = vertexes;
    }

    public Graph(int x, int y) {
        vertexes = new Vertex[x][y];
        verifieds = new HashSet<>();
    }

    void addVertex(Vertex v, int x, int y) {
        vertexes[x][y] = v;
    }

    void addEdge(Vertex in, Vertex out) {
        if (!(in.getNeighbors().contains(out) && out.getNeighbors().contains(in))) {
            in.getNeighbors().add(out);
            out.getNeighbors().add(in);
        }
    }

    public Map<Vertex, Vertex> bfs(Vertex initial, Vertex search) {
        Vertex iterator;
        Map<Vertex, Vertex> discovered = new HashMap<>();
        Stack<Vertex> betterWay;
        Set<Vertex> set = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();

        verifieds.clear();

        queue.add(initial);
        set.add(initial);

        while (!queue.isEmpty()) {
            iterator = queue.poll();

            if (iterator.equals(search)) {
                return discovered;
            }

            for (Vertex v : iterator.getNeighbors()) {
                verifieds.add(v);
                if (!set.contains(v)) {
                    discovered.put(v, iterator);
                    set.add(v);
                    queue.add(v);
                }
            }
        }
        /*
        if (discovered.get(search) != null) {
            betterWay = new Stack<>();
            betterWay.add(search);

            /*
            Vertex child = discovered.get(search);
            while (child != null) {
                betterWay.add(child);
                child = discovered.get(child);
            }
         */
        return null;
        /*
        } else {
            return null;
        }
         */
    }

    public Queue<Vertex> progressiveBfs(Vertex initial, Vertex search) {
        Vertex iterator;

        Queue<Vertex> queue = new LinkedList<>();
        Deque<Vertex> searchOrder = new LinkedList<>();
        Set<Vertex> set = new HashSet<>();

        verifieds.clear();
        queue.add(initial);
        searchOrder.add(initial);
        set.add(initial);

        while (!queue.isEmpty()) {
            iterator = queue.poll();
            searchOrder.offerLast(iterator);

            if (iterator.equals(search)) {
                return searchOrder;
            }

            for (Vertex v : iterator.getNeighbors()) {
                if (!set.contains(v)) {
                    set.add(v);
                    queue.add(v);
                }
            }
        }

        /*
            Vertex child = discovered.get(search);
            while (child != null) {
                betterWay.add(child);
                child = discovered.get(child);
            }
         */
        return null;
    }

    public Map<Vertex, Vertex> dfs(Vertex initial, Vertex search) {
        Vertex found = null;
        Map<Vertex, Vertex> path = new HashMap<>();

        Stack<Vertex> stack = new Stack<>();
        Set<Vertex> set = new HashSet<>();

        verifieds.clear();

        stack.push(initial);
        set.add(initial);

        while (!stack.isEmpty()) {
            found = null;
            for (Vertex neighbour : stack.peek().getNeighbors()) {
                verifieds.add(neighbour);
                if (!set.contains(neighbour)) {
                    path.put(neighbour, stack.peek());
                    set.add(neighbour);
                    found = neighbour;
                    break;
                }
            }

            if (found != null) {
                stack.push(found);
                if (found.equals(search)) {
                    return path;
                }
            } else if (!stack.isEmpty()) {
                stack.pop();
            }
        }
        return null;
    }

    public Queue<Vertex> progressiveDfs(Vertex initial, Vertex search) {
        Vertex found = null;
        Stack<Vertex> stack = new Stack<>();
        Queue<Vertex> searchOrder = new LinkedList<>();
        Set<Vertex> set = new HashSet<>();

        verifieds.clear();
        stack.push(initial);
        set.add(initial);

        while (!stack.isEmpty()) {
            found = null;
            searchOrder.add(stack.peek());
            for (Vertex neighbour : stack.peek().getNeighbors()) {
                if (!set.contains(neighbour)) {
                    set.add(neighbour);
                    found = neighbour;
                    break;
                }
            }
            if (found != null) {
                stack.push(found);
                if (found.equals(search)) {
                    return searchOrder;
                }
            } else if (!stack.isEmpty()) {
                stack.pop();
            }
        }
        return null;
    }

    public Map<Vertex, Vertex> dijkstra(Vertex start, Vertex end) {
        // Iterador da vez
        Stack<Vertex> betterPath = new Stack<>();
        Vertex iterator = start;

        // Map que armazena as estimativas
        Map<Vertex, Integer> dList = new HashMap<>();
        // Map que armazena pertinência do Vértice no conjunto de mínimos
        Map<Vertex, Boolean> perm = new HashMap<>();
        // Map que armazena o caminho
        Map<Vertex, Vertex> path = new HashMap<>();

        // Iniciando os maps
        for (int y = 0; y < vertexes[0].length; y++) {
            for (int x = 0; x < vertexes.length; x++) {
                dList.put(vertexes[x][y], -1);
                perm.put(vertexes[x][y], false);
                path.put(vertexes[x][y], null);
            }
        }

        verifieds.clear();

        // Define-se inicialmente o nó de origem (raiz), neste caso s, 
        // e inclui-se este nó em PERM. Atribui-se zero a sua distância (dist[s]) 
        // porque o custo de ir de s a s é obviamente 0. 
        // Todos os outros nós i tem suas distâncias (dist[i]) inicializadas com um valor bastante grande (-1);
        perm.replace(start, true);
        dList.replace(start, 0);

        while (iterator != null) {
            // EM GRAFOS COM DISTÂNCIA != 1 NÃO USAR ISSO!
            if (iterator == end) {
                break;
            }
            for (Vertex neighbor : iterator.getNeighbors()) {
                verifieds.add(neighbor);

                // A partir de s consulta-se os vértices adjacentes a ele, que no grafo G são u e x. 
                // Para todos os vértices adjacentes, que chamaremos z, calcula-se:
                // Se dist[z] > dist[s] + peso(s, z)
                //     dist[z] = dist[s] + peso(s, z)
                //    path[z] = s
                //  Fim Se 
                if (!perm.get(neighbor)) {
                    int distanceToInterator = 1;//distance(iterator, neighbor);
                    int neighborValue = dList.get(neighbor);
                    if (neighborValue > dList.get(iterator) + distanceToInterator || neighborValue < 0) {
                        dList.replace(neighbor, dList.get(iterator) + distanceToInterator);
                        path.replace(neighbor, iterator);
                    }
                }
            }
            // Dentre todos os vértices não pertencentes a PERM escolhe-se aquele com a menor distância. 
            // Neste caso é o vértice x, pois dist[x] = 5. 
            // Então, inclui-se x em PERM e a partir de x consulta-se os vértices adjacentes a ele que não estão em PERM, que no grafo G são u, v e y.
            // Para todos os vértices adjacentes, que chamaremos z, calcula-se:
            //  Se dist[z] > dist[x] + peso(x, z)
            //        dist[z] = dist[x] + peso(x, z)
            //        path[z] = x
            //  Fim Se
            //System.out.println();
            //Vertex vC = null;
            /*
            for (int y = 0; y < vertexes[0].length; y++) {
                for (int x = 0; x < vertexes.length; x++) {
                    vC = vertexes[x][y];
                    //System.out.println("Vértice : " + vC + " Estimativa : " + dList.get(vC) + " Pertencimento : " + perm.get(vC) + " Path : " + path.get(vC));
                }
            }
             */
            iterator = minorOf(dList, perm);
            perm.replace(iterator, true);
        }
        /*
        Vertex it = end;
        while (it != null) {
            betterPath.push(it);
            it = path.get(it);
        }
         */
        return path;
    }

    public Queue<Vertex> progressiveDijkstra(Vertex start, Vertex end) {
        // Iterador da vez
        Queue<Vertex> searchOrder = new LinkedList<>();
        Stack<Vertex> betterPath = new Stack<>();
        Vertex iterator = start;

        // Map que armazena as estimativas
        Map<Vertex, Integer> dList = new HashMap<>();
        // Map que armazena pertinência do Vértice no conjunto de mínimos
        Map<Vertex, Boolean> perm = new HashMap<>();

        // Iniciando os maps
        for (int y = 0; y < vertexes[0].length; y++) {
            for (int x = 0; x < vertexes.length; x++) {
                dList.put(vertexes[x][y], -1);
                perm.put(vertexes[x][y], false);
//                path.put(vertexes[x][y], null);
            }
        }

        verifieds.clear();

        // Define-se inicialmente o nó de origem (raiz), neste caso s, 
        // e inclui-se este nó em PERM. Atribui-se zero a sua distância (dist[s]) 
        // porque o custo de ir de s a s é obviamente 0. 
        // Todos os outros nós i tem suas distâncias (dist[i]) inicializadas com um valor bastante grande (-1);
        perm.replace(start, true);
        dList.replace(start, 0);

        while (iterator != null) {

            searchOrder.add(iterator);

            // EM GRAFOS COM DISTÂNCIA != 1 NÃO USAR ISSO!
            if (iterator == end) {
                return searchOrder;
            }

            for (Vertex neighbor : iterator.getNeighbors()) {

                // A partir de s consulta-se os vértices adjacentes a ele, que no grafo G são u e x. 
                // Para todos os vértices adjacentes, que chamaremos z, calcula-se:
                // Se dist[z] > dist[s] + peso(s, z)
                //     dist[z] = dist[s] + peso(s, z)
                //    path[z] = s
                //  Fim Se 
                if (!perm.get(neighbor)) {
                    int distanceToInterator = 1;//distance(iterator, neighbor);
                    int neighborValue = dList.get(neighbor);
                    if (neighborValue > dList.get(iterator) + distanceToInterator || neighborValue < 0) {
                        dList.replace(neighbor, dList.get(iterator) + distanceToInterator);
//                        path.replace(neighbor, iterator);
                    }
                }
            }
            // Dentre todos os vértices não pertencentes a PERM escolhe-se aquele com a menor distância. 
            // Neste caso é o vértice x, pois dist[x] = 5. 
            // Então, inclui-se x em PERM e a partir de x consulta-se os vértices adjacentes a ele que não estão em PERM, que no grafo G são u, v e y.
            // Para todos os vértices adjacentes, que chamaremos z, calcula-se:
            //  Se dist[z] > dist[x] + peso(x, z)
            //        dist[z] = dist[x] + peso(x, z)
            //        path[z] = x
            //  Fim Se
            //System.out.println();
            //Vertex vC = null;
            /*
            for (int y = 0; y < vertexes[0].length; y++) {
                for (int x = 0; x < vertexes.length; x++) {
                    vC = vertexes[x][y];
                    //System.out.println("Vértice : " + vC + " Estimativa : " + dList.get(vC) + " Pertencimento : " + perm.get(vC) + " Path : " + path.get(vC));
                }
            }
             */
            iterator = minorOf(dList, perm);
            perm.replace(iterator, true);
        }
        /*
        Vertex it = end;
        while (it != null) {
            betterPath.push(it);
            it = path.get(it);
        }
         */
        return null;
    }

    public Map<Vertex, Vertex> astar(Vertex start, Vertex end) {

        Map<Vertex, Integer> fCost = new HashMap<>();
        Map<Vertex, Integer> dCost = new HashMap<>();
        Map<Vertex, Vertex> path = new HashMap<>();
        List<Vertex> openedList = new ArrayList<>();
        List<Vertex> closedList = new ArrayList<>();
        Integer lastCost;

        openedList.add(start);
        dCost.put(start, 0);
        fCost.put(start, 0);

        verifieds.clear();

        while (!openedList.isEmpty()) {

            Vertex currentParent = getMinorFCost(openedList, fCost, dCost);
            openedList.remove(currentParent);
            closedList.add(currentParent);

            if (currentParent.equals(end)) {
                return path;
            }
            //System.out.println("\n########## \nSearching neighbours ... ");
            for (Vertex neighbours : currentParent.getNeighbors()) {
                verifieds.add(neighbours);
                if (!closedList.contains(neighbours)) {
                    //System.out.println("\nNeighbour " + neighbours.stringCoordinates() + " found by " + currentParent.stringCoordinates());
                    if (!openedList.contains(neighbours)) {
                        //System.out.println("Is not already in opened list.");
                        //verifieds.add(neighbours);
                        openedList.add(neighbours);
                    }

                    putCost(neighbours, currentParent, end, dCost, fCost, path);
                }

            }
            //System.out.println("\n##########");

        }
        return path;
    }

    public Queue<Vertex> progressiveAstar(Vertex start, Vertex end) {
        Map<Vertex, Integer> fCost = new HashMap<>();
        Map<Vertex, Integer> dCost = new HashMap<>();
        //Map<Vertex, Vertex> path = new HashMap<>();
        Queue<Vertex> order = new LinkedList<>();
        List<Vertex> openedList = new ArrayList<>();
        List<Vertex> closedList = new ArrayList<>();
        Integer lastCost;

        openedList.add(start);
        dCost.put(start, 0);
        fCost.put(start, 0);
        verifieds.clear();

        while (!openedList.isEmpty()) {

            Vertex currentParent = getMinorFCost(openedList, fCost, dCost);
            order.add(currentParent);
            openedList.remove(currentParent);
            closedList.add(currentParent);

            if (currentParent.equals(end)) {
                return order;
            }
            //System.out.println("\n########## \nSearching neighbours ... ");
            for (Vertex neighbours : currentParent.getNeighbors()) {
                order.add(neighbours);
                if (!closedList.contains(neighbours)) {
                    //System.out.println("\nNeighbour " + neighbours.stringCoordinates() + " found by " + currentParent.stringCoordinates());
                    if (!openedList.contains(neighbours)) {
                        //System.out.println("Is not already in opened list.");
                        openedList.add(neighbours);
                    }

                    int dx = Math.abs(neighbours.getX() - end.getX());
                    int dy = Math.abs(neighbours.getY() - end.getY());

                    int dcst = dCost.get(currentParent) + 1;
                    int fcst = (dx + dy) + dcst;
                    //int fcst = (dx > dy ? (14 * dy + 10 * (dx - dy)) : (14 * dx + 10 * (dy - dx))) + dcst * 10;

                    if (fcst < fCost.getOrDefault(neighbours, Integer.MAX_VALUE)) {
                        //System.out.println("Minor value setted " + fcst + " last : " + fCost.get(vertex));
                        dCost.put(neighbours, dcst);
                        fCost.put(neighbours, fcst);

                    }
                }

            }
            //System.out.println("\n##########");

        }
        return null;
    }

    public Map<Vertex, Vertex> iddfs(Vertex start, Vertex end, int startDeep) {
        Queue<Vertex> queue = new LinkedList<>();
        Stack<Vertex> stack = new Stack<>();
        Set<Vertex> set = new HashSet<>();

        Map<Vertex, Vertex> path = new HashMap<>();

        Vertex current = null;
        Vertex found;

        int deep = 0;
        stack.add(start);
        set.add(start);
        verifieds.clear();

        while (!(queue.isEmpty() && stack.isEmpty())) {

            found = null;

            if (stack.isEmpty()) {
                stack.add(queue.poll());
                startDeep += 1;
                deep = 0;
            }

            current = stack.peek();

            if (current.equals(end)) {
                return path;
            }

            if (deep <= startDeep) {

                for (Vertex neighbour : current.getNeighbors()) {
                    if (!set.contains(neighbour)) {
                        verifieds.add(neighbour);
                        set.add(neighbour);
                        path.put(neighbour, current);
                        found = neighbour;
                        break;
                    }
                }

                if (found != null) {
                    stack.push(found);
                    deep++;
                } else if (!stack.isEmpty()) {
                    stack.pop();
                    deep--;
                }
            } else {
                for (Vertex neighbour : stack.pop().getNeighbors()) {
                    if (!set.contains(neighbour)) {
                        verifieds.add(neighbour);
                        path.put(neighbour, current);
                        set.add(neighbour);
                        queue.add(neighbour);
                    }
                }
                deep--;
            }
        }
        return null;
    }

    public Queue<Vertex> progressiveIddfs(Vertex start, Vertex end, int startDeep) {
        Queue<Vertex> queue = new LinkedList<>();
        Stack<Vertex> stack = new Stack<>();
        Set<Vertex> set = new HashSet<>();
        Queue<Vertex> path = new LinkedList<>();

        Vertex current = null;
        Vertex found;

        int deep = 0;
        stack.add(start);
        set.add(start);

        while (!(queue.isEmpty() && stack.isEmpty())) {

            found = null;

            if (stack.isEmpty()) {
                stack.add(queue.poll());
                startDeep += 1;
                deep = 0;
            }

            current = stack.peek();

            if (current.equals(end)) {
                return path;
            }

            System.out.println(deep);
            if (deep <= startDeep) {

                for (Vertex neighbour : current.getNeighbors()) {
                    if (!set.contains(neighbour)) {
                        set.add(neighbour);
                        path.add(neighbour);
                        found = neighbour;
                        break;
                    }
                }

                if (found != null) {
                    stack.push(found);
                    deep++;
                } else if (!stack.isEmpty()) {
                    stack.pop();
                    deep--;
                }
            } else {
                for (Vertex neighbour : stack.pop().getNeighbors()) {
                    if (!set.contains(neighbour)) {
                        path.add(neighbour);
                        set.add(neighbour);
                        queue.add(neighbour);
                    }
                }
                deep--;
            }
        }
        return null;
    }

    private Vertex getMinorFCost(List<Vertex> openedList, Map<Vertex, Integer> fCost, Map<Vertex, Integer> dCost) {

        Vertex minor = null;

        int currentCost;
        int minorCost;

        int majorD;
        int currentD;

        //System.out.println("\nGetting minor...");
        for (Vertex current : openedList) {
            currentCost = fCost.get(current);
            minorCost = fCost.getOrDefault(minor, Integer.MAX_VALUE);

            //System.out.println("Found " + current.stringCoordinates() + " [" + currentCost + " fCost ] [" + dCost.get(current) + " dCost]");
            if (currentCost <= minorCost) {
                if (currentCost == minorCost) {
                    majorD = dCost.getOrDefault(minor, 0);
                    currentD = dCost.getOrDefault(current, 0);
                    minor = (currentD > majorD ? current : minor);
                } else {
                    minor = current;
                }
            }
        }

        //System.out.println("Minor -  " + minor.stringCoordinates() + " [" + fCost.get(minor) + " fCost ] [" + dCost.get(minor) + " dCost]");
        return minor;
    }

    private void putCost(Vertex vertex, Vertex parent, Vertex end, Map<Vertex, Integer> dCost, Map<Vertex, Integer> fCost, Map<Vertex, Vertex> path) {

        int dx = Math.abs(vertex.getX() - end.getX());
        int dy = Math.abs(vertex.getY() - end.getY());

        int dcst = dCost.get(parent) + 1;
        int fcst = (dx + dy) + dcst;
        //int fcst = (dx > dy ? (14 * dy + 10 * (dx - dy)) : (14 * dx + 10 * (dy - dx))) + dcst * 10;

        if (fcst < fCost.getOrDefault(vertex, Integer.MAX_VALUE)) {
            //System.out.println("Minor value setted " + fcst + " last : " + fCost.get(vertex));
            dCost.put(vertex, dcst);
            fCost.put(vertex, fcst);
            path.put(vertex, parent);
        }
    }

    private Vertex minorOf(Map<Vertex, Integer> value, Map<Vertex, Boolean> perm) {
        Vertex minor = null;
        int val = -1;

        for (Vertex key : value.keySet()) {
            int atual = value.get(key);
            ////System.out.println(key + " " + perm.get(key) + " " + value.get(key));
            if ((atual < val || val < 0) && !(atual < 0) && !perm.get(key)) {
                val = atual;
                minor = key;
            }
        }
        //System.out.println("Menor > " + minor);
        return minor;
    }
}
