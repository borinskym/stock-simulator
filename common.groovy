import javaposse.jobdsl.dsl.helpers.*

class common {

    static void readServiceConfiguration(String fileName){
        def text = new File(fileName).getText('UTF-8')
        def yaml = new Yaml()
        def load = yaml.load(text)
        print(load)
    }
}
