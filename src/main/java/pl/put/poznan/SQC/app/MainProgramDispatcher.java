/*
                                                                      WOW
                                                                 ▄              ▄
                                                                ▌▒█           ▄▀▒▌
                                                                ▌▒▒█        ▄▀▒▒▒▐
                                                               ▐▄█▒▒▀▀▀▀▄▄▄▀▒▒▒▒▒▐
                                                             ▄▄▀▒▒▒▒▒▒▒▒▒▒▒█▒▒▄█▒▐
                                                           ▄▀▒▒▒░░░▒▒▒░░░▒▒▒▀██▀▒▌
                                                          ▐▒▒▒▄▄▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▀▄▒▌
                                                          ▌░░▌█▀▒▒▒▒▒▄▀█▄▒▒▒▒▒▒▒█▒▐
                                                         ▐░░░▒▒▒▒▒▒▒▒▌██▀▒▒░░░▒▒▒▀▄▌
                                                         ▌░▒▒▒▒▒▒▒▒▒▒▒▒▒▒░░░░░░▒▒▒▒▌
                                                        ▌▒▒▒▄██▄▒▒▒▒▒▒▒▒░░░░░░░░▒▒▒▐
                                                        ▐▒▒▐▄█▄█▌▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▒▒▒▌   <- runs code better | less buges
                                                        ▐▒▒▐▀▐▀▒▒▒▒▒▒▒▒▒▒▒▒▒░▒░▒░▒▒▐
                                                         ▌▒▒▀▄▄▄▄▄▄▒▒▒▒▒▒▒▒░▒░▒░▒▒▒▌
                                                         ▐▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒▒░▒░▒▒▄▒▒▐
                                                          ▀▄▒▒▒▒▒▒▒▒▒▒▒▒▒░▒░▒▄▒▒▒▒▌
                                                            ▀▄▒▒▒▒▒▒▒▒▒▒▄▄▄▀▒▒▒▒▄▀
                                                              ▀▄▄▄▄▄▄▀▀▀▒▒▒▒▒▄▄▀
                                                                 ▀▀▀▀▀▀▀▀▀▀▀▀
                                                                MUCH MODULARNOŚĆ


                                            ███    ███  █████  ███    ██ ██ ███████ ███████ ███████ ████████
                                            ████  ████ ██   ██ ████   ██ ██ ██      ██      ██         ██
                                            ██ ████ ██ ███████ ██ ██  ██ ██ █████   █████   ███████    ██
                                            ██  ██  ██ ██   ██ ██  ██ ██ ██ ██      ██           ██    ██
                                            ██      ██ ██   ██ ██   ████ ██ ██      ███████ ███████    ██

                                                        TAK WIEM ŻE JAVA FX NIE JEST MODULARNA
                                               I WIEM ŻE LOGI SĄ CZERWONE JAK CEGŁA, ROZGRZANE JAK PIEC

                                                      I NIE, NIE CHCE MI SIĘ PRZECHODZIĆ NA GLUONA,
                                                 I TAK WIEM, MOŻNA BY BYŁO TO WSZYSTKO WRZUCIĆ DO BEANA 


 */
package pl.put.poznan.SQC.app;

import javafx.application.Application;
import pl.put.poznan.SQC.app.API.SpringBootApp;
import pl.put.poznan.SQC.app.GUI.JavaFxApp;

/**
* Klasa główna programu
 */
public class MainProgramDispatcher {
    // hehe threading go wziuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuum
    /**
     * Uruchamia program
     * @param args parametry z linii poleceń
     */
    public static void main(String[] args){


        Thread springThread = new Thread(() -> SpringBootApp.main(args));
        springThread.start();

        Application.launch(JavaFxApp.class, args);
    }



}


