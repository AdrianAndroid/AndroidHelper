package bean

class Person {
    String name
    int age

    void eat(String food) {
        println("你喂的${food}真难吃")
    }


    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
