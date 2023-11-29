package pl.edu.agh.kis.pg;

import java.util.*;
import java.io.*;

import static pl.edu.agh.kis.pg.TinyGp.*;

public class Statistics {

    void stats(TinyGp TinyGp,Printer printer, double [] fitness, char [][] pop, int gen ) {
        int i, best = rd.nextInt(POPSIZE);
        int node_count = 0;
        fbestpop = fitness[best];
        favgpop = 0.0;

        for ( i = 0; i < POPSIZE; i ++ ) {
            node_count +=  TinyGp.traverse( pop[i], 0 );
            favgpop += fitness[i];
            if ( fitness[i] > fbestpop ) {
                best = i;
                fbestpop = fitness[i];
            }
        }
        avg_len = (double) node_count / POPSIZE;
        favgpop /= POPSIZE;
        System.out.print("Generation="+gen+" Avg Fitness="+(-favgpop)+
                " Best Fitness="+(-fbestpop)+" Avg Size="+avg_len+
                "\nBest Individual: ");
        File stats = new File("stats.txt");
        try {
            FileWriter f2 = new FileWriter(stats, true);
            f2.write(gen + "\t" + (-favgpop) + "\t"+ (-fbestpop) + "\n");
            f2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        printer.printIndiv( pop[best], 0 );

        //TODO: otworz plik, zapisz tam skonwertowane równanie:
        File fold = new File("to_excel.txt");
        fold.delete();
        expressionToExcel = "=" + expressionToExcel;
        expressionToExcel = expressionToExcel.replace(".", ",");
        expressionToExcel = expressionToExcel.replace("- -", "+");
        expressionToExcel = expressionToExcel.replace("X1", "A1");
        expressionToExcel = expressionToExcel.replace("X2", "B1");

        File fnew = new File("to_excel.txt");
        try {
            FileWriter f2 = new FileWriter(fnew, false);
            f2.write(expressionToExcel);
            f2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        expressionToExcel = "";

        System.out.print( "\n");
        System.out.flush();
    }
}
