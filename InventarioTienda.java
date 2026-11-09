import java.util.Scanner;

/**
 * Control de inventario de una tienda de barrio (datos simulados).
 * El programa deja registrar ventas y reposiciones y avisa cuando un producto
 * queda por debajo de su stock minimo.
 */
public class InventarioTienda {

    // Datos simulados de cinco productos
    static final String[] PRODUCTOS = {"Arroz (lb)", "Aceite (lt)", "Panela (un)", "Huevos (un)", "Leche (lt)"};
    static final int[] STOCK_MINIMO = {10, 5, 8, 20, 6};
    static final int[] PRECIO = {3200, 12500, 2800, 700, 3600};
    static int[] stock = {40, 15, 25, 60, 12};
    static int totalVentas = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        System.out.println("=== Inventario de la tienda (datos simulados) ===");

        // Ciclo principal: se repite hasta que el usuario elija 0 (salir)
        do {
            mostrarMenu();
            opcion = leerEntero(sc, "Opcion: ", 0, 3);

            switch (opcion) {
                case 1:
                    registrarVenta(sc);
                    break;
                case 2:
                    registrarReposicion(sc);
                    break;
                case 3:
                    mostrarInventario();
                    break;
                default:
                    System.out.println("Cierre del dia. Total vendido: $" + totalVentas);
            }
        } while (opcion != 0);

        sc.close();
    }

    static void mostrarMenu() {
        System.out.println();
        System.out.println("1. Registrar venta");
        System.out.println("2. Registrar reposicion");
        System.out.println("3. Ver inventario");
        System.out.println("0. Salir");
    }

    // Pide un entero y repite la pregunta mientras el dato no sea valido
    static int leerEntero(Scanner sc, String mensaje, int min, int max) {
        while (true) {
            System.out.print(mensaje);
            if (!sc.hasNextLine()) {
                System.out.println();
                System.out.println("No hay mas datos de entrada. Se cierra el programa.");
                System.exit(0);
            }
            String texto = sc.nextLine().trim();
            try {
                int valor = Integer.parseInt(texto);
                if (valor >= min && valor <= max) {
                    return valor;
                }
                System.out.println("Error: escriba un numero entre " + min + " y " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Error: \"" + texto + "\" no es un numero entero.");
            }
        }
    }

    static void registrarVenta(Scanner sc) {
        mostrarInventario();
        int producto = leerEntero(sc, "Numero de producto (1-" + PRODUCTOS.length + "): ", 1, PRODUCTOS.length) - 1;
        int cantidad = leerEntero(sc, "Cantidad a vender: ", 1, 1000);

        if (cantidad > stock[producto]) {
            System.out.println("Venta rechazada: solo hay " + stock[producto] + " de " + PRODUCTOS[producto] + ".");
        } else {
            stock[producto] = stock[producto] - cantidad;
            int valor = cantidad * PRECIO[producto];
            totalVentas = totalVentas + valor;
            System.out.println("Venta registrada: " + cantidad + " x " + PRODUCTOS[producto] + " = $" + valor);

            if (stock[producto] < STOCK_MINIMO[producto]) {
                System.out.println("ALERTA: " + PRODUCTOS[producto] + " quedo con " + stock[producto]
                        + " (minimo " + STOCK_MINIMO[producto] + "). Conviene reponer.");
            }
        }
    }

    static void registrarReposicion(Scanner sc) {
        mostrarInventario();
        int producto = leerEntero(sc, "Numero de producto (1-" + PRODUCTOS.length + "): ", 1, PRODUCTOS.length) - 1;
        int cantidad = leerEntero(sc, "Cantidad recibida: ", 1, 500);

        stock[producto] = stock[producto] + cantidad;
        System.out.println("Reposicion registrada. " + PRODUCTOS[producto] + " ahora tiene " + stock[producto] + ".");
    }

    static void mostrarInventario() {
        System.out.println();
        System.out.printf("%-3s %-14s %6s %7s  %s%n", "N", "Producto", "Stock", "Minimo", "Estado");
        for (int i = 0; i < PRODUCTOS.length; i++) {
            String estado = stock[i] < STOCK_MINIMO[i] ? "REPONER" : "ok";
            System.out.printf("%-3d %-14s %6d %7d  %s%n", i + 1, PRODUCTOS[i], stock[i], STOCK_MINIMO[i], estado);
        }
    }
}
