import java.io.*;
import java.util.*;
import java.util.Map.Entry;


public class AddressBook {

    public static void main(String[] args) {
        HashMap<String, Object> myMapAddressBook = new HashMap<>(); // se declara el HashMap

        Scanner sn = new Scanner(System.in);
        boolean salir = false;
        int opcion;
        load(myMapAddressBook);
        while (!salir) {

            System.out.println("1. Ver Agenda");
            System.out.println("2. Crear contacto");
            System.out.println("3. Eliminar contacto");
            System.out.println("4. Salir");

            try {
                System.out.println("Escribe una de las opciones");
                opcion = sn.nextInt();

                switch (opcion) {
                    case 1:
                        list(myMapAddressBook);
                        break;
                    case 2:
                        create(myMapAddressBook);
                        break;
                    case 3:
                        delete(myMapAddressBook);
                        break;
                    case 4:
                        save(myMapAddressBook);
                        salir = true;
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 4");
                }
            } catch (InputMismatchException e) {
                System.out.println("Tienes que insertar un número");
                sn.next();
            }
        }
    }

    //delete: borrará un contacto.
    public static void delete(HashMap myMapAddressBook){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //Se tiene el "lector"
        String telefono = null;
        System.out.println("Ingresa el Teléfono:");//Se solicita el numero de teléfono del contacto

        try {
            telefono = br.readLine(); //Se lee el Teléfono con readLine() que retorna un String con el dato
        } catch (IOException e) {
            e.printStackTrace();
        }
        myMapAddressBook.remove(telefono);
    }

    //create: creará un nuevo contacto.
    public static void create(HashMap myMapAddressBook){

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); //Ya tenemos el "lector"
        String telefono = null;
        String nombre = null;
        System.out.println("Ingresa el Teléfono:");//Se solicita el teléfono del contacto

        try {
            telefono = br.readLine(); //Se lee el numero de Teléfono con readLine() que retorna un String con el dato
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Ingresa el Nombre:");//Se solicita el teléfono del contacto
        try {
            nombre = br.readLine(); //Se lee el Nombre con readLine() que retorna un String con el dato
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(telefono != null && nombre != null)
            myMapAddressBook.put(telefono, nombre);
    }

    //list: muestra los contactos de la agenda
    public static void list(HashMap myMapAddressBook) {
        System.out.println("\n AGENDA DE CONTACTOS");
        for (Iterator<Entry<String, Object>> entries = myMapAddressBook.entrySet().iterator(); entries.hasNext(); ) {
            Map.Entry<String, Object> entry = entries.next();
            String output = String.format("%s:%s", entry.getKey(), entry.getValue());
            System.out.println(output);
        }
    }

    //save: guardará los cambios en el archivo output.csv.
    public static void save(HashMap myMapAddressBook){
        String outputFilename = "src/output.csv"; //archivo resultante que se genera para guardar los cambios.
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(outputFilename));
            for (Iterator<Entry<String, Object>> entries = myMapAddressBook.entrySet().iterator(); entries.hasNext(); ) {
                Map.Entry<String, Object> entry = entries.next();
                String output = String.format("%s,%s", entry.getKey(), entry.getValue() + "\r\n");
                bufferedWriter.write(output);
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }

    //load: carga los contactos del archivo input.csv2.
    public static void load(HashMap myMapAddressBook){
        String inputFilename = "src/input.csv"; //archivo que se lee para cargar el HashMap
        BufferedReader bufferedReader = null;
        String Numero = "";
        String Nombre = "";
        try {
            bufferedReader = new BufferedReader(new FileReader(inputFilename));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int coma = line.indexOf(',');
                Numero = line.substring(0, coma);
                Nombre = line.substring(coma+1, line.length());
                myMapAddressBook.put(Numero, Nombre);
            }
        } catch(IOException e) {
            System.out.println("IOException catched while reading: " + e.getMessage());
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                System.out.println("IOException catched while closing: " + e.getMessage());
            }
        }
    }
}