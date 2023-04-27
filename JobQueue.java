import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class JobQueue {
    private int numWorkers;
    private int[] jobs;

    private int[] assignedWorker;
    private long[] startTime;

    private FastScanner in;
    private PrintWriter out;

    public static void main(String[] args) throws IOException {
        new JobQueue().solve();
    }

    private void readData() throws IOException {
        numWorkers = in.nextInt();
        int m = in.nextInt();
        jobs = new int[m];
        for (int i = 0; i < m; ++i) {
            jobs[i] = in.nextInt();
        }
    }

    private void writeResponse() {
        for (int i = 0; i < jobs.length; ++i) {
            out.println(assignedWorker[i] + " " + startTime[i]);
        }
    }
    /////////////////////////////////////////////////////////
    class ProcessorComparator implements Comparator<thread>{
    	@Override
    	public int compare (thread o1, thread o2) {
    		if(o1.next_free_time == o2.next_free_time){
                if(o1.id < o2.id)
                    return -1;
                else if(o1.id > o2.id)
                    return 1;
                else
                    return 0;
            }
            else if(o1.next_free_time < o2.next_free_time)
                return -1;
            else if(o1.next_free_time > o2.next_free_time)
                return 1;
            else
                return 0;
    	}
    }
    /////////////////////////////////////////////////////////
    
   /////////////////////////////PRIORITY QUEUE///////////////////////////////////
    private void assignJobs() {
        // TODO: replace this code with a faster algorithm.
        assignedWorker = new int[jobs.length]; //array
        startTime = new long[jobs.length]; //array
        ProcessorComparator comparator = new ProcessorComparator();
        //long[] nextFreeTime = new long[numWorkers];
        Queue<thread> threadWorkforce = new PriorityQueue<thread>(11, comparator); //array of the threads
        
        for (int i = 0; i < numWorkers; i++) {
        	threadWorkforce.add(new thread(i)); //adds threads (workers) to the work force.
        }

        for (int i = 0; i < jobs.length; i++) {
        	int duration = jobs[i]; //duration is the length of the current job
        	thread bestWorker = threadWorkforce.poll(); //pulls the worker (thread) with the hightest priority and puts in a new thread called bestWorker
        	assignedWorker[i] = bestWorker.id; //adds the id of the best worker to the current assigned worker
        	startTime[i] = bestWorker.next_free_time; //adds the next free time of the best worker to be the current start time
        	bestWorker.next_free_time += duration; //makes sure the duration of the current job is added to bestWorker's next free time so it complete it
        	threadWorkforce.add(bestWorker); //adds the bestWorker to the workforce
        }
        /*
        for (int i = 0; i < jobs.length; i++) {
            int duration = jobs[i];
            int bestWorker = 0;
            for (int j = 0; j < numWorkers; ++j) {
                if (nextFreeTime[j] < nextFreeTime[bestWorker])
                    bestWorker = j;
            }
            assignedWorker[i] = bestWorker;
            startTime[i] = nextFreeTime[bestWorker];
            nextFreeTime[bestWorker] += duration;
        }
        */
    }

    ///////////////////////////////////////////////////////////////
    
    public void solve() throws IOException {
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        readData();
        assignJobs();
        writeResponse();
        out.close();
    }

    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
   ///////////////////////////////////////////////////////////////
   public class thread{
    	public int id;
    	public long next_free_time;
    	thread (int id, int t){
    		this.id = id;
    		this.next_free_time = t;
    	}
    	thread (int id){
    		this.id = id;
    		this.next_free_time = 0;
    	}
    	public void addTime(long t) {
    		this.next_free_time += t;
    	}
    }
   ///////////////////////////////////////////////////////////////
}
