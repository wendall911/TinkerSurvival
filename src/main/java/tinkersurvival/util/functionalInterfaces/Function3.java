package tinkersurvival.util.functionalInterfaces;

@FunctionalInterface
public interface Function3<T1, T2, T3, T4> {
    T4 invoke(T1 t1, T2 t2, T3 t3);
}
