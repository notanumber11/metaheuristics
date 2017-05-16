package Utilities;

/**
 * Created by Snooze on 06/12/2016.
 */
public class Timer {

    public long tstart =0, tend =0;

    public Timer(){}

    public Timer start(){
        tstart = System.currentTimeMillis();
        return this;
    }

    public Timer end(){
        tend = System.currentTimeMillis();
        print();
        return this;
    }

    @Override
    public String toString() {
        float elapsedTime = (float)(tend - tstart)/1000f;
        return "Time (s): "+elapsedTime;
    }

    public void print(){
        System.out.println(this.toString());
    }
}