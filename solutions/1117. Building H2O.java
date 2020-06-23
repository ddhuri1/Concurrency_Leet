class H2O 
{
    private int oxygenCount, hydrogenCount;
    
    public H2O()
    {
        oxygenCount = 0;
        hydrogenCount = 0;
    }

    public synchronized void hydrogen(Runnable releaseHydrogen) throws InterruptedException 
    {
		
        while(hydrogenCount > 2 * oxygenCount) 
            wait();
        if(hydrogenCount <= 2 * oxygenCount)
        {
            releaseHydrogen.run();
            hydrogenCount++;
            notifyAll();
        }
    }

    // releaseOxygen.run() outputs "O". Do not change or remove this line.
    public synchronized void oxygen(Runnable releaseOxygen) throws InterruptedException 
    {
        while(hydrogenCount < 2 * oxygenCount) 
            wait();
        if(2 * oxygenCount <= hydrogenCount)
        {
		    releaseOxygen.run();
            oxygenCount++;
            notifyAll();
        }
    }
}