public class VolitileByteCOde {
     int foo = 10;
    volatile  int a = 1;

    public  int increment() {
        a = 2;
        a = foo + 10;
        int b = a + 20;
        return b;
    }

    public static void main(String[] ar) {
        new VolitileByteCOde().increment();
    }
}
