package pl.edu.agh.kis.pg;

import static pl.edu.agh.kis.pg.tiny_gp.*;


public class Printer {
    int print_indiv( char []buffer, int buffercounter ) {
        int a1=0, a2;

        if ( buffer[buffercounter] < FSET_START ) {
            if ( buffer[buffercounter] < varnumber ) {
                System.out.print("X" + (buffer[buffercounter] + 1) + " ");
                expressionToExcel += "X" + (buffer[buffercounter] + 1) + " ";
            }
            else {
                System.out.print(x[buffer[buffercounter]]);
                expressionToExcel += x[buffer[buffercounter]];
            }
            return( ++buffercounter );
        }
        switch(buffer[buffercounter]) {
            case ADD: System.out.print( "(");
                expressionToExcel += "(";
                a1=print_indiv( buffer, ++buffercounter );
                System.out.print( " + ");
                expressionToExcel += " + ";
                break;
            case SUB: System.out.print( "(");
                expressionToExcel += "(";
                a1=print_indiv( buffer, ++buffercounter );
                System.out.print( " - ");
                expressionToExcel += " - ";
                break;
            case MUL: System.out.print( "(");
                expressionToExcel += "(";
                a1=print_indiv( buffer, ++buffercounter );
                System.out.print( " * ");
                expressionToExcel += " * ";
                break;
            case DIV: System.out.print( "(");
                expressionToExcel += "(";
                a1=print_indiv( buffer, ++buffercounter );
                System.out.print( " / ");
                expressionToExcel += " / ";
                break;
            case SIN:
                System.out.print( "(");
                expressionToExcel += "(";
                System.out.print( "sin(");
                expressionToExcel += "sin(";
                a1=print_indiv( buffer, ++buffercounter );
                System.out.print( ")");
                expressionToExcel += ")";
                break;
            case COS:
                System.out.print( "(");
                expressionToExcel += "(";
                System.out.print( "cos(");
                expressionToExcel += "cos(";
                a1=print_indiv( buffer, ++buffercounter );
                System.out.print( ")");
                expressionToExcel += ")";
                break;
        }
        a2=print_indiv( buffer, a1 );
        System.out.print( ")");
        expressionToExcel += ")";
        return( a2);
    }

    void print_parms() {
        System.out.print("-- TINY GP (Java version) --\n");
        System.out.print("SEED="+seed+"\nMAX_LEN="+MAX_LEN+
                "\nPOPSIZE="+POPSIZE+"\nDEPTH="+DEPTH+
                "\nCROSSOVER_PROB="+CROSSOVER_PROB+
                "\nPMUT_PER_NODE="+PMUT_PER_NODE+
                "\nMIN_RANDOM="+minrandom+
                "\nMAX_RANDOM="+maxrandom+
                "\nGENERATIONS="+GENERATIONS+
                "\nTSIZE="+TSIZE+
                "\n----------------------------------\n");
    }

}
