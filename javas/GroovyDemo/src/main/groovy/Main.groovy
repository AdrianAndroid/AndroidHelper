import bean.Person
import closure.Android
import closure.View

class Main {
    void eat(String food) {
        println("我根本不会吃，不要喂我${food}")
    }

    def cc = {
        name = "hanmeimei"
        age = 26
        eat("油条")
    }


    static void main(String... args) {
        Main main = new Main()
        Person person = new Person(name: "lilei", age: 14)
        println person.toString()

        main.cc.delegate = person
//        main.cc.setResolveStrategy(Closure.DELEGATE_FIRST)
        main.cc.setResolveStrategy(Closure.OWNER_FIRST)
        main.cc.call()
        println person.toString()


        View view = new View()
        view.setOnClickListener { View v ->
            println v.toString()
        }
        Android bean = new Android()
        def android = {
            compileSdkVersion 25
            buildToolsVersion "25.0.2"
            defaultConfig {
                minSdkVersion 15
                targetSdkVersion 25
                versionCode 1
                versionName "1.0"
            }
        }
        android.delegate = bean
        android.call()
        println bean.toString()

    }
}
