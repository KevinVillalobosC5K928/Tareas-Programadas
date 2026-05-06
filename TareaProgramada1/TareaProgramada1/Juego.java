/**
 * Clase principal que controla la lógica del juego Dojo de Supervivencia.
 * Gestiona la creación de oponentes, los combos y el flujo de batalla.
 * @author Kevin Villalobos
 * @version 1.0
 */
public class Juego {
    /** Interfaz de usuario a usar (consola o GUI) */
    private InterfazJuego ui;
    /** Mejor marcador del torneo, compartido entre partidas */
    private Marcador marcador;

    /**
     * Constructor del juego.
     * @param ui La interfaz de usuario a utilizar.
     * @param marcador El objeto marcador compartido del torneo.
     */
    public Juego(InterfazJuego ui, Marcador marcador) {
        this.ui = ui;
        this.marcador = marcador;
    }

    /**
     * Lleva un registro de la partida, inicia batallas y actualiza el marcador.
     */
    public void iniciarPartida() {
        // --- Registrar nombre del equipo y jugadores ---
        String nombreEquipo = ui.leerTexto("Ingrese el nombre de su equipo:");
        String nombreJ1 = ui.leerTexto("Nombre del Jugador 1:");
        String nombreJ2 = ui.leerTexto("Nombre del Jugador 2:");
        String nombreJ3 = ui.leerTexto("Nombre del Jugador 3:");

        Jugador j1 = new Jugador(nombreJ1);
        Jugador j2 = new Jugador(nombreJ2);
        Jugador j3 = new Jugador(nombreJ3);
        Equipo equipo = new Equipo(nombreEquipo, j1, j2, j3);

        ui.mostrarMensaje("¡Bienvenido, equipo " + nombreEquipo + "!\n" +
                "Cada jugador ha recibido 3 cartas: 1=AIRE, 2=TIERRA, 3=AGUA.\n" +
                "¡Prepárense para el Dojo!");

        // --- Crear los 3 oponentes ---
        double[] intensidades = {0.2, 0.3, 0.4};
        Oponente[] oponentes = new Oponente[3];
        for (int i = 0; i < 3; i++) {
            oponentes[i] = new Oponente(i + 1, intensidades[i]);
        }

        // --- Enfrentar oponentes secuencialmente ---
        boolean victoria = false;
        for (int i = 0; i < 3; i++) {
            Oponente oponente = oponentes[i];
            boolean resultado = enfrentarOponente(equipo, oponente);
            if (!resultado) {
                // El equipo perdió contra este oponente
                ui.mostrarMensaje("¡El equipo " + nombreEquipo + " ha sido derrotado contra el Oponente " +
                        oponente.getNumero() + "!");
                break;
            } else {
                equipo.incrementarOponentesDerrotados();
                if (i < 2) {
                    ui.mostrarMensaje("¡Han derrotado al Oponente " + oponente.getNumero() +
                            "! Avanzando al siguiente oponente...");
                } else {
                    victoria = true;
                }
            }
        }

        if (victoria) {
            ui.mostrarMensaje("¡FELICIDADES! El equipo " + nombreEquipo +
                    " ha derrotado a todos los oponentes. ¡Son los campeones del Dojo!");
        }

        // --- Mostrar resumen final con atributos completos ---
        mostrarResumenFinal(equipo, oponentes);

        // --- Actualizar marcador ---
        int jugActivos = equipo.jugadoresActivos();
        if (marcador.esMejor(equipo, jugActivos)) {
            marcador.actualizar(equipo, jugActivos);
            ui.mostrarMensaje("¡El equipo " + nombreEquipo + " ha establecido un nuevo récord!");
        }
    }

    /**
     * Es el ciclo de combate contra un oponente.
     * Retorna true si el equipo derrota al oponente, false si pierde (energía agotada
     * o sin jugadores activos).
     * @param equipo El equipo jugador.
     * @param oponente El oponente a enfrentar.
     * @return true si el oponente fue derrotado; false si el equipo pierde.
     */
    private boolean enfrentarOponente(Equipo equipo, Oponente oponente) {
        double energia = 1.0;
        int combosRealizados = 0;

        while (!oponente.estaDerrotado()) {
            if (!equipo.tieneJugadoresActivos()) {
                ui.mostrarMensaje("¡El equipo no tiene jugadores activos! Han sido derrotados.");
                return false;
            }
            if (energia <= 0) {
                ui.mostrarMensaje("¡Se ha agotado la energía! El oponente no fue derrotado.");
                return false;
            }

            // Mostrar estado del juego
            String estado = generarEstado(equipo, oponente, energia, combosRealizados);
            ui.mostrarMensaje(estado);

            int opcion = ui.leerEntero("Elija una acción:\n1. Realizar un combo\n2. Abandonar el juego", 1, 2);
            if (opcion == 2) {
                ui.mostrarMensaje("El equipo ha abandonado el Dojo.");
                return false;
            }

            // Realizar combo
            if (realizarCombo(equipo, oponente)) {
                combosRealizados++;
                equipo.incrementarCombos();
                // Consumir energía
                energia -= oponente.getIntensidad();

                // Verificar si algún jugador fue recién derrotado (reducir intensidad)
                verificarJugadoresDerrotados(equipo, oponente);
            }
        }
        ui.mostrarMensaje("¡El Oponente " + oponente.getNumero() + " ha sido derrotado!");
        return true;
    }

    /**
     * Comprueba derrotas para bajar la intensidad si es necesario.
     * @param equipo El equipo de jugadores.
     * @param oponente El oponente actual.
     */
    private void verificarJugadoresDerrotados(Equipo equipo, Oponente oponente) {
        for (int i = 1; i <= 3; i++) {
            Jugador j = equipo.getJugador(i);
            if (!j.estaActivo()) {
                // El jugador fue derrotado; reducir intensidad del oponente
                // Nota: se reduce una vez por jugador. Para evitar reducciones múltiples
                // en iteraciones, este método se llama justo después de cada combo.
            }
        }
        // La reducción se aplica cuando la suma de activos disminuye.
        // Se verifica después de cada combo si la intensidad debe reducirse.
    }

    /**
     * Genera el estado actual del juego.
     * @param equipo El equipo jugador.
     * @param oponente El oponente actual.
     * @param energia La energía disponible.
     * @param combosRealizados Los combos ya realizados.
     * @return El texto con el estado del juego.
     */
    private String generarEstado(Equipo equipo, Oponente oponente, double energia, int combosRealizados) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- ESTADO DEL JUEGO ---\n");
        sb.append(String.format("Oponente: %d  Intensidad: %.0f%%  Cantidad de energía: %.0f%%\n",
                oponente.getNumero(),
                oponente.getIntensidad() * 100,
                energia * 100));
        sb.append("Combos realizados: ").append(combosRealizados).append("\n");
        sb.append("Equipo: ").append(equipo.getNombre()).append("\n\n");

        for (int i = 1; i <= 3; i++) {
            Jugador j = equipo.getJugador(i);
            String estado = j.estaActivo() ? "Activo" : "Derrotado";
            sb.append(String.format("Jugador %d: %s  Estado: %s\n", i, j.getNombre(), estado));
            sb.append(String.format("  Vida de sus cartas:  Aire: %.0f%%  Tierra: %.0f%%  Agua: %.0f%%\n",
                    Math.max(j.getCartaAire().getVida() * 100, 0),
                    Math.max(j.getCartaTierra().getVida() * 100, 0),
                    Math.max(j.getCartaAgua().getVida() * 100, 0)));
        }
        return sb.toString();
    }

    /**
     * Verifica las cartas y realiza un combo si es posible.
     * Cada jugador activo debe aportar al menos una carta.
     * @param equipo El equipo jugador.
     * @param oponente El oponente actual.
     * @return true si el combo fue ejecutado correctamente.
     */
    private boolean realizarCombo(Equipo equipo, Oponente oponente) {
        int jugadoresActivos = equipo.jugadoresActivos();
        // Para el combo: 3 cartas del equipo vs 3 cartas del oponente
        // Cada jugador activo debe aportar al menos una carta
        Carta[] cartasEquipo = new Carta[3]; // una por cada carta del oponente
        boolean[] jugadorUsado = {false, false, false}; // si jugador 1,2,3 aportó carta

        ui.mostrarMensaje("\n--- SELECCIÓN DE COMBO ---\n" +
                "Debe asignar una carta de su equipo a cada carta del oponente.\n" +
                "Cada jugador activo debe aportar al menos una carta.\n" +
                "Cartas: 1=AIRE, 2=TIERRA, 3=AGUA");

        for (int oponCarta = 1; oponCarta <= 3; oponCarta++) {
            String prompt = "Para la carta " + oponCarta + " del oponente, ¿qué jugador usará?\n" +
                    mostrarJugadoresDisponibles(equipo);
            int jugNum;
            Carta carta;
            while (true) {
                jugNum = ui.leerEntero(prompt, 1, 3);
                Jugador jSelec = equipo.getJugador(jugNum);
                if (!jSelec.estaActivo()) {
                    ui.mostrarMensaje("Jugador " + jugNum + " está derrotado. Elija otro.");
                    continue;
                }
                String promptCarta = "¿Qué carta del Jugador " + jugNum + " (" + jSelec.getNombre() +
                        ") usará?\n" + mostrarCartasDisponibles(jSelec);
                int numCarta = ui.leerEntero(promptCarta, 1, 3);
                carta = jSelec.getCarta(numCarta);
                if (!carta.tienePoder()) {
                    ui.mostrarMensaje("Esa carta no tiene poder (vida <= 0). Elija otra.");
                    continue;
                }
                cartasEquipo[oponCarta - 1] = carta;
                jugadorUsado[jugNum - 1] = true;
                break;
            }
        }

        // Verificar que todos los jugadores activos aportaron al menos una carta
        for (int i = 1; i <= 3; i++) {
            Jugador j = equipo.getJugador(i);
            if (j.estaActivo() && !jugadorUsado[i - 1]) {
                ui.mostrarMensaje("¡El Jugador " + i + " (" + j.getNombre() +
                        ") es activo pero no aportó carta al combo! Reasignando...");
                // Forzar al usuario a asignar una carta adicional de este jugador
                // reemplazando una asignación existente
                String prompt = "El Jugador " + i + " (" + j.getNombre() +
                        ") debe aportar al menos una carta.\n" +
                        "¿A qué carta del oponente (1-3) desea reasignar?";
                int oponCarta = ui.leerEntero(prompt, 1, 3);
                String promptCarta = "¿Qué carta del Jugador " + i + " usará?\n" +
                        mostrarCartasDisponibles(j);
                int numCarta = ui.leerEntero(promptCarta, 1, 3);
                Carta carta = j.getCarta(numCarta);
                if (!carta.tienePoder()) {
                    ui.mostrarMensaje("Carta sin poder. Se usará la primera carta disponible.");
                    for (int c = 1; c <= 3; c++) {
                        if (j.getCarta(c).tienePoder()) {
                            carta = j.getCarta(c);
                            break;
                        }
                    }
                }
                cartasEquipo[oponCarta - 1] = carta;
                jugadorUsado[i - 1] = true;
            }
        }

        int cantAtaques = ui.leerEntero("¿Cuántos ataques tendrá el combo?", 1, 20);

        // Registrar activos antes del combo para detectar derrotas
        boolean[] activosAntes = {
            equipo.getJugador(1).estaActivo(),
            equipo.getJugador(2).estaActivo(),
            equipo.getJugador(3).estaActivo()
        };

        // Ejecutar los ataques
        for (int ataque = 0; ataque < cantAtaques; ataque++) {
            for (int c = 0; c < 3; c++) {
                Carta cartaJ = cartasEquipo[c];
                Carta cartaO = oponente.getCarta(c + 1);
                // La carta del equipo ataca a la del oponente
                cartaJ.atacar(cartaO);
                // La carta del oponente contraataca a la del equipo (si puede)
                cartaO.atacar(cartaJ);
            }
        }

        // Verificar jugadores recién derrotados y reducir intensidad si aplica
        for (int i = 1; i <= 3; i++) {
            if (activosAntes[i - 1] && !equipo.getJugador(i).estaActivo()) {
                oponente.reducirIntensidad();
                ui.mostrarMensaje("¡El Jugador " + i + " (" + equipo.getJugador(i).getNombre() +
                        ") ha sido derrotado! La intensidad del oponente se redujo a la mitad: " +
                        String.format("%.1f%%", oponente.getIntensidad() * 100));
            }
        }

        ui.mostrarMensaje("Combo ejecutado con " + cantAtaques + " ataque(s).");
        return true;
    }

    /**
     * Muestra el estado de los jugadores.
     * @param equipo El equipo.
     * @return Texto con los jugadores disponibles.
     */
    private String mostrarJugadoresDisponibles(Equipo equipo) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 3; i++) {
            Jugador j = equipo.getJugador(i);
            String estado = j.estaActivo() ? "Activo" : "Derrotado";
            sb.append(i).append(". ").append(j.getNombre()).append(" (").append(estado).append(")\n");
        }
        return sb.toString();
    }

    /**
     * Genera texto con las cartas disponibles de un jugador para selección en combo.
     * @param j El jugador.
     * @return Texto con las cartas del jugador y su vida.
     */
    private String mostrarCartasDisponibles(Jugador j) {
        return String.format(
                "1. AIRE    Vida: %.0f%%\n" +
                "2. TIERRA  Vida: %.0f%%\n" +
                "3. AGUA    Vida: %.0f%%",
                Math.max(j.getCartaAire().getVida() * 100, 0),
                Math.max(j.getCartaTierra().getVida() * 100, 0),
                Math.max(j.getCartaAgua().getVida() * 100, 0));
    }

    /**
     * Muestra los atributosde la partida al finalizar.
     * @param equipo El equipo que jugó.
     * @param oponentes Los tres oponentes de la partida.
     */
    private void mostrarResumenFinal(Equipo equipo, Oponente[] oponentes) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n===== RESUMEN FINAL DE LA PARTIDA =====\n");
        sb.append("Equipo: ").append(equipo.getNombre()).append("\n");
        sb.append("Oponentes derrotados: ").append(equipo.getOponentesDerrotados()).append("/3\n");
        sb.append("Total combos realizados: ").append(equipo.getTotalCombos()).append("\n\n");

        // Cartas del equipo
        sb.append("--- Cartas del Equipo ---\n");
        for (int i = 1; i <= 3; i++) {
            Jugador j = equipo.getJugador(i);
            sb.append("Jugador ").append(i).append(": ").append(j.getNombre()).append("\n");
            mostrarInfoCarta(sb, "  AIRE", j.getCartaAire());
            mostrarInfoCarta(sb, "  TIERRA", j.getCartaTierra());
            mostrarInfoCarta(sb, "  AGUA", j.getCartaAgua());
        }

        // Cartas de los oponentes
        sb.append("\n--- Cartas de los Oponentes ---\n");
        for (int i = 0; i < 3; i++) {
            Oponente op = oponentes[i];
            sb.append("Oponente ").append(op.getNumero()).append(":\n");
            mostrarInfoCarta(sb, "  Carta 1 (" + op.getCarta(1).getTipo() + ")", op.getCarta(1));
            mostrarInfoCarta(sb, "  Carta 2 (" + op.getCarta(2).getTipo() + ")", op.getCarta(2));
            mostrarInfoCarta(sb, "  Carta 3 (" + op.getCarta(3).getTipo() + ")", op.getCarta(3));
        }
        ui.mostrarMensaje(sb.toString());
    }

    /**
     * Muestra la información de las cartas.
     * @param sb El StringBuilder donde se agrega la información.
     * @param etiqueta La etiqueta descriptiva de la carta.
     * @param carta La carta de la cual mostrar información.
     */
    private void mostrarInfoCarta(StringBuilder sb, String etiqueta, Carta carta) {
        sb.append(String.format("%s | Vida: %.0f%% | Ataque: %.2f | Defensa: %.2f\n",
                etiqueta,
                Math.max(carta.getVida() * 100, 0),
                carta.getAtaque(),
                carta.getDefensa()));
    }
}
