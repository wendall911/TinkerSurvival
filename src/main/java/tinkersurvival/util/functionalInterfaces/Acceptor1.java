package tinkersurvival.util.functionalInterfaces;

// recreate c# Action generics to use for lambdas


import javax.swing.*;


@FunctionalInterface
public interface Acceptor1<T1> {
    void accept(T1 t1);
}


