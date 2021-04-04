import java.util.ArrayList;
import java.util.Scanner;

public class Proyecto {

    static ArrayList<Activity> activities = new ArrayList<>();

    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        boolean isFinished = false;

        System.out.println("Bienvenido al programa. \n Comience introduciendo cada una de las actividades\n\n");

//        Inputs de las actividades.
        while (!isFinished) {
            createActivity();

            System.out.println("Deseas agregar otra actividad? (s/n): ");
            String c = scanner.nextLine();
            if(c.equalsIgnoreCase("n"))
                isFinished = true;
        }

        //Calcular y mostrar los valores es, ls, ef y lf de cada actividad.
        for (Activity activity: activities) {
            System.out.println(activity.getId() + " " + activity.getDuration() + " " + activity.predecessorsToString());
        }

        System.exit(0);
    }

    public static void createActivity(){

        System.out.println("Nombre de la actividad: ");
        String id = scanner.nextLine();

        System.out.println("Duracion de la actividad " + id + ": ");
        int duracion = Integer.parseInt(scanner.nextLine());

        Activity activity = new Activity(id, duracion);

        activities.add(activity);

        System.out.println("Cuantos predecesores tiene la actividad " + id + "?: ");
        int numPredecesores = Integer.parseInt(scanner.nextLine());

        for(int i = 0; i < numPredecesores; i++ ){
            String isOk = "";
            String predecesor;
            do {
                System.out.println("Introduzca el nombre de la actividad predecesora " + i + ": ");
                predecesor = scanner.nextLine();
                if (!activityExists(predecesor)) {
                    // No se ha creado el nodo predecesor => crear;
                    System.out.println("Deseas crear la actividad " + predecesor + "? (s/n): ");
                    isOk = scanner.nextLine();
                    if ( isOk.equalsIgnoreCase("s"))
                        createActivity(predecesor);
                } else {
                    isOk = "s";
                    activity.addPredecessor(getActivity(predecesor));
                }
            } while (!isOk.equalsIgnoreCase("s"));
            // Registra el predecesor para la actividad.
        }

        //Desea agregar otra actividad?
        //SI -> crearActividad()
        //NO -> termina.
    }

    private static boolean activityExists(String predecesor) {
        boolean exists = false;

        for(Activity activity: activities) {
            if (activity.getId().equals(predecesor))
                exists = true;
        }

        return exists;
    }

    public static void createActivity(String nombre){

        String id = nombre;

        System.out.println("Duracion de la actividad " + nombre + ": ");
        int duracion = Integer.parseInt(scanner.nextLine());

        Activity activity = new Activity(id, duracion);

        activities.add(activity);

        System.out.println("Cuantos predecesores tiene la actividad " + nombre + "?: ");
        int numPredecesores = Integer.parseInt(scanner.nextLine());

        for(int i = 0; i < numPredecesores; i++ ){
            String isOk = "";
            String predecesor;
            do {
                System.out.println("Introduzca el nombre de la actividad predecesora " + i + ": ");
                predecesor = scanner.nextLine();
                if (!activityExists(predecesor)) {
                    // No se ha creado el nodo predecesor => crear;
                    System.out.println("Deseas crear la actividad " + predecesor + "? (s/n): ");
                    isOk = scanner.nextLine();
                    if ( isOk.equalsIgnoreCase("s"))
                        createActivity(predecesor);
                }
                else {
                    isOk = "s";
                    activity.addPredecessor(getActivity(predecesor));
                }
            } while (!isOk.equalsIgnoreCase("s"));
            // Registra el predecesor para la actividad.

        }

        //Desea agregar otra actividad?
        //SI -> crearActividad()
        //NO -> termina.
    }

    public static Activity getActivity(String id) {
        for(Activity activity: activities) {
            if(activity.getId().equals(id))
                return activity;
        }
        return null;
    }

    public static void calculateDurations() {
        ArrayList<Activity> roots = new ArrayList<Activity>();

        for (Activity activity: activities) {
            if( activity.isRoot()) {
                roots.add(activity);
            }
        }






    }

}
