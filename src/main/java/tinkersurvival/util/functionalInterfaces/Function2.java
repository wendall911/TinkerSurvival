package tinkersurvival.util.functionalInterfaces;

@FunctionalInterface
public interface Function2<T1, T2, T3> {
    T3 invoke(T1 t1, T2 t2);
}
