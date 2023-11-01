package pl.edu.agh.kis.pg;

import java.util.*;
import java.io.*;

import static pl.edu.agh.kis.pg.tiny_gp.*;

public class Fitness{
    static int fitnesscases;
    static double [][] targets;

    double fitness_function(tiny_gp tiny_gp, char [] Prog, int varnumber) {
        int i = 0, len;
        double result, fit = 0.0;

        len = tiny_gp.traverse( Prog, 0 );
        for (i = 0; i < fitnesscases; i ++ ) {
            for (int j = 0; j < varnumber; j ++ )
                x[j] = targets[i][j];
            program = Prog;
            PC = 0;
            result = tiny_gp.run();
            fit += Math.abs( result - targets[i][varnumber]);
        }
        return(-fit );
    }

    //    Method sets parameters written in first line of file
    void setup_fitness(String fname) {
        try {
            int i,j;
            String line;

            BufferedReader in =
                    new BufferedReader(
                            new
                                    FileReader(fname));
            line = in.readLine();
            StringTokenizer tokens = new StringTokenizer(line);
            varnumber = Integer.parseInt(tokens.nextToken().trim());
            randomnumber = Integer.parseInt(tokens.nextToken().trim());
            minrandom =	Double.parseDouble(tokens.nextToken().trim());
            maxrandom =  Double.parseDouble(tokens.nextToken().trim());
            fitnesscases = Integer.parseInt(tokens.nextToken().trim());
            targets = new double[fitnesscases][varnumber+1];
            if (varnumber + randomnumber >= FSET_START )
                System.out.println("too many variables and constants");

            for (i = 0; i < fitnesscases; i ++ ) {
                line = in.readLine();
                tokens = new StringTokenizer(line);
                for (j = 0; j <= varnumber; j++) {
                    targets[i][j] = Double.parseDouble(tokens.nextToken().trim());
                }
            }
            in.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("ERROR: Please provide a data file");
            System.exit(0);
        }
        catch(Exception e ) {
            System.out.println("ERROR: Incorrect data format");
            System.exit(0);
        }
    }
}


