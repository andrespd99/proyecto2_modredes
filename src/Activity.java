import java.util.ArrayList;
import java.util.List;

public class Activity {
    private String id;
    private int duration;
    private ArrayList<Activity> predecessors;
    public int es;
    public int ef;
    public int ls;
    public int lf;
    public int h;

    Activity(String id, int duration){
        this.id = id;
        this.duration = duration;
        this.predecessors = new ArrayList<Activity>();
        this.es = 0;
        this.ef = -1;
    }

    public void addPredecessor(Activity activity) {
        predecessors.add(activity);
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

//    public int calculateH() {
//
//    }
//
    public int calculateEf() {

        for(Activity activity: predecessors) {
            if(activity.ef > this.es){
                this.es = activity.ef;
            }
        }

        this.ef = this.es + this.duration;
        return this.ef;
    }
//
//    public int calculateLs() {
//
//    }
//
//    public int calculateLf() {
//
//    }

    public String predecessorsToString(){
        String predecessorsStr = "";
        for (Activity activity: this.predecessors){
            predecessorsStr = predecessorsStr + ", " + activity.getId();
        }
        return predecessorsStr;
    }

    public boolean isRoot() {
        return predecessors.size() > 0;
    }
}
