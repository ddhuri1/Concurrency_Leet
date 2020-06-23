class ZeroEvenOdd 
{
    private int n;
    Semaphore zeroS, oddS, evenS;
    private int i;
    
    public ZeroEvenOdd(int n) 
    {
        this.n = n;
        this.i = 0;
        zeroS = new Semaphore(0);
        oddS = new Semaphore(0);
        evenS = new Semaphore(0);
        zeroS.release();
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException
    {
        while(i <=n) 
        {
            zeroS.acquire();
            i++;
            if(i>n)
            {
                evenS.release();
                oddS.release();
                break;
            }
            printNumber.accept(0);
            if(i%2==0) 
                evenS.release();
            else 
                oddS.release();
        }
    }

    public void even(IntConsumer printNumber) throws InterruptedException 
    {
        while(i<=n)
        {
            evenS.acquire();
            if(i>n)
                break;
            printNumber.accept(i);
            zeroS.release();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException 
    {
        while(i<=n) 
        {
            oddS.acquire();
            if(i>n)
                break;
            printNumber.accept(i);
            zeroS.release();
        }
    }
}