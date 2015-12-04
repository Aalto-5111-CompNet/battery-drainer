package fi.aalto.sci.cn2.drainer;

public class Dispatcher {
    private boolean state = false;

    public void start (){
        this.state = true;


    }

    public void stop (){
        state = false;


    }
}
