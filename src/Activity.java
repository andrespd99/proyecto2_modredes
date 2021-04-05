import com.sun.tools.javadoc.Start;

import java.util.ArrayList;

public class Activity {
    private String id;
    private int duration;
    private ArrayList<Activity> predecessors;
    private ArrayList<Activity> successors;
    public int es;
    public int ef;
    public int ls;
    public int lf;
    public int h;
    private boolean wasVisitedForward; // Variable auxiliar necesaria en el recorrido del grafo hacia adelante.
    private boolean wasVisitedBackwards; // Variable auxiliar necesaria en el recorrido del grafo hacia atras.



    Activity(String id, int duration){
        this.id = id;
        this.duration = duration;
        this.predecessors = new ArrayList<Activity>();
        this.successors = new ArrayList<Activity>();
        this.es = 0;
        this.ef = -1;
        this.lf = Integer.MAX_VALUE;
        wasVisitedForward = false;
        wasVisitedBackwards = false;
    }

    public void addPredecessor(Activity activity) {
        if(!predecessors.contains(activity))
            predecessors.add(activity);
        if(!activity.successors.contains(activity))
            activity.addSucessor(this);
    }

    // Registra un sucesor de este nodo.
    public void addSucessor(Activity successor) {
        if(!successors.contains(successor))
            this.successors.add(successor);

        if(!successor.predecessors.contains(this))
            successor.addPredecessor(this);
    }

    public String getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }



    public ArrayList<Activity> getPredecessors() {
        return predecessors;
    }

    public ArrayList<Activity> getSuccessors() {
        return successors;
    }

    //Calcula los valores Early Start y Early Finish de esta actividad.
    public void calculateForward() {
        for(Activity predecessor: predecessors){
            if(predecessor.ef > es)
                es = predecessor.ef;
        }
        ef = es + duration;
    }

    //Calcula los valores Late Start, Late Finish y la Holgura de esta actividad.
    private void calculateBackwards() {
        if (successors.isEmpty()){
            lf = ef;
        } else {
            for(Activity successor: successors){
                if (successor.ls < lf)
                    lf = successor.ls;
            }
        }
        ls = lf - duration;
        h = ls - es;
    }

    public String predecessorsToString(){
        String predecessorsStr = "";
        for (Activity predecessor: this.predecessors){
            if(predecessor instanceof StartNode){
                return predecessorsStr;
            }
            predecessorsStr = predecessorsStr + predecessor.getId() + ", ";
        }
        return predecessorsStr;
    }

    public boolean isRoot() {
        return (predecessors.size() == 0);
    }

    public boolean isLeaf() {
        return (successors.size() == 0);
    }

    // Retorna el numero de actividades que conectan HACIA este nodo.
    public int inDegree() {
        return predecessors.size();
    }

    // Marcar como visitado.
    public void markAsVisited(){
        this.wasVisitedForward = true;
    }

    // Retorna si el nodo ya fue visitado hacia adelante.
    public boolean wasVisitedForward(){
        return wasVisitedForward;
    }

    // Retorna si el nodo ya fue visitado hacia atras.
    public boolean wasVisitedBackwards() {
        return wasVisitedBackwards;
    }

    //Retorna si todos los predecesores de este nodo ya han sido visitados
    //y sus valores calculados.
    public boolean canVisitForward() {
        boolean canVisitForward = true;
        for(Activity predecessor: predecessors){
            if(!predecessor.wasVisitedForward()){
                canVisitForward = false;
            }
        }
        return canVisitForward;
    }
    //Retorna si todos los sucesores de este nodo ya han sido visitados
    //y sus valores calculados.
    public boolean canVisitBackwards() {
        boolean canVisitBackwards = true;
        for(Activity successors: successors){
            if(!successors.wasVisitedBackwards()){
                canVisitBackwards = false;
            }
        }
        return canVisitBackwards;
    }

    //Calcula los valores ES y EF cuando el nodo es visitable hacia adelante.
    public void visitForward(){
        if(canVisitForward()){
            calculateForward();
            this.wasVisitedForward = true;
        }
    }
    //Calcula los valores LS y LF cuando el nodo es visitable hacia atras.
    public void visitBackwards() {
        if(canVisitBackwards()){
            calculateBackwards();
            this.wasVisitedBackwards = true;
        }
    }

}
