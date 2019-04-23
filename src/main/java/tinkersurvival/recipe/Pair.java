package tinkersurvival.recipe;

// javafx Pair class
class Pair<T, T1> {
    Pair(T a, T1 b) {
        this.a = a;
        this.b = b;
    }

    private T a;
    private T1 b;

    T getKey(){
        return this.a;
    }

    T1 getValue(){
        return this.b;
    }
}
