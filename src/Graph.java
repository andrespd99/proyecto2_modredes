import java.util.ArrayList;

public class Graph {
    private final ArrayList<Activity> activities;
    private final StartNode startnode = new StartNode();
    private final EndNode endnode = new EndNode();
    private ArrayList<Activity> unvisitedNodes = new ArrayList<>();

    public Graph(ArrayList<Activity> activities) {
        this.activities = activities;
        this.unvisitedNodes.addAll(activities);
        unvisitedNodes.add(endnode);
    }

    public void run(){
        //Conectamos los nodos iniciales a un nodo comun startNode,
        //y los nodos terminales a un nodo comun endNode de tipos especiales.
        connectEndPoints(getLeaves());
        connectStartPoints(getRoots());

        runForward();
        restartUnvisitedList(); //Reiniciamos la lista auxiliar para el recorrido en reversa.
        runBackwards();

        printValues();
    }

    private void printValues(){
        System.out.println("\nLeyenda:");
        System.out.println("ES: Early Start, EF: Early Finish, LS: Late Start");
        System.out.println("LF: Late Finish, H: Holgura");
        System.out.println("\nValores del grafo:");
        for (Activity activity: activities) {
            System.out.println("Nodo " + activity.getId() +
                    " | Duracion: " + activity.getDuration() +
                    " | ES: " + activity.es +
                    " | EF: " + activity.ef +
                    " | LS: " + activity.ls +
                    " | LF: " + activity.lf +
                    " | H: " + activity.h + " |" +
                    " | Precedentes: " + activity.predecessorsToString()
            );
        }
    }

    //Reinicia la lista de nodos no visitados - metodo auxiliar.
    private void restartUnvisitedList(){
        this.unvisitedNodes.addAll(activities);
        this.unvisitedNodes.add(startnode);
    }

    //Hace la ejecucion hacia adelante del CPM.
    private void runForward(){
        //Hasta que todos los nodos hayan sido visitados hacia adelante.
        Activity currentNode = startnode;
        do {
            currentNode.visitForward();
            if(currentNode.wasVisitedForward())
                this.unvisitedNodes.remove(currentNode);
            try{
                currentNode = getNextNodeForward();
            } catch (NullPointerException e) {
                System.out.println("Revise que el grafo dado sea conexo!!!");
                System.exit(300);
            }
        } while (!allVisited());
    }

    //Hace la ejecucion hacia atras del CPM.
    private void runBackwards(){
        //Hasta que todos los nodos hayan sido visitados hacia atras.
        Activity currentNode = endnode;
        do {
            currentNode.visitBackwards();
            if(currentNode.wasVisitedBackwards())
                this.unvisitedNodes.remove(currentNode);
            try{
                currentNode = getNextNodeBackwards();
            } catch (NullPointerException e) {
                System.out.println("Error inesperado. Llamando a 0800-CANTV-00. Será atendido en 7356 dias.");
                System.exit(400);
            }
        } while (!allVisited());
    }

    private void connectStartPoints(ArrayList<Activity> roots){
        for(Activity root: roots){
            root.addPredecessor(startnode);
        }
    }

    private void connectEndPoints(ArrayList<Activity> leaves){
        for(Activity leaf: leaves){
            leaf.addSucessor(endnode);
        }
    }

    private boolean allVisited(){
        return unvisitedNodes.isEmpty();
    }

    private ArrayList<Activity> getRoots(){
        ArrayList<Activity> roots = new ArrayList<Activity>();
        for(Activity activity: activities){
            if(activity.isRoot())
                roots.add(activity);
        }
        return roots;
    }

    private ArrayList<Activity> getLeaves(){
        ArrayList<Activity> leaves = new ArrayList<Activity>();
        for(Activity activity: activities){
            if(activity.isLeaf())
                leaves.add(activity);
        }
        return leaves;
    }

    //Revisar posibles errores!!
    private Activity getNextNodeForward(){
        if(!allVisited()){
            for(Activity node: unvisitedNodes){
                if(node.canVisitForward()){
                    return node;
                }
            }
            throw new NullPointerException();
        }
        return endnode;
    }

    private Activity getNextNodeBackwards(){
        if(!allVisited()){
            for(Activity node: unvisitedNodes){
                if(node.canVisitBackwards()){
                    return node;
                }
            }
            throw new NullPointerException();
        }
        return startnode;
    }

}
