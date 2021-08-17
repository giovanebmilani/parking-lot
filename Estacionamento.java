/**
 * Avaliação 4 - Fundamentos de programação PUCRS
 * @author Giovane Bianchi Milani
 */

import java.util.Scanner;


public class Estacionamento {

    // Cria um mapa de estacionamento em um atributo de classe
    private static boolean[][] mapa = criarMapa();
    private static boolean rodando = true;
    

    public static boolean[][] criarMapa () {
        /**
         * Gera um mapa de estacionamento, com 10 linhas (A-J),
         * 7 delas possuem 8 vagas enquanto as restantes possuem 11.
         * Torna 70% das vagas ocupadas aleatoriamente.
         * true se a vaga esta livre e false se a vaga esta ocupada
         * ==================
         * @return Jagged array
         */
        boolean[][] map = new boolean[10][];
        // Atribui para cada fileira do mapa a qtd de vagas
        int qtd_vagas;
        for (int linha=0; linha<map.length; linha++) {
            // Se a linha for menor que 7, a qtd de vagas será 8, senão será 11
            qtd_vagas  = (linha < 7) ? 8 : 11;
            map[linha] = new boolean[qtd_vagas];

            // 'Preenche' todas as posições com true, ou seja, vaga livre
            for (int coluna=0; coluna<map[linha].length; coluna++) {
                map[linha][coluna] = true;
            }

            // Gera números aleatórios para posições até que cerca 
            // de 70% das vagas na linha estejam ocupadas
            int colunaAleatoria = 0;
            int cont = 0;
            while (cont < map[linha].length * 70 / 100) {
                colunaAleatoria = (int)(Math.random() * map[linha].length);
                if (map[linha][colunaAleatoria]) {
                    map[linha][colunaAleatoria] = false;
                    cont++;
                }
            }
        }
        return map;
    }


    public static void desenharMapa () {
        /**
         * Imprime na tela o mapa do estacionamento
         * ==================
         * @return void
         */
        String[] fileiras = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

        System.out.println("\n=========== ESTACIONAMENTO ===========");
        System.out.println("\t▄ - Vaga ocupada\n");

        System.out.println("______1___2___3___4___5___6___7___8___9___10___11_");

        // Passando por todas as linhas
        for (int linha=0; linha<mapa.length; linha++) {
            // Impriminda numeração fileira
            System.out.print("  "+fileiras[linha]+" ");
            // Imprimindo vagas
            System.out.print("|");
            for (int coluna=0; coluna<mapa[linha].length; coluna++) {
                boolean vaga = mapa[linha][coluna];
                if (vaga) {
                    System.out.print("   |"); // vaga livre
                }
                else {
                    System.out.print(" ▄ |"); // vaga ocupada
                }   
            }

            // Nova linha
            System.out.println();
            // Imprime o espaço do código da fileira
            System.out.print("____");

            // Imprime segunda linha para a vaga 
            for (int i = 0; i<mapa[linha].length+1; i++) {
                System.out.print("|");
                if (i < mapa[linha].length) {
                    System.out.print("___");
                }
            }

            // Imprime rua
            if (linha % 2 == 0) {
                System.out.println("\n");
                if (linha < 6) {
                    System.out.println("    -    -    -    -    -    -    -   -");
                    System.out.println("_____________________________________");
                }
                else {
                    System.out.println("    -    -    -    -    -    -    -   -   -   -   -");
                    System.out.println("_________________________________________________");
                }                       
            }
            else {
                System.out.println();
            }         
        } // for linha
    } // func    


    public static void ocuparVaga (String codigo_vaga) {
        /**
         * Ocupa a vaga especificada caso esteja disponível,
         * caso contrário, exibi uma mensagem para o usuário
         * ==================
         * @param codigo_vaga: código da vaga que será ocupada
         * @return void
         */
        String fileiras = "ABCDEFGHIJ";

        // Lendo o código
        String[] codigo = codigo_vaga.split(""); // Divide todos caracteres do código em um array
        int linha = fileiras.indexOf(codigo[0].toUpperCase()); // Índice na string fileiras é correspondente a linha do mapa
        // Identifica se a coluna é de um ou dois dígitos e converte para int da forma correta
        int coluna = (codigo.length == 2) ? Integer.parseInt(codigo[1])-1 : Integer.parseInt(codigo[1] + codigo[2])-1;
        
        // Verifica se a vaga está livre, nessa caso, editaria ela para ocupada
        if (mapa[linha][coluna]) {
            System.out.printf("\nVaga %s foi ocupada com sucesso!\n", codigo_vaga);
            mapa[linha][coluna] = false;
        }
        // Se não estiver, imprime uma mensagem para o usuário
        else {
            System.out.printf("\nA vaga %s já está ocupada...\n", codigo_vaga);
        }
    }


    public static void liberarVaga (String codigo_vaga) {
        /**
         * Libera a vaga especificada
         * ==================
         * @param codigo_vaga: código da vaga que será ocupada
         * @return void
         */
        String fileiras = "ABCDEFGHIJ";

        // Lendo o código
        String[] test = codigo_vaga.split(""); // Divide todos caracteres do código em um array
        int linha = fileiras.indexOf(test[0].toUpperCase()); // Índice na string fileiras é correspondente a linha do mapa
        // Identifica se a coluna é de um ou dois dígitos e converte para int da forma correta
        int coluna = (test.length == 2) ? Integer.parseInt(test[1])-1 : Integer.parseInt(test[1] + test[2])-1;
        
        // Verifica se a vaga está livre, nesse caso retorna uma mensagem para o usuário
        if (mapa[linha][coluna]) {
            System.out.printf("\nA vaga %s já está livre...\n", codigo_vaga);
        }
        // Se estiver ocupada, torna a vaga livre
        else {
            System.out.printf("\nVaga %s foi liberada com sucesso!\n", codigo_vaga);
            mapa[linha][coluna] = true;        
        }
    }


    public static void vagaLivre () {
        /**
         * Procura pela primeira vaga livre, marca-a como ocupada
         * e imprime ela na tela
         * ==================
         * @return void
         */
        String[] fileiras = {"A","B","C","D","E","F","G","H","I","J"};
        String codigo;
        boolean vagaEncontrada = false;

        for (int linha = 0; linha < mapa.length; linha++) {
            for (int coluna = 0; coluna < mapa[linha].length; coluna++) {
                // Se a vaga estiver livre e nenhuma vaga tiver sido encontrada, torna a vaga ocupada
                if (mapa[linha][coluna] && !vagaEncontrada) {
                    mapa[linha][coluna] = false;
                    vagaEncontrada = true;
                    codigo = ""+fileiras[linha]+(coluna+1);
                    System.out.printf("\nVaga encontrada... %s foi marcada como ocupada...\n", codigo);                
                }
            }
            /**Se tiver passado por todas as vagas e nenhuma tiver sido encontrada
             retorna uma mensagem pro usuário*/
            if (linha == mapa.length-1 && !vagaEncontrada) {
                System.out.printf("\nNenhuma vaga encontrada... Estaciomento lotado...\n");
            }
        }
    }


    public static void vagaEspecial () {
        /**
         * Procura por vagas especiais, ou seja, adjacentes,
         * e marca elas como ocupada, imprimindo-as na tela
         * ==================
         * @return void
         */
        String[] fileiras = {"A","B","C","D","E","F","G","H","I","J"};
        String codigo;
        String codigo2;
        boolean vagaEncontrada = false;

        for (int linha = 0; linha < mapa.length; linha++) {
            // Começando na segunda coluna
            for (int coluna = 1; coluna < mapa[linha].length; coluna++) {
                // Se a vaga estiver livre e nenhuma ainda não tiver sido encontrada
                if (mapa[linha][coluna] && !vagaEncontrada) {
                    // verifica se a vaga anterior também está livre
                    if (mapa[linha][coluna-1]) {
                        // Definindo as vagas como ocupadas
                        mapa[linha][coluna] = false;
                        mapa[linha][coluna-1] = false;
                        vagaEncontrada = true;
                        // Definindo os códigos das vagas
                        codigo = ""+fileiras[linha]+(coluna+1);
                        codigo2 = ""+fileiras[linha]+(coluna);
                        // Mensagem para o usuário
                        System.out.printf("\nVaga especial encontrada...\n%s e %s foram marcadas como ocupadas...\n", codigo2, codigo);
                    }
                }
            }
            /**Se tiver passado por todas as vagas e nenhuma tiver sido encontrada
             retorna uma mensagem pro usuário*/
            if (linha == mapa.length-1 && !vagaEncontrada) {
                System.out.printf("\nNenhuma vaga especial encontrada...\n");
            }
        }
    }


    public static void estatisticas () {
        /**
         * Imprime na tela o percentual de vagas ocupadas e
         * de vagas livres
         * ==================
         * @return void
         */
        int total_vagas;
        int vagas_livres=0;
        int vagas_ocupadas=0;
        
        // Para cada vaga
        for (int linha = 0; linha < mapa.length; linha++) {
            for (int coluna = 0; coluna < mapa[linha].length; coluna++) {
                // Verifica se está livre ou não
                if (mapa[linha][coluna]) {
                    vagas_livres++;
                }
                else {
                    vagas_ocupadas++;
                }
            }
        }
        total_vagas = vagas_livres + vagas_ocupadas;

        //Imprime as estastísticas na tela
        System.out.println("\t\t=== ESTATÍSTICAS ===");
        System.out.printf("Vagas livres: %d\tPercentual de vagas livres: %.2f%%", vagas_livres, 100.0*vagas_livres/total_vagas);
        System.out.printf("\nVagas ocupadas: %d\tPercentual de vagas ocupadas: %.2f%%\n", vagas_ocupadas, 100.0*vagas_ocupadas/total_vagas);
    }

    
    public static void imprimirMenuPrincipal () {
        /**
         * Imprime o menu principal
         * ==================
         * @return void
         */
        System.out.println("\n===== GERENCIADOR DE ESTACIONAMENTO =====");
        System.out.println("\t === Menu principal ===\n");
        System.out.println("Escolha oque deseja fazer:");
        System.out.println("1. Visualizar mapa do estacionamento");
        System.out.println("2. Ocupar uma vaga");
        System.out.println("3. Liberar uma vaga");
        System.out.println("4. Encontrar a primeira vaga livre");
        System.out.println("5. Encontrar vaga especial");
        System.out.println("6. Exibir estatísticas");
        System.out.println("7. Sair do programa");
        System.out.println("Digite a sua escolha:");

    }

    public static void sair () {
        /**
         * Altera o atributo de classe 'rodando' para false
         * encerrando assim o loop do programa
         * ==========
         * @return void
         */
        rodando = false;
        System.out.println("Saindo do sistema...");
    }

    public static void main (String[] args) {
        /**
         * Contém o loop de funcionamento do programa
         */

        Scanner in = new Scanner(System.in);
        int opcao;
        String codigo;

        // Loop de funcionamento
        while (rodando) {

            imprimirMenuPrincipal();

            // Perguntando ao usuário
            do {
                System.out.print("\n> ");
                opcao = in.nextInt();
            } while (opcao < 1 || opcao > 7);

            // Chamando a função solicitada
            switch (opcao) {
                case 1:
                    desenharMapa();
                    break;
                case 2:
                    System.out.println("Digite o código da vaga que deseja ocupar: ");
                    System.out.print("> ");
                    codigo = in.next();
                    ocuparVaga(codigo);
                    break;
                case 3:
                     System.out.println("Digite o código da vaga que deseja liberar: ");
                    System.out.print("> ");
                    codigo = in.next();
                    liberarVaga(codigo);
                    break;
                case 4:
                    vagaLivre();
                    break;
                case 5:
                    vagaEspecial();
                    break;
                case 6:
                    estatisticas();
                    break;
                case 7:
                    in.close();
                    sair();
                    break;
            } // switch case
        } // loop
    }      
}
