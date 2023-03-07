import java.util.*;

public class GameOfLife {

    // Declarando dos constantes.
    private static final int CELL_DEAD = 0;
    private static final int CELL_ALIVE = 1;

    // Declarando las variables.
    private int width;
    private int height;
    private int maxGenerations;
    private int speed;
    private int[][] population;
    private int[][] nextGeneration;
    private int generation;
    private boolean isRunning;
    private Scanner scanner;

    /**
     * La función ejecuta un bucle que imprime la generación actual, espera la
     * entrada del usuario y
     * luego calcula la próxima generación
     */
    public void run() throws InterruptedException {
        isRunning = true;
        while (isRunning) {
            if (maxGenerations != 0 && generation >= maxGenerations) {
                isRunning = false;
                break;
            }
            System.out.println("Generation: " + generation);
            printPopulation();
            Thread.sleep(speed);
            calculateNextGeneration();
            generation++;

            // Esto le pide al usuario que presione enter para continuar o q para finalizar.
            System.out.println("Press enter to continue or q to end");
            String input = scanner.nextLine();
            if (input.equals("q")) {
                isRunning = false;
                break;
            }

        }
    }

    /**
     * Para cada celda en la generación actual, cuenta la cantidad de vecinos y
     * luego aplica las
     * reglas del juego para determinar si la celda estará viva o muerta en la
     * próxima generación.
     */
    private void calculateNextGeneration() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int neighbors = countNeighbors(i, j);
                // Esta es la primera regla del juego de la vida. Si una celda está viva y tiene
                // menos
                // de 2 o más de 3 vecinas, muere.
                if (population[i][j] == CELL_ALIVE) {
                    if (neighbors < 2 || neighbors > 3) {
                        nextGeneration[i][j] = CELL_DEAD;
                    } else {
                        nextGeneration[i][j] = CELL_ALIVE;
                    }
                } else {
                    // Esta es la segunda regla del juego de la vida. Si una celda está muerta y
                    // tiene
                    // exactamente 3 vecinos, se vuelve viva.
                    if (neighbors == 3) {
                        nextGeneration[i][j] = CELL_ALIVE;
                    } else {
                        nextGeneration[i][j] = CELL_DEAD;
                    }
                }
            }
        }
        population = nextGeneration.clone();
        nextGeneration = new int[height][width];
    }

    /**
     * Para cada celda de la cuadrícula, cuenta el número de vecinos que están vivos
     */
    private int countNeighbors(int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i == row && j == col) {
                    continue;
                }
                if (i < 0 || i >= height || j < 0 || j >= width) {
                    continue;
                }
                if (population[i][j] == CELL_ALIVE) {
                    count++;
                }
            }
        }
        return count;
    }

    public void pause() {
        isRunning = false;
    }

    /**
     * Esta función imprime el estado actual de la población.
     */
    public void printPopulation() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                // Comprobando si la celda está viva.
                if (population[i][j] == CELL_ALIVE) {
                    System.out.print(" " + "x" + " ");

                } else {
                    System.out.print(" " + "." + " ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        // Declarando las variables.
        int width = 0;
        int height = 0;
        int maxGenerations = 0;
        int speed = 0;
        String p = "";

        // Solicitar al usuario que ingrese un número entre 10, 20, 40 u 80.
        while (true) {
            System.out.println("w (10, 20, 40 o 80):");
            if (scanner.hasNextInt()) {
                width = scanner.nextInt();
                if (width == 10 || width == 20 || width == 40 || width == 80) {
                    break;
                }
                System.out.println("The width should be 10, 20, 40 o 80");
            } else {
                scanner.next(); // descartar el valor no válido
                System.out.println("The width should be a valid integer");
            }
        }

        // Solicitar al usuario que ingrese un número entre 10, 20, 40 u 80.
        while (true) {
            System.out.println("h (10, 20, 40 o 80):");
            if (scanner.hasNextInt()) {
                height = scanner.nextInt();
                if (height == 10 || height == 20 || height == 40 || height == 80) {
                    break;
                }
                System.out.println("The height should be 10, 20, 40 o 80");
            } else {
                scanner.next(); // descartar el valor no válido
                System.out.println("The height should be a valid integer");
            }
        }

        // Solicitar al usuario que ingrese un número mayor o igual a 0.
        while (true) {
            System.out.println("g (0 for unlimited):");
            if (scanner.hasNextInt()) {
                maxGenerations = scanner.nextInt();
                if (maxGenerations >= 0) {
                    break;
                }
                System.out.println("The maxGenerations should be 0 for unlimited");
            } else {
                scanner.next(); // descartar el valor no válido
                System.out.println("The maxGenerations should be a valid integer");
            }
        }

        // Solicitar al usuario que ingrese un número entre 250 y 1000.
        while (true) {
            System.out.println("s (250 - 1000):");
            if (scanner.hasNextInt()) {
                speed = scanner.nextInt();
                if (speed >= 250 && speed <= 1000) {
                    break;
                }
                System.out.println("The speed should be 250 - 1000");
            } else {
                scanner.next(); // descartar el valor no válido
                System.out.println("The speed should be a valid integer");
            }
        }

        // Leer el carácter de nueva línea que queda en el búfer después de la entrada
        // anterior.
        scanner.nextLine();

        // Pedir al usuario que introduzca un patrón.
        while (true) {
            System.out.println("Initial pattern (rnd or #...#):");
            p = scanner.nextLine();
            if (p.equals("rnd")) {
                break;
            }

            // Comprobando si el patrón es válido mediante una expresión regular
            if (p.matches("^#?[01]+(#([01]+|#)?)*#?$")) {
                break;
            }

            System.out.println("The pattern must be rnd or #...#.");

        }

        // Creando una nueva instancia de la clase GameOfLife y asignando valores a las
        // variables.
        GameOfLife game = new GameOfLife();
        game.width = width;
        game.height = height;
        game.maxGenerations = maxGenerations;
        game.speed = speed;
        game.population = new int[height][width];
        game.nextGeneration = new int[height][width];
        game.generation = 0;
        game.scanner = scanner;

        if (p.equals("rnd")) {
            Random random = new Random();
            // Generando una población aleatoria.
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    game.population[i][j] = random.nextInt(2);
                }
            }
        } else {
            // Dividir la cadena en una matriz de cadenas.
            String[] rows = p.split("#");
            for (int i = 0; i < rows.length; i++) {
                String row = rows[i];
                // Este es un bucle for que itera sobre los caracteres de la cadena.
                for (int j = 0; j < row.length(); j++) {
                    char c = row.charAt(j);
                    // Comprobando si el carácter es igual a 1.
                    if (c == '1') {
                        game.population[i][j] = CELL_ALIVE;
                    } else {
                        game.population[i][j] = CELL_DEAD;
                    }
                }
            }

        }

        game.run();
    }
}