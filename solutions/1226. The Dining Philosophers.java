class DiningPhilosophers 
{
    int count = 5;
    ReentrantLock[] fork;
     
    public DiningPhilosophers() 
    { 
        fork = new ReentrantLock[count];
        for(int i = 0; i < this.count; i++) 
            fork[i] = new ReentrantLock();
    }

    // call the run() method of any runnable to execute its code
    public void wantsToEat(int philosopher,
                           Runnable pickLeftFork,
                           Runnable pickRightFork,
                           Runnable eat,
                           Runnable putLeftFork,
                           Runnable putRightFork) throws InterruptedException 
    {
        do
        {
            fork[philosopher].lock();
            if(fork[((philosopher + 1) % this.count)].tryLock())
            {
                pickLeftFork.run();
                pickRightFork.run();
                eat.run();
                putLeftFork.run();
                putRightFork.run();
                fork[((philosopher + 1) % this.count)].unlock();
                fork[philosopher].unlock();
                break;
            }
            else
            {   
                fork[philosopher].unlock();
                Thread.sleep(1);
            }            
        }while(true);
        
    }
}