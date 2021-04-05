import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Proyecto {

    private static ArrayList<Activity> activities = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        Graph graph;

        System.out.println("Bienvenido al programa.");
        System.out.println("Desea ejecutar el diagrama PERT de ejemplo o generar su propio grafo?");
        System.out.println("(1) Ejecutar diagrama de ejemplo.");
        System.out.println("(2) Generar grafo nuevo.");
        System.out.print("Respuesta: ");

        if(scanner.nextLine().equals("1")){
           graph = new Graph(createExample());
        } else {
            graph = new Graph(createActivities());
        }

        graph.run();

    }

    private static ArrayList<Activity> createActivities() {
        boolean done = false;

        System.out.println("\nIntroduzca la primera actividad.");
        do {
            System.out.print("\nNombre de la actividad: ");
            String id = scanner.nextLine().toUpperCase();

            //Crear la actividad
            createActivity(id);

            //Luego...
            System.out.print("\nDesea agregar otra actividad? (s/N): ");
            if(!scanner.nextLine().equalsIgnoreCase("s"))
                done = true;
        } while (!done);

        //Finalmente retornamos la lista de actividades.
        return activities;
    }

    private static ArrayList<Activity> createExample(){
        Activity aA = new Activity("A", 3);
        Activity aB = new Activity("B", 2);
        Activity aC = new Activity("C", 1);
        Activity aD = new Activity("D", 3);
        Activity aE = new Activity("E", 2);
        Activity aF = new Activity("F", 2);
        Activity aG = new Activity("G", 2);

        //B
        aB.addPredecessor(aA);
        //C
        aC.addPredecessor(aB);
        aC.addPredecessor(aE);
        //D
        aD.addPredecessor(aE);
        //E
        aE.addPredecessor(aA);
        //F
        aF.addPredecessor(aC);
        aF.addPredecessor(aD);
        //G
        aG.addPredecessor(aF);

        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(aA);
        activities.add(aB);
        activities.add(aC);
        activities.add(aD);
        activities.add(aE);
        activities.add(aF);
        activities.add(aG);
        return activities;
    }

    private static void createActivity(String id){
        ArrayList<String> precedentesCache = new ArrayList<>();

        int duration = getDuracion(id);

        Activity activity = new Activity(id, duration);
        activities.add(activity);

        System.out.print("Esta actividad tiene precedentes? (s/N): ");
        if(scanner.nextLine().equalsIgnoreCase("s")){
            int nPrecedentes = getNPrecedents();
            for(int i=1; i<=nPrecedentes; i++){
                System.out.print("Nombre de la " + i + "° actividad precedente a la actividad '" + id + "': ");
                String precedente = scanner.nextLine().toUpperCase();
                if(activityExists(precedente))
                    activity.addPredecessor(getActivity(precedente));
                else{
                    precedentesCache.add(precedente);
                }
            }
            //Luego de definir las actividades precedentes, crearlas.
            for(String precedente: precedentesCache){
                System.out.println("\n--- Creacion de la actividad " + precedente +" ---");
                createActivity(precedente);
                activity.addPredecessor(getActivity(precedente));
            }
//            activities.add(activity);
        }
    }

    private static int getDuracion(String id){
        boolean valid;
        String input;
        do {
            System.out.print("Duracion de la actividad " + id +": ");
            input = scanner.nextLine();
            valid = Pattern.matches("^[0-9]*$", input);
            if(!valid)
                System.out.println("Ingrese unicamente digitos!");
        } while (!valid);
        return Integer.parseInt(input);
    }

    private static int getNPrecedents(){
        boolean valid;
        String input;
        do {
            System.out.print("Cuantos precedentes tiene? (Introducir un digito): ");
            input = scanner.nextLine();
            valid = Pattern.matches("^[0-9]*$", input);
            if(!valid)
                System.out.println("Ingrese unicamente digitos!");
        } while (!valid);
        return Integer.parseInt(input);
    }

    private static boolean activityExists(String predecesor) {
        boolean exists = false;

        for(Activity activity: activities) {
            if (activity.getId().equals(predecesor))
                exists = true;
        }

        return exists;
    }

    private static Activity getActivity(String id) {
        for(Activity activity: activities) {
            if(activity.getId().equals(id))
                return activity;
        }
        return null;
    }
}
