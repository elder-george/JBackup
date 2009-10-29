/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package backupagent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Yuri Korchyomkin
 */
class Monitor implements Runnable{

    final BackupService service;
    final String monitoredDirectory;
    final Thread monitorThread;
    final Semaphore shouldStop;

    public Monitor(String monitoredDirectory, BackupService service) {
        this.monitoredDirectory = monitoredDirectory;
        this.service = service;
        this.monitorThread = new Thread(this, "monitor");
        this.shouldStop = new Semaphore(1);
    }

    public void start(){
        monitorThread.start();
    }

    public void run(){
        String[] monitoredFiles = service.getMonitoredFilesList();
        try{
            while(!shouldStop.tryAcquire(0, TimeUnit.MILLISECONDS)){

            }
        }
        catch(InterruptedException ex){
            System.err.println("Monitor thread was interrupted");
        }
    }

    public void stop(){
        try
        {
            shouldStop.release();
            monitorThread.join();
        }
        catch(InterruptedException ex){
            System.err.println("Monitor thread was interrupted");
        }
    }
}
