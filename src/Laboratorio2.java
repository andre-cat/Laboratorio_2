import javax.swing.JOptionPane;

public class Laboratorio2 {

    /**Este método contiene el código para sumar y multiplicar números y obtener números automórficos.
     * @author Andrea Arias, Aiker Acosta, Nilson Díaz, Elíaz Díaz. E
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Opciones del punto a evaluar.
        String[] puntos = { "1a) Sumar un conjunto de números", "1b) Multiplicar un conjunto de números.",
                "2) Saber si un número en una base es automórfico." };

        // Despliegue de una ventana para elegir una de las opciones de arriba.
        String caso = (String) JOptionPane.showInputDialog(null, "¿Qué desea hacer?:", "Programa", 3, null, puntos,
                null);

        // Abortar programa en caso de hacer click en «Cancelar».
        if (caso == null) {
            System.exit(0);
        }

        switch (caso) {// -------------------------------<Suma_y_resta>-------------------------------------------
            // Estas dos opciones comparten la misma instrucción inicial. Se crean unas
            // listas que representan cada número a través de la inserción de los dígitos
            // uno por uno.
            case "1a) Sumar un conjunto de números":
            case "1b) Multiplicar un conjunto de números.":
                int conjunto = numero_valido(10, caso, "Cantidad de números a operar:", "Inserte números válidos.");

                Simple[] listas = new Simple[conjunto]; // Arreglo de objetos de la clase Simple (listas no circulares).
                int[] numeros = new int[conjunto]; // Arreglo para el guardado de los números representados por tales
                                                   // listas para verificar los resultados.

                Simple total = new Simple(); // Lista con los dígitos del resultado.

                System.out.println(caso + "\n");

                for (int i = 0; i < conjunto; i++) {
                    Simple L = new Simple();

                    String[] conjunto_signos = { "+", "-" };

                    // Guardado del signo del número de la lista actual.
                    String signo = (String) JOptionPane.showInputDialog(null, "Signo del número " + (i + 1) + ":",
                            "Número " + (i + 1), 3, null, conjunto_signos, null);
                    L.sign(signo);

                    // Se valida que la cantidad de dígitos esté en un sistema numérico decimal y
                    // demás.
                    int digitos = numero_valido(10, "Número " + (i + 1),
                            "Cantidad de digitos del número " + (i + 1) + ":", "Inserte números válidos.");

                    // Validación de la base de cada dígito y unión de dígitos para guardarlos en el
                    // arreglo «numeros».
                    String cadena = "";
                    for (int j = 0; j < digitos; j++) {
                        int digito;
                        do {
                            digito = numero_valido(10, "Número " + (i + 1), "Dígito " + (j + 1) + ":",
                                    "Inserte dígitos válidos.");
                        } while (digito > 9 & digitos == 0);
                        cadena = cadena + digito;
                        L.add(digito);
                    }
                    numeros[i] = Integer.valueOf(cadena);

                    // Establecimiento del signo del número en el arreglo «numeros».
                    if (L.sign() == "-") {
                        numeros[i] = -numeros[i];
                    }

                    // Impresión y guardado de la lista actual.
                    L.print();
                    listas[i] = L;
                }

                if (caso == "1a) Sumar un conjunto de números") {
                    total.add(0);

                    for (int i = 0; i < listas.length; i++) {
                        if (i == 0) {
                            total.sign(listas[0].sign());
                        }

                        // Llenado de la lista de los totales con ceros para sumar números de la lista actual.
                        while (total.size() < listas[i].size()) {
                            total.add_before_all(0);

                        }

                        // Índice de la lista «total».
                        int index = 0;

                        // Elección de la operación de suma o de resta dependiendo de los signos.
                        switch (total.sign() + listas[i].sign()) {
                            // Casos suma.
                            case "++":
                            case "--":
                                int acarreo = 0; // Variable que guarda el valor a sumar al siguiente número.
                                for (int A = listas[i].size(); A >= 1; A--) {
                                    index = Math.abs(total.size() - listas[i].size()) + A;
                                    int suma = acarreo + total.info(index) + listas[i].info(A);
                                    acarreo = 0;

                                    // Si la suma tiene dos dígitos, se llena el acarreo.
                                    if (suma > 9) {
                                        acarreo = Integer.valueOf(Integer.toString(suma).substring(0, 1));
                                        suma = Integer.valueOf(Integer.toString(suma).substring(1, 2));
                                    }
                                    total.set(index, suma);
                                }

                                // Si al final de todas las sumas queda un número sin sumar, se suma en el nodo
                                // anterior respecto de la posición actual (si existe) o se crea un nuevo primer
                                // nodo..
                                if (acarreo != 0) {
                                    if (total.get(index - 1) != null) {
                                        total.set(index - 1, total.info(index - 1) + acarreo);
                                    } else {
                                        total.add_before_all(acarreo);
                                    }
                                }
                                break;

                            // Casos resta.
                            case "+-":
                            case "-+":
                                // Se necesita saber cuál es el número mayor para facilitar el restado.
                                Simple mayor = retornar_mayor(total, listas[i]);
                                Simple menor = new Simple();

                                // No se sabe de entrada cuál es el mayor.
                                if (total == mayor) {
                                    menor = listas[i];
                                } else {
                                    menor = total;
                                }

                                for (int A = menor.size(); A >= 1; A--) {
                                    index = Math.abs(mayor.size() - menor.size()) + A;

                                    // Si el minuendo es menor que el sustraendo se tiene que prestar una décima a
                                    // un número inmediatamente anterior mayor que cero.
                                    if (mayor.info(index) < menor.info(A)) {
                                        prestar(mayor, index);
                                    }

                                    int resta = mayor.info(index) - menor.info(A);
                                    mayor.set(index, resta); // Reemplazo de lo que tiene «mayor» en la posición del
                                                             // index por la resta.
                                }

                                // Eliminación de ceros a la izquierda.
                                while (mayor.info(1) == 0 & mayor.size() != 1) {
                                    mayor.delete(1);
                                }

                                total = mayor; // Se le asigna a total el mayor para la siguiente operación.
                                total.sign(mayor.sign()); // Se conserva el signo del mayor.
                                break;
                        }
                    }

                    // Impresión de la lista de resultados por consola.
                    System.out.print("Suma por nodos: ");
                    total.print();

                    // Suma e impresión de los números guardados en el vector «numeros».
                    double resultado = 0;
                    for (int i = 0; i < numeros.length; i++) {
                        resultado += numeros[i];
                    }
                    System.out.print("Suma por sistema: ");
                    System.out.print((int) resultado);

                    // Exposición de resultados por ventana.
                    String cadena = "La suma de los siguientes números...\n\n";
                    cadena = cadena + escribir(numeros);
                    cadena = cadena + "\nes " + total.number();
                    int salir = JOptionPane.showConfirmDialog(null, cadena, "Bye", JOptionPane.OK_CANCEL_OPTION);
                    if (salir == JOptionPane.OK_OPTION) {
                        System.exit(0);
                    } else {
                        System.exit(0);
                    } // Aborto del programa.
                } else { // --------------------------------<Multiplicación>-----------------------------------------
                    total.add(1);

                    for (int i = 0; i < listas.length; i++) {

                        Simple mayor = new Simple();
                        Simple menor = new Simple();

                        // Identificación del número con más digitos (mayor) y menos dígitos (menor)
                        // para que el multiplicando sea mayor que el multiplicador siempre.
                        if (listas[i].size() > total.size()) {
                            mayor = listas[i];
                            menor = total;
                        } else if (listas[i].size() < total.size()) {
                            mayor = total;
                            menor = listas[i];
                        } else {
                            mayor = listas[i];
                            menor = total;
                        }

                        // Creación de lista que guardará los productos y las sumas de éstos.
                        Simple productos = new Simple();

                        // Llenado de la lista de productos para la facilitación de las sumas.
                        while (productos.size() < mayor.size()) {
                            productos.add(0);
                        }

                        // El índice de la lista de productos debe reducirse una unidad cada vez que el
                        // dígito de la lista menor cambia.
                        int c = 2;
                        for (int A = menor.size(); A >= 1; A--) {
                            c--;
                            int index = 0;
                            index = c + productos.size();
                            int acarreo = 0;

                            for (int B = mayor.size(); B >= 1; B--) {
                                index--;
                                int producto = menor.info(A) * mayor.info(B) + acarreo;
                                acarreo = 0;

                                // Si la posición del índice no existe, entonces debe crear una anterior.
                                if (productos.get(index) == null) {
                                    productos.add_before_all(0);
                                    index++;
                                }

                                // En la suma se consideran en seguida los productos anteriores.
                                int suma = producto + productos.info(index);
                                if (suma > 9) {
                                    acarreo = Integer.valueOf(Integer.toString(suma).substring(0, 1));
                                    suma = Integer.valueOf(Integer.toString(suma).substring(1, 2));
                                }

                                // Reemplazar lo que hay en la posición por la suma.
                                productos.set(index, suma);
                            }

                            // Si el acarreo no es 0 es porque no hubo otra iteración y sobró un sumando.
                            // Tal sumando de agrega antes del último índice.
                            if (acarreo != 0) {
                                if (productos.get(index - 1) != null) {
                                    productos.set(index - 1, productos.info(index - 1) + acarreo);
                                } else {
                                    productos.add_before_all(acarreo);
                                }
                            }
                        }

                        // Establecimiento de signos.
                        switch (total.sign() + listas[i].sign()) {
                            case "++":
                            case "--":
                                productos.sign("+");
                                break;
                            case "+-":
                            case "-+":
                                productos.sign("-");
                                break;
                        }
                        // El total se convierte en el nuevo multiplicando.
                        total = productos;
                    }

                    // Impresión de resultados por consola.
                    if (total.info(1) == 1 & total.size() == 1) {
                        total.set(1, 0);
                    }
                    System.out.print("Multiplicación por nodos: ");
                    total.print();

                    // Operación realizada con los valores de la matriz «numeros».
                    double resultado = 1;
                    for (int i = 0; i < numeros.length; i++) {
                        resultado *= numeros[i];
                    }
                    System.out.print("Multiplicación por sistema: ");
                    System.out.print((int) resultado);

                    // Exposición de resultados por ventana.
                    String cadena = "La multiplicación de los siguientes números...\n\n";
                    cadena = cadena + escribir(numeros);
                    cadena = cadena + "\nes " + total.number();
                    int salir = JOptionPane.showConfirmDialog(null, cadena, "Bye", JOptionPane.OK_CANCEL_OPTION);
                    if (salir == JOptionPane.OK_OPTION) {
                        System.exit(0);
                    } else {
                        System.exit(0);
                    }
                } // --------------------------------------<Automorfia>----------------------------------------------
                break;
            case "2) Saber si un número en una base es automórfico.":
                String[] sistemas = { "Decimal", "Otra base" };
                String sistema_elegido = null;
                sistema_elegido = (String) JOptionPane.showInputDialog(null,
                        "¿En cuál sistema numérico desea insertar el número?:\n\n",
                        "Elección de sistema numérico - Números automórficos", 3, null, sistemas, null);

                // Si se hace click en cancelar y no se guarda nada en el String se aborta el
                // programa.
                if (sistema_elegido == null) {
                    System.exit(0);
                }

                int n = 0; // Variable del número.
                int n2 = 0; // Variable del cuadrado del número.

                // Advertencia y mensaje personalizado para este punto.
                String mensaje = "Inserción de número a evaluar:";
                String advertencia = "Insertar número:\nNo negativo.\nDentro del sistema evaluado.";

                int base = 0;
                switch (sistema_elegido) {
                    case "Decimal":
                        n = numero_valido(10, "Sistema " + sistema_elegido, mensaje, "Digite números positivos.");
                        n2 = (int) Math.pow(n, 2);
                        System.out.println(n2);
                        break;

                    case "Otra base":
                        // Validación del rango de bases utilizado.
                        do {
                            base = Integer.parseInt(JOptionPane.showInputDialog(null, "Insertar base (entre 2 y 9):"));
                        } while (base < 2 | base > 9);

                        n = numero_valido(base, "Sistema de base " + base, mensaje, advertencia);

                        // Se convierte el número de a decimal para obtener el resultado del cuadrado.
                        int d = base_a_decimal(n, base);
                        int d2 = (int) Math.pow(d, 2);

                        n2 = decimal_a_base(d2, base);
                        break;
                }

                Simple lista_n = new Simple();
                Simple lista_n2 = new Simple();

                llenar_lista_circular(n, lista_n);
                llenar_lista_circular(n2, lista_n2);

                // Exposición de listas por consola.
                System.out.print("n: ");
                lista_n.print();

                System.out.print("n^2: ");
                lista_n2.print();

                // Impresión de resultados.
                String cadena = "";
                for (int i = lista_n.size(); i >= 1; i--) {
                    int index = i + Math.abs(lista_n.size() - lista_n2.size());
                    if (lista_n.info(i) == lista_n2.info(index)) {
                        cadena = lista_n.info(i) + cadena;
                        if (i == 1) {
                            JOptionPane.showMessageDialog(null,
                                    "El número " + n + " en base " + base + " es automórfico porque las últimas cifras de su cuadrado (" + n2
                                            + ") son iguales a éste mismo: " + cadena,
                                    "Resultado", 2);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "El número " + n + " en base " + base + " no es automórfico porque las últimas cifras de su cuadrado (" + n2
                                        + ") no son iguales a éste mismo: " + n2,
                                "Resultado", 2);
                        break;
                    }
                }
        }
    }

    /**
     * Procedimiento que llena una lista circular a partir del ingreso de un número.
     * 
     * @param número
     * @param lista
     */
    public static void llenar_lista_circular(int número, Simple lista) {
        String cadena = Integer.toString(número);
        for (int i = 0; i < cadena.length(); i++) {
            lista.add(Integer.valueOf(cadena.substring(i, i + 1)));
        }
    }

    /**
     * Procedimiento que genera errores cuando el dato ingresado es menor que 0, no
     * es de la base especificada y no corresponden a un número o si no se ingresa
     * dato alguno. También permite abortar el programa en caso de hacer click en
     * «Cancelar».
     * 
     * @param base
     * @param título
     * @param mensaje
     * @param advertencia
     * @return int
     */
    public static int numero_valido(int base, String título, String mensaje, String advertencia) {
        int número = 0;
        boolean error;
        do {
            error = false;
            try {
                String cadena = null;
                cadena = JOptionPane.showInputDialog(null, mensaje, título, 1);
                if (cadena == null) {
                    System.exit(0);
                }
                número = Integer.valueOf(cadena);
                if (número < 0) {
                    error = true;
                }
                if (validar_base(número, base) == false) {
                    error = true;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, advertencia, "Error (!).", 0);
                error = true;
            }
        } while (error == true);
        return número;
    }

    /**
     * Función que retorna «false» cuando el número evaluado no se encuentra en el
     * conjunto de números de la base especificada.
     * 
     * @param número
     * @param base
     * @return boolean
     */
    public static boolean validar_base(int número, int base) {
        Boolean verde = true;
        String cadena = Integer.toString(número);
        for (int i = 0; i < cadena.length(); i++) {
            if (Integer.valueOf(cadena.substring(i, i + 1)) >= base) {
                verde = false;
            }
        }
        return verde;
    }

    /**
     * Función que convierte un número de una base numérica (base 2 - base 9) a
     * decimal.
     * 
     * @param número
     * @param base
     * @return int
     */
    public static int base_a_decimal(int número, int base) {
        String cadena = Integer.toString(número);
        int c = 0;
        int suma = 0;
        System.out.println("Conversión de " + número + " de base " + base + " a base 10:");
        for (int i = cadena.length(); i > 0; i--) {
            int cifra = Integer.valueOf(cadena.substring(i - 1, i));
            suma += cifra * Math.pow(base, c);
            System.out.println(cifra + " * " + (int) (Math.pow(base, c)));
            c++;
        }
        System.out.println("= " + suma + "\n");
        return suma;
    }

    /**
     * Función que convierte un número entero decimal en un número entero de otra
     * base.
     * 
     * @param número
     * @param base
     * @return int
     */
    public static int decimal_a_base(int número, int base) {
        int cociente = número;
        int raiz = (int) Math.sqrt(número);
        int residuo;
        String cadena = "";
        System.out.println("Conversión de número " + raiz + "^2 de base 10 a base " + base + ":");
        do {
            residuo = cociente % base;
            System.out.println(cociente + " mod " + base + " = " + residuo);
            cadena = residuo + cadena;
            cociente = cociente / base;
        } while (cociente > base);
        residuo = cociente % base;
        System.out.println(cociente + " mod " + base + " = " + residuo);
        cadena = residuo + cadena;
        int residuos = Integer.valueOf(cadena);
        System.out.println("= " + residuos + "\n");
        return residuos;
    }

    /**
     * Procedimiento utilizado cuando el minuendo es menor que el sustraendo en una
     * resta.
     * 
     * @param mayor
     * @param index_mayor
     */
    public static void prestar(Simple mayor, int index_mayor) {
        if (mayor.info(index_mayor - 1) < 1) {
            prestar(mayor, index_mayor - 1);
        }
        mayor.set(index_mayor - 1, mayor.info(index_mayor - 1) - 1);
        mayor.set(index_mayor, mayor.info(index_mayor) + 10);
    }

    /**
     * Función que retorna la lista que representa el número mayor entre dos listas
     * mediante la comparación del tamaño de cada una; cuando el tamaño de ambas es
     * igual, las envía a la función siguiente.
     * 
     * @param lista1
     * @param lista2
     * @return Simple
     */
    public static Simple retornar_mayor(Simple lista1, Simple lista2) {
        if (lista1.size() > lista2.size()) {
            return lista1;
        } else if (lista1.size() < lista2.size()) {
            return lista2;
        } else {
            return comparar(lista1, lista2, 1);
        }
    }

    // Variable que guarda la lista mayor encontrada en la función recursiva
    // siguiente.
    static Simple lista_mayor;

    /**
     * Función que compara dos listas dígito por dígito y retorna la que represente
     * el número mayor.
     * 
     * @param lista1
     * @param lista2
     * @param index
     * @return Simple
     */
    public static Simple comparar(Simple lista1, Simple lista2, int index) {
        if (lista1.get(index) != null) {
            if (lista1.info(index) == lista2.info(index)) {
                comparar(lista1, lista2, index + 1);
            } else {
                if (lista1.info(index) > lista2.info(index)) {
                    lista_mayor = lista1;
                } else {
                    lista_mayor = lista2;
                }
            }
        } else {
            lista_mayor = lista1;
        }
        return lista_mayor;
    }

    /**
     * Función que retorna la lista de los números a multiplicar o a sumar en una
     * cadena.
     * 
     * @param arreglo
     * @return String
     */
    public static String escribir(int[] arreglo) {
        String cadena = "";
        for (int i = 0; i < arreglo.length; i++) {
            cadena = cadena + arreglo[i] + "\n";
        }
        return cadena;
    }
}
