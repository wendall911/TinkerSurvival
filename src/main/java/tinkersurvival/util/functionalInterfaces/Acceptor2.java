package tinkersurvival.util.functionalInterfaces;

@FunctionalInterface
public interface Acceptor2<T1, T2> {
    void accept(T1 t1, T2 t2);
}
