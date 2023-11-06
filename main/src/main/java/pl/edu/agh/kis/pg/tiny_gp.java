package pl.edu.agh.kis.pg;

/**
 * Przykładowy kod do zajęć laboratoryjnych 2, 3, 4 z przedmiotu: Programowanie zaawansowane 1
 * @author Paweł Skrzyński
 */

import java.util.*;
import java.io.*;


public class tiny_gp {
    double [] fitness;
    char [][] pop;
    static Random rd = new Random();
    static final int
            ADD = 110,
            SUB = 111,
            MUL = 112,
            DIV = 113,
            SIN = 114,
            COS = 115,
            FSET_START = ADD,
            FSET_END = COS;
    static double [] x = new double[FSET_START];
    static double minrandom, maxrandom;
    static char [] program;
    static int PC;
    static int varnumber, fitnesscases, randomnumber;
    static double fbestpop = 0.0, favgpop = 0.0;
    static long seed;
    static double avg_len;
    static final int
            MAX_LEN = 10000,
            POPSIZE = 100000,
            DEPTH   = 5,
            GENERATIONS = 100,
            TSIZE = 2;
    public static final double
            PMUT_PER_NODE  = 0.05,
            CROSSOVER_PROB = 0.9;
    static double [][] targets;

    static String expressionToExcel = "";
    Fitness fit = new Fitness();
    Statistics statistics = new Statistics();
    Tournament tournament = new Tournament();
    Modifier modifier = new Modifier();
    Printer printer = new Printer();

    double run() { /* Interpreter */
        char primitive = program[PC++];
        if ( primitive < FSET_START )
            return(x[primitive]);
        switch ( primitive ) {
            case ADD : return( run() + run() );
            case SUB : return( run() - run() );
            case MUL : return( run() * run() );
            case DIV : {
                double num = run(), den = run();
                if ( Math.abs( den ) <= 0.001 )
                    return( num );
                else
                    return( num / den );
            }
            case SIN : return( Math.sin(run()) );
            case COS : return( Math.cos(run()) );
        }
        return( 0.0 ); // should never get here
    }

    int traverse( char [] buffer, int buffercount ) {
        if ( buffer[buffercount] < FSET_START )
            return( ++buffercount );

        switch(buffer[buffercount]) {
            case ADD:
            case SUB:
            case MUL:
            case DIV:
            case SIN:
            case COS:
                return( traverse( buffer, traverse( buffer, ++buffercount ) ) );
        }
        return( 0 ); // should never get here
    }


    int grow( char [] buffer, int pos, int max, int depth ) {
        char prim = (char) rd.nextInt(2);
        int one_child;

        if ( pos >= max )
            return( -1 );

        if ( pos == 0 )
            prim = 1;

        if ( prim == 0 || depth == 0 ) {
            prim = (char) rd.nextInt(varnumber + randomnumber);
            buffer[pos] = prim;
            return(pos+1);
        }
        else  {
            prim = (char) (rd.nextInt(FSET_END - FSET_START + 1) + FSET_START);
            switch(prim) {
                case ADD:
                case SUB:
                case MUL:
                case DIV:
                case SIN:
                case COS:
                    buffer[pos] = prim;
                    one_child = grow( buffer, pos+1, max,depth-1);
                    if ( one_child < 0 )
                        return( -1 );
                    return( grow( buffer, one_child, max,depth-1 ) );
            }
        }
        return( 0 ); // should never get here
    }


    static char [] buffer = new char[MAX_LEN];
    char [] create_random_indiv( int depth ) {
        char [] ind;
        int len;

        len = grow( buffer, 0, MAX_LEN, depth );

        while (len < 0 )
            len = grow( buffer, 0, MAX_LEN, depth );

        ind = new char[len];

        System.arraycopy(buffer, 0, ind, 0, len );
        return( ind );
    }

    char [][] create_random_pop(int n, int depth, double [] fitness ) {
        char [][]pop = new char[n][];
        int i;

        for ( i = 0; i < n; i ++ ) {
            pop[i] = create_random_indiv( depth );
            fitness[i] = fit.fitness_function(this, pop[i], varnumber);
        }
        return( pop );
    }



    public tiny_gp( String fname, long s ) {
        fitness =  new double[POPSIZE];
        seed = s;
        if ( seed >= 0 )
            rd.setSeed(seed);
        fit.setup_fitness(fname); // set parameters from first line of file
        for ( int i = 0; i < FSET_START; i ++ )
            x[i]= (maxrandom-minrandom)*rd.nextDouble()+minrandom;
        pop = create_random_pop(POPSIZE, DEPTH, fitness );
    }

    void evolve() {
        int gen = 0, indivs, offspring, parent1, parent2, parent;
        double newfit;
        char []newind;
        printer.print_parms();
        statistics.stats( this,printer, fitness, pop, 0 );
        for ( gen = 1; gen < GENERATIONS; gen ++ ) {
            if (  fbestpop > -1e-5 ) {
                System.out.print("PROBLEM SOLVED\n");
                System.exit( 0 );
            }
            for ( indivs = 0; indivs < POPSIZE; indivs ++ ) {
                if ( rd.nextDouble() < CROSSOVER_PROB  ) {
                    parent1 = tournament.tournament( fitness, TSIZE );
                    parent2 = tournament.tournament( fitness, TSIZE );
                    newind = modifier.crossover( this, pop[parent1],pop[parent2] );
                }
                else {
                    parent = tournament.tournament( fitness, TSIZE );
                    newind = modifier.mutation( this, pop[parent], PMUT_PER_NODE );
                }
                newfit = fit.fitness_function( this, newind, varnumber );
                offspring = tournament.negative_tournament( fitness, TSIZE );
                pop[offspring] = newind;
                fitness[offspring] = newfit;
            }
            statistics.stats(this, printer, fitness, pop, gen );
        }
        System.out.print("PROBLEM *NOT* SOLVED\n");
        System.exit( 1 );
    }

    public static void main(String[] args) {
        String fname = "problem.dat";
        long s = -1;

        if ( args.length == 2 ) {
            s = Integer.valueOf(args[0]).intValue();
            fname = args[1];
        }
        if ( args.length == 1 ) {
            fname = args[0];
        }

        tiny_gp gp = new tiny_gp(fname, s);
        gp.evolve();


    }
};
