package pl.edu.agh.kis.pg;

import static pl.edu.agh.kis.pg.tiny_gp.*;

public class Modifier {
    char [] crossover(tiny_gp tiny_gp, char []parent1, char [] parent2 ) {
        int xo1start, xo1end, xo2start, xo2end;
        char [] offspring;
        int len1 = tiny_gp.traverse( parent1, 0 );
        int len2 = tiny_gp.traverse( parent2, 0 );
        int lenoff;

        xo1start =  rd.nextInt(len1);
        xo1end = tiny_gp.traverse( parent1, xo1start );

        xo2start =  rd.nextInt(len2);
        xo2end = tiny_gp.traverse( parent2, xo2start );

        lenoff = xo1start + (xo2end - xo2start) + (len1-xo1end);

        offspring = new char[lenoff];

        System.arraycopy( parent1, 0, offspring, 0, xo1start );
        System.arraycopy( parent2, xo2start, offspring, xo1start,
                (xo2end - xo2start) );
        System.arraycopy( parent1, xo1end, offspring,
                xo1start + (xo2end - xo2start),
                (len1-xo1end) );

        return( offspring );
    }

    char [] mutation(tiny_gp tiny_gp, char [] parent, double pmut ) {
        int len = tiny_gp.traverse( parent, 0 ), i;
        int mutsite;
        char [] parentcopy = new char [len];

        System.arraycopy( parent, 0, parentcopy, 0, len );
        for (i = 0; i < len; i ++ ) {
            if ( rd.nextDouble() < pmut ) {
                mutsite =  i;
                if ( parentcopy[mutsite] < FSET_START )
                    parentcopy[mutsite] = (char) rd.nextInt(varnumber+randomnumber);
                else
                    switch(parentcopy[mutsite]) {
                        case ADD:
                        case SUB:
                        case MUL:
                        case DIV:
                        case SIN:
                        case COS:
                            parentcopy[mutsite] =
                                    (char) (rd.nextInt(FSET_END - FSET_START + 1)
                                            + FSET_START);
                    }
            }
        }
        return( parentcopy );
    }
}
