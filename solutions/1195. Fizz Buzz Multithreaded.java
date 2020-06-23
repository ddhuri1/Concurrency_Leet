class FizzBuzz 
{
    private int n;
    private Semaphore fizz = new Semaphore(0);
    private Semaphore buzz = new Semaphore(0);
    private Semaphore fizzbuzz = new Semaphore(0);
    private Semaphore numSem = new Semaphore(1);
    private AtomicInteger number = new AtomicInteger(1);

    public FizzBuzz(int n)
    {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException 
    {
        
        for(int i = 1; i <= n; i++)
        {
            fizz.acquire();
            if(i % 3 == 0 && i % 5 != 0 )
                printFizz.run();
            buzz.release();
        }
       
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException
    {
        
        for(int i = 1; i <= n; i++)
        {
            buzz.acquire();            
            if(i % 3 != 0 && i % 5 == 0)
                printBuzz.run();
            fizzbuzz.release();
        }
        
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException 
    {
        
        for(int i = 1; i <= n; i++)
        {
            fizzbuzz.acquire();
            if(i % 3 == 0 && i % 5 == 0)
               printFizzBuzz.run();                 
            numSem.release();   
        }        
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number(IntConsumer printNumber) throws InterruptedException 
    {
        for(int i = 1; i <= n; i++)
        {
            numSem.acquire(); 
            if(i % 3 != 0 && i % 5 != 0)
                printNumber.accept(i);                 
            fizz.release();            
        }
    }
}