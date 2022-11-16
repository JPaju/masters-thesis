import java.util.*;
import java.util.function.*;

class CovariantMutableArray {
    void example () {
        String[] a = new String[1];	
        Object[] b = a; // String is a subtype of Object, so this is legal
        b[0] = 1; // Runtime exception since cannot add Integer to String
    }
}

class UseSiteVariance {
    interface Supertype {}
    interface Subtype extends Supertype {}

    void invariant(List<Supertype> list) {
        /* Get and set list values */
    }
    void covariant(List<? extends Supertype> list) {
        /* Only get list values */
    }
    void contravariant(List<? super Subtype> list) {
        /* Only set list values */
    }
}
