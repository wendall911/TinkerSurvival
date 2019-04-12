package tinkersurvival.util.functionalInterfaces;

@FunctionalInterface
public interface Function1<T1, T2> {
    T2 invoke(T1 t1, T2 t2);
}
